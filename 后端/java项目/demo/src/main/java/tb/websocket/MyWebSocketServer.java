package tb.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.*;

import tb.util.myJwt;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.awt.*;
import java.io.IOException;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/test")
public class MyWebSocketServer {

    // 使用ConcurrentHashMap来存储会话和会话ID的映射  
    public static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        try {
            Map<String, List<String>> map2 = session.getRequestParameterMap();
            String sessionId = (map2.get("sessionId")).get(0);
            // 当新的WebSocket连接建立时，将会话ID和会话对象存入映射
            sessions.put(sessionId, session);

            System.out.println("新的连接已建立 sessionId: " + sessionId + "  结束");
            System.out.println("此时sessions.keySet 有"+sessions.keySet()+" 结束");


            String[] s = sessionId.split("_");
            String role = s[0];
            System.out.println("提取出来的role为" + role + " 结束");
            Integer id = Integer.parseInt(s[1]);
            System.out.println("提取出来的id为" + id + " 结束");

            //一建立连接即发送消息
            switch (role) {
                case "CU" -> {
                    //反馈和任务交流
                    String message1 = new FeedbackServiceImpl().getUnreadMessage(id, role);

                    System.out.println("普通用户登录获取未读反馈消息");

                    MyWebSocketServer.sendMessageToSession(sessionId, message1);

                    String message2 = new TalkServiceImpl().getUnreadMessage(id);

                    MyWebSocketServer.sendMessageToSession(sessionId, message2);

                    Map<String, Object> map = new HashMap<>();
                    map.put("cu_id", id);
                    map.put("cu_login", new Timestamp(System.currentTimeMillis()));
                    new CuServiceImpl().update(map);
                }
                case "AD" -> {
                    //内部交流通道
                    String message = new ChatServiceImpl().getUnreadMessage2(role, id);

                    MyWebSocketServer.sendMessageToSession(sessionId, message);

                    Map<String, Object> map = new HashMap<>();
                    map.put("ad_id", id);
                    map.put("ad_login", new Timestamp(System.currentTimeMillis()));
                    new AdServiceImpl().update(map);
                }
                case "AU" -> {
                    String message = new ChatServiceImpl().getUnreadMessage2(role, id);

                    MyWebSocketServer.sendMessageToSession(sessionId, message);

                    Map<String, Object> map = new HashMap<>();
                    map.put("au_id", id);
                    map.put("au_login", new Timestamp(System.currentTimeMillis()));
                    new AuServiceImpl().update(map);
                }
                case "CS" -> {
                    //内部交流通道和反馈

                    String message = new ChatServiceImpl().getUnreadMessage2(role, id);

                    MyWebSocketServer.sendMessageToSession(sessionId, message);


                    String message1 = new FeedbackServiceImpl().getUnreadMessage(id, role);

                    MyWebSocketServer.sendMessageToSession(sessionId, message1);

                    Map<String, Object> map = new HashMap<>();
                    map.put("cs_id", id);
                    map.put("cs_login", new Timestamp(System.currentTimeMillis()));
                    new CsServiceImpl().update(map);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @OnClose
    public void onClose(Session session) {
        try {
            for (String key : sessions.keySet()) {
                if (sessions.get(key) == session) {
                    sessions.remove(key);
                    System.out.println("remove " + key);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        System.out.println("收到的消息为"+message + " 结束");

        Map<String, Object> dataMap = JSON.parseObject(message, new TypeReference<Map<String, Object>>() {
        });


        String type1 = (String) dataMap.get("type");

        String token = null;
        List<Integer> idArray = null;
        myJwt mj = null;
        Map<String, Object> map = null;
        Map<String, Object> mapJson = null;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        Map<String,Object> msg = null;

        if (type1 != null) {
            switch (type1) {

                case "chatIsRead":

                    token = (String) dataMap.get("token");

                    mj = new myJwt(token);

                    jsonArray = (JSONArray) dataMap.get("id");

                    idArray = jsonArray.toJavaObject(new TypeReference<List<Integer>>(){});

                    new ChatServiceImpl().isRead((String) mj.getValue("role"), Integer.parseInt((String) mj.getValue("id")), idArray);
                    break;

                case "chat/send":

                    msg = ((JSONObject)dataMap.get("msg")).toJavaObject(new TypeReference<Map<String,Object>>(){});

                    map = new HashMap<>();
                    map.put("senderSessionId", msg.get("senderSessionId"));
                    map.put("content", msg.get("content"));
                    map.put("contentType", msg.get("contentType"));
                    map.put("timestamp", msg.get("timestamp"));

                    Integer chat_id = new ChatServiceImpl().sendMessage(map);//这里不包含广播给其他人,要返回一个编号

                    //这里广播给其他人

                    map.put("id", chat_id);
                    map.put("type", "chat");
                    map.put("isRead",false);
                    map.put("senderImg",msg.get("senderImg"));
                    map.put("senderName",msg.get("senderName"));

                    jsonObject = new JSONObject(map);

                    mapJson = new HashMap<>();
                    mapJson.put("type", "chat/receive");
                    mapJson.put("msg", jsonObject);


                    for (String id : sessions.keySet()) {
                        if (!id.contains("CU")&&!id.equals(msg.get("senderSessionId"))) {
                            //除了普通用户
                            System.out.println("chat接收者 "+ id);
                            sendMessageToSession(id, JSON.toJSONString(new JSONObject(mapJson), SerializerFeature.WriteMapNullValue));
                        }
                    }

                    break;

                case "feedbackIsRead":

                    token = (String) dataMap.get("token");

                    mj = new myJwt(token);

                    jsonArray = (JSONArray) dataMap.get("id");

                    idArray = jsonArray.toJavaObject(new TypeReference<List<Integer>>(){});

                    new FeedbackServiceImpl().isRead((String) mj.getValue("role"), Integer.parseInt((String) mj.getValue("id")), idArray);
                    break;

                case "feedback/send":

                    msg = ((JSONObject)dataMap.get("msg")).toJavaObject(new TypeReference<Map<String,Object>>(){});


                    map = new HashMap<>();
                    map.put("senderSessionId", msg.get("senderSessionId"));
                    map.put("receiverSessionId", msg.get("receiverSessionId"));
                    map.put("content", msg.get("content"));
                    map.put("contentType", msg.get("contentType"));
                    map.put("timestamp", msg.get("timestamp"));

                    Integer feedback_id = new FeedbackServiceImpl().sendMessage(map);//这里要包含发送消息给另一个人


                    map.put("type", "feedback");
                    map.put("id", feedback_id);
                    map.put("isRead", false);
                    map.put("senderImg",msg.get("senderImg"));
                    map.put("senderName",msg.get("senderName"));

                    jsonObject = new JSONObject(map);

                    mapJson = new HashMap<>();
                    mapJson.put("type", "feedback/receive");
                    mapJson.put("msg", jsonObject);

                    sendMessageToSession((String) msg.get("receiverSessionId"), JSON.toJSONString(new JSONObject(mapJson), SerializerFeature.WriteMapNullValue));

                    break;

                case "talkIsRead":

                    token = (String) dataMap.get("token");

                    mj = new myJwt(token);

                    jsonArray = (JSONArray) dataMap.get("id");

                    idArray = jsonArray.toJavaObject(new TypeReference<List<Integer>>(){});

                    new TalkServiceImpl().isRead(Integer.parseInt((String) mj.getValue("id")), idArray);
                    break;

                case "talk/send":
                    msg = ((JSONObject)dataMap.get("msg")).toJavaObject(new TypeReference<Map<String,Object>>(){});

                    map = new HashMap<>();
                    map.put("senderSessionId", msg.get("senderSessionId"));
                    map.put("receiverSessionId", msg.get("receiverSessionId"));
                    map.put("taskId", msg.get("taskId"));
                    map.put("content", msg.get("content"));
                    map.put("contentType", msg.get("contentType"));
                    map.put("timestamp", msg.get("timestamp"));

                    Integer talk_id = new TalkServiceImpl().sendMessage(map);//这里要包含发送消息给另一个人

                    map.put("type", "talk");
                    map.put("id", talk_id);
                    map.put("isRead", false);
                    map.put("senderImg",msg.get("senderImg"));
                    map.put("senderName",msg.get("senderName"));

                    jsonObject = new JSONObject(map);


                    mapJson = new HashMap<>();
                    mapJson.put("type", "talk/receive");
                    mapJson.put("msg", jsonObject);

                    sendMessageToSession((String) msg.get("receiverSessionId"), JSON.toJSONString(new JSONObject(mapJson), SerializerFeature.WriteMapNullValue));

                    break;

            }
        }

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // 处理错误  
        System.err.println("Error occurred: " + throwable.getMessage());
    }

    // 向指定会话发送消息的方法  
    public static void sendMessageToSession(String sessionId, String message) throws IOException {

        Session session = sessions.get(sessionId);
//        System.out.println("是否打开：" + session.isOpen());
        if (session != null && session.isOpen()) {
            synchronized (session) {//加锁，保证不会同时被执行
                session.getBasicRemote().sendText(message);
            }
        } else {
            System.err.println("Session not found or not open: " + sessionId);
        }

    }

}
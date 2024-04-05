package tb.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.ChatServiceImpl;
import tb.service.Impl.FeedbackServiceImpl;
import tb.service.Impl.TalkServiceImpl;
import tb.util.myJson;
import tb.util.myJwt;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/test")
public class MyWebSocketServer {

    // 使用ConcurrentHashMap来存储会话和会话ID的映射  
    private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        Map<String, List<String>> map = session.getRequestParameterMap();
        String sessionId = ((List<String>)map.get("sessionId")).get(0);
        // 当新的WebSocket连接建立时，将会话ID和会话对象存入映射
        sessions.put(sessionId, session);
        System.out.println("New connection established: " + sessionId);
    }

    @OnClose
    public void onClose(Session session) {
        for(String key : sessions.keySet()){
            if(sessions.get(key)==session){
                sessions.remove(key);
                System.out.println("remove "+key);
                break;
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        Map<String, Object> dataMap = JSON.parseObject(message, new TypeReference<Map<String, Object>>() {});
        List<Map<String,Object>> msg = (List<Map<String, Object>>) dataMap.get("msg");

        String type1 = (String) dataMap.get("type");

        String token = null;
        List<Integer> idArray = null;
        myJwt mj = null;
        Map<String,Object> map = null;
        Map<String,Object> mapJson = null;
        JSONObject jsonObject = null;

        switch (type1){

            case"chatIsRead":

                token = (String)dataMap.get("token");
                mj = new myJwt(token);

                //只需要修改数据库代码
                idArray = (List<Integer>) msg.get(0).get("id");
                new ChatServiceImpl().isRead((String)mj.getValue("role"),(Integer) mj.getValue("id"),idArray);
                break;

            case"chat/send":

                map = new HashMap<>();
                map.put("senderSessionId",msg.get(0).get("senderSessionId"));
                map.put("content",msg.get(0).get("content"));
                map.put("contentType",msg.get(0).get("contentType"));
                map.put("timestamp",msg.get(0).get("timestamp"));

                Integer chat_id = new ChatServiceImpl().sendMessage(map);//这里不包含广播给其他人,要返回一个编号

                //这里广播给其他人

                map.put("id",chat_id);
                map.put("type","chat");

                jsonObject = new JSONObject(map);

                mapJson.put("type","chat/receive");
                mapJson.put("msg",jsonObject);

                for(String id : sessions.keySet()){
                    if(!(id.charAt(0)=='C'&&id.charAt(1)=='U')){
                        //只有普通用户不在群里
                        sendMessageToSession(id,JSON.toJSONString(new JSONObject(mapJson), SerializerFeature.WriteMapNullValue));
                    }
                }

                break;

            case"feedbackIsRead":

                token = (String)dataMap.get("token");
                mj = new myJwt(token);

                idArray = (List<Integer>) msg.get(0).get("id");
                new FeedbackServiceImpl().isRead((String)mj.getValue("role"),(Integer) mj.getValue("id"),idArray);
                break;

            case"feedback/send":
                map = new HashMap<>();
                map.put("senderSessionId",msg.get(0).get("senderSessionId"));
                map.put("receiverSessionId",msg.get(0).get("receiverSessionId"));
                map.put("content",msg.get(0).get("content"));
                map.put("contentType",msg.get(0).get("contentType"));
                map.put("timestamp",msg.get(0).get("timestamp"));

                Integer feedback_id = new FeedbackServiceImpl().sendMessage(map);//这里要包含发送消息给另一个人


                map.put("type","feedback");
                map.put("id",feedback_id);
                map.put("isRead",false);

                jsonObject = new JSONObject(map);

                mapJson.put("type","feedback/receive");
                mapJson.put("msg",jsonObject);

                sendMessageToSession((String) msg.get(0).get("receiverSessionId"),JSON.toJSONString(new JSONObject(mapJson), SerializerFeature.WriteMapNullValue));

                break;

            case"talkIsRead":
                token = (String)dataMap.get("token");
                mj = new myJwt(token);

                idArray = (List<Integer>) msg.get(0).get("id");
                new TalkServiceImpl().isRead((Integer) mj.getValue("id"),idArray);
                break;

            case"talk/send":

                map = new HashMap<>();
                map.put("senderSessionId",msg.get(0).get("senderSessionId"));
                map.put("receiverSessionId",msg.get(0).get("receiverSessionId"));
                map.put("taskId",msg.get(0).get("taskId"));
                map.put("content",msg.get(0).get("content"));
                map.put("contentType",msg.get(0).get("contentType"));
                map.put("timestamp",msg.get(0).get("timestamp"));

                Integer talk_id = new TalkServiceImpl().sendMessage(map);//这里要包含发送消息给另一个人

                map.put("type","talk");
                map.put("id",talk_id);
                map.put("isRead",false);
                jsonObject = new JSONObject(map);


                mapJson.put("type","chat/receive");
                mapJson.put("msg",jsonObject);

                //TODO 3月28日晚

                sendMessageToSession((String) msg.get(0).get("receiverSessionId"),JSON.toJSONString(new JSONObject(mapJson), SerializerFeature.WriteMapNullValue));

                break;

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
        if (session != null && session.isOpen()) {
            session.getBasicRemote().sendText(message);
        } else {
            System.err.println("Session not found or not open: " + sessionId);
        }

    }


}
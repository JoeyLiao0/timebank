package tb.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.ibatis.session.SqlSession;

import tb.dao.AdDao;
import tb.dao.AuDao;
import tb.dao.ChatDao;

import tb.dao.CsDao;
import tb.entity.Ad;
import tb.entity.Au;
import tb.entity.Chat;
import tb.entity.Cs;
import tb.service.ChatService;
import tb.util.mySqlSession;


import java.sql.Timestamp;
import java.util.*;

public class ChatServiceImpl implements ChatService {
    @Override
    public List<Map<String, Object>> getUnreadMessage(String role, Integer id) {
        //基本逻辑按时间顺序获取chat里的全部数据
        //从后往前读，获取最先一个读取的chat_id 上面的自然都是读取的
        //然后再返回
        try (SqlSession session = mySqlSession.getSqSession()) {

            ChatDao chatDao = session.getMapper(ChatDao.class);
            List<Chat> chats = chatDao.selectAllChat();

            List<Map<String, Object>> unreadChats = new ArrayList<>();
            for (Chat chat : chats) {
                if (!chat.getChat_isread().contains(role.toUpperCase() + "_" + id)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", "chat");
                    map.put("id", chat.getChat_id());
                    map.put("senderSessionId", chat.getChat_senderrole().toUpperCase() + "_" + chat.getChat_senderid());
                    map.put("content", chat.getChat_content());
                    map.put("contentType", chat.getChat_contenttype());
                    map.put("timestamp", chat.getChat_timestamp().getTime());
                    map.put("isRead", false);

                    String senderName = "";
                    String senderImg = "";

                    switch (chat.getChat_senderrole()){
                        case "AD":
                            AdDao adDao = session.getMapper(AdDao.class);
                            Ad ad = adDao.SelectAdById(chat.getChat_senderid());
                            if(ad!=null){
                                senderName = ad.getAd_name();
                                senderImg = ad.getAd_img();
                            }

                            break;
                        case "AU":
                            AuDao auDao = session.getMapper(AuDao.class);
                            Au au = auDao.SelectAuById(chat.getChat_senderid());
                            if(au!=null){
                                senderName = au.getAu_name();
                                senderImg = au.getAu_img();
                            }

                            break;
                        case "CS":
                            CsDao csDao = session.getMapper(CsDao.class);
                            Cs cs = csDao.SelectCsById(chat.getChat_senderid());
                            if(cs!=null){
                                senderName = cs.getCs_name();
                                senderImg = cs.getCs_img();
                            }

                            break;

                    }
                    map.put("senderName",senderName);
                    map.put("senderImg",senderImg);

                    unreadChats.add(map);
                }
            }

            return unreadChats;

        } catch (Exception e) {
            return null;
        }

    }

    public String getUnreadMessage2(String role, Integer id) {
        //获取内部交流通道未读消息

        List<Map<String, Object>> chatArray = getUnreadMessage(role, id);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = (JSONArray) JSON.toJSON(chatArray);
        jsonObject.put("type", "chat/unread");
        jsonObject.put("msg", jsonArray);
        return JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);

    }

    @Override
    public String isRead(String role, Integer id, List<Integer> ids) {
        //读取了，在这些chat_id 对应的chat_isread 里添加 空格sessionId
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                ChatDao chatDao = session.getMapper(ChatDao.class);

                for (Integer chat_id : ids) {
                    Chat chat = chatDao.selectChatById(chat_id);
                    if(chat != null){
                        chatDao.updateChatIsRead(chat_id, chat.getChat_isread() + " " + role.toUpperCase() + "_" + id);
                    }
                }
                session.commit();
                return null;
            } catch (Exception e) {
                if (session != null) {
                    session.rollback();
                }
                return "设置状态失败 " + e.getMessage();
            }
        }
    }

    @Override
    public List<Map<String, Object>> getHistory(String role, Integer id) {
        //获取全部历史消息，暂时设定为全部历史消息获取
        try (SqlSession session = mySqlSession.getSqSession()) {

            ChatDao chatDao = session.getMapper(ChatDao.class);
            List<Chat> chats = chatDao.selectAllChat();

            List<Map<String, Object>> unreadChats = new ArrayList<>();
            for (Chat chat : chats) {
                if (chat.getChat_isread().contains(role.toUpperCase() + "_" + id)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", "chat");
                    map.put("id", chat.getChat_id());
                    map.put("senderSessionId", chat.getChat_senderrole().toUpperCase() + "_" + chat.getChat_senderid());
                    map.put("content", chat.getChat_content());
                    map.put("contentType", chat.getChat_contenttype());
                    map.put("timestamp", chat.getChat_timestamp().getTime());
                    map.put("isRead", true);

                    String senderName = "";
                    String senderImg = "";

                    switch (chat.getChat_senderrole()){
                        case "AD":
                            AdDao adDao = session.getMapper(AdDao.class);
                            Ad ad = adDao.SelectAdById(chat.getChat_senderid());
                            if(ad!=null){
                                senderName = ad.getAd_name();
                                senderImg = ad.getAd_img();
                            }

                            break;
                        case "AU":
                            AuDao auDao = session.getMapper(AuDao.class);
                            Au au = auDao.SelectAuById(chat.getChat_senderid());

                            if(au!=null){
                                senderName = au.getAu_name();
                                senderImg = au.getAu_img();
                            }

                            break;
                        case "CS":
                            CsDao csDao = session.getMapper(CsDao.class);
                            Cs cs = csDao.SelectCsById(chat.getChat_senderid());

                            if(cs!=null){
                                senderName = cs.getCs_name();
                                senderImg = cs.getCs_img();
                            }

                            break;

                    }
                    map.put("senderName",senderName);
                    map.put("senderImg",senderImg);

                    unreadChats.add(map);

                }
            }
            return unreadChats;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Integer sendMessage(Map<String, Object> datamap) {
        //就是把这个消息存储在数据库就行了，其他操作在上层已经添加转发了

        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                ChatDao chatDao = session.getMapper(ChatDao.class);

                Chat chat = new Chat();
                String senderSessionId = (String) datamap.get("senderSessionId");
                String[] info = (senderSessionId.split("_"));
                chat.setChat_senderrole(info[0]);
                chat.setChat_senderid(Integer.parseInt(info[1]));
                chat.setChat_content((String) datamap.get("content"));
                chat.setChat_contenttype((String) datamap.get("contentType"));
                chat.setChat_timestamp(new Timestamp((long) datamap.get("timestamp")));
                chat.setChat_isread(senderSessionId);

                chatDao.insertChat(chat);

                session.commit();

                return chat.getChat_id();

            } catch (Exception e) {
                if (session != null) {
                    session.rollback();
                }
            }
        }

        return -1;
    }
}

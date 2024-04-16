package tb.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.ibatis.session.SqlSession;
import tb.dao.CuDao;
import tb.dao.TalkDao;

import tb.entity.Cu;
import tb.entity.Talk;

import tb.service.TalkService;

import tb.util.mySqlSession;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TalkServiceImpl implements TalkService {
    @Override
    public List<Map<String, Object>> getUnreadMessage(Integer cu_id, Integer task_id) {
        //这里只包含指定任务的未读消息
        try (SqlSession session = mySqlSession.getSqSession()) {

            TalkDao talkDao = session.getMapper(TalkDao.class);

            List<Talk> talks = talkDao.selectByTaskId(task_id);

            List<Map<String, Object>> unreadTalks = new ArrayList<>();

            CuDao cuDao = session.getMapper(CuDao.class);

            for (Talk talk : talks) {
                if (!talk.getTalk_isread().contains("CU_" + cu_id)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", "talk");
                    map.put("id", talk.getTalk_id());
                    map.put("taskId", talk.getTalk_taskid());
                    map.put("senderSessionId", "CU_" + talk.getTalk_senderid());
                    map.put("receiverSessionId","CU_" + talk.getTalk_receiverid());
                    map.put("content", talk.getTalk_content());
                    map.put("contentType", talk.getTalk_contenttype());
                    map.put("timestamp", talk.getTalk_timestamp().getTime());
                    map.put("isRead", false);

                    Cu sender =cuDao.SelectCuById(talk.getTalk_senderid());
//                    Cu receiver = cuDao.SelectCuById(talk.getTalk_receiverid());

                    if(sender!=null){
                        map.put("senderName",sender.getCu_name());
                        map.put("senderImg",sender.getCu_img());
                    }else{
                        map.put("senderName","该用户已注销");
                        map.put("senderImg",null);
                    }

//                    map.put("receiverName",receiver.getCu_name());
//                    map.put("receiverImg",receiver.getCu_img());

                    unreadTalks.add(map);
                }
            }

            return unreadTalks;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getUnreadMessage(Integer id) {
        //这里包含全部任务的未读消息

        TaskServiceImpl taskServiceImpl = new TaskServiceImpl();
        List<Map<String, Object>> publishTask = taskServiceImpl.selectMyPublish(id);
        List<Map<String, Object>> takeTask = taskServiceImpl.selectMyTake(id);
        List<Integer> task_ids = new ArrayList<>();

        for (Map<String, Object> map : publishTask) {
            Integer task_id = (Integer) map.get("task_id");
            task_ids.add(task_id);
        }
        for (Map<String, Object> map : takeTask) {
            Integer task_id = (Integer) map.get("task_id");
            task_ids.add(task_id);
        }

        List<Map<String, Object>> talkArray = new ArrayList<>();
        for (Integer task_id : task_ids) {
            List<Map<String, Object>> talks = getUnreadMessage(id, task_id);
            talkArray.addAll(talks);
        }

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = (JSONArray) JSON.toJSON(talkArray);
        jsonObject.put("type", "talk/unread");
        jsonObject.put("msg", jsonArray);

        return JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    @Override
    public String isRead(Integer cu_id, List<Integer> ids) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                TalkDao talkDao = session.getMapper(TalkDao.class);

                for (Integer talk_id : ids) {
                    Talk talk = talkDao.selectByTalkId(talk_id);
                    talkDao.updateTalkIsRead(talk_id, talk.getTalk_isread() + " CU_" + cu_id);
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
    public List<Map<String, Object>> getHistory(Integer cu_id, Integer task_id) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            TalkDao talkDao = session.getMapper(TalkDao.class);

            CuDao cuDao = session.getMapper(CuDao.class);

            List<Talk> talks = talkDao.selectByTaskId(task_id);

            List<Map<String, Object>> unreadTalks = new ArrayList<>();

            for (Talk talk : talks) {
                if (talk!=null&&talk.getTalk_isread().contains("CU_" + cu_id)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", "talk");
                    map.put("id", talk.getTalk_id());
                    map.put("taskId", talk.getTalk_taskid());
                    map.put("senderSessionId", "CU_" + talk.getTalk_senderid());
                    map.put("content", talk.getTalk_content());
                    map.put("contentType", talk.getTalk_contenttype());
                    map.put("timestamp", talk.getTalk_timestamp().getTime());
                    map.put("isRead", true);
                    Cu sender =cuDao.SelectCuById(talk.getTalk_senderid());

                    if(sender!=null){
                        map.put("senderName",sender.getCu_name());
                        map.put("senderImg",sender.getCu_img());
                    }else{
                        map.put("senderName","该用户已注销");
                        map.put("senderImg",null);
                    }
                    unreadTalks.add(map);
                }
            }

            return unreadTalks;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer sendMessage(Map<String, Object> datamap) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                TalkDao talkDao = session.getMapper(TalkDao.class);

                Talk talk = new Talk();
                String senderSessionId = (String) datamap.get("senderSessionId");
                String receiverSessionId = (String) datamap.get("receiverSessionId");

                String[] info1 = (senderSessionId.split("_"));
                String[] info2 = (receiverSessionId.split("_"));

                talk.setTalk_senderid(Integer.parseInt(info1[1]));
                talk.setTalk_receiverid(Integer.parseInt(info2[1]));


                talk.setTalk_taskid((Integer)datamap.get("taskId"));
                talk.setTalk_content((String) datamap.get("content"));
                talk.setTalk_contenttype((String) datamap.get("contentType"));
                talk.setTalk_timestamp(new Timestamp((long) datamap.get("timestamp")));
                talk.setTalk_isread(senderSessionId);

                talkDao.insertTalk(talk);

                session.commit();

                return talk.getTalk_id();

            } catch (Exception e) {
                if (session != null) {
                    session.rollback();
                }
            }
        }

        return -1;
    }


}

package tb.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.ibatis.session.SqlSession;
import tb.dao.CsDao;
import tb.dao.CuDao;
import tb.dao.FeedbackDao;

import tb.entity.Cs;
import tb.entity.Cu;
import tb.entity.Feedback;

import tb.service.FeedbackService;
import tb.util.mySqlSession;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackServiceImpl implements FeedbackService {
    @Override
    public List<Map<String, Object>> getUnreadMessage(String role, Integer cu_id, Integer cs_id) {
        //一个用户只有一个反馈界面，在用户表多加一个分配的客服id即可
        try (SqlSession session = mySqlSession.getSqSession()) {

            String SessionId;
            if (role.equals("CU")) {
                SessionId = "CU_" + cu_id;
            } else {
                SessionId = "CS_" + cs_id;
            }

            CuDao cuDao = session.getMapper(CuDao.class);
            Cu cu = cuDao.SelectCuById(cu_id);

            String cu_name = null;
            String cu_img = null;

            if(cu!=null){
                cu_name = cu.getCu_name();
                cu_img = cu.getCu_img();
            }

            String cs_name = null;
            String cs_img = null;

            CsDao csDao = session.getMapper(CsDao.class);
            Cs cs = csDao.SelectCsById(cs_id);

            if(cs!=null){
                cs_name = cs.getCs_name();
                cs_img = cs.getCs_img();
            }



            FeedbackDao feedbackDao = session.getMapper(FeedbackDao.class);

            //通过客服id和用户id找到对应的feedback
            List<Feedback> list1 = feedbackDao.selectByIdAndRole("CU", cu_id, "CS", cs_id);
            List<Feedback> list2 = feedbackDao.selectByIdAndRole("CS", cs_id, "CU", cu_id);

            List<Map<String, Object>> feedbacks = new ArrayList<>();
            int i = 0, j = 0;

            if (list1.isEmpty() && !list2.isEmpty()) {
                for (Feedback feedback : list2) {
                    if (!feedback.getFeedback_isread().contains(SessionId)) {
                        Map<String, Object> map = new HashMap<>();
                        turnFeedbackToMap(feedback, map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        feedbacks.add(map);
                    }

                }
            }
            if (!list1.isEmpty() && list2.isEmpty()) {
                for (Feedback feedback : list1) {
                    if (!feedback.getFeedback_isread().contains(SessionId)) {
                        Map<String, Object> map = new HashMap<>();
                        turnFeedbackToMap(feedback, map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        feedbacks.add(map);
                    }
                }
            }
            while (!list1.isEmpty() && !list2.isEmpty()) {
                Map<String, Object> map = new HashMap<>();
                if (i == list1.size() && j == list2.size()) {
                    break;
                } else if (i == list1.size()) {
                    if (!list2.get(j).getFeedback_isread().contains(SessionId)) {
                        turnFeedbackToMap(list2.get(j), map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        feedbacks.add(map);
                    }
                    j++;
                } else if (j == list2.size()) {
                    if (!list1.get(i).getFeedback_isread().contains(SessionId)) {
                        turnFeedbackToMap(list1.get(i), map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        feedbacks.add(map);
                    }
                    i++;
                } else if (list1.get(i).getFeedback_timestamp().before(list2.get(j).getFeedback_timestamp())) {
                    if (!list1.get(i).getFeedback_isread().contains(SessionId)) {
                        turnFeedbackToMap(list1.get(i), map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        feedbacks.add(map);
                    }
                    i++;
                } else {
                    if (!list2.get(j).getFeedback_isread().contains(SessionId)) {
                        turnFeedbackToMap(list2.get(j), map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        feedbacks.add(map);
                    }
                    j++;
                }
            }
            return feedbacks;


        } catch (Exception e) {
            return null;
        }


    }

    public String getUnreadMessage(Integer id, String role) {
        //获取反馈未读消息

        //两种
        if (role.equals("CU")) {
            //普通用户
            Integer cs_id = new RelationServiceImpl().getCs_id(id);
            List<Map<String, Object>> feedbackArray = getUnreadMessage("CU", id, cs_id);//cs_id 唯一
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = (JSONArray) JSON.toJSON(feedbackArray);
            jsonObject.put("type", "feedback/unread");
            jsonObject.put("msg", jsonArray);
            return JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
        } else if (role.equals("CS")) {
            //客服
            List<Integer> cu_ids = new RelationServiceImpl().getCu_id(id);

            List<Map<String, Object>> feedbackArray = new ArrayList<>();
            for (Integer cu_id : cu_ids) {
                List<Map<String, Object>> feedback = getUnreadMessage("CS", cu_id, id);//cs_id 唯一
                feedbackArray.addAll(feedback);
            }
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = (JSONArray) JSON.toJSON(feedbackArray);
            jsonObject.put("type", "feedback/unread");
            jsonObject.put("msg", jsonArray);
            return JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
        }

        return null;

    }


    private void turnFeedbackToMap(Feedback feedback, Map<String, Object> map) {
        map.put("type", "feedback");
        map.put("id", feedback.getFeedback_id());
        map.put("senderSessionId", feedback.getFeedback_senderrole().toUpperCase() + "_" + feedback.getFeedback_senderid());
        map.put("content", feedback.getFeedback_content());
        map.put("contentType", feedback.getFeedback_contenttype());
        map.put("timestamp", feedback.getFeedback_timestamp().getTime());
        map.put("isRead", false);

    }

    @Override
    public String isRead(String role, Integer id, List<Integer> ids) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                FeedbackDao feedbackDao = session.getMapper(FeedbackDao.class);

                for (Integer feedback_id : ids) {
                    Feedback feedback = feedbackDao.selectFeedbackById(feedback_id);
                    feedbackDao.updateFeedbackIsRead(feedback_id, feedback.getFeedback_isread() + " " + role.toUpperCase() + "_" + id);
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
    public List<Map<String, Object>> getHistory(String role, Integer cu_id, Integer cs_id) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            String SessionId;
            if (role.equals("CU")) {
                SessionId = "CU_" + cu_id;
            } else {
                SessionId = "CS_" + cs_id;
            }

            CuDao cuDao = session.getMapper(CuDao.class);
            Cu cu = cuDao.SelectCuById(cu_id);

            String cu_name = null;
            String cu_img = null;

            if(cu!=null){
                cu_name = cu.getCu_name();
                cu_img = cu.getCu_img();
            }
            String cs_name = null;
            String cs_img = null;

            CsDao csDao = session.getMapper(CsDao.class);
            Cs cs = csDao.SelectCsById(cs_id);

            if(cs!=null){
                cs_name = cs.getCs_name();
                cs_img = cs.getCs_img();
            }

            FeedbackDao feedbackDao = session.getMapper(FeedbackDao.class);

            //通过客服id和用户id找到对应的feedback
            List<Feedback> list1 = feedbackDao.selectByIdAndRole("CU", cu_id, "CS", cs_id);
            List<Feedback> list2 = feedbackDao.selectByIdAndRole("CS", cs_id, "CU", cu_id);

            List<Map<String, Object>> feedbacks = new ArrayList<>();
            int i = 0, j = 0;

            if (list1.isEmpty() && !list2.isEmpty()) {
                for (Feedback feedback : list2) {
                    if (feedback.getFeedback_isread().contains(SessionId)) {
                        Map<String, Object> map = new HashMap<>();
                        turnFeedbackToMap(feedback, map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        map.replace("isRead",true);
                        feedbacks.add(map);
                    }

                }
            }
            if (!list1.isEmpty() && list2.isEmpty()) {
                for (Feedback feedback : list1) {
                    if (feedback.getFeedback_isread().contains(SessionId)) {
                        Map<String, Object> map = new HashMap<>();
                        turnFeedbackToMap(feedback, map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        map.replace("isRead",true);
                        feedbacks.add(map);
                    }
                }
            }
            while (!list1.isEmpty() && !list2.isEmpty()) {
                Map<String, Object> map = new HashMap<>();
                if (i == list1.size() && j == list2.size()) {
                    break;
                } else if (i == list1.size()) {
                    if (list2.get(j).getFeedback_isread().contains(SessionId)) {
                        turnFeedbackToMap(list2.get(j), map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        map.replace("isRead",true);
                        feedbacks.add(map);
                    }
                    j++;
                } else if (j == list2.size()) {
                    if (list1.get(i).getFeedback_isread().contains(SessionId)) {
                        turnFeedbackToMap(list1.get(i), map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        map.replace("isRead",true);
                        feedbacks.add(map);
                    }
                    i++;
                } else if (list1.get(i).getFeedback_timestamp().before(list2.get(j).getFeedback_timestamp())) {
                    if (list1.get(i).getFeedback_isread().contains(SessionId)) {
                        turnFeedbackToMap(list1.get(i), map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        map.replace("isRead",true);
                        feedbacks.add(map);
                    }
                    i++;
                } else {
                    if (list2.get(j).getFeedback_isread().contains(SessionId)) {
                        turnFeedbackToMap(list2.get(j), map);
                        String senderSessionId = (String) map.get("senderSessionId");
                        if(senderSessionId.contains("CU")) {
                            map.put("senderName", cu_name);
                            map.put("senderImg", cu_img);
                        }else if(senderSessionId.contains("CS")){
                            map.put("senderName", cs_name);
                            map.put("senderImg", cs_img);
                        }
                        map.replace("isRead",true);
                        feedbacks.add(map);
                    }
                    j++;
                }
            }
            return feedbacks;


        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer sendMessage(Map<String, Object> datamap) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                FeedbackDao feedbackDao = session.getMapper(FeedbackDao.class);

                Feedback feedback = new Feedback();
                String senderSessionId = (String) datamap.get("senderSessionId");
                String receiverSessionId = (String) datamap.get("receiverSessionId");

                String[] info1 = senderSessionId.split("_");
                String[] info2 = receiverSessionId.split("_");

                feedback.setFeedback_receiverrole(info2[0]);
                feedback.setFeedback_receiverid(Integer.parseInt(info2[1]));

                feedback.setFeedback_senderrole(info1[0]);
                feedback.setFeedback_senderid(Integer.parseInt(info1[1]));

                feedback.setFeedback_content((String) datamap.get("content"));
                feedback.setFeedback_contenttype((String) datamap.get("contentType"));
                feedback.setFeedback_timestamp(new Timestamp((long) datamap.get("timestamp")));
                feedback.setFeedback_isread(senderSessionId);

                feedbackDao.insertFeedback(feedback);

                session.commit();

                return feedback.getFeedback_id();

            } catch (Exception e) {
                if (session != null) {
                    session.rollback();
                }
            }
        }

        return -1;
    }
}

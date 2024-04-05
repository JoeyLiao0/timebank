package tb.service.Impl;

import org.apache.ibatis.session.SqlSession;
import tb.dao.TalkDao;
import tb.dao.TalkDao;
import tb.dao.TalkDao;
import tb.entity.Talk;
import tb.entity.Talk;
import tb.entity.Talk;
import tb.service.TalkService;
import tb.service.TaskService;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TalkServiceImpl implements TalkService{
    @Override
    public List<Map<String, Object>> getUnreadMessage(Integer cu_id, Integer task_id) {
        try(SqlSession session = mySqlSession.getSqSession()){

            TalkDao talkDao = session.getMapper(TalkDao.class);

            List<Talk> talks = talkDao.selectByTaskId(task_id);

            List<Map<String,Object>> unreadTalks = new ArrayList<>();
            
            for(Talk talk : talks){
                if(!talk.getTalk_isread().contains("CU_"+cu_id)){
                    Map<String,Object> map = new HashMap<>();
                    map.put("type","talk");
                    map.put("id",talk.getTalk_id());
                    map.put("taskId",talk.getTalk_taskid());
                    map.put("senderSessionId","CU_"+talk.getTalk_senderid());
                    map.put("content",talk.getTalk_content());
                    map.put("contentType",talk.getTalk_contenttype());
                    map.put("timestamp",talk.getTalk_timestamp().getTime()*1000);
                    map.put("isRead",false);

                    unreadTalks.add(map);
                }
            }

            return unreadTalks;

        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String isRead(Integer cu_id, List<Integer> ids) {
        try(SqlSession session = mySqlSession.getSqSession()){
            try{
                TalkDao talkDao = session.getMapper(TalkDao.class);

                for(Integer talk_id : ids){
                    Talk talk = talkDao.selectByTalkId(talk_id);
                    talkDao.updateTalkIsRead(talk_id,talk.getTalk_isread()+" CU+"+cu_id);
                }
                session.commit();
                return null;
            }catch (Exception e){
                if(session!=null){
                    session.rollback();
                }
                return "设置状态失败 "+e.getMessage();
            }
        }
    }

    @Override
    public List<Map<String, Object>> getHistory(Integer cu_id, Integer task_id) {
        try(SqlSession session = mySqlSession.getSqSession()){

            TalkDao talkDao = session.getMapper(TalkDao.class);

            List<Talk> talks = talkDao.selectByTaskId(task_id);

            List<Map<String,Object>> unreadTalks = new ArrayList<>();

            for(Talk talk : talks){
                if(talk.getTalk_isread().contains("CU_"+cu_id)){
                    Map<String,Object> map = new HashMap<>();
                    map.put("type","talk");
                    map.put("id",talk.getTalk_id());
                    map.put("taskId",talk.getTalk_taskid());
                    map.put("senderSessionId","CU_"+talk.getTalk_senderid());
                    map.put("content",talk.getTalk_content());
                    map.put("contentType",talk.getTalk_contenttype());
                    map.put("timestamp",talk.getTalk_timestamp().getTime()*1000);
                    map.put("isRead",false);

                    unreadTalks.add(map);
                }
            }

            return unreadTalks;

        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Integer sendMessage(Map<String, Object> datamap) {
        try(SqlSession session = mySqlSession.getSqSession()){
            try{
                TalkDao talkDao = session.getMapper(TalkDao.class);

                Talk talk = new Talk();
                String senderSessionId = (String) datamap.get("senderSessionId");
                String receiverSessionId = (String) datamap.get("receiverSessionId");

                String[] info1 = (senderSessionId.split("_"));
                String[] info2 = (receiverSessionId.split("_"));

                talk.setTalk_senderid(Integer.getInteger(info1[1]));
                talk.setTalk_receiverid(Integer.getInteger(info2[1]));


                talk.setTalk_content((String) datamap.get("content"));
                talk.setTalk_contenttype((String)datamap.get("contentType"));
                talk.setTalk_timestamp(new Timestamp((long)datamap.get("timestamp")*1000));
                talk.setTalk_isread(senderSessionId);

                talkDao.insertTalk(talk);

                session.commit();

                Integer talk_id = talk.getTalk_id();

                return talk_id;

            }catch (Exception e){
                if(session!=null){
                    session.rollback();
                }
            }
        }

        return -1;
    }
}

package DAO;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tb.dao.TalkDao;
import tb.entity.Talk;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TalkDAOTest {
    @Test
    public void TestSelectByTalkId(){
        try(SqlSession session = mySqlSession.getSqSession()){
            TalkDao talkDao = session.getMapper(TalkDao.class);

            Talk talk = talkDao.selectByTalkId(1);
            System.out.println(talk.getTalk_id());
            System.out.println(talk.getTalk_taskid());
            System.out.println(talk.getTalk_timestamp());
            System.out.println(talk.getTalk_senderid());
            System.out.println(talk.getTalk_content());
            System.out.println(talk.getTalk_isread());
            System.out.println(talk.getTalk_contenttype());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectByTaskId(){
        try(SqlSession session = mySqlSession.getSqSession()){
            TalkDao talkDao = session.getMapper(TalkDao.class);

            ArrayList<Talk> talks = (ArrayList<Talk>) talkDao.selectByTaskId(1);
            for(Talk talk : talks){
                System.out.println(talk.getTalk_id());
                System.out.println(talk.getTalk_taskid());
                System.out.println(talk.getTalk_timestamp());
                System.out.println(talk.getTalk_senderid());
                System.out.println(talk.getTalk_content());
                System.out.println(talk.getTalk_isread());
                System.out.println(talk.getTalk_contenttype());
                System.out.println();
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestInsertTalk(){
        try(SqlSession session = mySqlSession.getSqSession()){
            try{
                TalkDao talkDao = session.getMapper(TalkDao.class);

                Talk talk = new Talk();
                talk.setTalk_content("内容人");
                talk.setTalk_contenttype("text");
                talk.setTalk_senderid(1);
                talk.setTalk_receiverid(2);
                talk.setTalk_taskid(1);
                talk.setTalk_timestamp(new Timestamp(System.currentTimeMillis()));
                talk.setTalk_isread("CU_1 CU_2");

                talkDao.insertTalk(talk);
                session.commit();


            }catch (Exception e){
                session.rollback();
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    public void TestDeleteTalkById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            TalkDao talkDao = session.getMapper(TalkDao.class);

            talkDao.deleteTalkById(1);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestUpdateTalkIsRead(){
        try(SqlSession session = mySqlSession.getSqSession()){
            TalkDao talkDao = session.getMapper(TalkDao.class);

            talkDao.updateTalkIsRead(1,"CU_1");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}

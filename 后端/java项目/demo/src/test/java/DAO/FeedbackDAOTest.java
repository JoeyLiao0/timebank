package DAO;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tb.dao.AdDao;
import tb.dao.FeedbackDao;
import tb.entity.Ad;
import tb.entity.Feedback;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.ArrayList;

public class FeedbackDAOTest {

    @Test
    public void TestInsertFeedback(){
        try(SqlSession session = mySqlSession.getSqSession())
        {
            try{
                FeedbackDao feedbackDao = session.getMapper(FeedbackDao.class);

                Feedback feedback = new Feedback();

                feedback.setFeedback_content("nasdfdfas");
                feedback.setFeedback_contenttype("text");
                feedback.setFeedback_isread("CU_1 CS_1");

                feedback.setFeedback_receiverid(1);
                feedback.setFeedback_receiverrole("CS");

                feedback.setFeedback_senderid(1);
                feedback.setFeedback_senderrole("CU");

                feedback.setFeedback_timestamp(new Timestamp(System.currentTimeMillis()));

                feedbackDao.insertFeedback(feedback);

                session.commit();

            }catch (Exception e){
                session.rollback();
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    public void TestSelectFeedbackById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            FeedbackDao feedbackDao = session.getMapper(FeedbackDao.class);

            Feedback feedback = feedbackDao.selectFeedbackById(1);

            System.out.println(feedback);

            session.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestDeleteFeedbackById(){

        try(SqlSession session = mySqlSession.getSqSession()){
            try{
                FeedbackDao feedbackDao = session.getMapper(FeedbackDao.class);

                feedbackDao.deleteFeedbackById(1);

                session.commit();
            } catch (Exception e){
                session.rollback();
                System.out.println(e.getMessage());
            }
        }

    }



    @Test
    public void TestSelectByIdAndRole(){
        try(SqlSession session = mySqlSession.getSqSession()){
            FeedbackDao feedbackDao = session.getMapper(FeedbackDao.class);

            ArrayList<Feedback> feedbacks = (ArrayList<Feedback>) feedbackDao.selectByIdAndRole("CU",1,"CS",1);

            for(Feedback feedback : feedbacks){
                System.out.println(feedback);
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestUpdateFeedbackIsRead(){

        try(SqlSession session = mySqlSession.getSqSession()){
            try{
                FeedbackDao feedbackDao = session.getMapper(FeedbackDao.class);

                feedbackDao.updateFeedbackIsRead(1,"111");

                session.commit();
            }catch (Exception e){
                session.rollback();
                System.out.println(e.getMessage());
            }

        }
    }
}

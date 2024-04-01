package DAO;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tb.dao.AdDao;
import tb.dao.ChatDao;
import tb.entity.Ad;
import tb.entity.Chat;
import tb.util.mySqlSession;

import javax.websocket.Session;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ChatDAOTest {
    @Test
    public void TestSelectChatById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            ChatDao chatDao = session.getMapper(ChatDao.class);

            Chat chat = chatDao.selectChatById(3);

            System.out.println(chat.getChat_content());
            System.out.println(chat.getChat_contenttype());
            System.out.println(chat.getChat_isread());
            System.out.println(chat.getChat_senderid());
            System.out.println(chat.getChat_senderrole());
            System.out.println(chat.getChat_timestamp());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectBySenderIdAndSenderRole(){
        try(SqlSession session = mySqlSession.getSqSession()){
            ChatDao chatDao = session.getMapper(ChatDao.class);

            ArrayList<Chat> chats = (ArrayList<Chat>) chatDao.selectBySenderIdAndSenderRole(2,"CU");

            for(Chat chat : chats){
                System.out.println(chat.getChat_id());
                System.out.println(chat.getChat_content());
                System.out.println(chat.getChat_contenttype());
                System.out.println(chat.getChat_isread());
                System.out.println(chat.getChat_senderid());
                System.out.println(chat.getChat_senderrole());
                System.out.println(chat.getChat_timestamp());
                System.out.println();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestInsertChat(){
        try(SqlSession session = mySqlSession.getSqSession()){
            try{
                ChatDao chatDao = session.getMapper(ChatDao.class);

                Chat chat = new Chat();
                chat.setChat_content("内容");
                chat.setChat_contenttype("text");
                chat.setChat_isread("");
                chat.setChat_timestamp(new Timestamp(System.currentTimeMillis()));
                chat.setChat_senderid(2);
                chat.setChat_senderrole("CU");

                chatDao.insertChat(chat);
                session.commit();

            }catch (Exception e){
                session.rollback();
                System.out.println(e.getMessage());
            }
        }

    }

    @Test
    public void TestDeleteChatById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            try{
                ChatDao chatDao = session.getMapper(ChatDao.class);


                chatDao.deleteChatById(2);

                session.commit();
            }catch (Exception e){
                session.rollback();
                System.out.println(e.getMessage());
            }
        }
    }


    @Test
    public void TestUpdateChatIsRead(){
        try(SqlSession session = mySqlSession.getSqSession()){
            try{
                ChatDao chatDao = session.getMapper(ChatDao.class);

                chatDao.updateChatIsRead(3,"CS_1 CU_2");

                session.commit();

            }catch (Exception e){
                session.rollback();
                System.out.println(e.getMessage());
            }
        }
    }


}

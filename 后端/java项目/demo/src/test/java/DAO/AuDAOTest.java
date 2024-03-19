package DAO;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tb.dao.AuDao;
import tb.entity.Au;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    List<Au> SelectAuByMap(Map<String,Object> map);

    Au SelectAuById(Integer id);

    Au SelectAuByName(String name);

    void InsertAu(@Param("au") Au au);

    void DeleteAuById(Integer id);

    void UpdateAu(@Param("au") Au au);//调用时根据au 里的 au_id属性来锁定
*/
public class AuDAOTest {
    @Test
    public void TestInsertAu(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AuDao auDao = session.getMapper(AuDao.class);

            Au au = new Au();
            au.setAu_name("test_name_new");//账号
            au.setAu_pwd("test_pwd");//密码
            au.setAu_salt("test_salt3");//盐
            au.setAu_status(1);//状态
            au.setAu_tel("test_tel3");//手机号
            au.setAu_register(new Timestamp(System.currentTimeMillis()-1800*1000));//注册时间

            auDao.InsertAu(au);

            session.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    @Test
    public void TestSelectAuByMap(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AuDao auDao = session.getMapper(AuDao.class);

            Map<String, Object> DataMap = new HashMap<>();
            DataMap.put("name", "test_name");
            DataMap.put("tel", "test_tel2");
            DataMap.put("registerBegin", new Timestamp(System.currentTimeMillis()-3600*1000));
            DataMap.put("registerEnd", new Timestamp(System.currentTimeMillis()+3600*1000));
            DataMap.put("loginBegin", new Timestamp(System.currentTimeMillis()-7200*1000));
            DataMap.put("loginEnd", new Timestamp(System.currentTimeMillis()+7200*1000));
            DataMap.put("userStatus", 1);

            List<Au> aus = auDao.SelectAuByMap(DataMap);

            for(Au au : aus){
                System.out.println(au.getAu_name());
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectAuById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AuDao auDao = session.getMapper(AuDao.class);

            Integer id = 3;
            Au au = auDao.SelectAuById(id);

            System.out.println(au);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectAuByName(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AuDao auDao = session.getMapper(AuDao.class);

            String name = "test_name_new";
            Au au = auDao.SelectAuByName(name);

            System.out.println(au);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestDeleteById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AuDao auDao = session.getMapper(AuDao.class);

            Integer id = 2;
            auDao.DeleteAuById(id);

            session.commit();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestUpdateAu(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AuDao auDao = session.getMapper(AuDao.class);

            Au au = auDao.SelectAuById(1);

            au.setAu_status(1);
            au.setAu_login(new Timestamp(System.currentTimeMillis()));
            au.setAu_salt("test_salt_new2");
            au.setAu_pwd("test_pwd_new2");
            au.setAu_img("test_img");
            au.setAu_name("test_name_new2");
            au.setAu_unblocktime(new Timestamp(System.currentTimeMillis()+3600*1000));

            auDao.UpdateAu(au);

            session.commit();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

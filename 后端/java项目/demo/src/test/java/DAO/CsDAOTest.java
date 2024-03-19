package DAO;

import com.mysql.cj.Session;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tb.dao.CsDao;
import tb.entity.Cs;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    List<Cs> SelectCsByMap(Map<String,Object> map);

    Cs SelectCsById(Integer id);

    Cs SelectCsByName(String name);

    void InsertCs(@Param("cs") Cs cs);

    void DeleteCsById(Integer id);

    void UpdateCs(@Param("cs") Cs cs);//调用时根据cs 里的 cs_id属性来锁定
*/
public class CsDAOTest {
    @Test
    public void TestInsertCs(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CsDao csDao = session.getMapper(CsDao.class);

            Cs cs = new Cs();
            cs.setCs_name("test_name3");//账号
            cs.setCs_pwd("test_pwd3");//密码
            cs.setCs_salt("test_salt3");//盐
            cs.setCs_status(1);//状态
            cs.setCs_tel("test_tel3");//手机号
            cs.setCs_register(new Timestamp(System.currentTimeMillis()-1800*1000));//注册时间

            csDao.InsertCs(cs);

            session.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    @Test
    public void TestSelectCsByMap(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CsDao csDao = session.getMapper(CsDao.class);

            Map<String, Object> DataMap = new HashMap<>();
            DataMap.put("name", "test_name");
            DataMap.put("tel", "test_tel2");
            DataMap.put("registerBegin", new Timestamp(System.currentTimeMillis()-3600*1000));
            DataMap.put("registerEnd", new Timestamp(System.currentTimeMillis()+3600*1000));
            DataMap.put("loginBegin", new Timestamp(System.currentTimeMillis()-7200*1000));
            DataMap.put("loginEnd", new Timestamp(System.currentTimeMillis()+7200*1000));
            DataMap.put("userStatus", 1);

            List<Cs> css = csDao.SelectCsByMap(DataMap);

            for(Cs cs : css){
                System.out.println(cs.getCs_name());
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectCsById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CsDao csDao = session.getMapper(CsDao.class);

            Integer id = 1;
            Cs cs = csDao.SelectCsById(id);

            System.out.println(cs);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectCsByName(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CsDao csDao = session.getMapper(CsDao.class);

            String name = "test_name_new3";
            Cs cs = csDao.SelectCsByName(name);

            System.out.println(cs);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestDeleteById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CsDao csDao = session.getMapper(CsDao.class);

            Integer id = 1;
            csDao.DeleteCsById(id);

            session.commit();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestUpdateCs(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CsDao csDao = session.getMapper(CsDao.class);

            Cs cs = csDao.SelectCsById(2);

            cs.setCs_status(1);
            cs.setCs_login(new Timestamp(System.currentTimeMillis()));
            cs.setCs_salt("test_salt_new");
            cs.setCs_pwd("test_pwd_new");
            cs.setCs_img("test_img");
            cs.setCs_name("test_name_new");
            cs.setCs_unblocktime(new Timestamp(System.currentTimeMillis()+3600*1000));

            csDao.UpdateCs(cs);

            session.commit();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

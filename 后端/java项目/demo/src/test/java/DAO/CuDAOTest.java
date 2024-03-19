package DAO;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tb.dao.CuDao;
import tb.entity.Cu;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    List<Cu> SelectCuByMap(Map<String,Object> map);

    Cu SelectCuById(Integer id);

    Cu SelectCuByName(String name);

    void InsertCu(@Param("cu") Cu cu);

    void DeleteCuById(Integer id);

    void UpdateCu(@Param("cu") Cu cu);//调用时根据cu 里的 cu_id属性来锁定
*/

public class CuDAOTest {
    @Test
    public void TestInsertCu(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CuDao cuDao = session.getMapper(CuDao.class);

            Cu cu = new Cu();
            cu.setCu_name("test_name_new");//账号
            cu.setCu_pwd("test_pwd");//密码
            cu.setCu_salt("test_salt3");//盐
            cu.setCu_status(1);//状态
            cu.setCu_tel("test_tel3");//手机号
            cu.setCu_register(new Timestamp(System.currentTimeMillis()-1800*1000));//注册时间
            cu.setCu_coin(0);
            cu.setCu_release(0);
            cu.setCu_accept(0);
            cu.setCu_finish(0);


            cuDao.InsertCu(cu);

            session.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    @Test
    public void TestSelectCuByMap(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CuDao cuDao = session.getMapper(CuDao.class);

            Map<String, Object> DataMap = new HashMap<>();
            DataMap.put("name", "test_name");
//            DataMap.put("tel", "test_tel2");
            DataMap.put("registerBegin", new Timestamp(System.currentTimeMillis()-3600*1000));
            DataMap.put("registerEnd", new Timestamp(System.currentTimeMillis()+3600*1000));
//            DataMap.put("loginBegin", new Timestamp(System.currentTimeMillis()-7200*1000));
//            DataMap.put("loginEnd", new Timestamp(System.currentTimeMillis()+7200*1000));
            DataMap.put("userStatus", 1);

            List<Cu> cus = cuDao.SelectCuByMap(DataMap);

            for(Cu cu : cus){
                System.out.println(cu.getCu_name());
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectCuById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CuDao cuDao = session.getMapper(CuDao.class);

            Integer id = 3;
            Cu cu = cuDao.SelectCuById(id);

            System.out.println(cu);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectCuByName(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CuDao cuDao = session.getMapper(CuDao.class);

            String name = "test_name_new";
            Cu cu = cuDao.SelectCuByName(name);

            System.out.println(cu);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestDeleteById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CuDao cuDao = session.getMapper(CuDao.class);

            Integer id = 2;
            cuDao.DeleteCuById(id);

            session.commit();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestUpdateCu(){
        try(SqlSession session = mySqlSession.getSqSession()){
            CuDao cuDao = session.getMapper(CuDao.class);

            Cu cu = cuDao.SelectCuById(1);

            cu.setCu_status(1);
            cu.setCu_login(new Timestamp(System.currentTimeMillis()));
            cu.setCu_salt("test_salt_new2");
            cu.setCu_pwd("test_pwd_new2");
            cu.setCu_img("test_img");
            cu.setCu_name("test_name_new2");
            cu.setCu_unblocktime(new Timestamp(System.currentTimeMillis()+3600*1000));

            cuDao.UpdateCu(cu);

            session.commit();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

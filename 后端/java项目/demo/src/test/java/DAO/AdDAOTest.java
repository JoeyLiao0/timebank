package DAO;

import com.mysql.cj.Session;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tb.dao.AdDao;
import tb.entity.Ad;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    List<Ad> SelectAdByMap(Map<String,Object> map);

    Ad SelectAdById(Integer id);

    Ad SelectAdByName(String name);

    void InsertAd(@Param("ad") Ad ad);

    void DeleteAdById(Integer id);

    void UpdateAd(@Param("ad") Ad ad);//调用时根据ad 里的 ad_id属性来锁定
*/
public class AdDAOTest {
    @Test
    public void TestInsertAd(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AdDao adDao = session.getMapper(AdDao.class);

            Ad ad = new Ad();
            ad.setAd_name("test_name3");//账号
            ad.setAd_pwd("test_pwd3");//密码
            ad.setAd_salt("test_salt3");//盐
            ad.setAd_status(2);//状态
            ad.setAd_tel("test_tel3");//手机号
            ad.setAd_register(new Timestamp(System.currentTimeMillis()-1800*1000));//注册时间

            adDao.InsertAd(ad);

            session.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    @Test
    public void TestSelectAdByMap(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AdDao adDao = session.getMapper(AdDao.class);

            Map<String, Object> DataMap = new HashMap<>();
            DataMap.put("name", "test_name");
            DataMap.put("tel", "test_tel2");
            DataMap.put("registerBegin", new Timestamp(System.currentTimeMillis()-3600*1000));
            DataMap.put("registerEnd", new Timestamp(System.currentTimeMillis()+3600*1000));
            DataMap.put("loginBegin", new Timestamp(System.currentTimeMillis()-7200*1000));
            DataMap.put("loginEnd", new Timestamp(System.currentTimeMillis()+7200*1000));
            DataMap.put("userStatus", 1);

            List<Ad> ads = adDao.SelectAdByMap(DataMap);

            for(Ad ad : ads){
                System.out.println(ad.getAd_name());
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectAdById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AdDao adDao = session.getMapper(AdDao.class);

            Integer id = 3;
            Ad ad = adDao.SelectAdById(id);

            System.out.println(ad);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectAdByName(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AdDao adDao = session.getMapper(AdDao.class);

            String name = "test_name";
            Ad ad = adDao.SelectAdByName(name);

            System.out.println(ad);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestDeleteById(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AdDao adDao = session.getMapper(AdDao.class);

            Integer id = 5;
            adDao.DeleteAdById(id);

            session.commit();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestUpdateAd(){
        try(SqlSession session = mySqlSession.getSqSession()){
            AdDao adDao = session.getMapper(AdDao.class);

            Ad ad = adDao.SelectAdById(1);

            ad.setAd_status(1);
            ad.setAd_login(new Timestamp(System.currentTimeMillis()));
            ad.setAd_salt("test_salt_new");
            ad.setAd_pwd("test_pwd_new");
            ad.setAd_img("test_img");
            ad.setAd_name("test_name_new");


            adDao.UpdateAd(ad);

            session.commit();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

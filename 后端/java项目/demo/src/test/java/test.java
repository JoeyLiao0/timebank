
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tb.dao.AdDao;
import tb.entity.Ad;
import tb.util.mySqlSession;

import javax.management.ObjectName;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class test {
    @Test   //注意一定要写此注解表示是测试类
    public void init() throws IOException {


        SqlSession session = mySqlSession.getSqSession();
        Integer id = 1;// 在数据库中取哪个ID的数据
        try {
            AdDao adDao = session.getMapper(AdDao.class);
            Ad admin = adDao.SelectAdById(2);

            Map<String , Object> DataMap = null;
            List<Ad> admins = adDao.SelectAdByMap(DataMap);

//            adDao.InsertAd(admin);

//            String da = "2023-1-1";
//            String da2 = "2023-1-1";
//
//            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//
//            java.util.Date d = f.parse(da);
//
//            java.sql.Date sd = new Date(d.getTime());
//
//            java.util.Date d2 = f.parse(da2);
//
//            java.sql.Date sd2 = new Date(d2.getTime());
//
//            List<Ad> admin = adDao.SelectAdByTelFuzzy("1");
            admin.setAd_name("a");
            adDao.UpdateAd(admin);

            //下行返回的是一个Object对象要强制转化为User
            //Ad admin = session.selectOne("selectAdById",id);//注意：selectUserById要与userMapper.xml中的id一样
//            for(Ad admin2 :admin){
//                System.out.println(admin2.getAd_id());//打印信息
//                System.out.println(admin2.getAd_name());//打印信息
//                System.out.println(admin2.getAd_pwd());//打印信息
//                System.out.println(admin2.getAd_img());//打印信息
//                System.out.println(admin2.getAd_tel());//打印信息
//                System.out.println(admin2.getAd_login());//打印信息
//                System.out.println(admin2.getAd_register());//打印信息
//            }

        }catch (Exception e) {
            System.out.println(e);
        }finally {
            session.close();//始终要关掉所以用try-finally
        }

    }
}

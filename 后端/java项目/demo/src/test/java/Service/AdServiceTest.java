package Service;

import org.junit.Test;
import tb.service.Impl.AdServiceImpl;

import java.sql.Timestamp;
import java.util.*;

public class AdServiceTest {
    AdServiceImpl adServiceImpl = new AdServiceImpl();

    @Test
    public void TestJudgePassword(){
        System.out.println(adServiceImpl.judgePassword("new_liao2","1"));
    }

    @Test
    public void TestExistUsername(){
        System.out.println(adServiceImpl.existUsername(""));
    }

    @Test
    public void TestSelectByMap(){
        Map<String, Object> DataMap = new HashMap<>();
        DataMap.put("name", "liao2");
        DataMap.put("phone", "1");
        DataMap.put("registerBegin",new Timestamp(System.currentTimeMillis()-3600*1000));
        DataMap.put("registerEnd",new Timestamp(System.currentTimeMillis()+3600*1000) );
        DataMap.put("loginBegin", new Timestamp(System.currentTimeMillis()-3600*1000));
        DataMap.put("loginEnd", new Timestamp(System.currentTimeMillis()+3600*1000));
        DataMap.put("userStatus",false);

        ArrayList<Map<String,Object>> arrayList = adServiceImpl.selectByMap(DataMap);


        for(Map<String,Object> map : arrayList){
            System.out.println(map.get("name"));
        }

    }

    @Test
    public void TestSetStatus(){
        adServiceImpl.setStatus(1,true);
    }

    @Test
    public void TestDelete(){
        List<Integer> idArray = new ArrayList<>();
        idArray.add(2);
        idArray.add(3);
        System.out.println(adServiceImpl.delete(idArray));
    }

    @Test
    public void TestSelectById(){
        System.out.println(adServiceImpl.selectById(1).get("name"));
    }

    @Test
    public void TestUpdate(){

        Map<String,Object> DataMap = new HashMap<>();
        DataMap.put("ad_id",1);
        DataMap.put("ad_name", "new_liao2");
        DataMap.put("ad_pwd", "1");
        DataMap.put("ad_tel","new");
        DataMap.put("ad_register",new Timestamp(System.currentTimeMillis()));
        DataMap.put("ad_status",0);
        DataMap.put("ad_login",new Timestamp(System.currentTimeMillis()-10000));
        DataMap.put("ad_img","new.png");

        adServiceImpl.update(DataMap);

    }

    @Test
    public void TestInsert(){

        Map<String,Object> map = new HashMap<>();

        map.put("ad_name","liao2");
        map.put("ad_pwd","pwd");
        map.put("ad_tel","11111");
        map.put("ad_register",new Timestamp(System.currentTimeMillis()));
        map.put("ad_status",0);
        map.put("ad_login",new Timestamp(System.currentTimeMillis()+1000));
        map.put("ad_img","1.png");

        System.out.println(adServiceImpl.insert(map));
    }

}

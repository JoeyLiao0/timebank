package Service;

import org.junit.Test;
import tb.service.Impl.AuServiceImpl;

import java.sql.Timestamp;
import java.util.*;

public class AuServiceTest {
    AuServiceImpl auServiceImpl = new AuServiceImpl();

    @Test
    public void TestJudgePassword(){
        System.out.println(auServiceImpl.judgePassword("new_liao2","1"));
    }

    @Test
    public void TestExistUsername(){
        System.out.println(auServiceImpl.existUsername("new_liao2"));
    }

    @Test
    public void TestSelectByMap(){
        Map<String, Object> DataMap = new HashMap<>();
        DataMap.put("name", "liao2");
        DataMap.put("phone", "n");
        DataMap.put("registerBegin",new Timestamp(System.currentTimeMillis()-3600*1000));
        DataMap.put("registerEnd",new Timestamp(System.currentTimeMillis()+3600*1000) );
        DataMap.put("loginBegin", new Timestamp(System.currentTimeMillis()-3600*1000));
        DataMap.put("loginEnd", new Timestamp(System.currentTimeMillis()+3600*1000));
        DataMap.put("userStatus",false);

        ArrayList<Map<String,Object>> arrayList = auServiceImpl.selectByMap(DataMap);


        for(Map<String,Object> map : arrayList){
            System.out.println(map.get("name"));
        }

    }

    @Test
    public void TestSetStatus(){
        auServiceImpl.setStatus(1,false,new Timestamp(System.currentTimeMillis()+3600*1000));
    }

    @Test
    public void TestDelete(){
        List<Integer> idArray = new ArrayList<>();
        idArray.add(2);
        idArray.add(3);
        System.out.println(auServiceImpl.delete(idArray));
    }

    @Test
    public void TestSelectById(){
        System.out.println(auServiceImpl.selectById(1).get("name"));
    }

    @Test
    public void TestUpdate(){

        Map<String,Object> DataMap = new HashMap<>();
        DataMap.put("au_id",1);
        DataMap.put("au_name", "new_liao2");
        DataMap.put("au_pwd", "1");
        DataMap.put("au_tel","new");
        DataMap.put("au_register",new Timestamp(System.currentTimeMillis()));
        DataMap.put("au_status",0);
        DataMap.put("au_login",new Timestamp(System.currentTimeMillis()-10000));
        DataMap.put("au_img","new.png");

        auServiceImpl.update(DataMap);

    }

    @Test
    public void TestInsert(){

        Map<String,Object> map = new HashMap<>();

        map.put("au_name","liao2");
        map.put("au_pwd","pwd");
        map.put("au_tel","11111");
        map.put("au_register",new Timestamp(System.currentTimeMillis()));
        map.put("au_status",0);
        map.put("au_login",new Timestamp(System.currentTimeMillis()+1000));
        map.put("au_img","1.png");

        System.out.println(auServiceImpl.insert(map));
    }

}

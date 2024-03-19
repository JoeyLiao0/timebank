package Service;

import org.junit.Test;
import tb.service.Impl.CsServiceImpl;

import java.sql.Timestamp;
import java.util.*;

public class CsServiceTest {
    CsServiceImpl csServiceImpl = new CsServiceImpl();

    @Test
    public void TestJudgePassword(){
        System.out.println(csServiceImpl.judgePassword("new_liao2","1"));
    }

    @Test
    public void TestExistUsername(){
        System.out.println(csServiceImpl.existUsername("new_liao2"));
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

        ArrayList<Map<String,Object>> arrayList = csServiceImpl.selectByMap(DataMap);


        for(Map<String,Object> map : arrayList){
            System.out.println(map.get("name"));
        }

    }

    @Test
    public void TestSetStatus(){
        csServiceImpl.setStatus(1,false,new Timestamp(System.currentTimeMillis()+3600*1000));
    }

    @Test
    public void TestDelete(){
        List<Integer> idArray = new ArrayList<>();
        idArray.add(2);
        idArray.add(3);
        System.out.println(csServiceImpl.delete(idArray));
    }

    @Test
    public void TestSelectById(){
        System.out.println(csServiceImpl.selectById(1).get("name"));
    }

    @Test
    public void TestUpdate(){

        Map<String,Object> DataMap = new HashMap<>();
        DataMap.put("cs_id",1);
        DataMap.put("cs_name", "new_liao2");
        DataMap.put("cs_pwd", "1");
        DataMap.put("cs_tel","new");
        DataMap.put("cs_register",new Timestamp(System.currentTimeMillis()));
        DataMap.put("cs_status",0);
        DataMap.put("cs_login",new Timestamp(System.currentTimeMillis()-10000));
        DataMap.put("cs_img","new.png");

        csServiceImpl.update(DataMap);

    }

    @Test
    public void TestInsert(){

        Map<String,Object> map = new HashMap<>();

        map.put("cs_name","liao2");
        map.put("cs_pwd","pwd");
        map.put("cs_tel","11111");
        map.put("cs_register",new Timestamp(System.currentTimeMillis()));
        map.put("cs_status",0);
        map.put("cs_login",new Timestamp(System.currentTimeMillis()+1000));
        map.put("cs_img","1.png");

        System.out.println(csServiceImpl.insert(map));
    }

}

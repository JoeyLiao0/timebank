package Service;

import org.junit.Test;
import tb.service.Impl.CuServiceImpl;

import java.sql.Timestamp;
import java.util.*;

public class CuServiceTest {
    CuServiceImpl cuServiceImpl = new CuServiceImpl();

    @Test
    public void TestJudgePassword(){
        System.out.println(cuServiceImpl.judgePassword("new_liao2","1"));
    }

    @Test
    public void TestExistUsername(){
        System.out.println(cuServiceImpl.existUsername("new_liao2"));
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

        ArrayList<Map<String,Object>> arrayList = cuServiceImpl.selectByMap(DataMap);


        for(Map<String,Object> map : arrayList){
            System.out.println(map.get("name"));
        }

    }

    @Test
    public void TestSetStatus(){
        cuServiceImpl.setStatus(1,false,new Timestamp(System.currentTimeMillis()+3600*1000));
    }

    @Test
    public void TestDelete(){
        List<Integer> idArray = new ArrayList<>();
        idArray.add(2);
        idArray.add(3);
        System.out.println(cuServiceImpl.delete(idArray));
    }

    @Test
    public void TestSelectById(){
        System.out.println(cuServiceImpl.selectById(1).get("name"));
    }

    @Test
    public void TestUpdate(){

        Map<String,Object> DataMap = new HashMap<>();
//        DataMap.put("cu_id",1);
        DataMap.put("cu_name", "new_liao2");
        DataMap.put("cu_pwd", "1");
        DataMap.put("cu_tel","new");
        DataMap.put("cu_register",new Timestamp(System.currentTimeMillis()));
        DataMap.put("cu_status",0);
        DataMap.put("cu_login",new Timestamp(System.currentTimeMillis()-10000));
        DataMap.put("cu_img","new.png");
        DataMap.put("cu_coin",1);

        cuServiceImpl.update(DataMap);

    }

    @Test
    public void TestInsert(){

        Map<String,Object> map = new HashMap<>();

        map.put("cu_name","liao2");
        map.put("cu_pwd","pwd");
        map.put("cu_tel","11111");
        map.put("cu_register",new Timestamp(System.currentTimeMillis()));
        map.put("cu_status",0);
        map.put("cu_login",new Timestamp(System.currentTimeMillis()+1000));
        map.put("cu_img","1.png");

        System.out.println(cuServiceImpl.insert(map));
    }

}

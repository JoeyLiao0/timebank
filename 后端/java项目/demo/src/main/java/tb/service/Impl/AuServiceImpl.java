package tb.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuServiceImpl {
    public String judgePassword(String username,String password){
        return "yes";
    }

    public boolean existUsername(String username){
        return true;
    }

    public ArrayList<Map<String,Object>> selectByMap(Map<String,Object> dataMap){return null;}

    public String setStatus(int id ,boolean status){return null;}

    public String resetPassword(int id){return null;}

    public String delete(List<Integer> idArray){return null;}

    public Map<String,Object> selectById(int id){return null;}

    public String  updateById(Map<String,Object> dataMap){return null;}

    public String insert(Map<String,Object> dataMap){return null;}
}

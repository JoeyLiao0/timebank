package tb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AdService {
    public String judgePassword(String username,String password);

    public String existUsername(String username);

    public ArrayList<Map<String,Object>> selectByMap(Map<String,Object> dataMap);

    public String setStatus(int id,boolean status);

    public String resetPassword(int id,String newPassword);

    public String delete(List<Integer> idArray);

    public Map<String,Object> selectById(int id);

    public String update(Map<String,Object> dataMap);

    public String insert(Map<String,Object> dataMap);
}

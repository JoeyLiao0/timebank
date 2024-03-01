package tb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CsService {
    public String judgePassword(String username,String password);

    public boolean existUsername(String username);

    public ArrayList<Map<String,Object>> select(Map<String,Object> dataMap);

    public String setStatus(int id,boolean status);

    public String resetPassword(int id);

    public String delete(List<Integer> idArray);

    public Map<String,Object> selectById(int id);

    public String updateById(Map<String,Object> dataMap);

    public String insert(Map<String,Object> dataMap);
}

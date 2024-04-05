package tb.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AuService {
    String judgePassword(String username,String password);

    String existUsername(String username);

    ArrayList<Map<String,Object>> selectByMap(Map<String,Object> dataMap);

    String setStatus(int id, boolean status, Timestamp unblocktime);

    String resetPassword(int id,String newPassword);

    String delete(List<Integer> idArray);

    Map<String,Object> selectById(int id);

    Map<String, Object> selectByName(String username);
    String update(Map<String,Object> dataMap);

    String insert(Map<String,Object> dataMap);
}

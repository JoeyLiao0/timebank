package tb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//update 和 insert 前要转化前端json字段 为目标形式，而select则不需要
//select返回值要转化为 后端dto给前端的目标形式

//加密都留给service实现类来做，servlet不对密码再次加密
public interface AdService {
    String judgePassword(String username, String password);

    String existUsername(String username);


    //入参map
    /*
            DataMap.put("id", dataMap.get("id"));
            DataMap.put("name", dataMap.get("name"));
            DataMap.put("tel", dataMap.get("phone"));
            DataMap.put("registerBegin", dataMap.get("registerBegin"));
            DataMap.put("registerEnd", dataMap.get("registerEnd"));
            DataMap.put("loginBegin", dataMap.get("loginBegin"));
            DataMap.put("loginEnd", dataMap.get("loginEnd"));
            DataMap.put("userStatus", ((Boolean) dataMap.get("userStatus")) ? 1 : 0);
     */

    //返回值map
    /*
               Map<String, Object> map = new HashMap<>();
               map.put("id", ad.getAd_id());
               map.put("login", ad.getAd_login());
               map.put("name", ad.getAd_name());
               map.put("phone", ad.getAd_tel());
               map.put("register", ad.getAd_register());
               map.put("userStatus", ad.getAd_status() != 0);
               map.put("img", ad.getAd_img());
     */

    ArrayList<Map<String, Object>> selectByMap(Map<String, Object> dataMap);

    Map<String, Object> selectByName(String username);

    String setStatus(int id, boolean status); //这里加个超时时间

    String resetPassword(int id, String newPassword);

    String delete(List<Integer> idArray);

    Map<String, Object> selectById(int id);

    String update(Map<String, Object> dataMap);


    //入参map
    /*
            ad_id,
            ad_name,
            ad_pwd,
            <if test="ad.ad_img != null">ad_img,</if>
            <if test="ad.ad_login != null">ad_login,</if>
            ad_tel,
            ad_register,
            ad_salt,
            ad_status
     */

    String insert(Map<String, Object> dataMap);
}

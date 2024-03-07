package tb.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import tb.dao.AdDao;
import tb.entity.Ad;
import tb.service.AdService;
import tb.util.mySqlSession;

import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdServiceImpl implements AdService {
    public String judgePassword(String username, String password) {
        SqlSession session = mySqlSession.getSqSession();
        AdDao adDao = session.getMapper(AdDao.class);
        Ad ad = adDao.SelectAdByName(username);

        if (ad == null) {
            return "用户不存在！";
        }

        String salt = ad.getAd_salt();
        String storedPwd = ad.getAd_pwd();

        String decrypt = DigestUtil.sha256Hex(password);
        String givenPwd = salt + decrypt;

        if (givenPwd.equals(storedPwd)) {
            //说明密码正确，验证成功
            return "yes";
        } else {
            return "密码错误！";
        }

    }

    public boolean existUsername(String username) {
        SqlSession session = mySqlSession.getSqSession();
        AdDao adDao = session.getMapper(AdDao.class);
        Ad ad = adDao.SelectAdByName(username);

        if(ad!=null){
            return true;
        }else{
            return false;
        }

    }

    public ArrayList<Map<String, Object>> selectByMap(Map<String, Object> dataMap) {
        //get不存在的字段返回null
        //dto转数据库可以处理的数据
        Map<String, Object> DataMap = new HashMap<>();
        DataMap.put("id", dataMap.get("id"));
        DataMap.put("name", dataMap.get("name"));
        DataMap.put("tel", dataMap.get("phone"));
        DataMap.put("registerBegin", dataMap.get("registerBegin"));
        DataMap.put("registerEnd", dataMap.get("registerEnd"));
        DataMap.put("loginBegin", dataMap.get("loginBegin"));
        DataMap.put("loginEnd", dataMap.get("loginEnd"));
        DataMap.put("userStatus", ((Boolean) dataMap.get("userStatus")) ? 1 : 0);

        //执行
        SqlSession session = mySqlSession.getSqSession();
        AdDao adDao = session.getMapper(AdDao.class);
        ArrayList<Ad> ads = (ArrayList<Ad>) adDao.SelectAdByMap(DataMap);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();

        //数据库返回的数据转dto
        for (Ad ad : ads) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", ad.getAd_id());
            map.put("login", ad.getAd_login());
            map.put("name", ad.getAd_name());
            map.put("phone", ad.getAd_tel());
            map.put("register", ad.getAd_register());
            map.put("userStatus", ad.getAd_status() != 0);
            map.put("img", ad.getAd_img());
            maps.add(map);
        }
        return maps;
    }
    public Map<String, Object> selectById(int id) {

        return null;


    }
    public String setStatus(int id, boolean status) {
        return null;
    }

    public String resetPassword(int id) {
        return null;
    }

    public String delete(List<Integer> idArray) {
        return null;
    }



    public String updateById(Map<String, Object> dataMap) {
        return null;
    }

    public String insert(Map<String, Object> dataMap) {
        return null;
    }
}

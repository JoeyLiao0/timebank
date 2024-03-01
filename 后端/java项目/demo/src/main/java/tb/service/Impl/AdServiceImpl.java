package tb.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import tb.dao.AdDao;
import tb.entity.Ad;
import tb.service.AdService;
import tb.util.mySqlSession;

import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdServiceImpl implements AdService {
    public String judgePassword(String username,String password){

        SqlSession session = mySqlSession.getSqSession();
        AdDao adDao = session.getMapper(AdDao.class);
        Ad ad = adDao.SelectAdByName(username);

        if(ad ==null){
            return "用户不存在！";
        }

        String salt = ad.getAd_salt();
        String storedPwd = ad.getAd_pwd();

        String decrypt = DigestUtil.sha256Hex(password);
        String givenPwd = salt+decrypt;

        if(givenPwd.equals(storedPwd)){
            //说明密码正确，验证成功
            return "yes";
        }else{
            return "密码错误！";
        }

    }

    public boolean existUsername(String username){
        return true;
    }

    public ArrayList<Map<String,Object>> select(Map<String,Object> dataMap){return null;}

    public String setStatus(int id ,boolean status){return null;}

    public String resetPassword(int id){return null;}

    public String delete(List<Integer> idArray){return null;}

    public Map<String,Object> selectById(int id){return null;}

    public String updateById(Map<String,Object> dataMap){return null;}

    public String insert(Map<String,Object> dataMap){return null;}
}

package tb.service;

import cn.hutool.crypto.digest.DigestUtil;
import tb.dao.AdDao;
import tb.entity.Ad;
import tb.service.imp.AdServiceImpl;
import tb.util.mySqlSession;

import java.security.SecureRandom;
import java.util.Base64;

import org.apache.ibatis.session.SqlSession;
public class AdService implements AdServiceImpl {
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
}

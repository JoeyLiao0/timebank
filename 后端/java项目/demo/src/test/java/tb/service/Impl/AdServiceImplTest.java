package tb.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tb.dao.AdDao;
import tb.entity.Ad;
import tb.util.mySqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AdServiceImplTest {

    @Test
    public void judgePassword() {
        String username = "admin1";
        String password = "";
        SqlSession session = mySqlSession.getSqSession();
        AdDao adDao = session.getMapper(AdDao.class);

        Map<String ,Object> map = new HashMap<>();
        List<Ad> ad = adDao.SelectAdByMap(map);

//        if (ad == null) {
////            return "用户不存在！";
//        }
//
//        String salt = ad.getAd_salt();
//        String storedPwd = ad.getAd_pwd();
//
//        String decrypt = DigestUtil.sha256Hex(password);
//        String givenPwd = salt + decrypt;
//
//        if (givenPwd.equals(storedPwd)) {
//            //说明密码正确，验证成功
////            return "yes";
//        } else {
////            return "密码错误！";
//        }
    }
}
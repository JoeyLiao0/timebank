package tb.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import tb.dao.AdDao;
import tb.entity.Ad;
import tb.service.AdService;
import tb.util.mySqlSession;

import org.apache.ibatis.session.SqlSession;

import java.security.SecureRandom;
import java.util.*;

public class AdServiceImpl implements AdService {
    public String judgePassword(String username, String password) {

        try(SqlSession session = mySqlSession.getSqSession()){

            AdDao adDao = session.getMapper(AdDao.class);
            Ad ad = adDao.SelectAdByName(username);

            if (ad == null) {
                return "用户不存在！";
            }

            String salt = ad.getAd_salt();
            String storedPwd = ad.getAd_pwd();


            String hashedPassword = DigestUtil.sha256Hex(password+salt);

            if (hashedPassword.equals(storedPwd)) {
                //说明密码正确，验证成功
                return "yes";
            } else {
                return "密码错误！";
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String existUsername(String username) {

        try(SqlSession session = mySqlSession.getSqSession()) {

            AdDao adDao = session.getMapper(AdDao.class);
            Ad ad = adDao.SelectAdByName(username);
            if (ad != null) {
                return "yes";
            } else {
                return "no";
            }
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public ArrayList<Map<String, Object>> selectByMap(Map<String, Object> dataMap) {

        try(SqlSession session = mySqlSession.getSqSession()){
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
        }catch (Exception e){
            return null;
        }

    }

    public Map<String, Object> selectById(int id) {
        try(SqlSession session = mySqlSession.getSqSession()){

            AdDao adDao = session.getMapper(AdDao.class);
            Ad ad = adDao.SelectAdById(id);

            Map<String ,Object> adInfo =  new HashMap<>();

            adInfo.put("ad_id",ad.getAd_id());
            adInfo.put("ad_login",ad.getAd_login());
            adInfo.put("ad_tel",ad.getAd_tel());
            adInfo.put("ad_name",ad.getAd_name());
            adInfo.put("ad_status",ad.getAd_status());
            adInfo.put("ad_register",ad.getAd_register());
            adInfo.put("ad_img",ad.getAd_img());

            return adInfo;
        }catch (Exception e){
            return null;
        }
    }
    public String setStatus(int id, boolean status) {

        try(SqlSession session = mySqlSession.getSqSession()){

            AdDao adDao = session.getMapper(AdDao.class);

            Ad ad = adDao.SelectAdById(id);



            ad.setAd_status(status ?1:0);
            adDao.UpdateAd(ad);
            session.commit();
            return null;

        }catch (Exception e){
            return "设置状态失败 "+e.getMessage();
        }
    }

    public String resetPassword(int id, String newPassword) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            AdDao adDao = session.getMapper(AdDao.class);
            Ad ad = adDao.SelectAdById(id);

            if (ad == null) {
                return "此id不存在，ID：" + id;
            }

            // 生成盐值
            byte[] salt = new byte[16]; // 长度可以根据需要调整
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);
            String saltString = Base64.getEncoder().encodeToString(salt);

            // 拼接密码和盐值，并使用SHA-256哈希
            String hashedPassword = DigestUtil.sha256Hex(newPassword + saltString);

            ad.setAd_pwd(hashedPassword);
            ad.setAd_salt(saltString);

            adDao.UpdateAd(ad);
            session.commit();

        } catch (Exception e) {
            // 记录异常信息，并可能抛出运行时异常或记录到日志中
//            throw new RuntimeException("重置密码失败", e);
            return "重置密码失败 "+e.getMessage();
        }
        return null;
    }

    public String delete(List<Integer> idArray) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            AdDao adDao = session.getMapper(AdDao.class);
            for(Integer id :idArray){
                adDao.DeleteAdById(id);
            }
            session.commit();

        } catch (Exception e) {
            return "删除账号失败 "+e.getMessage();
        }
        return null;
    }



    public String update(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            AdDao adDao = session.getMapper(AdDao.class);

            Ad ad =   adDao.SelectAdById((Integer) dataMap.get("id"));

            //TODO:dataMap 应该有什么？

            adDao.UpdateAd(ad);

            session.commit();

        } catch (Exception e) {
            return "更新信息失败 "+e.getMessage();
        }
        return null;
    }

    public String insert(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            AdDao adDao = session.getMapper(AdDao.class);

            Ad ad =   adDao.SelectAdById((Integer) dataMap.get("id"));

            //TODO:dataMap 应该有什么？

            adDao.InsertAd(ad);

            session.commit();

        } catch (Exception e) {
            return "新增账号失败 "+e.getMessage();
        }
        return null;
    }
}

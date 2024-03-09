package tb.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.ibatis.session.SqlSession;
import tb.dao.AuDao;
import tb.entity.Au;
import tb.util.mySqlSession;

import java.security.SecureRandom;
import java.util.*;

public class AuServiceImpl {
    public String judgePassword(String username, String password) {

        try(SqlSession session = mySqlSession.getSqSession()){

            AuDao auDao = session.getMapper(AuDao.class);
            Au au = auDao.SelectAuByName(username);

            if (au == null) {
                return "用户不存在！";
            }

            String salt = au.getAu_salt();
            String storedPwd = au.getAu_pwd();


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

            AuDao auDao = session.getMapper(AuDao.class);
            Au au = auDao.SelectAuByName(username);
            if (au != null) {
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

            AuDao auDao = session.getMapper(AuDao.class);
            ArrayList<Au> aus = (ArrayList<Au>) auDao.SelectAuByMap(DataMap);
            ArrayList<Map<String, Object>> maps = new ArrayList<>();

            //数据库返回的数据转dto
            for (Au au : aus) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", au.getAu_id());
                map.put("login", au.getAu_login());
                map.put("name", au.getAu_name());
                map.put("phone", au.getAu_tel());
                map.put("register", au.getAu_register());
                map.put("userStatus", au.getAu_status() != 0);
                map.put("img", au.getAu_img());
                maps.add(map);
            }

            return maps;
        }catch (Exception e){
            return null;
        }

    }

    public Map<String, Object> selectById(int id) {
        try(SqlSession session = mySqlSession.getSqSession()){

            AuDao auDao = session.getMapper(AuDao.class);
            Au au = auDao.SelectAuById(id);

            Map<String ,Object> auInfo =  new HashMap<>();

            auInfo.put("au_id",au.getAu_id());
            auInfo.put("au_login",au.getAu_login());
            auInfo.put("au_tel",au.getAu_tel());
            auInfo.put("au_name",au.getAu_name());
            auInfo.put("au_status",au.getAu_status());
            auInfo.put("au_register",au.getAu_register());
            auInfo.put("au_img",au.getAu_img());

            return auInfo;
        }catch (Exception e){
            return null;
        }
    }
    public String setStatus(int id, boolean status) {

        try(SqlSession session = mySqlSession.getSqSession()){

            AuDao auDao = session.getMapper(AuDao.class);

            Au au = auDao.SelectAuById(id);



            au.setAu_status(status ?1:0);
            auDao.UpdateAu(au);
            session.commit();
            return null;

        }catch (Exception e){
            return "设置状态失败 "+e.getMessage();
        }
    }

    public String resetPassword(int id, String newPassword) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            AuDao auDao = session.getMapper(AuDao.class);
            Au au = auDao.SelectAuById(id);

            if (au == null) {
                return "此id不存在，ID：" + id;
            }

            // 生成盐值
            byte[] salt = new byte[16]; // 长度可以根据需要调整
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);
            String saltString = Base64.getEncoder().encodeToString(salt);

            // 拼接密码和盐值，并使用SHA-256哈希
            String hashedPassword = DigestUtil.sha256Hex(newPassword + saltString);

            au.setAu_pwd(hashedPassword);
            au.setAu_salt(saltString);

            auDao.UpdateAu(au);
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

            AuDao auDao = session.getMapper(AuDao.class);
            for(Integer id :idArray){
                auDao.DeleteAuById(id);
            }
            session.commit();

        } catch (Exception e) {
            return "删除账号失败 "+e.getMessage();
        }
        return null;
    }



    public String update(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            AuDao auDao = session.getMapper(AuDao.class);

            Au au =   auDao.SelectAuById((Integer) dataMap.get("id"));

            //TODO:dataMap 应该有什么？

            auDao.UpdateAu(au);

            session.commit();

        } catch (Exception e) {
            return "更新信息失败 "+e.getMessage();
        }
        return null;
    }

    public String insert(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            AuDao auDao = session.getMapper(AuDao.class);

            Au au =   auDao.SelectAuById((Integer) dataMap.get("id"));

            //TODO:dataMap 应该有什么？

            auDao.InsertAu(au);

            session.commit();

        } catch (Exception e) {
            return "新增账号失败 "+e.getMessage();
        }
        return null;
    }
}

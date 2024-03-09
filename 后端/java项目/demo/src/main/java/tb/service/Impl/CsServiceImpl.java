package tb.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.ibatis.session.SqlSession;
import tb.dao.CsDao;
import tb.entity.Cs;
import tb.util.mySqlSession;

import java.security.SecureRandom;
import java.util.*;

public class CsServiceImpl {
    public String judgePassword(String username, String password) {

        try(SqlSession session = mySqlSession.getSqSession()){

            CsDao csDao = session.getMapper(CsDao.class);
            Cs cs = csDao.SelectCsByName(username);

            if (cs == null) {
                return "用户不存在！";
            }

            String salt = cs.getCs_salt();
            String storedPwd = cs.getCs_pwd();


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

            CsDao csDao = session.getMapper(CsDao.class);
            Cs cs = csDao.SelectCsByName(username);
            if (cs != null) {
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

            CsDao csDao = session.getMapper(CsDao.class);
            ArrayList<Cs> css = (ArrayList<Cs>) csDao.SelectCsByMap(DataMap);
            ArrayList<Map<String, Object>> maps = new ArrayList<>();

            //数据库返回的数据转dto
            for (Cs cs : css) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", cs.getCs_id());
                map.put("login", cs.getCs_login());
                map.put("name", cs.getCs_name());
                map.put("phone", cs.getCs_tel());
                map.put("register", cs.getCs_register());
                map.put("userStatus", cs.getCs_status() != 0);
                map.put("img", cs.getCs_img());
                maps.add(map);
            }

            return maps;
        }catch (Exception e){
            return null;
        }

    }

    public Map<String, Object> selectById(int id) {
        try(SqlSession session = mySqlSession.getSqSession()){

            CsDao csDao = session.getMapper(CsDao.class);
            Cs cs = csDao.SelectCsById(id);

            Map<String ,Object> csInfo =  new HashMap<>();

            csInfo.put("cs_id",cs.getCs_id());
            csInfo.put("cs_login",cs.getCs_login());
            csInfo.put("cs_tel",cs.getCs_tel());
            csInfo.put("cs_name",cs.getCs_name());
            csInfo.put("cs_status",cs.getCs_status());
            csInfo.put("cs_register",cs.getCs_register());
            csInfo.put("cs_img",cs.getCs_img());

            return csInfo;
        }catch (Exception e){
            return null;
        }
    }
    public String setStatus(int id, boolean status) {

        try(SqlSession session = mySqlSession.getSqSession()){

            CsDao csDao = session.getMapper(CsDao.class);

            Cs cs = csDao.SelectCsById(id);



            cs.setCs_status(status ?1:0);
            csDao.UpdateCs(cs);
            session.commit();
            return null;

        }catch (Exception e){
            return "设置状态失败 "+e.getMessage();
        }
    }

    public String resetPassword(int id, String newPassword) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            CsDao csDao = session.getMapper(CsDao.class);
            Cs cs = csDao.SelectCsById(id);

            if (cs == null) {
                return "此id不存在，ID：" + id;
            }

            // 生成盐值
            byte[] salt = new byte[16]; // 长度可以根据需要调整
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);
            String saltString = Base64.getEncoder().encodeToString(salt);

            // 拼接密码和盐值，并使用SHA-256哈希
            String hashedPassword = DigestUtil.sha256Hex(newPassword + saltString);

            cs.setCs_pwd(hashedPassword);
            cs.setCs_salt(saltString);

            csDao.UpdateCs(cs);
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

            CsDao csDao = session.getMapper(CsDao.class);
            for(Integer id :idArray){
                csDao.DeleteCsById(id);
            }
            session.commit();

        } catch (Exception e) {
            return "删除账号失败 "+e.getMessage();
        }
        return null;
    }



    public String update(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            CsDao csDao = session.getMapper(CsDao.class);

            Cs cs =   csDao.SelectCsById((Integer) dataMap.get("id"));

            //TODO:dataMap 应该有什么？

            csDao.UpdateCs(cs);

            session.commit();

        } catch (Exception e) {
            return "更新信息失败 "+e.getMessage();
        }
        return null;
    }

    public String insert(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            CsDao csDao = session.getMapper(CsDao.class);

            Cs cs =   csDao.SelectCsById((Integer) dataMap.get("id"));

            //TODO:dataMap 应该有什么？

            csDao.InsertCs(cs);

            session.commit();

        } catch (Exception e) {
            return "新增账号失败 "+e.getMessage();
        }
        return null;
    }
}

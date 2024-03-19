package tb.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.ibatis.session.SqlSession;
import tb.dao.CsDao;
import tb.dao.CsDao;
import tb.entity.Cs;
import tb.entity.Cs;
import tb.service.CsService;
import tb.util.mySqlSession;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.*;

public class CsServiceImpl implements CsService {
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

            csInfo.put("id",cs.getCs_id());
            csInfo.put("login",cs.getCs_login());
            csInfo.put("phone",cs.getCs_tel());
            csInfo.put("name",cs.getCs_name());
            csInfo.put("userStatus",cs.getCs_status() == 1);
            csInfo.put("register",cs.getCs_register());
            csInfo.put("img",cs.getCs_img());

            return csInfo;

        }catch (Exception e){
            return null;
        }
    }
    public String setStatus(int id, boolean status, Timestamp unblocktime) {

        try(SqlSession session = mySqlSession.getSqSession()){
            try{
                CsDao csDao = session.getMapper(CsDao.class);

                Cs cs = csDao.SelectCsById(id);

                cs.setCs_status(status ?1:0);

                cs.setCs_unblocktime(unblocktime);

                csDao.UpdateCs(cs);

                session.commit();
                return null;
            }catch (Exception e){

                if(session!=null)session.rollback();
                return "设置状态失败 "+e.getMessage();
            }


        }
    }

    public String resetPassword(int id, String newPassword) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try{
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
                return null;

            }catch (Exception e) {
                if(session!=null)session.rollback();
                return "重置密码失败 "+e.getMessage();
            }
        }
    }

    public String delete(List<Integer> idArray) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try{
                CsDao csDao = session.getMapper(CsDao.class);
                for(Integer id :idArray){
                    csDao.DeleteCsById(id);
                }
                session.commit();
                return null;
            } catch (Exception e) {

                if(session!=null)session.rollback();

                return "删除账号失败 "+e.getMessage();
            }

        }

    }

    public String update(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try{
                CsDao csDao = session.getMapper(CsDao.class);

                Cs cs =   csDao.SelectCsById((Integer) dataMap.get("cs_id"));

                if(cs == null){
                    throw new Exception("用户不存在！");
                }

                if(dataMap.get("cs_pwd")!=null){
                    // 生成盐值
                    byte[] salt = new byte[16]; // 长度可以根据需要调整
                    SecureRandom random = new SecureRandom();
                    random.nextBytes(salt);
                    String saltString = Base64.getEncoder().encodeToString(salt);

                    // 拼接密码和盐值，并使用SHA-256哈希
                    String hashedPassword = DigestUtil.sha256Hex((String) dataMap.get("cs_pwd")+ saltString);

                    cs.setCs_pwd(hashedPassword);
                    cs.setCs_salt(saltString);
                }

                cs.setCs_name((String) dataMap.get("cs_name"));

                cs.setCs_tel((String) dataMap.get("cs_tel"));
                cs.setCs_register((Timestamp) dataMap.get("cs_register"));
                cs.setCs_status((Integer) dataMap.get("cs_status"));
                cs.setCs_login((Timestamp) dataMap.get("cs_login"));
                cs.setCs_img((String) dataMap.get("cs_img"));

                csDao.UpdateCs(cs);

                session.commit();

                return null;
            }catch (Exception e) {

                if(session!=null)session.rollback();

                return "更新信息失败 "+e.getMessage();
            }
        }

    }

    public String insert(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try{
                CsDao csDao = session.getMapper(CsDao.class);

                Cs cs = new Cs();
                cs.setCs_name((String)dataMap.get("cs_name"));

                if(existUsername((String)dataMap.get("cs_name")).equals("yes")){
                    throw new Exception("账号已存在！");
                }

                String pwd = (String)dataMap.get("cs_pwd");
                //生成盐
                SecureRandom random = new SecureRandom();
                byte bytes[] = new byte[15];
                random.nextBytes(bytes);
                // 将字节数组转换为Base64编码的字符串，仅包含字母和数字
                String saltString = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
                // 删除Base64中的非字母数字字符（如'+'和'/'）
                saltString = saltString.replaceAll("\\+", "").replaceAll("/", "");
                // 如果生成的字符串长度小于指定的盐长度，则填充'='以达到指定长度
                while (saltString.length() < 15) {
                    saltString += '=';
                }
                String decrypt = DigestUtil.sha256Hex(pwd+saltString);


                cs.setCs_pwd(decrypt);
                cs.setCs_salt(saltString);
                cs.setCs_tel((String)dataMap.get("cs_tel"));
                cs.setCs_register((Timestamp) dataMap.get("cs_register"));
                cs.setCs_status(1);
                cs.setCs_login((Timestamp) dataMap.get("cs_login"));
                cs.setCs_img((String)dataMap.get("cs_img"));

                csDao.InsertCs(cs);

                session.commit();
                return null;
            }catch (Exception e) {

                if(session!=null)session.rollback();

                return "新增账号失败 "+e.getMessage();
            }
        }

    }
}

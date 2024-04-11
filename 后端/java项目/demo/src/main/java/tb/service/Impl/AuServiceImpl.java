package tb.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.ibatis.session.SqlSession;
import tb.dao.AuDao;
import tb.entity.Au;

import tb.service.AuService;
import tb.util.mySqlSession;

import java.security.SecureRandom;

import java.sql.Timestamp;
import java.util.*;

public class AuServiceImpl implements AuService {
    public String judgePassword(String username, String password) {

        try (SqlSession session = mySqlSession.getSqSession()) {

            AuDao auDao = session.getMapper(AuDao.class);
            Au au = auDao.SelectAuByName(username);

            if (au == null) {
                return "用户不存在！";
            }

            String salt = au.getAu_salt();
            String storedPwd = au.getAu_pwd();


            String hashedPassword = DigestUtil.sha256Hex(password + salt);

            if (hashedPassword.equals(storedPwd)) {
                //说明密码正确，验证成功
                return "yes";
            } else {
                return "密码错误！";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String existUsername(String username) {

        try (SqlSession session = mySqlSession.getSqSession()) {

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

        try (SqlSession session = mySqlSession.getSqSession()) {
            //get不存在的字段返回null
            //dto转数据库可以处理的数据
            Map<String, Object> DataMap = new HashMap<>();
            DataMap.put("id", dataMap.get("id"));
            DataMap.put("name", dataMap.get("name"));
            DataMap.put("tel", dataMap.get("phone"));
            if (dataMap.get("registerBegin") != null) {
                DataMap.put("registerBegin", new Timestamp((long) dataMap.get("registerBegin")));
            }else{
                DataMap.put("registerBegin", null);
            }
            if (dataMap.get("registerEnd") != null) {
                DataMap.put("registerEnd", new Timestamp((long) dataMap.get("registerEnd")));
            }else{
                DataMap.put("registerEnd", null);

            }
            if (dataMap.get("loginBegin") != null) {
                DataMap.put("loginBegin", new Timestamp((long) dataMap.get("loginBegin")));
            }else{
                DataMap.put("loginBegin", null);
            }
            if (dataMap.get("loginEnd") != null) {
                DataMap.put("loginEnd", new Timestamp((long) dataMap.get("loginEnd")));
            }else {
                DataMap.put("loginEnd", null);
            }
            if (dataMap.get("userStatus") != null) {
                DataMap.put("userStatus", ((Boolean) dataMap.get("userStatus")) ? 1 : 0);
            } else {
                DataMap.put("userStatus", null);
            }

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
        } catch (Exception e) {
            return null;
        }

    }

    public Map<String, Object> selectById(int id) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            AuDao auDao = session.getMapper(AuDao.class);
            Au au = auDao.SelectAuById(id);

            Map<String, Object> auInfo = new HashMap<>();

            auInfo.put("id", au.getAu_id());
            auInfo.put("login", au.getAu_login());
            auInfo.put("phone", au.getAu_tel());
            auInfo.put("name", au.getAu_name());
            auInfo.put("userStatus", au.getAu_status() == 1);
            auInfo.put("register", au.getAu_register());
            auInfo.put("img", au.getAu_img());
            auInfo.put("auditNum",au.getAuditNum());

            return auInfo;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Object> selectByName(String username) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            AuDao auDao = session.getMapper(AuDao.class);
            Au au = auDao.SelectAuByName(username);

            Map<String, Object> auInfo = new HashMap<>();

            auInfo.put("id", au.getAu_id());
            auInfo.put("login", au.getAu_login());
            auInfo.put("phone", au.getAu_tel());
            auInfo.put("name", au.getAu_name());
            auInfo.put("userStatus", au.getAu_status() == 1);
            auInfo.put("register", au.getAu_register());
            auInfo.put("img", au.getAu_img());

            return auInfo;
        } catch (Exception e) {
            return null;
        }
    }

    public String setStatus(int id, boolean status, Timestamp unblocktime) {

        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                AuDao auDao = session.getMapper(AuDao.class);

                Au au = auDao.SelectAuById(id);

                au.setAu_status(status ? 1 : 0);
                au.setAu_unblocktime(unblocktime);

                auDao.UpdateAu(au);

                session.commit();
                return "yes";
            } catch (Exception e) {
                if (session != null) session.rollback();
                return "设置状态失败 " + e.getMessage();
            }

        }
    }

    public String resetPassword(int id, String newPassword) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
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

                return "yes";
            } catch (Exception e) {
                if (session != null) session.rollback();
                return "重置密码失败 " + e.getMessage();
            }


        }

    }

    public String delete(List<Integer> idArray) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                AuDao auDao = session.getMapper(AuDao.class);
                for (Integer id : idArray) {
                    auDao.DeleteAuById(id);
                }
                session.commit();
                return "yes";
            } catch (Exception e) {
                if (session != null) session.rollback();
                return "删除账号失败 " + e.getMessage();
            }
        }
    }


    public String update(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                AuDao auDao = session.getMapper(AuDao.class);

                Au au = auDao.SelectAuById((Integer) dataMap.get("au_id"));

                if (au == null) {
                    throw new Exception("用户不存在！");
                }
                Au _au_ = auDao.SelectAuByName((String) dataMap.get("au_name"));
                if(_au_!=null){
                    throw new Exception("用户名不能和其他人重复");
                }

                au.setAu_name((String) dataMap.get("au_name"));
                au.setAu_tel((String) dataMap.get("au_tel"));
                au.setAu_img((String) dataMap.get("au_img"));
                au.setAu_login((Timestamp) dataMap.get("au_login"));
                auDao.UpdateAu(au);

                session.commit();
                return "yes";
            } catch (Exception e) {
                if (session != null) session.rollback();
                return "更新信息失败 " + e.getMessage();
            }
        }

    }

    public String insert(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                AuDao auDao = session.getMapper(AuDao.class);

                Au au = new Au();
                au.setAu_name((String) dataMap.get("au_name"));

                if (existUsername((String) dataMap.get("au_name")).equals("yes")) {
                    throw new Exception("账号已存在！");
                }

                String pwd = (String) dataMap.get("au_pwd");
                //生成盐
                SecureRandom random = new SecureRandom();
                byte[] bytes = new byte[15];
                random.nextBytes(bytes);
                // 将字节数组转换为Base64编码的字符串，仅包含字母和数字
                StringBuilder saltString = new StringBuilder(Base64.getUrlEncoder().withoutPadding().encodeToString(bytes));
                // 删除Base64中的非字母数字字符（如'+'和'/'）
                saltString = new StringBuilder(saltString.toString().replaceAll("\\+", "").replaceAll("/", ""));
                // 如果生成的字符串长度小于指定的盐长度，则填充'='以达到指定长度
                while (saltString.length() < 15) {
                    saltString.append('=');
                }
                String decrypt = DigestUtil.sha256Hex(pwd + saltString);


                au.setAu_pwd(decrypt);
                au.setAu_salt(saltString.toString());
                au.setAu_tel((String) dataMap.get("au_tel"));
                au.setAu_register((Timestamp) dataMap.get("au_register"));
                au.setAu_status(1);
                au.setAu_img((String) dataMap.get("au_img"));
                //TODO 这里加一些审核字段

                auDao.InsertAu(au);

                session.commit();
                return "yes";
            } catch (Exception e) {
                if (session != null) session.rollback();
                return "新增账号失败 " + e.getMessage();
            }
        }

    }
}

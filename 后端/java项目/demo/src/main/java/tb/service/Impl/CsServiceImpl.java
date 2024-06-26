package tb.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.ibatis.session.SqlSession;
import tb.dao.CsDao;

import tb.entity.Au;
import tb.entity.Cs;

import tb.service.CsService;
import tb.util.mySqlSession;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.*;

public class CsServiceImpl implements CsService {
    public String judgePassword(String username, String password) {

        try (SqlSession session = mySqlSession.getSqSession()) {

            CsDao csDao = session.getMapper(CsDao.class);
            Cs cs = csDao.SelectCsByName(username);

            if (cs == null) {
                return "用户不存在！";
            }

            String salt = cs.getCs_salt();
            String storedPwd = cs.getCs_pwd();


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
        } catch (Exception e) {
            return null;
        }

    }

    public Map<String, Object> selectById(int id) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            CsDao csDao = session.getMapper(CsDao.class);
            Cs cs = csDao.SelectCsById(id);

            Map<String, Object> csInfo = new HashMap<>();

            csInfo.put("id", cs.getCs_id());
            csInfo.put("login", cs.getCs_login());
            csInfo.put("phone", cs.getCs_tel());
            csInfo.put("name", cs.getCs_name());
            csInfo.put("userStatus", cs.getCs_status() == 1);
            csInfo.put("register", cs.getCs_register());
            csInfo.put("img", cs.getCs_img());
            csInfo.put("processNum",cs.getCs_feedbackNum());

            return csInfo;

        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Object> selectByName(String username) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            CsDao csDao = session.getMapper(CsDao.class);
            Cs cs = csDao.SelectCsByName(username);

            Map<String, Object> csInfo = new HashMap<>();

            csInfo.put("id", cs.getCs_id());
            csInfo.put("login", cs.getCs_login());
            csInfo.put("phone", cs.getCs_tel());
            csInfo.put("name", cs.getCs_name());
            csInfo.put("userStatus", cs.getCs_status() == 1);
            csInfo.put("register", cs.getCs_register());
            csInfo.put("img", cs.getCs_img());

            return csInfo;

        } catch (Exception e) {
            return null;
        }
    }

    public String setStatus(int id, boolean status, Timestamp unblocktime) {

        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                CsDao csDao = session.getMapper(CsDao.class);

                Cs cs = csDao.SelectCsById(id);

                cs.setCs_status(status ? 1 : 0);

                cs.setCs_unblocktime(unblocktime);

                csDao.UpdateCs(cs);

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
                CsDao csDao = session.getMapper(CsDao.class);
                for (Integer id : idArray) {
                    csDao.DeleteCsById(id);
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
                CsDao csDao = session.getMapper(CsDao.class);

                Cs cs = csDao.SelectCsById((Integer) dataMap.get("cs_id"));

                if (cs == null) {
                    throw new Exception("用户不存在！");
                }

                Cs _cs_ = csDao.SelectCsByName((String)dataMap.get("cs_name"));
                if(_cs_!=null){
                    throw new Exception("用户名不能和其他人重复");
                }


                cs.setCs_name((String) dataMap.get("cs_name"));
                cs.setCs_tel((String) dataMap.get("cs_tel"));
                cs.setCs_img((String) dataMap.get("cs_img"));
                cs.setCs_login((Timestamp) dataMap.get("cs_login"));
                csDao.UpdateCs(cs);

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
                CsDao csDao = session.getMapper(CsDao.class);

                Cs cs = new Cs();
                cs.setCs_name((String) dataMap.get("cs_name"));

                if (existUsername((String) dataMap.get("cs_name")).equals("yes")) {
                    throw new Exception("账号已存在！");
                }

                String pwd = (String) dataMap.get("cs_pwd");
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


                cs.setCs_pwd(decrypt);
                cs.setCs_salt(saltString.toString());
                cs.setCs_tel((String) dataMap.get("cs_tel"));
                cs.setCs_register((Timestamp) dataMap.get("cs_register"));
                cs.setCs_status(1);
                cs.setCs_login((Timestamp) dataMap.get("cs_login"));
                cs.setCs_img((String) dataMap.get("cs_img"));

                csDao.InsertCs(cs);

                session.commit();
                return "yes";
            } catch (Exception e) {

                if (session != null) session.rollback();

                return "新增账号失败 " + e.getMessage();
            }
        }

    }

    public Integer getCsIdToFeedback() {
        try (SqlSession session = mySqlSession.getSqSession()) {

            try{
                CsDao csDao = session.getMapper(CsDao.class);
                List<Cs> css = csDao.getMinFeedbackNumCs();

                if (css != null&&!css.isEmpty()) {
                    //同时客服反馈数要+1
                    css.get(0).setCs_feedbackNum(css.get(0).getCs_feedbackNum()+1);
                    csDao.UpdateCs(css.get(0));

                    session.commit();

                    return css.get(0).getCs_id();
                } else {
                    throw new Exception("没有客服，无法反馈");
                }
            }catch (Exception e) {
                if(session!=null)session.rollback();
                return null;
            }
        }
    }
}

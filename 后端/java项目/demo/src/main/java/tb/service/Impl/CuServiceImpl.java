package tb.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.ibatis.session.SqlSession;
import tb.dao.CuDao;
import tb.entity.Au;
import tb.entity.Cu;
import tb.service.CuService;
import tb.util.mySqlSession;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.*;

public class CuServiceImpl implements CuService {

    public Map<String, Object> getStatisticAboutCoin() {

        try (SqlSession session = mySqlSession.getSqSession()) {

            CuDao cuDao = session.getMapper(CuDao.class);
            List<Cu> cus = cuDao.SelectAll();

            List<Integer> userCoinArray = new ArrayList<>();

            int coinSum = 0;

            for (Cu cu : cus) {
                userCoinArray.add(cu.getCu_coin());
                coinSum += cu.getCu_coin();
            }

            //从小到大排序
            Collections.sort(userCoinArray);

            Map<String, Object> map = new HashMap<>();
            map.put("userCoinArray", userCoinArray);
            map.put("coinSum", coinSum);

            return map;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public String judgePassword(String username, String password) {

        try (SqlSession session = mySqlSession.getSqSession()) {

            CuDao cuDao = session.getMapper(CuDao.class);
            Cu cu = cuDao.SelectCuByName(username);

            if (cu == null) {
                return "用户不存在！";
            }

            String salt = cu.getCu_salt();
            String storedPwd = cu.getCu_pwd();


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

            CuDao cuDao = session.getMapper(CuDao.class);
            Cu cu = cuDao.SelectCuByName(username);
            if (cu != null) {
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

            CuDao cuDao = session.getMapper(CuDao.class);
            ArrayList<Cu> cus = (ArrayList<Cu>) cuDao.SelectCuByMap(DataMap);
            ArrayList<Map<String, Object>> maps = new ArrayList<>();

            //数据库返回的数据转dto
            for (Cu cu : cus) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", cu.getCu_id());
                map.put("login", cu.getCu_login());
                map.put("name", cu.getCu_name());
                map.put("phone", cu.getCu_tel());
                map.put("register", cu.getCu_register());
                map.put("userStatus", cu.getCu_status() != 0);
                map.put("img", cu.getCu_img());
                maps.add(map);
            }

            return maps;
        } catch (Exception e) {
            return null;
        }

    }

    public Map<String, Object> selectById(int id) {
        //这里是第二层了，要涉及更深的东西了
        try (SqlSession session = mySqlSession.getSqSession()) {

            CuDao cuDao = session.getMapper(CuDao.class);
            Cu cu = cuDao.SelectCuById(id);

            Map<String, Object> cuInfo = new HashMap<>();

            cuInfo.put("publishTaskNum", cu.getCu_release());
            cuInfo.put("acceptTaskNum", cu.getCu_accept());
            cuInfo.put("finishTaskNum", cu.getCu_finish());
            cuInfo.put("userScore", 5); //暂时不涉及 TODO
            cuInfo.put("userCoin", cu.getCu_coin());
            cuInfo.put("userImg", cu.getCu_img());

            return cuInfo;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Object> selectByName(String username) {

        try (SqlSession session = mySqlSession.getSqSession()) {

            CuDao cuDao = session.getMapper(CuDao.class);
            Cu cu = cuDao.SelectCuByName(username);

            Map<String, Object> cuInfo = new HashMap<>();

            cuInfo.put("id", cu.getCu_id());
            cuInfo.put("login", cu.getCu_login());
            cuInfo.put("phone", cu.getCu_tel());
            cuInfo.put("name", cu.getCu_name());
            cuInfo.put("userStatus", cu.getCu_status() == 1);
            cuInfo.put("register", cu.getCu_register());
            cuInfo.put("img", cu.getCu_img());
            cuInfo.put("release", cu.getCu_release());
            cuInfo.put("finish", cu.getCu_finish());
            cuInfo.put("accept", cu.getCu_accept());
            cuInfo.put("coin", cu.getCu_coin());

            return cuInfo;
        } catch (Exception e) {
            return null;
        }
    }


    public String setStatus(int id, boolean status, Timestamp unblocktime) {

        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                CuDao cuDao = session.getMapper(CuDao.class);

                Cu cu = cuDao.SelectCuById(id);

                cu.setCu_status(status ? 1 : 0);
                cu.setCu_unblocktime(unblocktime);
                cuDao.UpdateCu(cu);
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
                CuDao cuDao = session.getMapper(CuDao.class);
                Cu cu = cuDao.SelectCuById(id);

                if (cu == null) {
                    return "此id不存在，ID：" + id;
                }

                // 生成盐值
                byte[] salt = new byte[16]; // 长度可以根据需要调整
                SecureRandom random = new SecureRandom();
                random.nextBytes(salt);
                String saltString = Base64.getEncoder().encodeToString(salt);

                // 拼接密码和盐值，并使用SHA-256哈希
                String hashedPassword = DigestUtil.sha256Hex(newPassword + saltString);

                cu.setCu_pwd(hashedPassword);
                cu.setCu_salt(saltString);

                cuDao.UpdateCu(cu);
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
                CuDao cuDao = session.getMapper(CuDao.class);
                for (Integer id : idArray) {
                    cuDao.DeleteCuById(id);
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
                CuDao cuDao = session.getMapper(CuDao.class);

                Cu cu = cuDao.SelectCuById((Integer) dataMap.get("cu_id"));

                if (cu == null) {
                    throw new Exception("用户不存在！");
                }

                Cu _cu_ = cuDao.SelectCuByName((String) dataMap.get("cu_name"));
                if(_cu_!=null){
                    throw new Exception("用户名不能和其他人重复");
                }

                cu.setCu_name((String) dataMap.get("cu_name"));
                cu.setCu_tel((String) dataMap.get("cu_tel"));
                cu.setCu_img((String) dataMap.get("cu_img"));
                cu.setCu_coin((Integer) dataMap.get("cu_coin"));
                cu.setCu_login((Timestamp) dataMap.get("cu_login"));
                //TODO 这里cu的score可以设置吗

                cuDao.UpdateCu(cu);

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
                CuDao cuDao = session.getMapper(CuDao.class);

                Cu cu = new Cu();
                cu.setCu_name((String) dataMap.get("cu_name"));

                if (existUsername((String) dataMap.get("cu_name")).equals("yes")) {
                    throw new Exception("账号已存在！");
                }

                String pwd = (String) dataMap.get("cu_pwd");
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


                cu.setCu_pwd(decrypt);
                cu.setCu_salt(saltString.toString());

                cu.setCu_tel((String) dataMap.get("cu_tel"));
                cu.setCu_register((Timestamp) dataMap.get("cu_register"));
                cu.setCu_status(1);
                cu.setCu_login((Timestamp) dataMap.get("cu_login"));
                cu.setCu_img((String) dataMap.get("cu_img"));

                cu.setCu_coin((Integer)dataMap.get("cu_coin"));

                cu.setCu_release(0);
                cu.setCu_accept(0);
                cu.setCu_finish(0);


                cuDao.InsertCu(cu);

                session.commit();
                return "yes";
            } catch (Exception e) {

                if (session != null) session.rollback();

                return "新增账号失败 " + e.getMessage();
            }

        }

    }

    public String issueCoin(Integer coinNum) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                CuDao cuDao = session.getMapper(CuDao.class);

                cuDao.IssueCoin(coinNum);

                session.commit();
                return "yes";
            } catch (Exception e) {

                if (session != null) session.rollback();

                return "发放时间币失败 " + e.getMessage();
            }

        }
    }
}

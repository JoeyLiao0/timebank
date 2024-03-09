package tb.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.ibatis.session.SqlSession;
import tb.dao.CuDao;
import tb.entity.Cu;
import tb.util.mySqlSession;

import java.security.SecureRandom;
import java.util.*;

public class CuServiceImpl {
    public String judgePassword(String username, String password) {

        try(SqlSession session = mySqlSession.getSqSession()){

            CuDao cuDao = session.getMapper(CuDao.class);
            Cu cu = cuDao.SelectCuByName(username);

            if (cu == null) {
                return "用户不存在！";
            }

            String salt = cu.getCu_salt();
            String storedPwd = cu.getCu_pwd();


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
        }catch (Exception e){
            return null;
        }

    }

    public Map<String, Object> selectById(int id) {
        try(SqlSession session = mySqlSession.getSqSession()){

            CuDao cuDao = session.getMapper(CuDao.class);
            Cu cu = cuDao.SelectCuById(id);

            Map<String ,Object> cuInfo =  new HashMap<>();

            cuInfo.put("cu_id",cu.getCu_id());
            cuInfo.put("cu_login",cu.getCu_login());
            cuInfo.put("cu_tel",cu.getCu_tel());
            cuInfo.put("cu_name",cu.getCu_name());
            cuInfo.put("cu_status",cu.getCu_status());
            cuInfo.put("cu_register",cu.getCu_register());
            cuInfo.put("cu_img",cu.getCu_img());

            return cuInfo;
        }catch (Exception e){
            return null;
        }
    }
    public String setStatus(int id, boolean status) {

        try(SqlSession session = mySqlSession.getSqSession()){

            CuDao cuDao = session.getMapper(CuDao.class);

            Cu cu = cuDao.SelectCuById(id);



            cu.setCu_status(status ?1:0);
            cuDao.UpdateCu(cu);
            session.commit();
            return null;

        }catch (Exception e){
            return "设置状态失败 "+e.getMessage();
        }
    }

    public String resetPassword(int id, String newPassword) {
        try (SqlSession session = mySqlSession.getSqSession()) {
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

        } catch (Exception e) {
            // 记录异常信息，并可能抛出运行时异常或记录到日志中
//            throw new RuntimeException("重置密码失败", e);
            return "重置密码失败 "+e.getMessage();
        }
        return null;
    }

    public String delete(List<Integer> idArray) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            CuDao cuDao = session.getMapper(CuDao.class);
            for(Integer id :idArray){
                cuDao.DeleteCuById(id);
            }
            session.commit();

        } catch (Exception e) {
            return "删除账号失败 "+e.getMessage();
        }
        return null;
    }



    public String update(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            CuDao cuDao = session.getMapper(CuDao.class);

            Cu cu =   cuDao.SelectCuById((Integer) dataMap.get("id"));

            //TODO:dataMap 应该有什么？

            cuDao.UpdateCu(cu);

            session.commit();

        } catch (Exception e) {
            return "更新信息失败 "+e.getMessage();
        }
        return null;
    }

    public String insert(Map<String, Object> dataMap) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            CuDao cuDao = session.getMapper(CuDao.class);

            Cu cu =   cuDao.SelectCuById((Integer) dataMap.get("id"));

            //TODO:dataMap 应该有什么？

            cuDao.InsertCu(cu);

            session.commit();

        } catch (Exception e) {
            return "新增账号失败 "+e.getMessage();
        }
        return null;
    }
}

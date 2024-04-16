package tb.servlet.Home.MyAccount;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.AdServiceImpl;

import tb.util.myJson;
import tb.util.myJwt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 管理员、审核员、客服、普通用户
 * 我的账号
 */
@WebServlet("/adAccount/*")
public class adAccount extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();

        String token = (String) dataMap.get("token");//json

        myJwt mj = new myJwt(token);

        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            switch (pathInfo) {
                case "/get" -> {

                    Integer id = Integer.parseInt((String) mj.getValue("id"));//从token里提取id


                    Map<String, Object> map = new AdServiceImpl().selectById(id);//根据id来获取


                    Map<String, Object> datamap = new HashMap<>();

                    JSONObject jsonObject;

                    if (map != null) {
                        datamap.put("id", map.get("id"));
                        datamap.put("tel", map.get("phone"));
                        datamap.put("img", map.get("img"));
                        datamap.put("name", map.get("name"));
                        jsonObject = new JSONObject(datamap);
                        jsonObject.put("status", true);
                    } else {
                        jsonObject = new JSONObject();
                        jsonObject.put("status", false);
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);

                }
                case "/set" -> {

                    Map<String, Object> map = new HashMap<>();
                    map.put("ad_name", dataMap.get("name"));
                    map.put("ad_tel", dataMap.get("tel"));

                    Integer id = Integer.parseInt((String)mj.getValue("id"));

                    map.put("ad_id",id);

                    String msg = new AdServiceImpl().update(map);

                    JSONObject jsonObject = new JSONObject();

                    if (msg != null && msg.equals("yes")) {
                        jsonObject.put("status", true);
                    } else {
                        jsonObject.put("status", false);
                    }
                    jsonObject.put("msg", msg);


                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);

                }
                case "/setPwd" -> {

                    JSONObject jsonObject = new JSONObject();
                    //先验证密码
                    //再设置密码
                    String name = (String) mj.getValue("username");

                    Integer id = Integer.parseInt((String)mj.getValue("id"));
                    String pwd = (String) dataMap.get("pwd");
                    String newPwd = (String) dataMap.get("newPwd");

                    String msg1 = new AdServiceImpl().judgePassword(name, pwd);

                    Map<String, Object> map = new HashMap<>();
                    map.put("ad_id", id);
                    map.put("ad_pwd", newPwd);


                    if (msg1!=null &&msg1.equals("yes")) {
                        String msg2 = new AdServiceImpl().update(map);
                        if (msg2!=null && msg2.equals("yes")) {
                            jsonObject.put("status", true);
                            jsonObject.put("msg", null);
                        } else {
                            jsonObject.put("status", false);
                            jsonObject.put("msg", msg2);
                        }
                    } else {
                        jsonObject.put("status", false);
                        jsonObject.put("msg", msg1);
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);

                }
                case "/upload" -> {

                    String img = (String) dataMap.get("img");

                    //提取逗号前后的字符串
                    String[] data = img.split(",");


                    String typeData = data[0];
                    String base64Data = data[1];

                    // 解码Base64字符串为字节数组
                    byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

                    // 获取项目的static目录路径
                    String staticDir = req.getServletContext().getRealPath("") + File.separator + "static" ;

                    String imgType = null;

                    if (typeData.contains("PNG") || typeData.contains("png")) {

                        imgType = ".png";

                    } else if (typeData.contains("jpeg") || typeData.contains("jpeg")) {

                        imgType = ".jpeg";
                    }
                    JSONObject jsonObject = new JSONObject();
                    if (imgType != null) {
                        // 创建完整的文件路径
                        String uniquePrefix = UUID.randomUUID().toString() + "_";

                        String fileName = uniquePrefix + imgType;

                        File file = new File(staticDir + File.separator + "userImg", fileName);

                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            // 将字节数组写入文件
                            fos.write(decodedBytes);
                            fos.flush();
                            System.out.println("绝对路径" + file.getAbsolutePath());

                            String FileName = File.separator  + "userImg" + File.separator + fileName;

                            System.out.println("相对路径" + FileName );

                            jsonObject.put("status", true);
                            jsonObject.put("msg", null);

                            Integer id = Integer.parseInt((String)mj.getValue("id"));

                            Map<String, Object> map = new HashMap<>();
                            map.put("ad_id", id);
                            map.put("ad_img", FileName);
                            //成功后，将图片相对路径和账号对应起来

                            Map<String, Object> m = new AdServiceImpl().selectById(id);

                            String msg = new AdServiceImpl().update(map);

                            if(!msg.equals("yes")){
                                throw new Exception(msg);
                            }

                            String filePath = (String) m.get("img");
                            if(!filePath.contains("defaultImg")){
                                // 转换路径为Path对象
                                Path path = Paths.get(staticDir+filePath);
                                try {
                                    // 删除被用户换掉的文件
                                    Files.delete(path);
                                    System.out.println("文件已成功删除!");
                                } catch (IOException e) {
                                    System.err.println("删除文件时发生错误: " + e.getMessage());
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            System.err.println("Error saving file: " + e.getMessage());
                            jsonObject.put("status", false);
                            jsonObject.put("msg", "上传失败");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.println("Error saving file: " + e.getMessage());
                            jsonObject.put("status", false);
                            jsonObject.put("msg", "上传失败");
                        }
                    } else {
                        jsonObject.put("status", false);
                        jsonObject.put("msg", "上传失败");
                    }
                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);


                }
            }
        }
    }
}


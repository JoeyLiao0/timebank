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

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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

                    Integer id = (Integer) mj.getValue("id");//从token里提取id


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

                    String msg = new AdServiceImpl().update(dataMap);

                    JSONObject jsonObject = new JSONObject();

                    if (msg == null) {
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
                    String name = (String) mj.getValue("name");

                    String pwd = (String) dataMap.get("pwd");
                    String newPwd = (String) dataMap.get("newPwd");
                    String msg1 = new AdServiceImpl().judgePassword(name, pwd);

                    Map<String, Object> map = new HashMap<>();
                    map.put("ad_id", mj.getValue("id"));
                    map.put("ad_pwd", newPwd);


                    if (msg1.equals("yes")) {
                        String msg2 = new AdServiceImpl().update(map);
                        if (msg2 == null) {
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


//                imgData= "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";
                    String typeData = data[0];
                    String base64Data = data[1];

                    // 解码Base64字符串为字节数组
                    byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

                    // 获取项目的static目录路径
                    String staticDir = req.getServletContext().getRealPath("") + File.separator + "static";


                    String imgType = null;

                    if (typeData.contains("PNG") || typeData.contains("png")) {

                        imgType = ".png";

                    } else if (typeData.contains("jpeg") || typeData.contains("jpeg")) {

                        imgType = ".jpeg";
                    }

                    JSONObject jsonObject = new JSONObject();
                    if (imgType != null) {
                        // 创建完整的文件路径
                        String fileName = mj.getValue("sessionId") + imgType;
                        File file = new File(staticDir, fileName);

                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            // 将字节数组写入文件
                            fos.write(decodedBytes);
                            fos.flush();
                            System.out.println("File saved successfully: " + file.getAbsolutePath());
                            jsonObject.put("status", true);
                            jsonObject.put("msg", null);

                            Map<String, Object> map = new HashMap<>();
                            map.put("ad_id", mj.getValue("id"));
                            map.put("ad_img", fileName);
                            //成功后，将图片路径和账号对应起来
                            new AdServiceImpl().update(map);

                        } catch (IOException e) {
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


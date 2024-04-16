package tb.servlet.Home.AccoutManage;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import tb.service.Impl.CuServiceImpl;

import tb.util.myJson;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 管理员
 * 普通用户管理
 */

@WebServlet("/CUM/*")
public class CUM extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {


        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            switch (pathInfo) {
                case "/selectById" -> {

                    //查询普通用户的详细信息,包含 发布任务数publishTaskNum,接收任务数acceptTaskNum,完成任务数finishTaskNum,用户得分userScore,用户时间币userCoin，用户头像userImg

                    //TODO 这里类型待验证，是java.lang.String 还是 java.lang.Integer

                    System.out.println("servlet: /CUM/selectById: id 的类型为 " + dataMap.get("id").getClass());

                    Integer id = (Integer) dataMap.get("id");

                    //根据编号获取全部信息
                    Map<String, Object> data = (new CuServiceImpl()).selectById(id);

                    //TODO 这里未转换data

                    JSONObject jsonObject;

                    if (data != null) {
                        jsonObject = new JSONObject(data);
                        jsonObject.put("status", true);
                    } else {
                        jsonObject = new JSONObject();
                        jsonObject.put("status", false);
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);


                }
                case "/updateById" -> {

                    //更新普通用户的个人信息

                    //TODO 这里类型待验证，是java.lang.String 还是 java.lang.Integer

                    System.out.println("servlet: /CUM/updateById: id 的类型为 " + dataMap.get("id").getClass());

                    Map<String, Object> datamap = new HashMap<>();

                    datamap.put("cu_id", dataMap.get("id"));
                    datamap.put("cu_name", dataMap.get("name"));
                    datamap.put("cu_tel", dataMap.get("phone"));
                    datamap.put("cu_img", dataMap.get("img"));
                    datamap.put("cu_coin", dataMap.get("coin"));
                    datamap.put("cu_score", dataMap.get("score"));

                    //更新个人信息,并返回msg
                    String msg = new CuServiceImpl().update(datamap);

                    JSONObject jsonObject = new JSONObject();
                    if (msg != null && msg.equals("yes")) {
                        //更新成功
                        jsonObject.put("status", true);
                        jsonObject.put("msg", null);
                    } else {
                        //更新失败
                        jsonObject.put("status", false);
                        jsonObject.put("msg", "错误原因");
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);


                }
                case "/insert" -> {

                    //增加单个普通用户

                    //TODO 这里的pwd待验证是不是对应的是123456
                    String pwd = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";
                    if(dataMap.get("pwd")!=null && dataMap.get("pwd")!=""){
                        pwd = (String) dataMap.get("pwd");
                    }
                    Map<String, Object> datamap = new HashMap<>();
                    if (dataMap.get("img") == null) {
                        int imgId = new Random().nextInt(6) + 1;
                        String staticDir = "userImg" + File.separator + "defaultImg";
                        String imgUrl = staticDir + File.separator + imgId + ".png";
                        datamap.put("cu_img", imgUrl);
                    } else {
                        datamap.put("cu_img", dataMap.get("img"));
                    }
                    datamap.put("cu_name", dataMap.get("name"));
                    datamap.put("cu_pwd", pwd);
                    datamap.put("cu_tel", dataMap.get("phone"));
                    datamap.put("cu_register", new Timestamp(System.currentTimeMillis()));//当前时间就认为是账号的注册时间

                    datamap.put("cu_coin", dataMap.get("coin"));

                    //增加一个普通用户,并返回msg
                    String msg = new CuServiceImpl().insert(datamap);

                    JSONObject jsonObject = new JSONObject();

                    if (msg !=null &&msg.equals("yes")) {
                        //新增成功
                        jsonObject.put("status", true);
                        jsonObject.put("msg", null);
                    } else {
                        //新增失败
                        jsonObject.put("status", false);
                        jsonObject.put("msg", "错误原因");
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);

                }
            }

        }
    }
}

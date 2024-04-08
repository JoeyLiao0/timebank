package tb.servlet.Home.AccoutManage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.AuServiceImpl;
import tb.util.myJson;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员
 * 审核员管理
 */

@WebServlet("/AUM/*")
public class AUM extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {


        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();


        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            switch (pathInfo) {
                case "/selectById" -> {

                    //通过id查询审核员特有信息，TODO，特有信息包含审核任务总数 reviewNum，通过率 passRate，头像 userImg

                    //TODO 这里类型待验证，是java.lang.String 还是 java.lang.Integer

                    System.out.println("servlet: /AUM/selectById: id 的类型为 " + dataMap.get("id").getClass());

                    Integer id = (Integer) dataMap.get("id");//token内部的id是自己，而这里的id指的是查询的目标审核员


                    //根据审核员获取全部信息
                    Map<String, Object> data = (new AuServiceImpl()).selectById(id);

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

                    //更新单个审核员信息

                    //TODO 这里类型待验证，是java.lang.String 还是 java.lang.Integer

                    System.out.println("servlet: /AUM/updateById: id 的类型为 " + dataMap.get("id").getClass());


                    Map<String, Object> datamap = new HashMap<>();

                    //对前端传来的数据进行转化
                    datamap.put("au_id", dataMap.get("id"));
                    datamap.put("au_name", dataMap.get("name"));
                    datamap.put("au_tel", dataMap.get("phone"));
                    datamap.put("au_img", dataMap.get("img"));

                    //更新个人信息，并返回msg
                    //TODO 这里的修改真的有必要吗
                    String msg = new AuServiceImpl().update(datamap);

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

                    //增加单个审核员

                    //TODO 这里的pwd待验证是不是对应的是123456
                    String pwd = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";

                    Map<String, Object> datamap = new HashMap<>();

                    //对前端传来的数据进行转化
                    datamap.put("au_name", dataMap.get("name"));

                    datamap.put("au_pwd", pwd);
                    datamap.put("au_tel", dataMap.get("phone"));
                    datamap.put("au_img", dataMap.get("img"));//TODO 管理员来注册审核员账号的话，要不要预设头像呢？

                    datamap.put("au_register", new Timestamp(System.currentTimeMillis()));//当前时间就认为是账号的注册时间


                    JSONObject jsonObject = new JSONObject();

                    //增加一个审核员账号，并返回msg
                    String msg = new AuServiceImpl().insert(datamap);

                    if (msg != null && msg.equals("yes")) {
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

package tb.servlet.Home.AccoutManage.CommonApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


import tb.service.Impl.AdServiceImpl;
import tb.service.Impl.AuServiceImpl;
import tb.service.Impl.CsServiceImpl;
import tb.service.Impl.CuServiceImpl;

import tb.util.myJson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@WebServlet("/common/*")
public class COMMON extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();


        if (pathInfo != null) {

            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/select")) {

                String role = (String) dataMap.get("role");

                    ArrayList<Map<String,Object>> userArray = new ArrayList<>();

                    if (role != null) {
                        userArray = switch (role) {
                            //所需的参数dataMap是一样的，返回的userArray字段不同
                            case "AD" -> new AdServiceImpl().selectByMap(dataMap);
                            case "AU" -> new AuServiceImpl().selectByMap(dataMap);
                            case "CS" -> new CsServiceImpl().selectByMap(dataMap);
                            case "CU" -> new CuServiceImpl().selectByMap(dataMap);
                            default -> new ArrayList<>();
                        };
                    }

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("userArray",userArray);

                    res.getWriter().write(JSON.toJSONString(jsonObject,SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {


        Map<String, Object> dataMap = new myJson().getMap(req);

        String pathInfo = req.getPathInfo();



        if (pathInfo != null) {

            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/statusSet")) {

                //装箱和拆箱，Integer能当int用吗

                String role = (String) dataMap.get("role");
                Boolean status = (Boolean) dataMap.get("status");
                Integer id = (Integer) dataMap.get("id");
                Timestamp unblocktime = (Timestamp) dataMap.get("unblocktime");



                    String msg = null;//带出错误原因

                    if (role != null) {
                        msg = switch (role) {
                            //所需的参数dataMap是一样的，返回的userArray字段不同
                            case "AD" -> new AdServiceImpl().setStatus(id,status);
                            case "AU" -> new AuServiceImpl().setStatus(id,status,unblocktime);
                            case "CS" -> new CsServiceImpl().setStatus(id,status,unblocktime);
                            case "CU" -> new CuServiceImpl().setStatus(id,status,unblocktime);
                            default -> null;
                        };
                    }

                    Map<String, Object> data = new HashMap<>();

                    if(msg==null){
                        data.put("status",true);
                        data.put("msg",null);
                    }else{
                        data.put("status",false);
                        data.put("msg",msg);
                    }

                    String responseJson = JSON.toJSONString(data,SerializerFeature.WriteMapNullValue);//map 转 json写入response
                    res.getWriter().write(responseJson);
                    res.setStatus(200);


            } else if (pathInfo.equals("/passwordReset")) {

                String role = (String) dataMap.get("role");
                Integer id = (Integer) dataMap.get("id");
                String newPassword = (String) dataMap.get("newPassword");


                    //TODO ：token有效，尝试重置指定用户密码
                    String msg = null;

                    if (role != null) {
                        msg = switch (role) {
                            //所需的参数dataMap是一样的，返回的userArray字段不同
                            case "AD" -> new AdServiceImpl().resetPassword(id,newPassword);
                            case "AU" -> new AuServiceImpl().resetPassword(id,newPassword);
                            case "CS" -> new CsServiceImpl().resetPassword(id,newPassword);
                            case "CU" -> new CuServiceImpl().resetPassword(id,newPassword);
                            default -> null;
                        };
                    }

                    Map<String, Object> data = new HashMap<>();

                    if(msg==null){
                        //成功
                        data.put("status",true);
                        data.put("msg",null);
                    }else{
                        //失败
                        data.put("status",false);
                        data.put("msg",msg);
                    }

                    String responseJson = JSON.toJSONString(data,SerializerFeature.WriteMapNullValue);//map 转 json写入response
                    res.getWriter().write(responseJson);
                    res.setStatus(200);

            } else if (pathInfo.equals("/delete")) {


                String role = (String) dataMap.get("role");
                List<Integer> idArray = (List<Integer>) dataMap.get("idArray");


                    String msg = null;
                    if (role != null) {
                        msg = switch (role) {
                            //所需的参数dataMap是一样的，返回的userArray字段不同
                            case "AD" -> new AdServiceImpl().delete(idArray);
                            case "AU" -> new AuServiceImpl().delete(idArray);
                            case "CS" -> new CsServiceImpl().delete(idArray);
                            case "CU" -> new CuServiceImpl().delete(idArray);
                            default -> null;
                        };
                    }

                    Map<String, Object> data = new HashMap<>();

                    if(msg==null){
                        //成功
                        data.put("status",true);
                        data.put("msg",null);
                    }else{
                        //失败
                        data.put("status",false);
                        data.put("msg",msg);
                    }

                    String responseJson = JSON.toJSONString(data,SerializerFeature.WriteMapNullValue);//map 转 json写入response
                    res.getWriter().write(responseJson);
                    res.setStatus(200);


            }
        }
    }
}

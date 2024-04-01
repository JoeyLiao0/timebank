package tb.servlet.Home.AccoutManage;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import tb.service.Impl.CuServiceImpl;
import tb.util.myDomainSetting;
import tb.util.myJson;
import tb.util.myJwt;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnClose;
import java.io.IOException;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员
 * 普通用户管理
 */

@WebServlet("/CUM/*")
public class CUM extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req , HttpServletResponse res) throws IOException {


        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();


        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/selectById")) {
                /**
                 * 第二层查询普通用户
                 */
                Integer id = (Integer) dataMap.get("id");



                    Map<String,Object> data = null ;

                    data = (new CuServiceImpl()).selectById(id);//根据编号获取全部信息

                    JSONObject jsonObject = null;

                    if(data!=null){
                        jsonObject = new JSONObject(data);
                        jsonObject.put("status",true);
                    }else{
                        jsonObject = new JSONObject();
                        jsonObject.put("status",false);
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);


            }else{
                res.setStatus(404);
            }
        }
    }
    @Override
    public void doPost(HttpServletRequest req , HttpServletResponse res) throws IOException {


        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();



        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/updateById")) {
                /**
                 * 更新单个普通用户信息
                 */


                    JSONObject jsonObject = new JSONObject();

                    Map<String,Object> datamap = new HashMap<>();

                    datamap.put("cu_id",dataMap.get("id"));
                    datamap.put("cu_name",dataMap.get("name"));
                    datamap.put("cu_tel",dataMap.get("phone"));
                    datamap.put("cu_img",dataMap.get("img"));
                    datamap.put("cu_coin",dataMap.get("coin"));
                    datamap.put("cu_score",dataMap.get("score"));

                    String msg = new CuServiceImpl().update(datamap);

                    if(msg==null){//更新个人信息
                        //更新成功
                        jsonObject.put("status",true);
                        jsonObject.put("msg",null);
                    }else{
                        //更新失败
                        jsonObject.put("status",false);
                        jsonObject.put("msg","错误原因");
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);


            }else if(pathInfo.equals("/insert")){
                /**
                 * 增加单个普通用户
                 */


                    Map<String,Object> datamap = new HashMap<>();

                    datamap.put("cu_name",dataMap.get("name"));
                    datamap.put("cu_pwd","123456");//这里应该是123456的加密形式
                    datamap.put("cu_tel",dataMap.get("phone"));
                    datamap.put("cu_img",dataMap.get("img"));
                    datamap.put("cu_register",new Timestamp(System.currentTimeMillis()));
                    datamap.put("cu_coin",dataMap.get("coin"));
                    datamap.put("cu_login",null);

                    JSONObject jsonObject = new JSONObject();

                    String msg = new CuServiceImpl().insert(datamap);//增加一个普通用户

                    if(msg==null){
                        //新增成功
                        jsonObject.put("status",true);
                        jsonObject.put("msg",null);
                    }else{
                        //新增失败失败
                        jsonObject.put("status",false);
                        jsonObject.put("msg","错误原因");
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);

            }

        }
    }
}

package tb.servlet.Home.MyAccount;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.CuServiceImpl;
import tb.service.Impl.CsServiceImpl;

import tb.util.myJson;
import tb.util.myJwt;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员、审核员、客服、普通用户
 * 我的账号
 */
@WebServlet("/cuAccount/*")
public class cuAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {

            Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据
            String token = (String) dataMap.get("token");//json
            myJwt mj = new myJwt(token);
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/get")) {
                /**
                 * 查询自己的账号信息
                 */

                    Map<String, Object> data = null;

                    Integer id = (Integer) mj.getValue("id");

                    data = (new CsServiceImpl()).selectById(id);

                    JSONObject jsonObject = null;
                    if (data != null) {
                        jsonObject = new JSONObject(data);
                    } else {
                        jsonObject = new JSONObject();
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {

            Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

            String token = (String) dataMap.get("token");//json
            myJwt mj = new myJwt(token);

            if (pathInfo.equals("/set")) {

                /**
                 * 设置自己的账号信息
                 */


                    String msg = null;

                    //保证可拓展性，易于检查字段存在与否
                    Map<String,Object> datamap = new HashMap<>();

                    datamap.put("id",(Integer)mj.getValue("id"));
                    datamap.put("cu_name",(String)dataMap.get("cu_name"));
                    datamap.put("cu_img",(String)dataMap.get("cu_img"));
                    datamap.put("cu_tel",(String)dataMap.get("cu_tel"));
                    datamap.put("cu_pwd",(String)dataMap.get("cu_pwd"));


                    msg = (new CsServiceImpl()).update(datamap);

                    JSONObject jsonObject  = new JSONObject();

                    if (msg != null) {
                        jsonObject.put("status",true);
                    } else {
                        jsonObject.put("status",false);
                    }

                    jsonObject.put("msg",msg);

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);

            }else if(pathInfo.equals("/setPwd")){
                JSONObject jsonObject = new JSONObject();
                //先验证密码
                //再设置密码
                String name = (String) mj.getValue("name");

                String pwd = (String) dataMap.get("pwd");
                String newPwd = (String)dataMap.get("newPwd");
                String msg1 = new CuServiceImpl().judgePassword(name,pwd);

                Map<String,Object> map = new HashMap<>();
                map.put("cu_id",mj.getValue("id"));
                map.put("cu_pwd",newPwd);


                if(msg1.equals("yes")){
                    String msg2 = new CuServiceImpl().update(map);
                    if(msg2==null){
                        jsonObject.put("status", true);
                        jsonObject.put("msg",null);
                    }else{
                        jsonObject.put("status", false);
                        jsonObject.put("msg",msg2);
                    }
                }else{
                    jsonObject.put("status", false);
                    jsonObject.put("msg",msg1);
                }

                res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                res.setStatus(200);
            }
        }
    }
}

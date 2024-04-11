package tb.servlet.Login;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.*;

import tb.util.myJson;
import tb.util.myJwt;
import tb.websocket.MyWebSocketServer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.Timestamp;
import java.util.HashMap;

import java.util.Map;


@WebServlet("/login/*")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {


        Map<String, Object> dataMap = new myJson().getMap(req);

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/tokenJudge")) {

                String token = (String) dataMap.get("token");//json

                myJwt mj = new myJwt(token);

                if (mj.judgeToken()) {
                    //token有效，200，并返回新的token
                    String newToken = mj.updateTokenTime();//根据已有的token，更新token时效
                    Map<String, Object> data = new HashMap<>();
                    data.put("token", newToken);
                    String responseJson = JSON.toJSONString(data, SerializerFeature.WriteMapNullValue);//map 转 json写入response
                    res.getWriter().write(responseJson);

                    res.setStatus(200);

                    //这里更新最近登录时间
                    Integer id = Integer.parseInt((String) mj.getValue("id"));
                    String role = (String) mj.getValue("role");
                    switch (role) {
                        case "CU" -> {
                            Map<String,Object> map = new HashMap<>();
                            map.put("cu_id",id);
                            map.put("cu_login",new Timestamp(System.currentTimeMillis()));
                            new CuServiceImpl().update(map);
                        }
                        case "AD" -> {
                            Map<String,Object> map = new HashMap<>();
                            map.put("ad_id",id);
                            map.put("ad_login",new Timestamp(System.currentTimeMillis()));
                            new AdServiceImpl().update(map);
                        }
                        case "AU"->{
                            Map<String,Object> map = new HashMap<>();
                            map.put("au_id",id);
                            map.put("au_login",new Timestamp(System.currentTimeMillis()));
                            new AuServiceImpl().update(map);
                        }
                        case "CS" -> {
                            Map<String,Object> map = new HashMap<>();
                            map.put("cs_id",id);
                            map.put("cs_login",new Timestamp(System.currentTimeMillis()));
                            new CsServiceImpl().update(map);
                        }
                    }


                } else {
                    //token失效,401
                    res.setStatus(401);
                }

            } else if (pathInfo.equals("/passwordJudge")) {
                //判断密码是否对应,这里没有令牌，因为可能是第一次登录

                String username = (String) dataMap.get("username");
                String password = (String) dataMap.get("password");
                String role = (String) dataMap.get("role");

                String msg = "";
                Map<String, Object> datamap = null;

                if (role != null) {
                    switch (role) {
                        case "AD":
                            msg = new AdServiceImpl().judgePassword(username, password);
                            datamap = new AdServiceImpl().selectByName(username);
                            break;
                        case "AU":
                            msg = new AuServiceImpl().judgePassword(username, password);
                            datamap = new AuServiceImpl().selectByName(username);
                            break;
                        case "CS":
                            msg = new CsServiceImpl().judgePassword(username, password);
                            datamap = new CsServiceImpl().selectByName(username);
                            break;
                        case "CU":
                            msg = new CuServiceImpl().judgePassword(username, password);
                            datamap = new CuServiceImpl().selectByName(username);
                            break;
                    }
                }

                if (msg.equals("yes")) {

                    Map<String, String> claims = new HashMap<>();

                    claims.put("id", String.valueOf(datamap.get("id")));
                    claims.put("username", username);
                    claims.put("role", role);
                    claims.put("sessionId", role.toUpperCase() + "_" + datamap.get("id"));


                    String token = myJwt.createToken(claims);//token 里有时效，username，role

                    Map<String, Object> data = new HashMap<>();
                    data.put("token", token);
                    data.put("status", true);
                    String responseJson = JSON.toJSONString(data);//map 转 json写入response
                    res.getWriter().write(responseJson);
                    res.setStatus(200);
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("msg", msg);
                    data.put("status", false);
                    String responseJson = JSON.toJSONString(data, SerializerFeature.WriteMapNullValue);//map 转 json写入response
                    res.getWriter().write(responseJson);
                    res.setStatus(200);
                }

            }
        }

    }

}

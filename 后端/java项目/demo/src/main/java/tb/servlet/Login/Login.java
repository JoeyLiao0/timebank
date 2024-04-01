package tb.servlet.Login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.AdServiceImpl;
import tb.service.Impl.AuServiceImpl;
import tb.service.Impl.CsServiceImpl;
import tb.service.Impl.CuServiceImpl;
import tb.util.myDomainSetting;
import tb.util.myJson;
import tb.util.myJwt;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/login/*")
public class Login extends HttpServlet {
    @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        myDomainSetting.set(req, res);//跨域设置，先这样，之后改成过滤器实现全局配置

        Map<String, Object> dataMap = new myJson().getMap(req);

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/tokenJudge")) {

                String token = (String) dataMap.get("token");//json

                //此时判断token是否有效
//                String token = req.getHeader("token");//获取token，TODO：这里不能这样获取，参数都是json格式一起传过来的

                if (new myJwt(token).judgeToken()) {
                    //token有效，200，并返回新的token
                    String newToken = new myJwt(token).updateTokenTime();//根据已有的token，更新token时效
                    Map<String, Object> data = new HashMap<>();
                    data.put("token", newToken);
                    String responseJson = JSON.toJSONString(data,SerializerFeature.WriteMapNullValue);//map 转 json写入response
                    res.getWriter().write(responseJson);
                    res.setStatus(200);
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

                if (role != null) {
                    msg = switch (role) {
                        case "AD" -> new AdServiceImpl().judgePassword(username, password);
                        case "AU" -> new AuServiceImpl().judgePassword(username, password);
                        case "CS" -> new CsServiceImpl().judgePassword(username, password);
                        case "CU" -> new CuServiceImpl().judgePassword(username, password);
                        default -> msg;
                    };
                }

                if (msg.equals("yes")) {
                    Map<String, String> claims = new HashMap<>();
                    claims.put("username", username);
                    claims.put("role", role);
                    String token = myJwt.createToken(claims);//token 里有时效，username，role

                    Map<String, Object> data = new HashMap<>();
                    data.put("token", token);
                    data.put("status", "true");
                    String responseJson = JSON.toJSONString(data);//map 转 json写入response
                    res.getWriter().write(responseJson);
                    res.setStatus(200);
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("msg", msg);
                    data.put("status", true);
                    String responseJson = JSON.toJSONString(data, SerializerFeature.WriteMapNullValue);//map 转 json写入response
                    res.getWriter().write(responseJson);
                    res.setStatus(200);
                }

            }
        }

    }

}

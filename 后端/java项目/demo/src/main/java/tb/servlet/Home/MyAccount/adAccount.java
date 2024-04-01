package tb.servlet.Home.MyAccount;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.AuServiceImpl;
import tb.util.myDomainSetting;
import tb.util.myJson;
import tb.util.myJwt;

import javax.servlet.ServletException;
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
@WebServlet("/adAccount/*")
public class adAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();


        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/get")) {

                    Map map = new HashMap();

                    JSONObject jsonObject = new JSONObject(map);

                    jsonObject.put("status", true);


                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/set")) {


                    Map map = new HashMap();

                    JSONObject jsonObject = new JSONObject(map);

                    jsonObject.put("status", true);


                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);

            } else if (pathInfo != null) {
                // 根据pathInfo的值决定如何处理请求
                if (pathInfo.equals("/setPwd")) {


                        Map map = new HashMap();

                        JSONObject jsonObject = new JSONObject(map);

                        jsonObject.put("status", true);


                        res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                        res.setStatus(200);

                } else if (pathInfo != null) {
                    // 根据pathInfo的值决定如何处理请求
                    if (pathInfo.equals("/upload")) {


                            Map map = new HashMap();

                            JSONObject jsonObject = new JSONObject(map);

                            jsonObject.put("status", true);


                            res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                            res.setStatus(200);
                        }
                    }
                }
            }
        }
    }
}

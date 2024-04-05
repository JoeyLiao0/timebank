package tb.servlet.Home.Chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.ChatServiceImpl;
import tb.service.Impl.CsServiceImpl;
import tb.service.Impl.TaskServiceImpl;
import tb.util.myJson;
import tb.util.myJwt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

@WebServlet("/chat/*")
public class Chat extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/history")) {

                String token = (String)dataMap.get("token");

                myJwt mj = new myJwt(token);
                String role = (String) mj.getValue("role");
                Integer id = (Integer)mj.getValue("id");

                ArrayList<Map<String,Object>> chatArray = null ;

                chatArray = (ArrayList<Map<String, Object>>) (new ChatServiceImpl()).getHistory(role,id);//根据编号获取全部信息


                JSONArray jsonArray = (JSONArray) JSON.toJSON(chatArray);
                JSONObject  jsonObject = new JSONObject();
                jsonObject.put("msg",jsonArray);
                jsonObject.put("type","chat/history");

                res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                res.setStatus(200);

            }
        }
    }
}

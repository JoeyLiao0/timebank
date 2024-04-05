package tb.servlet.Home.Feedback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.FeedbackServiceImpl;
import tb.util.myJson;
import tb.util.myJwt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet("/feedback/*")
public class Feedback extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/history")) {
                String token = (String)dataMap.get("token");

                String role = (String) new myJwt(token).getValue("role");

                String mySessionId = (String) new myJwt(token).getValue("SessionId");

                String cuSessionId = (String) dataMap.get("cuSessionId");
                String csSessionId = (String) dataMap.get("csSessionId");

                String [] info1 = cuSessionId.split("_");
                String [] info2 = csSessionId.split("_");

                ArrayList<Map<String,Object>> feedbackArray = null ;

                feedbackArray = (ArrayList<Map<String, Object>>) (new FeedbackServiceImpl()).getHistory(role,Integer.getInteger(info1[1]),Integer.getInteger(info2[2]));//获取全部信息


                JSONArray jsonArray = (JSONArray) JSON.toJSON(feedbackArray);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg",jsonArray);
                jsonObject.put("type","feedback/history");

                res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                res.setStatus(200);

            }
        }
    }
}
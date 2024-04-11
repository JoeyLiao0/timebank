package tb.servlet.Home.Talk;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.TalkServiceImpl;
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

@WebServlet("/talk/*")
public class Talk extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/history")) {

                Integer taskId = (Integer) dataMap.get("taskId");

                String token = (String)dataMap.get("token");

                Integer cu_id = Integer.parseInt((String) new myJwt(token).getValue("id"));

                ArrayList<Map<String,Object>> talkArray  ;

                talkArray = (ArrayList<Map<String, Object>>) (new TalkServiceImpl()).getHistory(cu_id,taskId);//根据编号获取全部信息


                JSONArray jsonArray = (JSONArray) JSON.toJSON(talkArray);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg",jsonArray);
                jsonObject.put("type","talk/history");

                res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                res.setStatus(200);

            }
        }
    }
}
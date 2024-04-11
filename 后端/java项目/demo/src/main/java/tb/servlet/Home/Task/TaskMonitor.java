package tb.servlet.Home.Task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.TaskServiceImpl;
import tb.util.myJson;
import tb.util.myJwt;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Map;

@WebServlet("/taskMonitor/*")
public class TaskMonitor extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {

            Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据
            String token = (String) dataMap.get("token");//json
            myJwt mj = new myJwt(token);
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/select")) {

                //根据状态筛选全部任务


//                Map<String, Object> datamap = new HashMap<>();


                String _status = (String) dataMap.get("status");

                String []list = _status.split("-");

                String status = list[0];
                String timeout = list[1];

                ArrayList<Map<String, Object>> taskArray = (new TaskServiceImpl()).selectTaskByStatus(status,timeout);

                JSONArray jsonArray = (JSONArray) JSON.toJSON(taskArray);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("taskArray", jsonArray);

                res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                res.setStatus(200);

            }else if(pathInfo.equals("/info")){
                //TODO 待开发
            }
        }
    }


}

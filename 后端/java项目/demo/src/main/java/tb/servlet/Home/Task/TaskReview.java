package tb.servlet.Home.Task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.TaskServiceImpl;
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

@WebServlet("/taskReview/*")
public class TaskReview extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {

            Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据
            String token = (String) dataMap.get("token");//json
            myJwt mj = new myJwt(token);
            // 根据pathInfo的值决定如何处理请求
            switch (pathInfo) {
                case "/get" -> {

                    //获取待审核且未超时的任务

                    String status = "0000";
                    String timeout = "00";

                    ArrayList<Map<String, Object>> taskArray = (new TaskServiceImpl()).selectTaskByStatus(status, timeout);

                    JSONArray jsonArray = (JSONArray) JSON.toJSON(taskArray);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("taskArray", jsonArray);

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);

                }
                case "review" -> {
                    Integer id = Integer.parseInt((String) mj.getValue("id"));
                    Integer task_id = (Integer) dataMap.get("task_id");
                    Integer reviewStatus = (Integer) dataMap.get("reviewStatus");
                    String reviewAdvice = (String) dataMap.get("reviewAdvice");

                    boolean flag;
                    flag = reviewStatus != 1;
                    String msg = new TaskServiceImpl().review(task_id, id, flag, reviewAdvice);

                    JSONObject jsonObject = new JSONObject();
                    if (msg != null && msg.equals("yes")) {
                        //更新成功
                        jsonObject.put("status", true);
                        jsonObject.put("msg", null);
                    } else {
                        //更新失败
                        jsonObject.put("status", false);
                        jsonObject.put("msg", "错误原因");
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);
                }
                case "history" -> {

                    //获取全部已审核的任务

                    String status = "0000";
                    String timeout = null;

                    ArrayList<Map<String, Object>> taskArray = (new TaskServiceImpl()).selectTaskByNotStatus(status, timeout);

                    JSONArray jsonArray = (JSONArray) JSON.toJSON(taskArray);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("taskArray", jsonArray);

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);
                }
            }
        }
    }


}

package tb.servlet.Phone.CU.Home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.AuServiceImpl;
import tb.service.Impl.TaskServiceImpl;
import tb.util.myDomainSetting;
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

/**
 * 普通用户
 * 已领取任务
 */

@WebServlet("/CU/*")
public class Task extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        myDomainSetting.set(req, res);

        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/task")) {
                /**
                 * 查询自己可以领取的任务 （要排除自己发布的）
                 */

                String token = (String) dataMap.get("token");//json

                //此时判断token是否有效


                if (new myJwt(token).judgeToken()) {
                    //token有效

                    Integer id = (Integer) new myJwt(token).getValue("id");

                    ArrayList<Map<String,Object>> taskArray = (new TaskServiceImpl()).selectAvailable(id);

                    //此处不用判断，因为taskArray为0即没有任务
                    JSONArray jsonArray = (JSONArray) JSON.toJSON(taskArray);
                    JSONObject  jsonObject = new JSONObject();
                    jsonObject.put("taskArray",jsonArray);

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);
                } else {
                    //token失效,401
                    res.setStatus(401);
                }
            }else if(pathInfo.equals("/mypublish")){
                /**
                 * 查询自己发布的任务 （无论什么状态的）
                 */

                String token = (String) dataMap.get("token");//json

                //此时判断token是否有效


                if (new myJwt(token).judgeToken()) {
                    //token有效，返回用户信息

                    Integer id = (Integer) new myJwt(token).getValue("id");

                    ArrayList<Map<String,Object>> taskArray = (new TaskServiceImpl()).selectMyPublish(id);

                    //此处不用判断，因为taskArray为0即没有任务
                    JSONArray jsonArray = (JSONArray) JSON.toJSON(taskArray);
                    JSONObject  jsonObject = new JSONObject();
                    jsonObject.put("taskArray",jsonArray);

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);
                } else {
                    //token失效,401
                    res.setStatus(401);
                }
            }else if(pathInfo.equals("/mytake")){
                /**
                 * 查询自己领取的任务 （无论什么状态的）
                 */

                String token = (String) dataMap.get("token");//json

                //此时判断token是否有效

                if (new myJwt(token).judgeToken()) {
                    //token有效，返回用户信息

                    Integer id = (Integer) new myJwt(token).getValue("id");

                    ArrayList<Map<String,Object>> taskArray = (new TaskServiceImpl()).selectMyTake(id);

                    //此处不用判断，因为taskArray为0即没有任务
                    JSONArray jsonArray = (JSONArray) JSON.toJSON(taskArray);
                    JSONObject  jsonObject = new JSONObject();
                    jsonObject.put("taskArray",jsonArray);

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);
                } else {
                    //token失效,401
                    res.setStatus(401);
                }
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        myDomainSetting.set(req, res);

        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/publish")) {
                /**
                 * 发布任务，一次一个
                 */

                String token = (String) dataMap.get("token");//json

                //此时判断token是否有效

                if (new myJwt(token).judgeToken()) {
                    //token有效

                    Integer id = (Integer)new myJwt(token).getValue("id");

                    String msg = (new TaskServiceImpl()).publish(id,dataMap);

                    JSONObject jsonObject =new JSONObject();

                    if(msg != null){
                        jsonObject.put("status",true);
                    }else{
                        jsonObject.put("status",false);
                    }

                    jsonObject.put("msg",msg);

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);
                } else {
                    //token失效,401
                    res.setStatus(401);
                }
            }
        }
    }
}

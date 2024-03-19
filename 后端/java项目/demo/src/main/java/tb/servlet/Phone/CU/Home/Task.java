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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 普通用户
 * 已领取任务
 */

@WebServlet("/task/*")
public class Task extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            myDomainSetting.set(req, res);
            Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据
            String token = (String)dataMap.get("token");
            myJwt mj = new myJwt(token);

            if (pathInfo.equals("/show")) {
                /**
                 * 查询自己可以领取的任务 （要排除自己发布的）
                 */

                if (mj.judgeToken()) {
                    //token有效

                    Map<String , Object> datamap = new HashMap<>();

                    //这里从dataMap和token中提取需要的字段，而不把无关字段传进service，保证一定的安全性
                    //同时保证可拓展性


                    ArrayList<Map<String,Object>> taskArray = (new TaskServiceImpl()).selectAvailableTask();

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
                 * 查询自己发布的任务
                 */

                if (mj.judgeToken()) {
                    //token有效

                    //这里从dataMap和token中提取需要的字段
//                    Map<String,Object> datamap = new HashMap<>();
//
//                    datamap.put("id",(Integer) mj.getValue("id"));
//                    datamap.put("task_status",(Integer) dataMap.get("task_status"));

                    Integer id = (Integer) mj.getValue("id");

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
                 * 查询自己领取的任务
                 */


                if (mj.judgeToken()) {
                    //token有效
//                    Map<String,Object> datamap = new HashMap<>();

//                    datamap.put("id",(Integer) mj.getValue("id"));
//                    datamap.put("task_status",(Integer)dataMap.get("task_status"));

                    Integer id = (Integer) mj.getValue("id");
                    ArrayList<Map<String,Object>> taskArray = (new TaskServiceImpl()).selectMyTake(id);

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
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            myDomainSetting.set(req, res);
            Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据
            String token = (String) dataMap.get("token");//json
            myJwt mj = new myJwt(token);
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/publish")) {
                /**
                 * 发布任务，一次一个
                 */
                if (mj.judgeToken()) {
                    //token有效

                    Integer publisherId = (Integer)mj.getValue("id");

                    Map<String,Object> Datamap = new HashMap<>();

                    Datamap.put("task_begintime",new Timestamp((long)dataMap.get("task_begintime")*1000));
                    Datamap.put("task_endtime",new Timestamp((long)dataMap.get("task_endtime")*1000));
                    Datamap.put("task_coin",dataMap.get("task_coin"));
                    Datamap.put("task_title",dataMap.get("task_title"));
                    Datamap.put("task_text",dataMap.get("task_text"));
                    Datamap.put("task_location",dataMap.get("task_location"));

                    String msg = (new TaskServiceImpl()).publishNewTask(publisherId,dataMap);

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
            }else if(pathInfo.equals("/take")){
                if (mj.judgeToken()) {
                    //token有效

                    //TODO 加锁
                    Integer id = (Integer) mj.getValue("id");

                    Integer task_id = (Integer) dataMap.get("task_id");

                    String msg = null;

                    msg = new TaskServiceImpl().take(id,task_id);
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

            }else if(pathInfo.equals("/publishCancel")){
                if (mj.judgeToken()) {
                    //token有效

                    Integer id = (Integer) mj.getValue("id");

                    Integer task_id = (Integer) dataMap.get("task_id");

                    String msg = null ;
                    msg = new TaskServiceImpl().publishCancel(id,task_id);

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

            }else if(pathInfo.equals("/takeCancel")){
                if (mj.judgeToken()) {

                    Integer id = (Integer) mj.getValue("id");

                    Integer task_id = (Integer) dataMap.get("task_id");

                    String msg = null;
                    msg = new TaskServiceImpl().takeCancel(id,task_id);

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

            }else if(pathInfo.equals("/takerFinish")){
                if (mj.judgeToken()) {
                    //token有效

                    Integer id = (Integer) mj.getValue("id");

                    Integer task_id = (Integer) dataMap.get("task_id");

                    //TODO 这个图片怎么读取？

                    String msg = null;
                    msg = new TaskServiceImpl().takerFinish(id,task_id);

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

            }else if(pathInfo.equals("/publisherFinish")){
                if (mj.judgeToken()) {
                    //token有效

                    Integer id = (Integer) mj.getValue("id");

                    Integer task_id = (Integer) dataMap.get("task_id");

                    String msg = null;
                    msg = new TaskServiceImpl().publisherFinish(id,task_id);

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

            }else if(pathInfo.equals("/comment")){
                if (mj.judgeToken()) {
                    //token有效

                    //TODO 加锁
                    Integer id = (Integer) mj.getValue("id");

                    Integer task_id = (Integer) dataMap.get("task_id");

                    Integer task_score = (Integer) dataMap.get("task_score");

                    String msg = null;
                    msg = new TaskServiceImpl().comment(id,task_id,task_score);

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


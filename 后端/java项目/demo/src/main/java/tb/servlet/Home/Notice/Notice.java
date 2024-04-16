package tb.servlet.Home.Notice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import tb.service.Impl.NoticeServiceImpl;

import tb.util.myJson;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员
 * 公告发布
 */
@WebServlet("/notice/*")
public class Notice extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            switch (pathInfo) {
                case "/get" -> {

                    ArrayList<Map<String, Object>> noticeArray = (ArrayList<Map<String, Object>>) (new NoticeServiceImpl()).getNotice();//根据编号获取全部信息


                    JSONArray jsonArray = (JSONArray) JSON.toJSON(noticeArray);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("noticeArray", jsonArray);

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回

                    res.setStatus(200);
                }
                case "/publish" -> {


                    Map<String, Object> map = new HashMap<>();

                    map.put("notice_title", dataMap.get("notice_title"));
                    map.put("notice_content", dataMap.get("notice_content"));

                    String msg =  new NoticeServiceImpl().publishNotice(map);

                    JSONObject jsonObject = new JSONObject();


                    if (msg!=null&&msg.equals("yes")){
                        jsonObject.put("status",true);
                        jsonObject.put("msg",null);
                    }else{
                        jsonObject.put("status",false);
                        jsonObject.put("msg",msg);
                    }
                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);
                }
                case "/delete" -> {


                    Integer id = (Integer) dataMap.get("notice_id");

                    System.out.println("公告删除"+dataMap);
                    String msg = new NoticeServiceImpl().deleteNotice(id);

                    JSONObject jsonObject = new JSONObject();
                    if (msg!=null&&msg.equals("yes")){
                        jsonObject.put("status",true);
                        jsonObject.put("msg",null);
                    }else{
                        jsonObject.put("status",false);
                        jsonObject.put("msg",msg);
                    }
                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);
                }
            }
        }
    }
}

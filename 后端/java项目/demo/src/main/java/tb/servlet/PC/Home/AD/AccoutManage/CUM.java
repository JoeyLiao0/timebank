package tb.servlet.PC.Home.AD.AccoutManage;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import tb.service.Impl.CuServiceImpl;
import tb.util.myDomainSetting;
import tb.util.myJson;
import tb.util.myJwt;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;

/**
 * 管理员
 * 普通用户管理
 */

@WebServlet("/CUM/*")
public class CUM extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req , HttpServletResponse res) throws IOException {
        myDomainSetting.set(req, res);

        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/selectById")) {
                /**
                 * 第二层查询普通用户
                 */

                String token = (String) dataMap.get("token");//json
                Integer id = (Integer) dataMap.get("id");

                //此时判断token是否有效

                if (new myJwt().judgeToken(token)) {
                    //token有效，返回用户信息
                    Map<String,Object> data = null ;
                    data = (new CuServiceImpl()).selectById(id);//根据编号获取全部信息

                    JSONObject jsonObject = null;
                    if(data!=null){
                        jsonObject = new JSONObject(data);
                        jsonObject.put("status",true);
                    }else{
                        jsonObject = new JSONObject();
                        jsonObject.put("status",false);
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);
                } else {
                    //token失效,401
                    res.setStatus(401);
                }

            }else{
                res.setStatus(404);
            }
        }
    }
    @Override
    public void doPost(HttpServletRequest req , HttpServletResponse res) throws IOException {
        myDomainSetting.set(req, res);

        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/updateById")) {
                /**
                 * 更新单个普通用户信息
                 */

                String token = (String) dataMap.get("token");

                //此时判断token是否有效
                if (new myJwt().judgeToken(token)) {
                    //token有效
                    JSONObject jsonObject = new JSONObject();

                    String msg = new CuServiceImpl().updateById(dataMap);

                    if(msg==null){//更新个人信息
                        //更新成功
                        jsonObject.put("status",true);
                        jsonObject.put("msg",null);
                    }else{
                        //更新失败
                        jsonObject.put("status",false);
                        jsonObject.put("msg","错误原因");
                    }

                    res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                    res.setStatus(200);
                } else {
                    //token失效,401
                    res.setStatus(401);
                }

            }else if(pathInfo.equals("/insert")){
                /**
                 * 增加单个普通用户
                 */
                String token = (String) dataMap.get("token");

                //此时判断token是否有效
                if (new myJwt().judgeToken(token)) {
                    //token有效
                    JSONObject jsonObject = new JSONObject();

                    String msg = new CuServiceImpl().insert(dataMap);//增加一个普通用户

                    if(msg==null){
                        //新增成功
                        jsonObject.put("status",true);
                        jsonObject.put("msg",null);
                    }else{
                        //新增失败失败
                        jsonObject.put("status",false);
                        jsonObject.put("msg","错误原因");
                    }

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

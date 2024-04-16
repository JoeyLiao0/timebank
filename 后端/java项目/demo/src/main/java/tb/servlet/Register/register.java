package tb.servlet.Register;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tb.service.Impl.CuServiceImpl;
import tb.util.myJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@WebServlet("/register")
public class register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Map<String, Object> dataMap = new myJson().getMap(req);

        Map<String, Object> datamap = new HashMap<>();

        datamap.put("cu_name", dataMap.get("username"));
        datamap.put("cu_pwd", dataMap.get("password"));//这里应该是123456的加密形式
        datamap.put("cu_tel", dataMap.get("telephone"));
        datamap.put("cu_register", new Timestamp(System.currentTimeMillis()));
        datamap.put("cu_coin", 10);
        datamap.put("cu_login", null);

        if (dataMap.get("img") == null) {
            Integer imgId = new Random().nextInt(6) + 1;
            String staticDir = File.separator + "userImg" + File.separator + "defaultImg";
            String imgUrl = staticDir + File.separator + imgId + ".png";
            datamap.put("cu_img", imgUrl);
        } else {
            datamap.put("cu_img", dataMap.get("img"));
        }

        JSONObject jsonObject = new JSONObject();

        String msg = new CuServiceImpl().insert(datamap);//增加一个普通用户

        if (msg != null&&msg.equals("yes")) {

            //新增成功
            jsonObject.put("status", true);
            jsonObject.put("msg", null);
        } else {
            //新增失败失败
            jsonObject.put("status", false);
            jsonObject.put("msg", "错误原因");
        }

        res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
        res.setStatus(200);


    }
}

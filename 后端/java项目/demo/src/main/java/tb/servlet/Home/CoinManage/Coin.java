package tb.servlet.Home.CoinManage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import tb.service.Impl.CuServiceImpl;
import tb.util.myJson;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * 管理员
 * 时间币管理
 */
@WebServlet("/coin/*")
public class Coin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Map<String, Object> dataMap = new myJson().getMap(req);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/getStatistic")) {

                //获取全部用户从小到大排序的时间币数量，和时间币总数

                Map<String,Object> map = new CuServiceImpl().getStatisticAboutCoin();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("coinSum",map.get("coinSum"));

                List<Integer> userCoinArray = (List<Integer>) map.get("userCoinArray");
                JSONArray jsonArray = (JSONArray) JSON.toJSON(userCoinArray);
                jsonObject.put("userCoinArray",jsonArray);

                res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                res.setStatus(200);

            }else if(pathInfo.equals("/issue")){

                //TODO 这里类型待验证，是java.lang.String 还是 java.lang.Integer

                System.out.println("servlet: /coin/issue: id 的类型为 " + dataMap.get("id").getClass());

                //为每个用户发放时间币
                Integer coinNum = (Integer) dataMap.get("coinNum");

                String msg = new CuServiceImpl().issueCoin(coinNum);

                JSONObject jsonObject = new JSONObject();
                if(msg!=null&&msg.equals("yes")){
                    //成功发放
                    jsonObject.put("status",true);
                    jsonObject.put("msg",null);
                }else{
                    //失败
                    jsonObject.put("status",false);
                    jsonObject.put("msg",msg);
                }

                res.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
                res.setStatus(200);
            }
        }
    }
}

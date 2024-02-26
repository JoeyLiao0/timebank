package tb.servlet.login;
import tb.service.Impl.AdServiceImpl;
import tb.service.Impl.AuServiceImpl;
import tb.service.Impl.CsServiceImpl;
import tb.service.Impl.CuServiceImpl;
import tb.util.myDomainSetting;
import tb.util.myJwt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;//提供注解形式指定接口
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login/*")
public class login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {

        myDomainSetting.set(req,res);//跨域设置，先这样，之后改成过滤器实现全局配置

        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/tokenJudge")) {
                //这里单独拎出来一个tokenJudge,为登录这个特定场景服务，主页面则是同一过滤器处理了
                //此时判断token是否有效
                String token = req.getHeader("token");
                if(new myJwt().judgeToken(token)){
                    //成功
                    new myJwt().updateTokenTime(token);
                    res.setHeader("newToken",token);
                    res.setStatus(200);
                }else{
                    //失败
                    String msg = "token is not correct";//内容只能写成ASCII码
                    res.setHeader("msg",msg);
                    res.setHeader("token","token");
                    res.setStatus(200);
                }

            } else if (pathInfo.equals("/passwordJudge")) {
                //判断密码是否对应
                String username = res.getHeader("username");
                String password = res.getHeader("password");
                String role = res.getHeader("role");

                String msg = "";

                if(role!=null){
                    switch (role){
                        case "AD":
                            msg = new AdServiceImpl().judgePassword(username,password);
                            break;
                        case "AU":
                            msg = new AuServiceImpl().judgePassword(username,password);
                            break;
                        case "CS":
                            msg = new CsServiceImpl().judgePassword(username,password);
                            break;
                        case "CU":
                            msg = new CuServiceImpl().judgePassword(username,password);
                            break;
                    }
                }

                if(msg.equals( "yes")){
                    Map<String,String> claims = new HashMap<>();
                    claims.put("username",username);
                    claims.put("role",role);
                    String token = myJwt.createToken(claims);

                    res.setHeader("token",token);
                    res.setStatus(200);
                }else{
                    res.setHeader("msg",msg);
                    res.setStatus(200);
                }

            }
        }

    }
}

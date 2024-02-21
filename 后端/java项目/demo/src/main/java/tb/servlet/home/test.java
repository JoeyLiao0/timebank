package tb.servlet.home;

import io.jsonwebtoken.Claims;
import tb.service.loginService;
import tb.util.myJwt;

import javax.servlet.annotation.WebServlet;//提供注解形式指定接口
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/t")
public class test extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res){
        //先判断token，如果有效说明登录成功有效，可以访问，否则404
        Object jwt = req.getAttribute("token");//jwt存在localstorage，同时在主页面请求时带上
        // 这里部分应该写在主页面接受请求，或者单独一个工具类里

        //检查token是否有效
        if(myJwt.judgeToken((String)jwt)){
            res.setStatus(200);
        }else{
            System.out.println("test");
            ((HttpServletResponse)res).setStatus(402);
        }
    }
}

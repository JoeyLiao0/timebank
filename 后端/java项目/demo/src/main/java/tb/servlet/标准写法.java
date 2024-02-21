package tb.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;//提供注解形式指定接口
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/t")
//public class test extends HttpServlet {
//    @Override
//    public void service(HttpServletRequest req, HttpServletResponse res){
//        //先判断token，如果有效说明登录成功有效，可以访问，否则404
//        Object jwt = req.getAttribute("token");//jwt存在localstorage，同时在主页面请求时带上
//        // 这里部分应该写在主页面接受请求，或者单独一个工具类里
//
//        //检查token是否有效
//        if(myJwt.judgeToken((String)jwt)){
//            res.setStatus(200);
//        }else{
//            System.out.println("test");
//            ((HttpServletResponse)res).setStatus(402);
//        }
//    }
//}

//用下面方式，可以大幅减少servlet的数目
@WebServlet("/t/*")
public class 标准写法 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            // 根据pathInfo的值决定如何处理请求
            if (pathInfo.equals("/users")) {
                // 处理用户相关的请求
            } else if (pathInfo.equals("/products")) {
                // 处理产品相关的请求
            }
            // ... 其他路径处理逻辑
        }
    }
}

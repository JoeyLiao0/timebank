package tb.servlet.login;
import cn.hutool.crypto.digest.DigestUtil;
import tb.util.myJwt;

import java.security.SecureRandom;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;//提供注解形式指定接口
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;
@WebServlet("/login")
public class login extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {

        //处理跨域报错,这里可以单独封装起来或者用拦截器拦截统一设置即可
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=utf-8");
        //设置响应头允许ajax跨域访问
        res.setHeader("Access-Control-Allow-Origin", "*"); //实际上线时，这里换成具体的前端网址，设置只有该网站才能访问响应资源
        //res.setHeader("Access-Control-Allow-Origin", "");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "login, token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        //不仅要包含，还要expose给前端，这样才能被vue项目获取
        res.setHeader("Access-Control-Expose-Headers", "login, token");

        String username = res.getHeader("username");
        String password = res.getHeader("password");

        //加密
        String decrypt = DigestUtil.sha256Hex(password);

        //生成盐
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[15];
        random.nextBytes(bytes);
        // 将字节数组转换为Base64编码的字符串，仅包含字母和数字
        String saltString = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        // 删除Base64中的非字母数字字符（如'+'和'/'）
        saltString = saltString.replaceAll("\\+", "").replaceAll("/", "");
        // 如果生成的字符串长度小于指定的盐长度，则填充'='以达到指定长度
        while (saltString.length() < 15) {
            saltString += '=';
        }

        String pwd = saltString+decrypt;//拼接

        //将盐值和pwd存入后端




        String msg = "";

        String role = new LoginService().judgePassword(username,password,msg);

        if(role.isEmpty()){
            //登录失败
            res.setHeader("login","no");
            res.setHeader("message",msg);

        }else{
            //登录成功
            Map<String,String> claims = new HashMap<>();
            claims.put("username",username);
            claims.put("role",role);
            String token = myJwt.createToken(claims);//生成新的token
            res.setHeader("token",token);
            res.setHeader("login","yes");

        }
    }
}

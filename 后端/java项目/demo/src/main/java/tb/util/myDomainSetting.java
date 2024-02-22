package tb.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class myDomainSetting {
    public static void set(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
        //处理跨域报错,这里可以单独封装起来或者用拦截器拦截统一设置即可
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=utf-8");
        //设置响应头允许ajax跨域访问
        res.setHeader("Access-Control-Allow-Origin", "*"); //实际上线时，这里换成具体的前端网址，设置只有该网站才能访问响应资源
        //res.setHeader("Access-Control-Allow-Origin", "");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "token,msg,Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        //不仅要包含，还要expose给前端，这样才能被vue项目获取
        res.setHeader("Access-Control-Expose-Headers", "token,msg");
    }
}

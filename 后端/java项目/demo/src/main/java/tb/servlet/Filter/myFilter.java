package tb.servlet.Filter;



import tb.util.CustomHttpServletRequestWrapper;
import tb.util.myDomainSetting;
import tb.util.myJson;
import tb.util.myJwt;

import javax.servlet.annotation.WebFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//这里的url 不包括login的相关，TODO
@WebFilter(urlPatterns = "/*")
public class myFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化代码  
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;


        //判断token有无效果
        myDomainSetting.set(req, res);

        CustomHttpServletRequestWrapper reqCopy = new CustomHttpServletRequestWrapper(req);

        Map<String, Object> dataMap = new myJson().getMap(reqCopy);//封装，读取解析req中的json数据

        String pathInfo = req.getPathInfo();

        String token = (String) dataMap.get("token");//json

        myJwt mj = new myJwt(token);

        //此时判断token是否有效

        if (!mj.judgeToken()) {

            (res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "token错误");
            return ;

        }

        chain.doFilter(reqCopy, res);


    }

    @Override
    public void destroy() {
        // 销毁代码  
    }
}
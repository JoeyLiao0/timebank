package tb.servlet.Filter;


import tb.util.CustomHttpServletRequestWrapper;
import tb.util.myJson;
import tb.util.myJwt;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class TokenJudgeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // 初始化代码  
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String method = req.getMethod();

        if ("POST".equalsIgnoreCase(method)) {
            // 处理POST请求的逻辑



            CustomHttpServletRequestWrapper reqCopy = new CustomHttpServletRequestWrapper(req);

            Map<String, Object> dataMap = new myJson().getMap(reqCopy);//封装，读取解析req中的json数据


            String token = (String) dataMap.get("token");//json

            myJwt mj = new myJwt(token);

            //此时判断token是否有效

            if (!mj.judgeToken()) {

                (res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "token错误");
                return;

            }

            chain.doFilter(reqCopy, res);
        }else if ("OPTIONS".equalsIgnoreCase(method)) {

            res.setStatus(200);
            return ;
        }

    }

    @Override
    public void destroy() {
        // 销毁代码  
    }
}
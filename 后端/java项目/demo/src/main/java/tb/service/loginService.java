package tb.service;
import io.jsonwebtoken.Claims;
import tb.service.imp.loginServiceImpl;

public class loginService implements loginServiceImpl {
    @Override
    public String judgePassword(String username, String password,String msg) {
        return "AD";
    }
}

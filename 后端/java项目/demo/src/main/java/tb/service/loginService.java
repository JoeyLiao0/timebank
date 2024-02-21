package tb.service;
import tb.service.imp.LoginServiceImpl;

public class LoginService implements LoginServiceImpl {
    @Override
    public String judgePassword(String username, String password,String msg) {
        return "AD";
    }
}

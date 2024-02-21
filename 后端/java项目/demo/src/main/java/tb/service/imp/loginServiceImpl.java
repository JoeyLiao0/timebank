package tb.service.imp;

import io.jsonwebtoken.Claims;

public interface loginServiceImpl {
    //空字符串代表密码错误，其他代表密码正确同时指出身份信息，AD代表管理员，AU代表审核员，CS代表客服,msg传出失败原因
    public String judgePassword(String username , String password,String msg);

}

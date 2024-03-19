package tb.entity;

import java.sql.Timestamp ;

/**
 * 普通用户实体类
 */
public class Cu {
    //用户编号
    private Integer cu_id;

    //用户账号
    private String cu_name;

    //用户密码
    private String cu_pwd;

    //盐值
    private String cu_salt;

    //用户电话号码
    private String cu_tel;

    //用户时间币
    private Integer cu_coin;

    //注册时间
    private Timestamp cu_register;

    //用户上次登录时间
    private Timestamp cu_login;

    //用户发布任务数
    private Integer cu_release;

    //用户接受任务数
    private Integer cu_accept;

    //用户完成任务数
    private Integer cu_finish;

    //用户头像
    private String cu_img;

    //状态
    private Integer cu_status;

    private Timestamp cu_unblocktime;

    public Integer getCu_id() {
        return cu_id;
    }

    public void setCu_id(Integer cu_id) {
        this.cu_id = cu_id;
    }

    public String getCu_name() {
        return cu_name;
    }

    public void setCu_name(String cu_name) {
        this.cu_name = cu_name;
    }

    public String getCu_pwd() {
        return cu_pwd;
    }

    public void setCu_pwd(String cu_pwd) {
        this.cu_pwd = cu_pwd;
    }

    public String getCu_salt() {
        return cu_salt;
    }

    public void setCu_salt(String cu_salt) {
        this.cu_salt = cu_salt;
    }

    public String getCu_tel() {
        return cu_tel;
    }

    public void setCu_tel(String cu_tel) {
        this.cu_tel = cu_tel;
    }

    public Integer getCu_coin() {
        return cu_coin;
    }

    public void setCu_coin(Integer cu_coin) {
        this.cu_coin = cu_coin;
    }

    public Timestamp getCu_register() {
        return cu_register;
    }

    public void setCu_register(Timestamp cu_register) {
        this.cu_register = cu_register;
    }

    public Timestamp getCu_login() {
        return cu_login;
    }

    public void setCu_login(Timestamp cu_login) {
        this.cu_login = cu_login;
    }

    public Integer getCu_release() {
        return cu_release;
    }

    public void setCu_release(Integer cu_release) {
        this.cu_release = cu_release;
    }

    public Integer getCu_accept() {
        return cu_accept;
    }

    public void setCu_accept(Integer cu_accept) {
        this.cu_accept = cu_accept;
    }

    public Integer getCu_finish() {
        return cu_finish;
    }

    public void setCu_finish(Integer cu_finish) {
        this.cu_finish = cu_finish;
    }

    public String getCu_img() {
        return cu_img;
    }

    public void setCu_img(String cu_img) {
        this.cu_img = cu_img;
    }

    public Integer getCu_status() {
        return cu_status;
    }

    public void setCu_status(Integer cu_status) {
        this.cu_status = cu_status;
    }

    public Timestamp getCu_unblocktime() {
        return cu_unblocktime;
    }

    public void setCu_unblocktime(Timestamp cu_unblocktime) {
        this.cu_unblocktime = cu_unblocktime;
    }
}

package tb.entity;


import java.sql.Timestamp;

/**
 * 客服实体类
 */
public class Cs {
    //客服编号
    private Integer cs_id;

    //客服账号
    private String cs_name;

    //客服密码
    private String cs_pwd;

    //盐值
    private String cs_salt;

    //客服头像
    private String cs_img;

    //客服上次登录时间
    private Timestamp cs_login;

    //客服手机号
    private String cs_tel;

    //客服注册时间
    private Timestamp cs_register;

    //状态
    private Integer cs_status;

    //解禁时间
    private Timestamp cs_unblocktime;

    //处理反馈数
    private Integer cs_feedbackNum;

    public Integer getCs_id() {
        return cs_id;
    }

    public void setCs_id(Integer cs_id) {
        this.cs_id = cs_id;
    }

    public String getCs_name() {
        return cs_name;
    }

    public void setCs_name(String cs_name) {
        this.cs_name = cs_name;
    }

    public String getCs_pwd() {
        return cs_pwd;
    }

    public void setCs_pwd(String cs_pwd) {
        this.cs_pwd = cs_pwd;
    }

    public String getCs_salt() {
        return cs_salt;
    }

    public void setCs_salt(String cs_salt) {
        this.cs_salt = cs_salt;
    }

    public String getCs_img() {
        return cs_img;
    }

    public void setCs_img(String cs_img) {
        this.cs_img = cs_img;
    }

    public Timestamp getCs_login() {
        return cs_login;
    }

    public void setCs_login(Timestamp cs_login) {
        this.cs_login = cs_login;
    }

    public String getCs_tel() {
        return cs_tel;
    }

    public void setCs_tel(String cs_tel) {
        this.cs_tel = cs_tel;
    }

    public Timestamp getCs_register() {
        return cs_register;
    }

    public void setCs_register(Timestamp cs_register) {
        this.cs_register = cs_register;
    }

    public Integer getCs_status() {
        return cs_status;
    }

    public void setCs_status(Integer cs_status) {
        this.cs_status = cs_status;
    }

    public Timestamp getCs_unblocktime() {
        return cs_unblocktime;
    }

    public void setCs_unblocktime(Timestamp cs_unblocktime) {
        this.cs_unblocktime = cs_unblocktime;
    }

    public Integer getCs_feedbackNum() {
        return cs_feedbackNum;
    }

    public void setCs_feedbackNum(Integer cs_feedbackNum) {
        this.cs_feedbackNum = cs_feedbackNum;
    }
}

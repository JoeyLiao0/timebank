package tb.entity;


import java.sql.Timestamp;

/**
 * 审核员实体类
 */
public class Au {
    //审核员编号
    private Integer au_id;

    //审核员账号
    private String au_name;

    //审核员密码
    private String au_pwd;

    //盐值
    private String au_salt;

    //审核员头像
    private String au_img;

    //审核员上次登录时间
    private Timestamp au_login;

    //审核员电话号码
    private String au_tel;

    //审核员账号注册时间
    private Timestamp au_register;

    public Integer getAuditNum() {
        return auditNum;
    }

    public void setAuditNum(Integer auditNum) {
        this.auditNum = auditNum;
    }

    //状态
    private Integer au_status;

    private Timestamp au_unblocktime;

    private Integer auditNum;

    public Integer getAu_id() {
        return au_id;
    }

    public void setAu_id(Integer au_id) {
        this.au_id = au_id;
    }

    public String getAu_name() {
        return au_name;
    }

    public void setAu_name(String au_name) {
        this.au_name = au_name;
    }

    public String getAu_pwd() {
        return au_pwd;
    }

    public void setAu_pwd(String au_pwd) {
        this.au_pwd = au_pwd;
    }

    public String getAu_salt() {
        return au_salt;
    }

    public void setAu_salt(String au_salt) {
        this.au_salt = au_salt;
    }

    public String getAu_img() {
        return au_img;
    }

    public void setAu_img(String au_img) {
        this.au_img = au_img;
    }

    public Timestamp getAu_login() {
        return au_login;
    }

    public void setAu_login(Timestamp au_login) {
        this.au_login = au_login;
    }

    public String getAu_tel() {
        return au_tel;
    }

    public void setAu_tel(String au_tel) {
        this.au_tel = au_tel;
    }

    public Timestamp getAu_register() {
        return au_register;
    }

    public void setAu_register(Timestamp au_register) {
        this.au_register = au_register;
    }

    public Integer getAu_status() {
        return au_status;
    }

    public void setAu_status(Integer au_status) {
        this.au_status = au_status;
    }

    public Timestamp getAu_unblocktime() {
        return au_unblocktime;
    }

    public void setAu_unblocktime(Timestamp au_unblocktime) {
        this.au_unblocktime = au_unblocktime;
    }
}

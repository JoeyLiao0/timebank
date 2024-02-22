package tb.entity;

import java.sql.Date;

/**
 * 普通用户实体类
 */
//cu_id int AI PK
//cu_name varchar(255)
//cu_pwd varchar(255)
//cu_tel varchar(255)
//cu_icon int
//cu_register datetime
//cu_login datetime
//cu_release int
//cu_accept int
//cu_img varchar(255)
public class Cu {
    //用户编号
    private Integer cu_id;
    //用户账号
    private String cu_name;
    //用户密码
    private String cu_pwd;
    //用户电话号码
    private String cu_tel;
    //用户时间币
    private Integer cu_icon;
    //用户上次登录时间
    private Date cu_login;
    //用户发布任务数
    private Integer cu_release;
    //用户接受任务数
    private Integer cu_accept;
    //用户头像
    private String cu_img;

    public String getCu_salt() {
        return cu_salt;
    }

    public void setCu_salt(String cu_salt) {
        this.cu_salt = cu_salt;
    }

    //盐值
    private String cu_salt;

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

    public String getCu_tel() {
        return cu_tel;
    }

    public void setCu_tel(String cu_tel) {
        this.cu_tel = cu_tel;
    }

    public Integer getCu_icon() {
        return cu_icon;
    }

    public void setCu_icon(Integer cu_icon) {
        this.cu_icon = cu_icon;
    }

    public Date getCu_login() {
        return cu_login;
    }

    public void setCu_login(Date cu_login) {
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

    public String getCu_img() {
        return cu_img;
    }

    public void setCu_img(String cu_img) {
        this.cu_img = cu_img;
    }
}

package tb.entity;

import java.sql.Date;

//ad_id int AI PK
//ad_name varchar(255)
//ad_pwd varchar(255)
//ad_img varchar(255)
//ad_login datetime
//ad_tel varchar(255)
//ad_register datetime

/**
 * 管理员实体类
 *
 */
public class Ad {
    //管理员编号
    private Integer ad_id;

    //管理员账号
    private String ad_name;

    //管理员密码
    private String ad_pwd;

    //管理员头像
    private String ad_img;

    //管理员上次登录时间
    private Date ad_login;

    //管理员手机号
    private String ad_tel;

    //盐值
    private String ad_salt;

    public String getAd_salt() {
        return ad_salt;
    }

    public void setAd_salt(String ad_salt) {
        this.ad_salt = ad_salt;
    }

    //管理员注册时间
    private Date ad_register;

    public Integer getAd_id() {
        return ad_id;
    }

    public void setAd_id(Integer ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_name() {return ad_name;}

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }

    public String getAd_pwd() {
        return ad_pwd;
    }

    public void setAd_pwd(String ad_pwd) {
        this.ad_pwd = ad_pwd;
    }

    public String getAd_img() {
        return ad_img;
    }

    public void setAd_img(String ad_img) {
        this.ad_img = ad_img;
    }

    public Date getAd_login() {
        return ad_login;
    }

    public void setAd_login(Date ad_login) {
        this.ad_login = ad_login;
    }

    public String getAd_tel() {
        return ad_tel;
    }

    public void setAd_tel(String ad_tel) {
        this.ad_tel = ad_tel;
    }

    public Date getAd_register() {
        return ad_register;
    }

    public void setAd_register(Date ad_register) {
        this.ad_register = ad_register;
    }
}

package tb.entity;


//au_id int AI PK
//au_name varchar(255)
//au_pwd varchar(255)
//au_img varchar(255)
//au_login datetime
//au_tel varchar(255)
//au_register datetime

import java.sql.Date;

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

    //审核员头像
    private String au_img;

    //审核员上次登录时间
    private Date au_login;

    //审核员电话号码
    private String au_tel;

    //审核员账号注册时间
    private Date au_register;

    //盐值
    private String au_salt;

    public String getAu_salt() {
        return au_salt;
    }

    public void setAu_salt(String au_salt) {
        this.au_salt = au_salt;
    }

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

    public String getAu_img() {
        return au_img;
    }

    public void setAu_img(String au_img) {
        this.au_img = au_img;
    }

    public Date getAu_login() {
        return au_login;
    }

    public void setAu_login(Date au_login) {
        this.au_login = au_login;
    }

    public String getAu_tel() {
        return au_tel;
    }

    public void setAu_tel(String au_tel) {
        this.au_tel = au_tel;
    }

    public Date getAu_register() {
        return au_register;
    }

    public void setAu_register(Date au_register) {
        this.au_register = au_register;
    }
}

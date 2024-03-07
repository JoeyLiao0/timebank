package tb.entity;


import java.sql.Date;

//cs_id int AI PK
//cs_name varchar(255)
//cs_pwd varchar(255)
//cs_img varchar(255)
//cs_login varchar(255)
//cs_tel varchar(255)
//cs_register datetime
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

    //客服头像
    private String cs_img;

    //客服上次登录时间
    private Date cs_login;

    //客服手机号
    private String cs_tel;

    //客服注册时间
    private Date cs_register;

    //盐值
    private String cs_salt;


    //状态
    private Integer cs_status;

    public Integer getCs_status() {
        return cs_status;
    }

    public void setCs_status(Integer cs_status) {
        this.cs_status = cs_status;
    }

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

    public String getCs_img() {
        return cs_img;
    }

    public void setCs_img(String cs_img) {
        this.cs_img = cs_img;
    }

    public Date getCs_login() {
        return cs_login;
    }

    public void setCs_login(Date cs_login) {
        this.cs_login = cs_login;
    }

    public String getCs_tel() {
        return cs_tel;
    }

    public void setCs_tel(String cs_tel) {
        this.cs_tel = cs_tel;
    }

    public Date getCs_register() {
        return cs_register;
    }

    public void setCs_register(Date cs_register) {
        this.cs_register = cs_register;
    }

    public String getCs_salt() {
        return cs_salt;
    }

    public void setCs_salt(String cs_salt) {
        this.cs_salt = cs_salt;
    }

}

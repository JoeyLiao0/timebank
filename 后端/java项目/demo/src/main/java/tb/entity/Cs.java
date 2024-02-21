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

    public Integer getcs_id() {
        return cs_id;
    }

    public void setcs_id(Integer cs_id) {
        this.cs_id = cs_id;
    }

    public String getcs_name() {
        return cs_name;
    }

    public void setcs_name(String cs_name) {
        this.cs_name = cs_name;
    }

    public String getcs_pwd() {
        return cs_pwd;
    }

    public void setcs_pwd(String cs_pwd) {
        this.cs_pwd = cs_pwd;
    }

    public String getcs_img() {
        return cs_img;
    }

    public void setcs_img(String cs_img) {
        this.cs_img = cs_img;
    }

    public Date getcs_login() {
        return cs_login;
    }

    public void setcs_login(Date cs_login) {
        this.cs_login = cs_login;
    }

    public String getcs_tel() {
        return cs_tel;
    }

    public void setcs_tel(String cs_tel) {
        this.cs_tel = cs_tel;
    }

    public Date getcs_register() {
        return cs_register;
    }

    public void setcs_register(Date cs_register) {
        this.cs_register = cs_register;
    }
}

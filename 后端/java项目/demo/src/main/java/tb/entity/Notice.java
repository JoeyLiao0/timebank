package tb.entity;
//notice_id int AI PK
//notice_begintime datetime
//notice_endtime datetime
//notice_text varchar(255)
//notice_title varchar(255)

import java.sql.Date;

/**
 * 公告实体类
 */
public class Notice {
    //公告编号
    private Integer notice_id;
    //公告开始时间
    private Date notice_begintime;
    //公告结束时间
    private Date notice_endtime;
    //公告正文
    private String notice_text;
    //公告标题
    private String notice_title;

    public Integer getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(Integer notice_id) {
        this.notice_id = notice_id;
    }

    public Date getNotice_begintime() {
        return notice_begintime;
    }

    public void setNotice_begintime(Date notice_begintime) {
        this.notice_begintime = notice_begintime;
    }

    public Date getNotice_endtime() {
        return notice_endtime;
    }

    public void setNotice_endtime(Date notice_endtime) {
        this.notice_endtime = notice_endtime;
    }

    public String getNotice_text() {
        return notice_text;
    }

    public void setNotice_text(String notice_text) {
        this.notice_text = notice_text;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }
}

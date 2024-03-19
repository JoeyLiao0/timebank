package tb.entity;
//notice_id int AI PK
//notice_begintime datetime
//notice_endtime datetime
//notice_text varchar(255)
//notice_title varchar(255)

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 公告实体类
 */
public class Notice {
    //公告编号
    private Integer notice_id;
    //公告开始时间
    private Timestamp notice_time;

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

    public Timestamp getNotice_time() {
        return notice_time;
    }

    public void setNotice_time(Timestamp notice_time) {
        this.notice_time = notice_time;
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

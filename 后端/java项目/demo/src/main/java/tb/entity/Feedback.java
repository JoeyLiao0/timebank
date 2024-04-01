package tb.entity;

import java.sql.Timestamp;

public class Feedback {
    //编号
    private Integer feedback_id;


    //发送者编号
    private Integer feedback_senderid;

    //发送者角色
    private String feedback_senderrole;

    //接收者编号
    private Integer feedback_receiverid;

    //接收者身份
    private String feedback_receiverrole;

    //内容
    private String feedback_content;

    //类型
    private String feedback_contenttype;

    //时间戳
    private Timestamp feedback_timestamp;

    //已读名单
    private  String feedback_isread;

    public Integer getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(Integer feedback_id) {
        this.feedback_id = feedback_id;
    }

    public Integer getFeedback_senderid() {
        return feedback_senderid;
    }

    public void setFeedback_senderid(Integer feedback_senderid) {
        this.feedback_senderid = feedback_senderid;
    }

    public String getFeedback_senderrole() {
        return feedback_senderrole;
    }

    public void setFeedback_senderrole(String feedback_senderrole) {
        this.feedback_senderrole = feedback_senderrole;
    }

    public Integer getFeedback_receiverid() {
        return feedback_receiverid;
    }

    public void setFeedback_receiverid(Integer feedback_receiverid) {
        this.feedback_receiverid = feedback_receiverid;
    }

    public String getFeedback_receiverrole() {
        return feedback_receiverrole;
    }

    public void setFeedback_receiverrole(String feedback_receiverrole) {
        this.feedback_receiverrole = feedback_receiverrole;
    }

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }

    public String getFeedback_contenttype() {
        return feedback_contenttype;
    }

    public void setFeedback_contenttype(String feedback_contenttype) {
        this.feedback_contenttype = feedback_contenttype;
    }

    public Timestamp getFeedback_timestamp() {
        return feedback_timestamp;
    }

    public void setFeedback_timestamp(Timestamp feedback_timestamp) {
        this.feedback_timestamp = feedback_timestamp;
    }

    public String getFeedback_isread() {
        return feedback_isread;
    }

    public void setFeedback_isread(String feedback_isread) {
        this.feedback_isread = feedback_isread;
    }
}

package tb.entity;

import java.sql.Timestamp;

/**
 * 任务对应的对话，在任务被接受时创建
 */
public class Talk {
    //对话编号
    private Integer talk_id;

    //任务编号
    private Integer talk_taskid;

    //对话消息发出者编号
    private Integer talk_senderid;

    //消息接收者编号
    private Integer talk_receiverid;

    //对话消息
    private String talk_content;

    //消息类型
    private String talk_contenttype;

    //对话时间戳
    private Timestamp talk_timestamp;

    //已读名单
    private String talk_isread;

    public Integer getTalk_id() {
        return talk_id;
    }

    public void setTalk_id(Integer talk_id) {
        this.talk_id = talk_id;
    }

    public Integer getTalk_receiverid() {
        return talk_receiverid;
    }

    public void setTalk_receiverid(Integer talk_receiverid) {
        this.talk_receiverid = talk_receiverid;
    }

    public String getTalk_contenttype() {
        return talk_contenttype;
    }

    public void setTalk_contenttype(String talk_contenttype) {
        this.talk_contenttype = talk_contenttype;
    }

    public String getTalk_isread() {
        return talk_isread;
    }

    public void setTalk_isread(String talk_isread) {
        this.talk_isread = talk_isread;
    }

    public Integer getTalk_taskid() {
        return talk_taskid;
    }

    public void setTalk_taskid(Integer talk_taskid) {
        this.talk_taskid = talk_taskid;
    }

    public Integer getTalk_senderid() {
        return talk_senderid;
    }

    public void setTalk_senderid(Integer talk_senderid) {
        this.talk_senderid = talk_senderid;
    }

    public String getTalk_content() {
        return talk_content;
    }

    public void setTalk_content(String talk_content) {
        this.talk_content = talk_content;
    }

    public Timestamp getTalk_timestamp() {
        return talk_timestamp;
    }

    public void setTalk_timestamp(Timestamp talk_timestamp) {
        this.talk_timestamp = talk_timestamp;
    }
}

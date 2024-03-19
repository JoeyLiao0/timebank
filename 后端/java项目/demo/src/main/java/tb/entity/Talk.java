package tb.entity;
//talk_taskid int PK
//talk_senderid int
//talk_content varchar(255)
//talk_timestamp varchar(255)

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 任务对应的对话，在任务被接受时创建
 */
public class Talk {
    //对话编号
    private Integer talk_taskid;
    //对话消息发出者
    private Integer talk_senderid;
    //对话消息
    private String talk_content;
    //对话时间戳
    private Timestamp talk_timestamp;

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

package tb.entity;

import java.sql.Date;

//chat_id int AI PK
//chat_senderid int
//chat_content varchar(255)
//chat_timestamp datetime

/**
 * 内部交流通道
 */
public class Chat {
    //消息编号
    private Integer chat_id;
    //消息发送者编号
    private Integer chat_senderid;
    //消息内容
    private String chat_content;
    //消息时间戳
    private Date chat_timestamp;
    //消息发送者身份
    private String chat_senderrole;

    public String getChat_senderrole() {
        return chat_senderrole;
    }

    public void setChat_senderrole(String chat_senderrole) {
        this.chat_senderrole = chat_senderrole;
    }

    public Integer getChat_id() {
        return chat_id;
    }

    public void setChat_id(Integer chat_id) {
        this.chat_id = chat_id;
    }

    public Integer getChat_senderid() {
        return chat_senderid;
    }

    public void setChat_senderid(Integer chat_senderid) {
        this.chat_senderid = chat_senderid;
    }

    public String getChat_content() {
        return chat_content;
    }

    public void setChat_content(String chat_content) {
        this.chat_content = chat_content;
    }

    public Date getChat_timestamp() {
        return chat_timestamp;
    }

    public void setChat_timestamp(Date chat_timestamp) {
        this.chat_timestamp = chat_timestamp;
    }
}

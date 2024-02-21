package tb.entity;
//talk_taskid int PK
//talk_senderid int
//talk_content varchar(255)
//talk_timestamp varchar(255)

import java.sql.Date;

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
    private Date talk_timestamp;
}

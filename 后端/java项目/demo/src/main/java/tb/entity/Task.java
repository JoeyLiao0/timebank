package tb.entity;
//task_id int AI PK
//task_status varchar(255)
//task_result varchar(255)
//task_begintime datetime
//task_endtime datetime
//task_finishtime datetime
//task_publisherid int
//task_takerid int
//task_coin int
//task_score int

import java.sql.Date;

/**
 * 任务实体类
 */
public class Task {
    //任务编号
    private Integer task_id;
    //任务领取状态
    private String task_status;
    //任务完成状态
    private String task_result;
    //任务开始时间
    private Date task_begintime;
    //任务结束时间
    private Date task_endtime;
    //任务完成时间
    private Date task_finishtime;
    //任务发布者编号
    private Integer task_publisherid;
    //任务领取者编号
    private Integer task_takerid;
    //任务完成时间币报酬
    private Integer task_icon;
    //任务完成评价
    private Integer task_score;

    //任务审核状态
    private String task_audit;

    //任务审核意见
    private String task_advice;

    //任务审核员编号
    private Integer task_auid;

    public String getTask_audit() {
        return task_audit;
    }

    public void setTask_audit(String task_audit) {
        this.task_audit = task_audit;
    }

    public String getTask_advice() {
        return task_advice;
    }

    public void setTask_advice(String task_advice) {
        this.task_advice = task_advice;
    }

    public Integer getTask_auid() {
        return task_auid;
    }

    public void setTask_auid(Integer task_auid) {
        this.task_auid = task_auid;
    }

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public String getTask_result() {
        return task_result;
    }

    public void setTask_result(String task_result) {
        this.task_result = task_result;
    }

    public Date getTask_begintime() {
        return task_begintime;
    }

    public void setTask_begintime(Date task_begintime) {
        this.task_begintime = task_begintime;
    }

    public Date getTask_endtime() {
        return task_endtime;
    }

    public void setTask_endtime(Date task_endtime) {
        this.task_endtime = task_endtime;
    }

    public Date getTask_finishtime() {
        return task_finishtime;
    }

    public void setTask_finishtime(Date task_finishtime) {
        this.task_finishtime = task_finishtime;
    }

    public Integer getTask_publisherid() {
        return task_publisherid;
    }

    public void setTask_publisherid(Integer task_publisherid) {
        this.task_publisherid = task_publisherid;
    }

    public Integer getTask_takerid() {
        return task_takerid;
    }

    public void setTask_takerid(Integer task_takerid) {
        this.task_takerid = task_takerid;
    }

    public Integer getTask_icon() {
        return task_icon;
    }

    public void setTask_icon(Integer task_icon) {
        this.task_icon = task_icon;
    }

    public Integer getTask_score() {
        return task_score;
    }

    public void setTask_score(Integer task_score) {
        this.task_score = task_score;
    }
}

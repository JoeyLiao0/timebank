package tb.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 任务实体类
 */
public class Task {
    //任务编号
    private Integer task_id;

    //任务(审核领取完成评价)状态
    private String task_status;

    //超时与否，0未超时，1超时
    private String task_timeout;
    //任务开始时间
    private Timestamp task_begintime;
    //任务结束时间
    private Timestamp task_endtime;
    //任务完成时间
    private Timestamp task_finishtime;
    //任务发布者编号
    private Integer task_publisherid;
    //任务领取者编号
    private Integer task_takerid;
    //任务完成时间币报酬
    private Integer task_coin;
    //任务完成评价
    private Integer task_score;

    //任务审核意见
    private String task_advice;

    //任务审核员编号
    private Integer task_auid;

    private String task_title;

    private String task_location;

    private String task_text;

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public String getTask_location() {
        return task_location;
    }

    public void setTask_location(String task_location) {
        this.task_location = task_location;
    }

    public String getTask_text() {
        return task_text;
    }

    public void setTask_text(String task_text) {
        this.task_text = task_text;
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


    public String getTask_timeout() {
        return task_timeout;
    }

    public void setTask_timeout(String task_timeout) {
        this.task_timeout = task_timeout;
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


    public Timestamp getTask_begintime() {
        return task_begintime;
    }

    public void setTask_begintime(Timestamp task_begintime) {
        this.task_begintime = task_begintime;
    }

    public Timestamp getTask_endtime() {
        return task_endtime;
    }

    public void setTask_endtime(Timestamp task_endtime) {
        this.task_endtime = task_endtime;
    }

    public Timestamp getTask_finishtime() {
        return task_finishtime;
    }

    public void setTask_finishtime(Timestamp task_finishtime) {
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


    public Integer getTask_coin() {
        return task_coin;
    }

    public void setTask_coin(Integer task_coin) {
        this.task_coin = task_coin;
    }

    public Integer getTask_score() {
        return task_score;
    }

    public void setTask_score(Integer task_score) {
        this.task_score = task_score;
    }
}

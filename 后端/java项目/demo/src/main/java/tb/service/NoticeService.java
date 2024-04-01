package tb.service;

import tb.entity.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeService {
    //notice相关需要什么接口呢？
    //1、用户界面相关：只需要按时间顺序获取全部notice
    //2、管理员层面，可以增删查，不做改

    //按时间顺序获取全部公告
    List<Map<String,Object>> getNotice();

    //发布公告
    void publishNotice(Map<String,Object> notice);

    //删除公告
    void deleteNotice(Integer notice_id);

}

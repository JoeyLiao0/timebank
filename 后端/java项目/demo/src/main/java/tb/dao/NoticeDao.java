package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Notice;

import java.util.List;

public interface NoticeDao {

    /**
     *
     * 按时间顺序获取全部公告
     *
     */
    public List<Notice> SelectAllNotice();

    /**
     *
     * 新增公告
     *
     */
    public void InsertNewNotice(@Param("notice") Notice notice);

    /**
     *
     * 通过公告编号删除公告
     * 使用方法：先用SelectAllNotice 获取到全部公告，在其中找到某个并获取编号用此接口删除
     *
     */
    public void DeleteNoticeById(Integer id);

    /**
     *
     * 通过公告编号删除，同上,但是传参为Notice对象
     *
     */
    public void DeleteNotice(@Param("notice") Notice notice);

}

package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Notice;

import java.util.List;

public interface NoticeDao {

    //获取所有公告
    List<Notice> SelectAllNotice();

    //新建公告
    void InsertNewNotice(@Param("notice") Notice notice);

    //通过编号删除公告
    void DeleteNoticeById(Integer id);

}

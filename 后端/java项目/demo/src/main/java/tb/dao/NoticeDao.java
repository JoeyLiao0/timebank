package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Notice;

import java.util.List;

public interface NoticeDao {

    List<Notice> SelectAllNotice();

    void InsertNewNotice(@Param("notice") Notice notice);

    void DeleteNoticeById(Integer id);

    void DeleteNotice(@Param("notice") Notice notice);

}

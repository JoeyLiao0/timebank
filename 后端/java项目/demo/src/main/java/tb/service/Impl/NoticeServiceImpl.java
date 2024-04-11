package tb.service.Impl;

import org.apache.ibatis.session.SqlSession;

import tb.dao.NoticeDao;

import tb.entity.Notice;
import tb.service.NoticeService;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeServiceImpl implements NoticeService {

    //按时间顺序获取全部公告
    public List<Map<String, Object>> getNotice() {
        try (SqlSession session = mySqlSession.getSqSession()) {
            NoticeDao noticeDao = session.getMapper(NoticeDao.class);
            List<Notice> notices = noticeDao.SelectAllNotice();
            List<Map<String, Object>> noticeArray = new ArrayList<>();

            for (Notice notice : notices) {

                Map<String, Object> map = new HashMap<>();

                map.put("notice_id", notice.getNotice_id());
                map.put("notice_time", notice.getNotice_time());
                map.put("notice_title", notice.getNotice_title());
                map.put("notice_content", notice.getNotice_content());

                noticeArray.add(map);
            }

            return noticeArray;
        } catch (Exception e) {
            return null;

        }
    }

    //发布公告
    public String publishNotice(Map<String, Object> map) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                NoticeDao noticeDao = session.getMapper(NoticeDao.class);

                Notice notice = new Notice();

                notice.setNotice_time(new Timestamp(System.currentTimeMillis()));
                notice.setNotice_title((String) map.get("notice_title"));
                notice.setNotice_content((String) map.get("notice_content"));

                noticeDao.InsertNewNotice(notice);

                session.commit();

                return "yes";
            } catch (Exception e) {
                if (session != null) session.rollback();
                return null;
            }
        }

    }

    //删除公告
    public String deleteNotice(Integer notice_id) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                NoticeDao noticeDao = session.getMapper(NoticeDao.class);

                noticeDao.DeleteNoticeById(notice_id);

                session.commit();

                return "yes";
            } catch (Exception e) {
                if (session != null) session.rollback();
                return null;
            }
        }

    }
}

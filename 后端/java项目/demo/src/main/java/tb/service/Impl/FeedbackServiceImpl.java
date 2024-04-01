package tb.service.Impl;

import tb.service.FeedbackService;

import java.util.List;
import java.util.Map;

public class FeedbackServiceImpl implements FeedbackService {
    @Override
    public List<Map<String, Object>> getUnreadMessage(Integer cu_id, Integer cs_id) {
        return null;
    }

    @Override
    public void isRead(String role, Integer id, List<Integer> ids) {

    }

    @Override
    public List<Map<String, Object>> getHistory(Integer cu_id, Integer cs_id) {
        return null;
    }

    @Override
    public Integer sendMessage(Map<String, Object> datamap) {
        return 1;
    }
}

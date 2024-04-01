package tb.service.Impl;

import tb.service.TalkService;
import tb.service.TaskService;

import java.util.List;
import java.util.Map;

public class TalkServiceImpl implements TalkService{
    @Override
    public List<Map<String, Object>> getUnreadMessage(Integer cu_id, Integer task_id) {
        return null;
    }

    @Override
    public void isRead(Integer cu_id, List<Integer> ids) {

    }

    @Override
    public List<Map<String, Object>> getHistory(Integer cu_id, Integer task_id) {
        return null;
    }

    @Override
    public Integer sendMessage(Map<String, Object> datamap) {
        return 1;
    }
}

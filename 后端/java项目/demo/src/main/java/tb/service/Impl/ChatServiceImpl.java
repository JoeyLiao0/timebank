package tb.service.Impl;

import tb.service.ChatService;

import java.util.List;
import java.util.Map;

public class ChatServiceImpl implements ChatService {


    @Override
    public List<Map<String, Object>> getUnreadMessage(String role, Integer id) {
        //基本逻辑按时间顺序获取chat里的全部数据
        //从后往前读，获取最先一个读取的chat_id 上面的自然都是读取的
        //然后再反转顺序返回



        return null;
    }

    @Override
    public void isRead(String role, Integer id, List<Integer> ids) {
        //读取了，在这些chat_id 对应的chat_isread 里添加 空格sessionId

    }

    @Override
    public List<Map<String, Object>> getHistory(String role, Integer id) {
        //获取全部历史消息，暂时设定为全部历史消息获取


        return null;
    }

    @Override
    public Integer sendMessage(Map<String, Object> datamap) {
        //就是把这个消息存储在数据库就行了，其他操作在上层已经添加转发了
        /*
                map.put("senderSessionId",msg.get(0).get("senderSessionId"));
                map.put("content",msg.get(0).get("content"));
                map.put("contentType",msg.get(0).get("contentType"));
                map.put("timestamp",msg.get(0).get("timestamp"));
        */
        return 1;
    }
}

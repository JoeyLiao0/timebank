package tb.service;

import java.util.List;
import java.util.Map;

public interface FeedbackService {

    /**
     * 返回格式
     * [{
     * type:"feedback"
     * id
     * senderSessionId
     * content
     * contentType
     * timestamp
     * isRead
     * }]
     */
    //根据客服和用户的编号唯一确定地获取未读消息
    List<Map<String, Object>> getUnreadMessage(String role, Integer cu_id, Integer cs_id);

    String getUnreadMessage(Integer id, String role);

    //根据用户身份、用户id和消息id添加已读状态
    String isRead(String role, Integer id, List<Integer> ids);


    /**
     * 返回格式
     * [{
     * type:"feedback"
     * id
     * senderSessionId
     * content
     * contentType
     * timestamp
     * isRead
     * }]
     */
    //获取全部历史消息，即获取自己id下的全部已读消息
    List<Map<String, Object>> getHistory(String role, Integer cu_id, Integer cs_id);

    //添加一条消息记录
    Integer sendMessage(Map<String, Object> datamap);

}

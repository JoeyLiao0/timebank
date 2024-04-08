package tb.service;

import java.util.List;
import java.util.Map;

public interface ChatService {
    /**
     * 返回格式
     * [{
     * type:"chat"
     * id
     * senderSessionId
     * content
     * contentType
     * timestamp
     * isRead
     * }]
     */
    //获取未读消息
    List<Map<String, Object>> getUnreadMessage(String role, Integer id);

    String getUnreadMessage2(String role, Integer id);

    //根据用户身份、用户id和消息id添加已读状态
    String isRead(String role, Integer id, List<Integer> ids);

    /**
     * 返回格式
     * [{
     * type:"chat"
     * id
     * senderSessionId
     * content
     * contentType
     * timestamp
     * isRead
     * }]
     */
    //获取内部交流通道的全部历史消息，即获取自己id下的全部已读消息
    List<Map<String, Object>> getHistory(String role, Integer id);

    /**
     * 参数格式
     * [{
     * type:"chat"
     * id
     * senderSessionId
     * content
     * contentType
     * timestamp
     * }]
     */
    //添加一条消息记录
    Integer sendMessage(Map<String, Object> datamap);
}

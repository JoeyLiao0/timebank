package tb.service;

import java.util.List;
import java.util.Map;

public interface TalkService {
    //talk,任务双方的对话，目前假设只有发布者和领取者两个人能看到

    //根据用户id和任务id获取这个任务对话中的未读消息

    /**
     *
     * 返回格式
     * [{
     *  type:"talk"
     *  id
     *  taskId
     *  senderSessionId
     *  content
     *  contentType
     *  timestamp
     *  isRead
     *  }]
     */


    List<Map<String,Object>> getUnreadMessage(Integer cu_id,Integer task_id);





    //根据用户id和消息id添加已读状态
    void isRead(Integer cu_id,List<Integer> ids);





    /**
     *
     * 返回格式
     * [{
     *  type:"talk"
     *  id
     *  taskId
     *  senderSessionId
     *  content
     *  contentType
     *  timestamp
     *  isRead
     *  }]
     */
    //获取这个任务id下的全部历史消息
    List<Map<String,Object>> getHistory(Integer cu_id,Integer task_id);



    /**
     *
     * 参数格式
     * [{
     *  type:"talk"
     *  taskId
     *  senderSessionId
     *  receiverSessionId
     *  content
     *  contentType
     *  timestamp
     *  }]
     */
    //添加一条消息记录
    Integer sendMessage(Map<String, Object> datamap);

}

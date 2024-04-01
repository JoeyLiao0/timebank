package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Chat;
import java.util.List;

public interface ChatDao {



    //通过消息编号筛选新消息
    Chat selectChatById(Integer chat_id);

    //根据编号角色筛选新消息
    List<Chat> selectBySenderIdAndSenderRole(@Param("chat_senderid") Integer senderId,@Param("chat_senderrole") String senderRole);

    //发送新消息
    void insertChat(@Param("chat") Chat chat);

    void updateChatIsRead(@Param("chat_id") Integer chat_id ,@Param("chat_isread") String chat_isread);
    //
    void deleteChatById(Integer chat_id);
}
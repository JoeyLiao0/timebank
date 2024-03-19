package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Chat;
import java.util.List;

public interface ChatDao {

    List<Chat> SelectAllChat();

    void InsertChat(@Param("chat") Chat chat);


    //TODO 思考怎么获取聊天信息

}

package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Chat;

import java.util.List;

public interface ChatDao {

    /**
     *
     *按时间顺序获取全部消息
     *
     */
    public List<Chat> SelectAllChat();

    /**
     *
     * 新消息存储
     *
     */
    public void InsertChat(@Param("chat") Chat chat);


    //TODO 思考怎么获取聊天信息

}

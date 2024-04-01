package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Talk;

import java.util.List;

public interface TalkDao {



    //根据消息编号查询消息
    Talk selectByTalkId(Integer talk_Id);

    //根据任务编号查询消息
    List<Talk> selectByTaskId(Integer talk_taskId);

    //发送新消息
    void insertTalk(@Param("talk") Talk talk);

    void updateTalkIsRead(@Param("talk_id")Integer talk_id , @Param("talk_isread")String talk_isread);

    //根据编号删除消息
    void deleteTalkById(Integer talk_id);

}

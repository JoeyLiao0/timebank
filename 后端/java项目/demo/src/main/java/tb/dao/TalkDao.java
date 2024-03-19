package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Talk;

import java.util.List;

public interface TalkDao {

    List<Talk> SelectTalkByTaskId(Integer taskId);

    void InsertNewTalk(@Param("talk") Talk talk);

}

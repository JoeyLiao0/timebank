package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Talk;

import java.util.List;

public interface TalkDao {

    /**
     *
     * 根据任务的编号获取该任务下双方的历史对话记录
     *
     */
    public List<Talk> SelectTalkByTaskId(Integer talk_taskid);

    /**
     *
     * 新增对话记录
     *
     */
    public void InsertNewTalk(@Param("talk") Talk talk);

}

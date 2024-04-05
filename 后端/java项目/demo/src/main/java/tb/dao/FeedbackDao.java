package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Feedback;

import java.util.List;

public interface FeedbackDao {
    // 插入一条反馈记录
    int insertFeedback(@Param("feedback") Feedback feedback);

    // 根据反馈编号查询反馈记录
    Feedback selectFeedbackById(@Param("feedback_id") Integer feedbackId);

    // 根据编号和角色查询反馈记录列表
    List<Feedback> selectByIdAndRole(@Param("sender_role") String senderRole,@Param("sender_id")Integer senderId,@Param("receiver_role") String receiverRole,@Param("receiver_id") int receiverId);

    //根据编号删除记录
    void deleteFeedbackById(Integer id);

    void updateFeedbackIsRead(@Param("feedback_id")Integer feedback_id,@Param("feedback_isread")String feedback_isread);

}

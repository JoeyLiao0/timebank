package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Task;

import java.util.List;

/**
 * ****提前约定****
 * SelectAllTask SelectAllTaskByPublisherId 是例外，包含未审核的，未通过的全部任务
 * 其他函数名没有audit的 都是审核过的 且 通过的，讨论有没有被领取，有没有被完成，默认该任务审核通过
 */
public interface TaskDao {

    List<Task> SelectTaskByStatusAndTimeout(@Param("status") String status,@Param("timeout") String timeout);

    List<Task> SelectTaskByPublisherId(Integer publisherId);

    List<Task> SelectTaskByTakerId(Integer taskerId);

    Task SelectTaskById(Integer taskId);

    void PublishNewTask(@Param("task") Task task);

    void UpdateTask(@Param("task") Task task);

    void DeleteTaskById(Integer taskId);

}

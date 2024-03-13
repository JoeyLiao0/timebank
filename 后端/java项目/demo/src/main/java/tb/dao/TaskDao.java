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

    public List<Task> selectTaskByStatus(Integer status);

    public List<Task> selectTaskByPublisherId(Integer publisherId);

    public List<Task> selectTaskByTakerId(Integer taskerId);

    public void publishNewTask(@Param("task") Task task);

    public void InsertTask(@Param("task") Task task);

    public void UpdateTask(@Param("task") Task task);

    public void DeleteTaskById(Integer id);

    public void DeleteTask(@Param("task") Task task);


}

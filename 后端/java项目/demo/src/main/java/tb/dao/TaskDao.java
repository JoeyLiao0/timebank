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

    /*
     * 管理员界面用到
     */

    /**
     *
     * 获取全部任务（无论有没有被审核）
     *
     */
    public List<Task> SelectAllTask();

    /**
     *
     * 获取全部未被审核过的任务
     *
     */
    public List<Task> SelectAllUnauditedTask();

    /**
     *
     * 获取全部被审核过但未通过的任务
     *
     */
    public List<Task> SelectAllAuditedNotSucceedTask();

    /**
     *
     * 获取全部被审核过且通过的任务
     *
     */
    public List<Task> SelectAllAuditedSucceedTask();

    /**
     *
     * 获取全部未被领取的任务
     *
     */
    public List<Task> SelectAllUnclaimedTask();

    /**
     *
     * 获取全部被领取但未被完成的的任务
     *
     */
    public List<Task> SelectAllClaimedNotFinishedTask();

    /**
     *
     * 获取全部完成的任务
     *
     */
    public List<Task> SelectAllFinishedTask();


    /*
     * 普通用户界面用到
     */

    //发布者视角，关乎我发布的任务




    /**
     *
     * 通过发布者编号获取全部发布的任务(无论审核状态)
     *
     */
    public List<Task> SelectAllTaskByPublisherId(Integer publisherId);

    /**
     *
     * 通过发布者获取全部未被审核过的任务
     *
     */
    public List<Task> SelectAllUnauditedTaskByPublisherId(Integer publisherId);

    /**
     *
     * 通过发布者获取全部被审核过但未通过的任务
     *
     */
    public List<Task> SelectAllAuditedNotSucceedTaskByPublisherId(Integer publisherId);

    /**
     *
     * 通过发布者获取全部被审核过且通过的任务
     *
     */
    public List<Task> SelectAllAuditedSucceedTaskByPublisherId(Integer publisherId);

    /**
     *
     * 获取发布者编号全部未被领取的任务
     *
     */
    public List<Task> SelectAllUnclaimedTaskByPublisherId(Integer publisherId);

    /**
     *
     * 通过发布者编号获取全部被领取但未被完成的的任务
     *
     */
    public List<Task> SelectAllClaimedNotFinishedTaskByPublisherId(Integer publisherId);

    /**
     *
     * 发布者编号获取全部完成的任务
     *
     */
    public List<Task> SelectAllFinishedTaskByPublisherId(Integer publisherId);


    //接受者视角，关乎我领取的任务

    /**
     *
     * 通过接受者编号获取全部被领取但未被完成的的任务
     *
     */
    public List<Task> SelectAllClaimedNotFinishedTaskByTakerId(Integer takerId);

    /**
     *
     * 通过接受者编号获取全部完成的任务
     *
     */
    public List<Task> SelectAllFinishedTaskByTakerId(Integer takerId);

    /**
     *
     * 增加任务
     *
     */
    public void InsertTask(@Param("task") Task task);

    /**
     *
     * 更新任务
     *
     */
    public void UpdateTask(@Param("task") Task task);

    /**
     *
     * 通过任务编号删除任务
     *
     */
    public void DeleteTaskById(Integer id);

    /**
     *
     * 删除任务
     *
     */
    public void DeleteTask(@Param("task") Task task);


}

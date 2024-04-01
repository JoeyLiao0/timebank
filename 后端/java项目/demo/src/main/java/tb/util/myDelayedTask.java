package tb.util;

import tb.service.Impl.TaskServiceImpl;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class myDelayedTask implements Delayed {
    private final long executeTime;
    private final Integer taskId;

    private final Integer delayType;//1：发布者截止时间  2：发布者截止时间+30min  3：领取者确认完成后三天

    public myDelayedTask(long delay, Integer taskId, Integer delayType){
        this.executeTime = System.currentTimeMillis()+delay;
        this.taskId = taskId;
        this.delayType = delayType;
    }

    @Override
    public long getDelay(TimeUnit unit){
        long diff = executeTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MICROSECONDS);
    }

    @Override
    public int compareTo(Delayed task){
        if(this.executeTime < ((myDelayedTask) task).executeTime){
            return -1;
        }
        if(this.executeTime > ((myDelayedTask) task).executeTime){
            return 1;
        }
        return 0;
    }

    public void execute() {
        //这里分类
        TaskServiceImpl taskService = new TaskServiceImpl();

        taskService.timeout(delayType,taskId);//基于封装的设计，交给service处理

        // 执行任务的逻辑
        System.out.println("Executing task: " + taskId + " at " + System.currentTimeMillis() + "delayType :" + delayType);
    }


}

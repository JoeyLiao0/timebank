package tb.util;

import java.util.concurrent.DelayQueue;

public class myDelayQueueManager {
    private DelayQueue<myDelayedTask> delayQueue = new DelayQueue<>();

    private volatile boolean running = true ;

    public void start(){
        Thread managerThread = new Thread(()->{
            while (running) {
                try{
                    myDelayedTask task = delayQueue.take();
                    task.execute();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        managerThread.setDaemon(true);
        managerThread.start();
    }

    public void stop(){
        running = false;
    }

    public void addTask(myDelayedTask task){
        delayQueue.put(task);
    }
}

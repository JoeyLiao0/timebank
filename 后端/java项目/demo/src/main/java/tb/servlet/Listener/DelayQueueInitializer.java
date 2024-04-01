package tb.servlet.Listener;

import tb.util.myDelayQueueManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DelayQueueInitializer implements ServletContextListener {
    private static myDelayQueueManager delayQueueManager;

    @Override
    public void contextInitialized(ServletContextEvent sce){
        delayQueueManager = new myDelayQueueManager();
        delayQueueManager.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
        if(delayQueueManager != null){
            delayQueueManager.stop();
        }
    }

    public static myDelayQueueManager getDelayQueueManager(){
        return delayQueueManager;
    }

}

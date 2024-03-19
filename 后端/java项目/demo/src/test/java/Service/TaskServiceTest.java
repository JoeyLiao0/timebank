package Service;

import org.junit.Test;
import tb.service.Impl.TaskServiceImpl;
import tb.service.TaskService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*

    ArrayList<Map<String,Object>> selectAvailableTask();

    ArrayList<Map<String,Object>> selectMyPublish(Map<String , Object> datamap);

    ArrayList<Map<String,Object>> selectMyTake(Map<String , Object> datamap);

    String publishNewTask(Map<String,Object> datamap);

    String take(Integer id,Integer task_id);

    String publishCancel(Integer id,Integer task_id);

    String takeCancel(Integer id,Integer task_id);

    String takerFinish(Integer id,Integer task_id);

    String publisherFinish(Integer id,Integer task_id);

    String comment(Integer id,Integer task_id,Integer task_score);
 */
public class TaskServiceTest {
    public TaskServiceImpl taskServiceImpl = new TaskServiceImpl();


    @Test
    public void TestSelectAvailableTask(){
        ArrayList<Map<String,Object>> tasks = taskServiceImpl.selectAvailableTask();

        for(Map<String,Object> task : tasks){
            System.out.println(task);
        }
    }

    @Test
    public void TestSelectMyPublish(){
        ArrayList<Map<String,Object>> tasks = taskServiceImpl.selectMyPublish(1);

        for(Map<String,Object> task : tasks){
            System.out.println(task);
        }
    }

    @Test
    public void TestSelectMyTake(){

        ArrayList<Map<String,Object>> tasks = taskServiceImpl.selectMyTake(1);

        for(Map<String,Object> task : tasks){
            System.out.println(task);
        }
    }

    @Test
    public void TestPublishNewTask(){
        Map<String , Object> Datamap = new HashMap<>();

        Datamap.put("task_begintime",new Timestamp(System.currentTimeMillis()));
        Datamap.put("task_endtime",new Timestamp(System.currentTimeMillis()+1000*3600));
        Datamap.put("task_coin",10);
        Datamap.put("task_title","标题");
        Datamap.put("task_text","内容");
        Datamap.put("task_location","任务位置");

        System.out.println(taskServiceImpl.publishNewTask(2,Datamap));
    }

    @Test
    public void TestTake(){
        System.out.println(taskServiceImpl.take(1,3));
    }

    @Test
    public void TestPublishCancel(){

    }

    @Test
    public void TestTakeCancel(){

    }

    @Test
    public void TestTakerFinish(){

    }

    @Test
    public void TestPublisherFinish(){

    }

    @Test
    public void Comment(){

    }

}

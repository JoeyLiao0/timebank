package tb.service;

import tb.entity.Task;

import java.util.ArrayList;
import java.util.Map;

public interface TaskService {

    public void finishTask(Integer id);

    public void finishTask(Task task);

    public ArrayList<Map<String,Object>> selectAvailable(Integer id);//剔除自己发布的任务

    public ArrayList<Map<String,Object>> selectMyPublish(Integer id);

    public ArrayList<Map<String,Object>> selectMyTake(Integer id);

    public String publish(Integer id,Map<String,Object> datamap);

}

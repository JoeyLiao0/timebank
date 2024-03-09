package tb.service;

import tb.entity.Task;

import java.util.ArrayList;
import java.util.Map;

public interface TaskService {

    public void finishTask(Integer id);

    public void finishTask(Task task);

    public ArrayList<Map<String,Object>> selectAvailableTask(Map<String , Object> datamap);//剔除自己发布的任务

    public ArrayList<Map<String,Object>> selectMyPublish(Map<String , Object> datamap);

    public ArrayList<Map<String,Object>> selectMyTake(Map<String , Object> datamap);

    public String publishNewTask(Map<String,Object> datamap);

}

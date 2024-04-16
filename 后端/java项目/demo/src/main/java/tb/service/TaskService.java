package tb.service;


import java.util.ArrayList;
import java.util.Map;

public interface TaskService {

    ArrayList<Map<String, Object>> selectTaskByStatus(String status, String timeout);

    ArrayList<Map<String, Object>> selectAvailableTask();

    ArrayList<Map<String, Object>> selectMyPublish(Integer id);

    ArrayList<Map<String, Object>> selectMyTake(Integer id);

    String publishNewTask(Integer id, Map<String, Object> datamap);

    String take(Integer id, Integer task_id);

    String publishCancel(Integer id, Integer task_id);

    String takeCancel(Integer id, Integer task_id);

    String takerFinish(Integer id, Integer task_id,String evidenceImg);

    String publisherFinish(Integer id, Integer task_id);

    String comment(Integer id, Integer task_id, Integer task_score);

    String review(Integer task_id, Integer au_id, boolean flag, String reviewAdvice);

    String timeout(Integer delayType, Integer task_id);

}

package tb.service;

import tb.entity.Task;

public interface TaskService {

    public void finishTask(Integer id);

    public void finishTask(Task task);

}

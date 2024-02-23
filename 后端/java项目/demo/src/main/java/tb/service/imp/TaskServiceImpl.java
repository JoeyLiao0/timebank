package tb.service.imp;

import tb.entity.Task;

public interface TaskServiceImpl {

    public void finishTask(Integer id);

    public void finishTask(Task task);

}

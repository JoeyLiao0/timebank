package DAO;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tb.dao.TaskDao;
import tb.entity.Task;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.List;

/*
    List<Task> SelectTaskByStatus(String status);

    List<Task> SelectTaskByPublisherId(Integer publisherId);

    List<Task> SelectTaskByTakerId(Integer taskerId);

    Task SelectTaskById(Integer taskId);

    void publishNewTask(@Param("task") Task task);

    void UpdateTask(@Param("task") Task task);

    void DeleteTaskById(Integer taskId);
 */
public class TaskDAOTest {
    @Test
    public void TestSelectTaskByStatusAndTimeout(){
        try(SqlSession session = mySqlSession.getSqSession()){
            TaskDao taskDao = session.getMapper(TaskDao.class);

            List<Task> tasks = taskDao.SelectTaskByStatusAndTimeout("1000","00");

            for(Task task : tasks){
                System.out.println(task);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectTaskByPublisherId(){
        try(SqlSession session = mySqlSession.getSqSession()){
            TaskDao taskDao = session.getMapper(TaskDao.class);

            List<Task> tasks = taskDao.SelectTaskByPublisherId(1);

            for(Task task : tasks){
                System.out.println(task);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectTaskByTakerId(){
        try(SqlSession session = mySqlSession.getSqSession()){

            TaskDao taskDao = session.getMapper(TaskDao.class);

            List<Task> tasks = taskDao.SelectTaskByTakerId(1);

            for(Task task : tasks){
                System.out.println(task);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelectTaskById(){
        try(SqlSession session = mySqlSession.getSqSession()){

            TaskDao taskDao = session.getMapper(TaskDao.class);

            Task task = taskDao.SelectTaskById(1);

            System.out.println(task);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void TestPublishNewTask(){
        try(SqlSession session = mySqlSession.getSqSession()){

            TaskDao taskDao = session.getMapper(TaskDao.class);

            Task task = new Task();
            task.setTask_status("0000");
            task.setTask_timeout("00");
            task.setTask_begintime(new Timestamp(System.currentTimeMillis()));
            task.setTask_endtime(new Timestamp(System.currentTimeMillis()+3600*1000));

            task.setTask_publisherid(1);
            task.setTask_coin(10);
            task.setTask_location("景苑");



            taskDao.PublishNewTask(task);

            session.commit();

        }
    }

    @Test
    public void TestUpdateTask(){
        try(SqlSession session = mySqlSession.getSqSession()){

            TaskDao taskDao = session.getMapper(TaskDao.class);


            Task task = taskDao.SelectTaskById(1);

            task.setTask_finishtime(new Timestamp(System.currentTimeMillis()));
            task.setTask_taketime(new Timestamp(System.currentTimeMillis()-10000));
            task.setTask_takerid(2);
            task.setTask_score(1);
            task.setTask_auid(2);
            task.setTask_status("2110");
            task.setTask_timeout("10");


            taskDao.UpdateTask(task);

            session.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void TestDeleteTaskById(){
        try(SqlSession session = mySqlSession.getSqSession()){

            TaskDao taskDao = session.getMapper(TaskDao.class);

            taskDao.DeleteTaskById(1);

            session.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}

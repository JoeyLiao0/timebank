package tb.service.Impl;

import org.apache.ibatis.session.SqlSession;
import tb.dao.AdDao;
import tb.dao.CuDao;
import tb.dao.TaskDao;
import tb.entity.Ad;
import tb.entity.Cu;
import tb.entity.Task;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TaskServiceImpl {

    //筛选所有的待领取的任务
    public ArrayList<Map<String,Object>> selectAvailableTask(Map<String , Object> datamap){
        try(SqlSession session = mySqlSession.getSqSession()) {

            Integer status = 2000;//审核

            Integer timeout = 1;

            TaskDao taskDao = session.getMapper(TaskDao.class);

            CuDao cuDao = session.getMapper(CuDao.class);

            ArrayList<Task> taskArray = (ArrayList<Task>) taskDao.selectTaskByStatus(status);

            ArrayList<Map<String,Object>> taskArray2 = new ArrayList<>();

            for(Task task : taskArray){
                if(Objects.equals(task.getTask_timeout(), timeout)){

                    Cu cu = cuDao.SelectCuById(task.getTask_publisherid());

                    Map<String,Object> taskMap = new HashMap<>();

                    taskMap.put("task_id",task.getTask_id());
                    taskMap.put("task_publisherId",task.getTask_publisherid());
                    taskMap.put("task_publisherName",cu.getCu_name());
                    taskMap.put("task_coin",task.getTask_coin());
                    taskMap.put("task_title",task.getTask_title());
                    taskMap.put("task_text",task.getTask_text());
                    taskMap.put("task_location",task.getTask_location());
                    taskMap.put("task_begintime",task.getTask_begintime().getTime()/1000);
                    taskMap.put("task_endtime",task.getTask_endtime().getTime()/1000);

                    taskArray2.add(taskMap);
                }
            }

            return taskArray2;

        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Map<String,Object>> selectMyPublish(Map<String , Object> datamap){
        try(SqlSession session = mySqlSession.getSqSession()) {

            Integer id =(Integer)datamap.get("id");

            TaskDao taskDao = session.getMapper(TaskDao.class);

            CuDao cuDao = session.getMapper(CuDao.class);

            ArrayList<Task> taskArray = (ArrayList<Task>) taskDao.selectTaskByPublisherId(id);

            ArrayList<Map<String,Object>> taskArray2 = new ArrayList<>();

            for(Task task : taskArray){

                Cu cu = cuDao.SelectCuById(task.getTask_takerid());

                Map<String,Object> map = new HashMap<>();

                map.put("task_id",task.getTask_id());
                map.put("task_coin",task.getTask_coin());
                map.put("task_title",task.getTask_title());
                map.put("task_text",task.getTask_text());

                map.put("task_status",task.getTask_status());
                map.put("task_timeout",task.getTask_timeout());

                map.put("task_advice",task.getTask_advice());

                map.put("task_taskerId",task.getTask_takerid());
                map.put("task_takerName",cu.getCu_name());

                map.put("task_score",task.getTask_score());
                map.put("task_begintime",task.getTask_begintime().getTime()/1000);
                map.put("task_endtime",task.getTask_endtime().getTime()/1000);
                map.put("task_finishtime",task.getTask_finishtime().getTime()/1000);

                taskArray2.add(map);
            }

            return taskArray2;

        } catch (Exception e) {
            return null;
        }


    }

    public ArrayList<Map<String,Object>> selectMyTake(Map<String , Object> datamap){
        try(SqlSession session = mySqlSession.getSqSession()) {

            Integer id =(Integer)datamap.get("id");

            TaskDao taskDao = session.getMapper(TaskDao.class);

            CuDao cuDao = session.getMapper(CuDao.class);

            ArrayList<Task> taskArray = (ArrayList<Task>) taskDao.selectTaskByTakerId(id);

            ArrayList<Map<String,Object>> taskArray2 = new ArrayList<>();

            for(Task task : taskArray){
                Cu cu = cuDao.SelectCuById(task.getTask_publisherid());

                Map<String,Object> map = new HashMap<>();

                map.put("task_id",task.getTask_id());
                map.put("task_coin",task.getTask_coin());
                map.put("task_title",task.getTask_title());
                map.put("task_text",task.getTask_text());

                map.put("task_status",task.getTask_status());
                map.put("task_timeout",task.getTask_timeout());

                map.put("task_publisherId",task.getTask_publisherid());
                map.put("task_publisherName",cu.getCu_name());

                map.put("task_score",task.getTask_score());
                map.put("task_begintime",task.getTask_begintime().getTime()/1000);
                map.put("task_endtime",task.getTask_endtime().getTime()/1000);
                map.put("task_finishtime",task.getTask_finishtime().getTime()/1000);

                taskArray2.add(map);

            }

            return taskArray2;

        } catch (Exception e) {
            return null;
        }
    }

    public String publishNewTask(Map<String,Object> datamap){
        try(SqlSession session = mySqlSession.getSqSession()) {

            TaskDao taskDao = session.getMapper(TaskDao.class);
            CuDao cuDao = session.getMapper(CuDao.class);

            Task task = new Task();
            Cu cu = cuDao.SelectCuById((Integer) task.getTask_publisherid());

            cu.setCu_coin(cu.getCu_coin()-(Integer)datamap.get("task_coin"));

            //TODO 也许有其他的错误原因

            if((Integer)datamap.get("task_coin")>cu.getCu_coin()){
                throw new Exception("时间币余额不足！");
            }else{
                task.setTask_begintime(new Timestamp((long) datamap.get("task_begintime")*1000));
                task.setTask_endtime(new Timestamp((long) datamap.get("task_endtime")*1000));
                task.setTask_publisherid((Integer) datamap.get("task_publisherId"));
                task.setTask_coin((Integer) datamap.get("task_coin"));
                task.setTask_title((String) datamap.get("task_title"));
                task.setTask_text( (String) datamap.get("task_text"));
                task.setTask_location( (String) datamap.get("task_location"));
                task.setTask_timeout("0");
                task.setTask_status("0000");

                taskDao.publishNewTask(task);

                cuDao.UpdateCu(cu);

                session.commit();

                return "yes";
            }

        } catch (Exception e) {
            return e.getMessage();
        }
    }
}

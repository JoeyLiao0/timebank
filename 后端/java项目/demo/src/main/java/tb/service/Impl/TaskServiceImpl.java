package tb.service.Impl;

import org.apache.ibatis.session.SqlSession;

import tb.dao.CuDao;
import tb.dao.TaskDao;

import tb.entity.Cu;
import tb.entity.Task;
import tb.service.TaskService;
import tb.servlet.Listener.DelayQueueInitializer;
import tb.util.myDelayQueueManager;
import tb.util.myDelayedTask;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TaskServiceImpl implements TaskService {

    public ArrayList<Map<String, Object>> selectTaskByStatus(String status, String timeout) {
        try (SqlSession session = mySqlSession.getSqSession()) {


            TaskDao taskDao = session.getMapper(TaskDao.class);

//            CuDao cuDao = session.getMapper(CuDao.class);

            ArrayList<Task> taskArray = (ArrayList<Task>) taskDao.SelectTaskByStatusAndTimeout(status, timeout);

            ArrayList<Map<String, Object>> taskArray2 = new ArrayList<>();

            for (Task task : taskArray) {

                Map<String, Object> taskMap = new HashMap<>();

                taskMap.put("task_id", task.getTask_id());
                taskMap.put("task_coin", task.getTask_coin());
                taskMap.put("task_title", task.getTask_title());
                taskMap.put("task_text", task.getTask_text());
                taskMap.put("task_status", status + "_" + timeout);
                taskMap.put("task_advice", task.getTask_advice());
                taskMap.put("task_takerId", task.getTask_takerid());
                taskMap.put("task_publisherId", task.getTask_publisherid());
                taskMap.put("task_begintime", task.getTask_begintime().getTime());
                taskMap.put("task_endtime", task.getTask_endtime().getTime());
                taskMap.put("task_finishtime", task.getTask_finishtime().getTime());
                taskMap.put("task_score", task.getTask_score());
                taskMap.put("task_location", task.getTask_location());

                taskArray2.add(taskMap);

            }

            return taskArray2;

        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Map<String, Object>> selectTaskByNotStatus(String status, String timeout) {
        try (SqlSession session = mySqlSession.getSqSession()) {


            TaskDao taskDao = session.getMapper(TaskDao.class);

//            CuDao cuDao = session.getMapper(CuDao.class);

            ArrayList<Task> taskArray = (ArrayList<Task>) taskDao.SelectTaskByNotStatusAndTimeout(status, timeout);

            ArrayList<Map<String, Object>> taskArray2 = new ArrayList<>();

            for (Task task : taskArray) {

                Map<String, Object> taskMap = new HashMap<>();

                taskMap.put("task_id", task.getTask_id());
                taskMap.put("task_coin", task.getTask_coin());
                taskMap.put("task_title", task.getTask_title());
                taskMap.put("task_text", task.getTask_text());
                taskMap.put("task_status", status + "_" + timeout);
                taskMap.put("task_advice", task.getTask_advice());
                taskMap.put("task_takerId", task.getTask_takerid());
                taskMap.put("task_publisherId", task.getTask_publisherid());
                taskMap.put("task_begintime", task.getTask_begintime().getTime());
                taskMap.put("task_endtime", task.getTask_endtime().getTime());
                taskMap.put("task_finishtime", task.getTask_finishtime().getTime());
                taskMap.put("task_score", task.getTask_score());
                taskMap.put("task_location", task.getTask_location());

                taskArray2.add(taskMap);

            }

            return taskArray2;

        } catch (Exception e) {
            return null;
        }
    }

    //筛选所有的待领取的任务
    public ArrayList<Map<String, Object>> selectAvailableTask() {
        try (SqlSession session = mySqlSession.getSqSession()) {

            String status = "2000";//审核完成待领取
            String timeout = "00";//同时未超时

            TaskDao taskDao = session.getMapper(TaskDao.class);

            CuDao cuDao = session.getMapper(CuDao.class);

            ArrayList<Task> taskArray = (ArrayList<Task>) taskDao.SelectTaskByStatusAndTimeout(status, timeout);

            ArrayList<Map<String, Object>> taskArray2 = new ArrayList<>();

            for (Task task : taskArray) {

                Cu cu = cuDao.SelectCuById(task.getTask_publisherid());

                Map<String, Object> taskMap = new HashMap<>();

                taskMap.put("task_id", task.getTask_id());
                taskMap.put("task_publisherId", task.getTask_publisherid());
                taskMap.put("task_publisherName", cu.getCu_name());
                taskMap.put("task_coin", task.getTask_coin());
                taskMap.put("task_title", task.getTask_title());
                taskMap.put("task_text", task.getTask_text());
                taskMap.put("task_location", task.getTask_location());
                taskMap.put("task_begintime", task.getTask_begintime().getTime() / 1000);
                taskMap.put("task_endtime", task.getTask_endtime().getTime() / 1000);

                taskArray2.add(taskMap);

            }

            return taskArray2;

        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Map<String, Object>> selectMyPublish(Integer id) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            TaskDao taskDao = session.getMapper(TaskDao.class);

            CuDao cuDao = session.getMapper(CuDao.class);

            ArrayList<Task> taskArray = (ArrayList<Task>) taskDao.SelectTaskByPublisherId(id);

            ArrayList<Map<String, Object>> taskArray2 = new ArrayList<>();

            for (Task task : taskArray) {

                Cu cu = new Cu();
                if (task.getTask_takerid() != null) cu = cuDao.SelectCuById(task.getTask_takerid());

                Map<String, Object> map = new HashMap<>();

                map.put("task_id", task.getTask_id());
                map.put("task_coin", task.getTask_coin());
                map.put("task_title", task.getTask_title());
                map.put("task_text", task.getTask_text());

                map.put("task_status", task.getTask_status() + "-" + task.getTask_timeout());

                map.put("task_advice", task.getTask_advice());

                map.put("task_taskerId", task.getTask_takerid());
                map.put("task_takerName", cu.getCu_name());

                map.put("task_location", task.getTask_location());

                map.put("task_score", task.getTask_score());
                map.put("task_begintime", task.getTask_begintime());
                map.put("task_endtime", task.getTask_endtime());
                map.put("task_finishtime", task.getTask_finishtime());

                taskArray2.add(map);
            }

            return taskArray2;

        } catch (Exception e) {
            return null;
        }


    }

    public ArrayList<Map<String, Object>> selectMyTake(Integer id) {
        try (SqlSession session = mySqlSession.getSqSession()) {

            TaskDao taskDao = session.getMapper(TaskDao.class);

            CuDao cuDao = session.getMapper(CuDao.class);

            ArrayList<Task> taskArray = (ArrayList<Task>) taskDao.SelectTaskByTakerId(id);

            ArrayList<Map<String, Object>> taskArray2 = new ArrayList<>();

            for (Task task : taskArray) {
                Cu cu = cuDao.SelectCuById(task.getTask_publisherid());

                Map<String, Object> map = new HashMap<>();

                map.put("task_id", task.getTask_id());
                map.put("task_coin", task.getTask_coin());
                map.put("task_title", task.getTask_title());
                map.put("task_text", task.getTask_text());

                map.put("task_status", task.getTask_status());

                map.put("task_publisherId", task.getTask_publisherid());
                map.put("task_publisherName", cu.getCu_name());

                map.put("task_location", task.getTask_location());

                map.put("task_score", task.getTask_score());
                map.put("task_begintime", task.getTask_begintime().getTime() / 1000);
                map.put("task_endtime", task.getTask_endtime().getTime() / 1000);
                map.put("task_finishtime", task.getTask_finishtime().getTime() / 1000);

                taskArray2.add(map);

            }

            return taskArray2;

        } catch (Exception e) {
            return null;
        }
    }

    public String publishNewTask(Integer publisherId, Map<String, Object> datamap) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                TaskDao taskDao = session.getMapper(TaskDao.class);
                CuDao cuDao = session.getMapper(CuDao.class);

                Task task = new Task();
                Cu cu = cuDao.SelectCuById(publisherId);

                //TODO 也许有其他的错误原因

                if ((Integer) datamap.get("task_coin") > cu.getCu_coin()) {
                    throw new Exception("时间币余额不足！");
                } else {

                    cu.setCu_coin(cu.getCu_coin() - (Integer) datamap.get("task_coin"));//扣除时间币
                    cu.setCu_release(cu.getCu_release() + 1);

                    task.setTask_begintime((Timestamp) datamap.get("task_begintime"));//已经转化为Timestamp了
                    task.setTask_endtime((Timestamp) datamap.get("task_endtime"));
                    task.setTask_publisherid(publisherId);
                    task.setTask_coin((Integer) datamap.get("task_coin"));
                    task.setTask_title((String) datamap.get("task_title"));
                    task.setTask_text((String) datamap.get("task_text"));
                    task.setTask_location((String) datamap.get("task_location"));

                    task.setTask_status("0000");
                    task.setTask_timeout("00");

                    taskDao.PublishNewTask(task);

                    cuDao.UpdateCu(cu);

                    session.commit();

                    //这里把任务加入延时队列
                    myDelayQueueManager delayQueueManager = DelayQueueInitializer.getDelayQueueManager();
                    Integer taskId = task.getTask_id();
                    long delay = ((Timestamp) datamap.get("task_endtime")).getTime() - System.currentTimeMillis();
                    myDelayedTask task1 = new myDelayedTask(delay, taskId, 1);//截止日期
                    myDelayedTask task2 = new myDelayedTask(delay + 1800 * 1000, taskId, 2);//可以补完成的范围，现在写死为半小时
                    delayQueueManager.addTask(task1);
                    delayQueueManager.addTask(task2);

                    return "yes";
                }

            } catch (Exception e) {
                if (session != null) session.rollback();
                return e.getMessage();
            }

        }
    }

    public String take(Integer takerId, Integer task_id) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                //一方面要判断当前id能不能再次领取这个任务了，（一天能领取的任务有限额）

                //没完成的任务最多只能有x个

                TaskDao taskDao = session.getMapper(TaskDao.class);
                CuDao cuDao = session.getMapper(CuDao.class);

                Cu cu = cuDao.SelectCuById(takerId);

                Task task = taskDao.SelectTaskById(task_id);
                if (task.getTask_publisherid().equals(takerId)) {
                    throw new Exception("不能领取自己发布的任务！");
                } else if (cu.getCu_accept() - cu.getCu_finish() > 3) {//TODO 这个3可以给管理员设置
                    throw new Exception("领取但未完成的任务数量有限额，请先完成领取的任务！");
                } else if (!(task.getTask_status().equals("2000") && task.getTask_timeout().equals("00"))) {
                    throw new Exception("该任务无法领取,请刷新界面！");
                } else {

                    task.setTask_takerid(takerId);

                    task.setTask_status("2100");

                    task.setTask_taketime(new Timestamp(System.currentTimeMillis()));

                    taskDao.UpdateTask(task);

                    session.commit();

                    return "yes";
                }
            } catch (Exception e) {
                if (session != null) session.rollback();
                return e.getMessage();
            }
        }
    }

    public String publishCancel(Integer publisherId, Integer task_id) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                //要判断这个任务是不是他发布的

                TaskDao taskDao = session.getMapper(TaskDao.class);

                Task task = taskDao.SelectTaskById(task_id);

                if (task.getTask_publisherid() != (int) publisherId) {
                    return "此任务并不是你发布的，无法由你取消！";
                } else if (!((task.getTask_status().equals("2000") || task.getTask_status().equals("0000")) && task.getTask_timeout().equals("00"))) {
                    return "此任务不是待审核/待领取状态，无法取消！";
                } else {
                    task.setTask_status("9999");//被发布者取消就设置为9999
                    task.setTask_timeout("00");
                    taskDao.UpdateTask(task);

                    session.commit();
                    return "yes";
                }
            } catch (Exception e) {
                if (session != null) session.rollback();
                return e.getMessage();
            }
        }
    }


    public String takeCancel(Integer takerId, Integer task_id) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                //要判断这个任务是不是他领取的
                TaskDao taskDao = session.getMapper(TaskDao.class);

                Task task = taskDao.SelectTaskById(task_id);

                if (task.getTask_takerid() != (int) takerId) {
                    throw new Exception("此任务并不是你领取的，无法取消领取！");
                } else if (!(task.getTask_status().equals("2100") && task.getTask_timeout().equals("00")
                        && (new Timestamp(System.currentTimeMillis()).getTime() - task.getTask_taketime().getTime()) / 1000 < 2 * 60)) {//这个2可以由管理员设置
                    throw new Exception("此任务无法取消领取！");
                } else {
                    task.setTask_status("2000");
                    //根据状态码来展现数据，所以过时的数据不需要清零，下次会覆盖
                    taskDao.UpdateTask(task);
                    session.commit();
                    return "yes";
                }
            } catch (Exception e) {
                if (session != null) session.rollback();
                return e.getMessage();
            }

        }
    }

    public String takerFinish(Integer taskerId, Integer task_id) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                //TODO 这个要加一个证据图片存储

                //要判断这个任务是不是他领取的
                TaskDao taskDao = session.getMapper(TaskDao.class);

                Task task = taskDao.SelectTaskById(task_id);

                if (task.getTask_takerid() != (int) taskerId) {
                    throw new Exception("此任务并不是你领取的，无法完成！");
                } else if (!task.getTask_status().equals("2100-00") && !task.getTask_status().equals("2100-10")) {
                    throw new Exception("此任务未在规定的时间完成！");
                } else {

                    task.setTask_status("2110");//状态更新

                    task.setTask_finishtime(new Timestamp(System.currentTimeMillis()));//任务完成时间更新

                    taskDao.UpdateTask(task);

                    session.commit();

                    return "yes";
                }
            } catch (Exception e) {
                if (session != null) session.rollback();
                return e.getMessage();
            }
        }
    }

    public String publisherFinish(Integer publisherId, Integer task_id) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                //要判断这个任务是不是他发布的
                TaskDao taskDao = session.getMapper(TaskDao.class);
                CuDao cuDao = session.getMapper(CuDao.class);

                Task task = taskDao.SelectTaskById(task_id);

                Cu taker = cuDao.SelectCuById(task.getTask_takerid());

                if (task.getTask_publisherid() != (int) publisherId) {
                    throw new Exception("此任务并不是你发布的，无法确认完成！");
                } else if (!(task.getTask_status().equals("2110")
                        && (task.getTask_timeout().equals("00") || task.getTask_timeout().equals("10")))) {
                    throw new Exception("此任务并没有被完成，无法确认完成！");
                } else {

                    task.setTask_status("2120");

                    taskDao.UpdateTask(task);

                    //发放时间币，任务领取者加时间币

                    Integer task_coin = task.getTask_coin();

                    taker.setCu_coin(taker.getCu_coin() + task_coin);//获得时间币
                    taker.setCu_finish(taker.getCu_finish() + 1);//完成任务数+1

                    cuDao.UpdateCu(taker);

                    session.commit();

                    //这里把任务加入延时队列
                    myDelayQueueManager delayQueueManager = DelayQueueInitializer.getDelayQueueManager();
                    Integer taskId = task.getTask_id();

                    long delay = 60 * 60 * 24 * 3 * 1000;
                    myDelayedTask delayedTask = new myDelayedTask(delay, taskId, 3);//截止日期
                    delayQueueManager.addTask(delayedTask);

                    return "yes";
                }

            } catch (Exception e) {
                if (session != null) session.rollback();
                return e.getMessage();
            }
        }
    }

    public String comment(Integer publisherId, Integer task_id, Integer task_score) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                //要判断这个任务是不是他发布的
                TaskDao taskDao = session.getMapper(TaskDao.class);

                Task task = taskDao.SelectTaskById(task_id);

                if (task.getTask_publisherid() != (int) publisherId) {
                    throw new Exception("此任务并不是你发布的，无法评价！");
                } else if (!(task.getTask_status().equals("2120") && (task.getTask_timeout().equals("00") || task.getTask_timeout().equals("10")))) {
                    throw new Exception("无法评价！");
                } else {

                    task.setTask_status("2121");

                    if (task_score <= 5 && task_score >= 1) throw new Exception("评分非法！");

                    task.setTask_score(task_score);

                    taskDao.UpdateTask(task);

                    session.commit();

                    return "yes";
                }
            } catch (Exception e) {
                if (session != null) session.rollback();
                return e.getMessage();
            }
        }
    }

    public String review(Integer task_id, Integer au_id, boolean flag, String reviewAdvice) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {

                TaskDao taskDao = session.getMapper(TaskDao.class);

                Task task = taskDao.SelectTaskById(task_id);

                if (task != null) {
                    if (flag && task.getTask_timeout().equals("00")) {
                        //如果审核通过则为2
                        task.setTask_status("2000");
                        task.setTask_auid(au_id);
                    } else {
                        task.setTask_status("1000");
                        task.setTask_auid(au_id);
                        task.setTask_advice(reviewAdvice);
                    }
                }

                taskDao.UpdateTask(task);

                session.commit();

                return "yes";

            } catch (Exception e) {
                if (session != null) session.rollback();
                return e.getMessage();
            }
        }
    }

    public String timeout(Integer delayType, Integer task_id) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            try {
                TaskDao taskDao = session.getMapper(TaskDao.class);
                Task task = taskDao.SelectTaskById(task_id);

                String task_timeout = task.getTask_timeout();
                String task_status = task.getTask_status();

                CuDao cuDao = session.getMapper(CuDao.class);
                Cu cu = cuDao.SelectCuById(task.getTask_publisherid());

                //TODO 这里要考虑到领取
                if (delayType == 1 && task_status.charAt(2) == '0' && task_timeout.equals("00")) {
                    //到了第一个截止日期
                    //如果未完成，应该把时间状态码设为10
                    task_timeout = "10";
                    task.setTask_timeout(task_timeout);

                    //返还一定比例时间币
                    Integer task_coin = task.getTask_coin() / 2;
                    Integer back_coin = task.getTask_coin() - task_coin;
                    task.setTask_coin(task_coin);

                    cu.setCu_coin(cu.getCu_coin() + back_coin);
                    //TODO 这里可能要记录
                    cuDao.UpdateCu(cu);

                } else if (delayType == 2 && task_status.charAt(2) == '0' && task_timeout.equals("10")) {
                    //到了第二个截止日期
                    task_timeout = "20";
                    //返还全部时间币
                    task.setTask_timeout(task_timeout);

                    Integer task_coin = task.getTask_coin();

                    task.setTask_coin(0);

                    cu.setCu_coin(cu.getCu_coin() + task_coin);
                    cuDao.UpdateCu(cu);
                } else if (delayType == 3 && task_status.charAt(3) == '0') {
                    //到了第三个截止日期
                    task_timeout = task_timeout.charAt(0) + "1";
                    task.setTask_timeout(task_timeout);
                    task.setTask_score(5);
                }

                taskDao.UpdateTask(task);

                session.commit();

                return "yes";

            } catch (Exception e) {
                if (session != null) session.rollback();
                return e.getMessage();
            }
        }

    }
}

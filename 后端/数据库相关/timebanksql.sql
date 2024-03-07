create database timebank;
use timebank;

DROP TABLE IF EXISTS AD;
CREATE TABLE AD(
    `ad_id` INT AUTO_INCREMENT COMMENT '管理员编号' ,
    `ad_name` VARCHAR(255) NOT NULL  COMMENT '管理员账号' ,
    `ad_pwd` VARCHAR(255) NOT NULL  COMMENT '管理员密码' ,
    `ad_salt` VARCHAR(255) NOT NULL  COMMENT '盐值' ,
    `ad_img` VARCHAR(255)   COMMENT '管理员头像' ,
    `ad_login` DATETIME NOT NULL  COMMENT '管理员上次登录时间' ,
    `ad_tel` VARCHAR(255) NOT NULL  COMMENT '管理员手机号' ,
    `ad_register` DATETIME NOT NULL  COMMENT '管理员注册时间' ,
    `ad_status` INT NOT NULL  COMMENT '禁用与否' ,
    PRIMARY KEY (ad_id)
)  COMMENT = '管理员';


DROP TABLE IF EXISTS AU;
CREATE TABLE AU(
    `au_id` INT AUTO_INCREMENT COMMENT '审核员编号' ,
    `au_name` VARCHAR(255) NOT NULL  COMMENT '审核员用户名' ,
    `au_pwd` VARCHAR(255) NOT NULL  COMMENT '审核员密码' ,
    `au_salt` VARCHAR(255) NOT NULL  COMMENT '盐值' ,
    `au_img` VARCHAR(255)   COMMENT '审核员头像' ,
    `au_login` DATETIME NOT NULL  COMMENT '审核员上次登录时间' ,
    `au_tel` VARCHAR(255) NOT NULL  COMMENT '审核员手机号' ,
    `au_register` DATETIME NOT NULL  COMMENT '审核员注册时间' ,
    PRIMARY KEY (au_id)
)  COMMENT = '审核员';

DROP TABLE IF EXISTS CU;
CREATE TABLE CU(
    `cu_id` INT AUTO_INCREMENT COMMENT '用户编号' ,
    `cu_name` VARCHAR(255) NOT NULL  COMMENT '用户名' ,
    `cu_pwd` VARCHAR(255) NOT NULL  COMMENT '用户密码' ,
    `cu_salt` VARCHAR(255) NOT NULL  COMMENT '盐值' ,
    `cu_tel` VARCHAR(255) NOT NULL  COMMENT '用户手机号' ,
    `cu_icon` INT NOT NULL DEFAULT 0 COMMENT '用户时间币数' ,
    `cu_register` DATETIME NOT NULL  COMMENT '用户注册时间' ,
    `cu_login` DATETIME NOT NULL  COMMENT '用户上次登录时间' ,
    `cu_release` INT NOT NULL DEFAULT 0 COMMENT '用户发布任务数' ,
    `cu_accept` INT NOT NULL DEFAULT 0 COMMENT '用户接受任务数' ,
    `cu_img` VARCHAR(255)   COMMENT '用户头像' ,
    PRIMARY KEY (cu_id)
)  COMMENT = '普通用户';

DROP TABLE IF EXISTS CS;
CREATE TABLE CS(
    `cs_id` INT AUTO_INCREMENT COMMENT '客服编号' ,
    `cs_name` VARCHAR(255) NOT NULL  COMMENT '客服用户名' ,
    `cs_pwd` VARCHAR(255) NOT NULL  COMMENT '客服密码' ,
    `cs_salt` VARCHAR(255) NOT NULL  COMMENT '盐值' ,
    `cs_img` VARCHAR(255)   COMMENT '客服头像' ,
    `cs_login` VARCHAR(255) NOT NULL  COMMENT '客服上次登录时间' ,
    `cs_tel` VARCHAR(255) NOT NULL  COMMENT '客服手机号' ,
    `cs_register` DATETIME NOT NULL  COMMENT '客服注册时间' ,
    PRIMARY KEY (cs_id)
)  COMMENT = '客服';

DROP TABLE IF EXISTS NOTICE;
CREATE TABLE NOTICE(
    `notice_id` INT AUTO_INCREMENT COMMENT '公告编号' ,
    `notice_begintime` DATETIME NOT NULL  COMMENT '公告开始时间' ,
    `notice_endtime` DATETIME NOT NULL  COMMENT '公告结束时间' ,
    `notice_text` VARCHAR(255) NOT NULL  COMMENT '公告正文' ,
    `notice_title` VARCHAR(255) NOT NULL  COMMENT '公告标题' ,
    PRIMARY KEY (notice_id)
)  COMMENT = '公告';

DROP TABLE IF EXISTS TASK;
CREATE TABLE TASK(
    `task_id` INT AUTO_INCREMENT COMMENT '任务编号' ,
    `task_status` VARCHAR(255) NOT NULL  COMMENT '任务领取状态（领取/未领取）' ,
    `task_result` VARCHAR(255) NOT NULL  COMMENT '任务完成状态（完成/未完成）' ,
    `task_begintime` DATETIME NOT NULL  COMMENT '任务开始展示时间' ,
    `task_endtime` DATETIME NOT NULL  COMMENT '任务结束展示时间' ,
    `task_finishtime` DATETIME   COMMENT '任务完成时间' ,
    `task_publisherid` INT NOT NULL  COMMENT '任务发起者编号' ,
    `task_takerid` INT   COMMENT '任务接受者编号' ,
    `task_coin` INT NOT NULL  COMMENT '任务时间币' ,
    `task_score` INT   COMMENT '任务满意度评分（一星-五星）' ,
    `task_audit` VARCHAR(255) NOT NULL  COMMENT '任务审核状态(未审核/审核未通过/审核通过)' ,
    `task_advice` VARCHAR(255)   COMMENT '任务审核意见（审核未通过时非空）' ,
    `task_auid` INT   COMMENT '任务审核员编号' ,
    PRIMARY KEY (task_id)
)  COMMENT = '任务';



DROP TABLE IF EXISTS TALK;
CREATE TABLE TALK(
    `talk_taskid` INT NOT NULL  COMMENT '对话任务编号' ,
    `talk_senderid` INT NOT NULL  COMMENT '发言者编号' ,
    `talk_content` VARCHAR(255) NOT NULL  COMMENT '信息内容' ,
    `talk_timestamp` VARCHAR(255) NOT NULL  COMMENT '信息时间戳' ,
    PRIMARY KEY (talk_taskid)
)  COMMENT = '任务双方对话';

DROP TABLE IF EXISTS CHAT;
CREATE TABLE CHAT(
    `chat_id` INT AUTO_INCREMENT COMMENT '消息编号' ,
    `chat_senderid` INT NOT NULL  COMMENT '消息发送者编号' ,
    `chat_content` VARCHAR(255) NOT NULL  COMMENT '消息内容' ,
    `chat_timestamp` DATETIME NOT NULL  COMMENT '消息时间戳' ,
    `chat_senderrole` VARCHAR(255) NOT NULL  COMMENT '消息发送者身份' ,
    PRIMARY KEY (chat_id)
)  COMMENT = '内部交流通道';

-- 插入示例数据  
INSERT INTO AD (`ad_name`, `ad_pwd`, `ad_salt`, `ad_img`, `ad_login`, `ad_tel`, `ad_register`, `ad_status`)  
VALUES   
('admin1', 'encrypted_password1', 'salt1', 'path_to_image1.jpg', '2023-10-23 10:00:00', '12345678901', '2023-10-01 10:00:00', 1),  
('admin2', 'encrypted_password2', 'salt2', 'path_to_image2.jpg', '2023-10-23 11:00:00', '23456789012', '2023-10-02 10:00:00', 0),  
('admin3', 'encrypted_password3', 'salt3', 'path_to_image3.jpg', '2023-10-23 12:00:00', '34567890123', '2023-10-03 10:00:00', 1);

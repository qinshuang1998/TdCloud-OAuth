/*
MySQL Data Transfer

Date: 2019-11-16 10:23:58
*/

SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `oauth`;
CREATE DATABASE `oauth` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;
USE `oauth`;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `app_id` varchar(128) NOT NULL,
  `app_key` varchar(128) NOT NULL,
  `app_name` varchar(10) NOT NULL,
  `app_description` tinytext NOT NULL,
  `app_logo` varchar(128) NOT NULL,
  `redirect_uri` varchar(128) NOT NULL,
  `scope` varchar(5) NOT NULL DEFAULT 'read',
  PRIMARY KEY (`app_id`),
  UNIQUE KEY `app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `refresh_token` varchar(128) NOT NULL,
  `token_id` varchar(128) NOT NULL,
  `app_id` varchar(128) NOT NULL,
  `user_identity` varchar(10) NOT NULL,
  `user_role` char(7) NOT NULL,
  `expire_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`refresh_token`),
  UNIQUE KEY `token_owner` (`app_id`,`user_identity`) USING BTREE,
  UNIQUE KEY `refresh_token` (`refresh_token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for oauth_user_student
-- ----------------------------
DROP TABLE IF EXISTS `oauth_user_student`;
CREATE TABLE `oauth_user_student` (
  `stu_number` varchar(10) NOT NULL,
  `stu_pwd` varchar(100) NOT NULL,
  `stu_name` varchar(10) NOT NULL,
  `stu_sex` char(1) NOT NULL,
  `stu_nation` varchar(5) NOT NULL,
  `stu_major` varchar(30) NOT NULL,
  `stu_id_card` char(18) NOT NULL,
  `stu_phone` char(11) NOT NULL,
  `stu_email` varchar(20) NOT NULL,
  PRIMARY KEY (`stu_number`),
  UNIQUE KEY `stu_number` (`stu_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for oauth_user_teacher
-- ----------------------------
DROP TABLE IF EXISTS `oauth_user_teacher`;
CREATE TABLE `oauth_user_teacher` (
  `tch_id` int(11) unsigned NOT NULL,
  `tch_worknum` varchar(10) NOT NULL COMMENT '工号',
  `tch_name` varchar(10) NOT NULL COMMENT '姓名(作为用户名)',
  `tch_pwd` char(37) NOT NULL,
  `tch_id_card` char(18) NOT NULL COMMENT '身份证',
  `tch_phone` char(11) NOT NULL COMMENT '手机号',
  `tch_work_type` varchar(20) NOT NULL COMMENT '职位',
  PRIMARY KEY (`tch_id`),
  UNIQUE KEY `tch_name` (`tch_name`,`tch_pwd`) USING BTREE,
  UNIQUE KEY `tch_worknum` (`tch_worknum`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for oauth_zf_cookie
-- ----------------------------
DROP TABLE IF EXISTS `oauth_zf_cookie`;
CREATE TABLE `oauth_zf_cookie` (
  `stu_number` varchar(10) NOT NULL,
  `cookie_hash` char(37) NOT NULL,
  `cookie_prefix` varchar(20) NOT NULL,
  `cookie_value` varchar(128) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`stu_number`),
  UNIQUE KEY `cookie_hash` (`cookie_hash`) USING BTREE,
  UNIQUE KEY `stu_number` (`stu_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
SET FOREIGN_KEY_CHECKS=1;

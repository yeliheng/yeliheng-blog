/*
 Navicat Premium Data Transfer

 Source Server         : Yeliheng学生机
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : www.yeliheng.com:3306
 Source Schema         : yeliheng_blog_dev

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 10/02/2022 14:49:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for operate_log
-- ----------------------------
DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '操作者ID',
  `username` varchar(255) DEFAULT NULL COMMENT '操作者用户名',
  `module_name` varchar(255) DEFAULT NULL COMMENT '模块名称',
  `operate_type` int(2) DEFAULT '0' COMMENT '操作类型',
  `ip` varchar(255) DEFAULT NULL COMMENT '操作者IP',
  `url` varchar(255) DEFAULT NULL COMMENT '接口URL',
  `method` varchar(255) DEFAULT NULL COMMENT '请求方法',
  `function` varchar(255) DEFAULT NULL COMMENT '调用函数',
  `param` varchar(255) DEFAULT NULL COMMENT '操作参数',
  `result` varchar(255) DEFAULT NULL COMMENT '操作结果',
  `error_detail` varchar(255) DEFAULT NULL COMMENT '错误详情',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态: 1成功,0失败',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;

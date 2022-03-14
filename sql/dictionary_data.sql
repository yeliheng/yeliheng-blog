
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dictionary_data
-- ----------------------------
DROP TABLE IF EXISTS `dictionary_data`;
CREATE TABLE `dictionary_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `dict_label` varchar(100) NOT NULL COMMENT '字典文本',
  `dict_value` varchar(100) NOT NULL COMMENT '字典值',
  `dict_code` varchar(100) NOT NULL COMMENT '字典代码',
  `class_type` varchar(100) DEFAULT NULL COMMENT '前端class: primary,danger..',
  `description` varchar(255) DEFAULT NULL COMMENT '字典描述',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态 (1启用，0禁用)',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of dictionary_data
-- ----------------------------
BEGIN;
INSERT INTO `dictionary_data` VALUES (1, 1, '停用', '0', 'disable_enable', 'info', '表示一个项已被禁用', 1, 0, '2022-03-13 15:16:50', '2022-03-13 15:16:56');
INSERT INTO `dictionary_data` VALUES (2, 1, '启用', '1', 'disable_enable', 'success', '表示一个项已被启用', 1, 0, '2022-03-13 15:17:15', '2022-03-13 15:17:15');
INSERT INTO `dictionary_data` VALUES (3, 1, '锁定', '1', 'lock_unlock', 'warning', '表示一个项已被锁定', 1, 0, '2022-03-13 15:18:13', '2022-03-13 15:18:51');
INSERT INTO `dictionary_data` VALUES (4, 1, '正常', '0', 'lock_unlock', 'success', '表示一个项已解锁', 1, 0, '2022-03-13 15:18:46', '2022-03-13 15:18:46');
INSERT INTO `dictionary_data` VALUES (5, 1, '公开', '1', 'public_private', 'success', '表示一个项公开可见', 1, 0, '2022-03-13 15:20:46', '2022-03-13 15:20:46');
INSERT INTO `dictionary_data` VALUES (6, 1, '私密', '0', 'public_private', 'warning', '表示一个项不被公开', 1, 0, '2022-03-13 15:21:08', '2022-03-13 15:21:08');
INSERT INTO `dictionary_data` VALUES (7, 1, '是', '1', 'yes_no', NULL, NULL, 1, 0, '2022-03-13 21:18:33', '2022-03-13 21:18:33');
INSERT INTO `dictionary_data` VALUES (8, 1, '否', '0', 'yes_no', NULL, NULL, 1, 0, '2022-03-13 21:18:41', '2022-03-13 21:18:41');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

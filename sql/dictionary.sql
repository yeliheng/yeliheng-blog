
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `dict_name` varchar(100) NOT NULL COMMENT '字典名称',
  `dict_code` varchar(100) NOT NULL COMMENT '字典代码',
  `description` varchar(255) DEFAULT NULL COMMENT '字典描述',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态（1启用 0禁用）',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
BEGIN;
INSERT INTO `dictionary` VALUES (1, 1, '停用/启用', 'disable_enable', '系统停用/启用开关选项', 1, '2022-03-13 15:15:56', '2022-03-13 15:15:56');
INSERT INTO `dictionary` VALUES (2, 1, '锁定/解锁', 'lock_unlock', '系统锁定/解锁开关选项', 1, '2022-03-13 15:17:48', '2022-03-13 15:17:48');
INSERT INTO `dictionary` VALUES (3, 1, '公开/私密', 'public_private', '内容的公开/私密切换', 1, '2022-03-13 15:19:34', '2022-03-13 15:22:44');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

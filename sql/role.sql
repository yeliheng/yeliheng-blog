
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_char` varchar(255) NOT NULL COMMENT '角色标识',
  `role_name` varchar(255) NOT NULL COMMENT '角色名称',
  `locked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'admin', '管理员', 0, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role` VALUES (2, 'test', '测试角色', 0, '2022-01-11 16:13:45', '2022-01-11 17:00:18');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

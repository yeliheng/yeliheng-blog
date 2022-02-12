
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
BEGIN;
INSERT INTO `role_menu` VALUES (1, 1, 1, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (2, 1, 17, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (3, 1, 18, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (4, 1, 19, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (5, 1, 20, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (6, 1, 21, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (7, 1, 22, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (8, 1, 23, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (9, 1, 24, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (10, 1, 25, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (11, 1, 26, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (12, 1, 27, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (13, 1, 28, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (14, 1, 29, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (15, 1, 30, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (16, 1, 31, '2022-01-11 16:12:40', '2022-01-11 16:12:40');
INSERT INTO `role_menu` VALUES (44, 2, 1, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (45, 2, 4, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (46, 2, 9, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (47, 2, 14, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (48, 2, 18, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (49, 2, 23, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (50, 2, 28, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (51, 2, 2, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (52, 2, 7, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (53, 2, 12, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (54, 2, 17, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (55, 2, 22, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
INSERT INTO `role_menu` VALUES (56, 2, 27, '2022-01-11 17:00:18', '2022-01-11 17:00:18');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

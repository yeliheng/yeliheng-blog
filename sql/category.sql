
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `user_id` bigint(20) NOT NULL COMMENT '创建者id',
  `category_name` varchar(20) NOT NULL COMMENT '分类名称',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of category
-- ----------------------------
BEGIN;
INSERT INTO `category` VALUES (1, 1, 'Java', '2022-01-01 13:33:19', '2022-01-01 13:33:19');
INSERT INTO `category` VALUES (2, 1, 'VR', '2022-01-01 13:33:25', '2022-01-01 13:33:25');
INSERT INTO `category` VALUES (3, 1, 'Linux', '2022-01-01 13:33:30', '2022-01-01 13:33:30');
INSERT INTO `category` VALUES (4, 1, 'C', '2022-01-01 13:33:38', '2022-01-01 13:33:38');
INSERT INTO `category` VALUES (5, 1, '杂文记事', '2022-01-01 13:33:44', '2022-01-01 13:33:44');
INSERT INTO `category` VALUES (6, 1, 'Android', '2022-01-01 13:33:52', '2022-01-01 13:33:52');
INSERT INTO `category` VALUES (7, 1, 'Git', '2022-01-01 13:36:40', '2022-01-01 13:36:40');
INSERT INTO `category` VALUES (8, 1, '捣鼓', '2022-01-01 14:13:38', '2022-01-01 14:13:38');
INSERT INTO `category` VALUES (9, 1, '博客系统', '2022-01-04 17:30:10', '2022-01-04 17:30:23');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

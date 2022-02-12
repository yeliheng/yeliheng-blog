
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `user_id` bigint(20) NOT NULL COMMENT '创建者id',
  `tag_name` varchar(20) NOT NULL COMMENT '标签名称',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tag
-- ----------------------------
BEGIN;
INSERT INTO `tag` VALUES (1, 1, 'SpringBoot', '2022-01-04 16:43:02', '2022-01-04 16:43:02');
INSERT INTO `tag` VALUES (2, 1, 'Android', '2022-01-04 16:43:10', '2022-01-04 16:43:10');
INSERT INTO `tag` VALUES (3, 1, 'Git', '2022-01-04 16:43:19', '2022-01-04 16:43:19');
INSERT INTO `tag` VALUES (4, 1, 'SSL', '2022-01-04 16:44:18', '2022-01-04 16:44:18');
INSERT INTO `tag` VALUES (5, 1, 'Https', '2022-01-04 16:44:22', '2022-01-04 16:44:22');
INSERT INTO `tag` VALUES (6, 1, 'Java', '2022-01-04 17:30:32', '2022-01-04 17:30:32');
INSERT INTO `tag` VALUES (7, 1, 'Mybatis', '2022-01-04 17:30:39', '2022-01-04 17:30:39');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

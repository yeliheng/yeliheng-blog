
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  `tag_id` bigint(20) NOT NULL COMMENT '标签id',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of article_tag
-- ----------------------------
BEGIN;
INSERT INTO `article_tag` VALUES (1, 5, 2, '2022-01-04 16:43:49', '2022-01-04 16:43:49');
INSERT INTO `article_tag` VALUES (45, 22, 1, '2022-01-05 15:51:35', '2022-01-05 15:51:35');
INSERT INTO `article_tag` VALUES (46, 22, 3, '2022-01-05 15:51:35', '2022-01-05 15:51:35');
INSERT INTO `article_tag` VALUES (47, 22, 2, '2022-01-05 15:51:35', '2022-01-05 15:51:35');
INSERT INTO `article_tag` VALUES (48, 22, 4, '2022-01-05 15:51:35', '2022-01-05 15:51:35');
INSERT INTO `article_tag` VALUES (49, 22, 5, '2022-01-05 15:51:35', '2022-01-05 15:51:35');
INSERT INTO `article_tag` VALUES (50, 22, 6, '2022-01-05 15:51:35', '2022-01-05 15:51:35');
INSERT INTO `article_tag` VALUES (51, 22, 7, '2022-01-05 15:51:35', '2022-01-05 15:51:35');
INSERT INTO `article_tag` VALUES (52, 10, 2, '2022-01-11 12:02:51', '2022-01-11 12:02:51');
INSERT INTO `article_tag` VALUES (53, 10, 3, '2022-01-11 12:02:51', '2022-01-11 12:02:51');
INSERT INTO `article_tag` VALUES (54, 10, 4, '2022-01-11 12:02:51', '2022-01-11 12:02:51');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

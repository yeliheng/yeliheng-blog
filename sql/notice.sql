
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `user_id` bigint(20) NOT NULL COMMENT '公告发起人',
  `content` varchar(255) NOT NULL COMMENT '公告内容',
  `level` tinyint(1) DEFAULT '0' COMMENT '紧急程度',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of notice
-- ----------------------------
BEGIN;
INSERT INTO `notice` VALUES (1, 1, '这是Yeliheng的个人博客,我会在这里分享我计算机学习生涯中的笔记、总结、技术干货...', 0, '2022-01-02 23:27:17', '2022-01-02 23:27:20');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

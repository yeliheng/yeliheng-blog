
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) DEFAULT NULL COMMENT '电子邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `locked` tinyint(1) DEFAULT '0' COMMENT '锁定用户',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$glZSVIoD0yN6PxDtvQurFueBlPbe8ipaBDcP16l6P7HFB/PXLp5om', NULL, NULL, NULL, NULL, 0, '2022-01-01 00:00:00', '2022-01-01 00:00:00');
INSERT INTO `user` VALUES (2, 'ynetwork', '$2a$10$0IzG3BHA54qfcuIapNx5pOX7VhoJh2e/S0ZlEiFDoWGhB8oPtzxF.', '测试用户', NULL, NULL, NULL, 0, '2022-01-01 00:00:00', '2022-01-11 16:13:55');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

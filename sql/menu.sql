
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单id',
  `menu_type` varchar(10) NOT NULL COMMENT '菜单类型',
  `menu_name` varchar(255) NOT NULL COMMENT '菜单名称',
  `order_num` int(11) DEFAULT '100' COMMENT '排序',
  `external` tinyint(1) DEFAULT '0' COMMENT '是否外链',
  `path` varchar(255) DEFAULT NULL COMMENT 'URL',
  `component` varchar(255) DEFAULT NULL COMMENT '组件地址',
  `cache` tinyint(1) DEFAULT '1' COMMENT '是否缓存',
  `hidden` tinyint(1) DEFAULT '0' COMMENT '可见性',
  `permission` varchar(255) DEFAULT NULL COMMENT '菜单权限',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单字体图标',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` VALUES (1, 1, 0, 'SECOND', '首页', 0, 0, '/dashboard', 'Home', 1, 0, NULL, 'fa-dashboard', '2021-11-02 06:26:04', '2022-02-10 14:54:49');
INSERT INTO `menu` VALUES (2, 1, 0, 'TOP', '文章管理', 0, 0, '/articles', 'Layout', 1, 0, '', 'fa-file', '2021-10-29 18:48:46', '2022-03-14 21:27:51');
INSERT INTO `menu` VALUES (3, 1, 2, 'SECOND', '发布文章', 0, 0, '/articles/publish', 'article/Article', 1, 0, 'admin:articles:add', 'fa-paper-plane', '2021-10-29 18:55:14', '2021-10-29 18:55:14');
INSERT INTO `menu` VALUES (4, 1, 2, 'SECOND', '文章列表', 0, 0, '/articles/list', 'article/ArticleList', 1, 0, 'admin:articles:list', 'fa-file-text', '2021-10-29 18:55:57', '2021-10-29 18:55:57');
INSERT INTO `menu` VALUES (5, 1, 2, 'SECOND', '修改文章', 0, 0, '/articles/:id', 'article/Edit', 1, 1, 'admin:articles:edit', NULL, '2021-11-14 06:23:28', '2021-11-14 06:23:28');
INSERT INTO `menu` VALUES (6, 1, 2, 'BUTTON', '删除文章', 0, 0, '', '', 1, 0, 'admin:articles:delete', NULL, '2021-11-14 06:23:36', '2021-11-14 06:23:36');
INSERT INTO `menu` VALUES (7, 1, 2, 'SECOND', '分类管理', 1, 0, '/categories', 'category/Category', 1, 0, 'admin:categories:view', 'fa-th', '2021-10-29 18:56:09', '2021-10-29 18:56:09');
INSERT INTO `menu` VALUES (8, 1, 7, 'BUTTON', '新增分类', 0, 0, '', '', 1, 0, 'admin:categories:add', NULL, '2021-11-14 06:21:30', '2021-11-14 06:21:44');
INSERT INTO `menu` VALUES (9, 1, 7, 'BUTTON', '分类列表', 0, 0, '', '', 1, 0, 'admin:categories:list', NULL, '2021-11-14 06:21:30', '2021-11-14 06:21:44');
INSERT INTO `menu` VALUES (10, 1, 7, 'BUTTON', '修改分类', 0, 0, '', '', 1, 0, 'admin:categories:edit', NULL, '2021-11-14 06:21:10', '2021-11-14 06:21:10');
INSERT INTO `menu` VALUES (11, 1, 7, 'BUTTON', '删除分类', 0, 0, '', '', 1, 0, 'admin:categories:delete', NULL, '2021-11-14 06:22:03', '2021-11-14 06:22:03');
INSERT INTO `menu` VALUES (12, 1, 2, 'SECOND', '标签管理', 2, 0, '/tags', 'tag/Tag', 1, 0, 'admin:tags:view', 'fa-tags', '2021-10-29 18:56:22', '2021-10-29 18:56:22');
INSERT INTO `menu` VALUES (13, 1, 12, 'BUTTON', '新增标签', 0, 0, '', '', 1, 0, 'admin:tags:add', NULL, '2021-11-14 06:24:07', '2021-11-14 06:24:07');
INSERT INTO `menu` VALUES (14, 1, 12, 'BUTTON', '标签列表', 0, 0, '', '', 1, 0, 'admin:tags:list', NULL, '2021-11-14 06:24:07', '2021-11-14 06:24:07');
INSERT INTO `menu` VALUES (15, 1, 12, 'BUTTON', '修改标签', 0, 0, '', '', 1, 0, 'admin:tags:edit', NULL, '2021-11-14 06:24:15', '2021-11-14 06:24:15');
INSERT INTO `menu` VALUES (16, 1, 12, 'BUTTON', '删除标签', 0, 0, '', '', 1, 0, 'admin:tags:delete', NULL, '2021-11-14 06:24:25', '2021-11-14 06:24:25');
INSERT INTO `menu` VALUES (17, 1, 0, 'TOP', '用户管理', 1, 0, '/users', 'Layout', 1, 0, 'admin:users:view', 'fa-user', '2021-10-29 18:57:10', '2022-01-29 18:59:21');
INSERT INTO `menu` VALUES (18, 1, 17, 'SECOND', '用户列表', 1, 0, '/users/list', 'user/User', 1, 0, 'admin:users:list', 'fa-users', '2021-10-29 18:58:54', '2021-10-29 18:58:54');
INSERT INTO `menu` VALUES (19, 1, 17, 'BUTTON', '新增用户', 0, 0, '', '', 1, 0, 'admin:users:add', NULL, '2021-11-14 22:37:32', '2022-03-13 23:17:20');
INSERT INTO `menu` VALUES (20, 1, 17, 'BUTTON', '修改用户', 0, 0, '', '', 1, 0, 'admin:users:edit', NULL, '2021-11-14 22:37:32', '2021-11-14 22:37:32');
INSERT INTO `menu` VALUES (21, 1, 17, 'BUTTON', '删除用户', 0, 0, '', '', 1, 0, 'admin:users:delete', NULL, '2021-11-14 22:37:32', '2021-11-14 22:37:32');
INSERT INTO `menu` VALUES (22, 1, 0, 'TOP', '权限管理', 3, 0, '/perm', 'Layout', 1, 0, 'admin:perm:view', 'fa-shield', '2021-11-14 22:41:59', '2022-01-29 18:55:46');
INSERT INTO `menu` VALUES (23, 1, 22, 'SECOND', '角色管理', 0, 0, '/roles', 'role/Role', 1, 0, 'admin:roles:list', 'fa-id-badge', '2021-11-14 22:49:27', '2021-11-14 22:49:27');
INSERT INTO `menu` VALUES (24, 1, 22, 'BUTTON', '新增角色', 0, 0, '', '', 1, 0, 'admin:roles:add', '', '2021-11-14 22:49:27', '2021-11-14 22:49:27');
INSERT INTO `menu` VALUES (25, 1, 22, 'BUTTON', '修改角色', 0, 0, '', '', 1, 0, 'admin:roles:edit', '', '2021-11-14 22:49:27', '2021-11-14 22:49:27');
INSERT INTO `menu` VALUES (26, 1, 22, 'BUTTON', '删除角色', 0, 0, '', '', 1, 0, 'admin:roles:delete', '', '2021-11-14 22:49:27', '2021-11-14 22:49:27');
INSERT INTO `menu` VALUES (27, 1, 0, 'SECOND', '菜单管理', 4, 0, '/menus', 'menu/Menu', 1, 0, 'admin:menus:view', 'fa-list-ul', '2021-11-15 22:23:41', '2022-01-29 19:00:01');
INSERT INTO `menu` VALUES (28, 1, 27, 'BUTTON', '新增菜单', 0, 0, '', '', 1, 0, 'admin:menus:add', '', '2021-11-15 22:31:08', '2021-11-15 22:31:08');
INSERT INTO `menu` VALUES (29, 1, 27, 'BUTTON', '修改菜单', 0, 0, '', '', 1, 0, 'admin:menus:edit', '', '2021-11-15 22:31:08', '2021-11-15 22:31:08');
INSERT INTO `menu` VALUES (30, 1, 27, 'BUTTON', '删除菜单', 0, 0, '', '', 1, 0, 'admin:menus:delete', '', '2021-11-15 22:31:08', '2021-11-15 22:31:08');
INSERT INTO `menu` VALUES (31, 1, 0, 'TOP', '系统监控', 100, 0, '/monitor', 'Layout', 1, 0, NULL, 'fa-dashboard   ', '2022-01-28 18:59:14', '2022-03-12 16:27:43');
INSERT INTO `menu` VALUES (32, 1, 31, 'SECOND', '数据库监控', 1, 0, '/druid', 'monitor/Druid', 1, 0, 'admin:monitor:druid', 'fa-database', '2022-01-28 19:03:18', '2022-01-28 19:08:38');
INSERT INTO `menu` VALUES (33, 1, 0, 'TOP', '日志管理', 7, 0, '/log', 'Layout', 1, 0, 'admin:log:view', 'fa-file-text-o', '2022-02-10 17:12:14', '2022-02-10 17:12:14');
INSERT INTO `menu` VALUES (34, 1, 33, 'SECOND', '操作日志', 1, 0, '/log/operateLog', 'log/OperateLog', 1, 0, 'admin:operatelog:view', 'fa-file-text', '2022-02-10 17:13:48', '2022-02-10 17:13:48');
INSERT INTO `menu` VALUES (35, 1, 34, 'BUTTON', '删除操作日志', 100, 0, '', '', 1, 0, 'admin:operatelog:delete', NULL, '2022-02-10 17:14:48', '2022-02-10 17:14:48');
INSERT INTO `menu` VALUES (36, 1, 0, 'SECOND', '字典管理', 5, 0, '/dictionary', 'dictionary/Dictionary', 1, 0, 'admin:dictionary:list', 'fa-book', '2022-03-12 14:19:56', '2022-03-12 14:19:56');
INSERT INTO `menu` VALUES (37, 1, 36, 'BUTTON', '新增字典', 100, 0, '', '', 1, 0, 'admin:dictionary:add', NULL, '2022-03-12 14:25:13', '2022-03-12 14:25:13');
INSERT INTO `menu` VALUES (38, 1, 36, 'BUTTON', '修改字典', 100, 0, '', '', 1, 0, 'admin:dictionary:edit', NULL, '2022-03-12 14:25:57', '2022-03-12 14:25:57');
INSERT INTO `menu` VALUES (39, 1, 36, 'BUTTON', '删除字典', 100, 0, '', '', 1, 0, 'admin:dictionary:delete', NULL, '2022-03-12 14:26:44', '2022-03-12 14:26:44');
INSERT INTO `menu` VALUES (40, 1, 34, 'BUTTON', '查看操作日志', 100, 0, '', '', 1, 0, 'admin:operatelog:list', NULL, '2022-03-12 16:01:09', '2022-03-12 16:01:09');
INSERT INTO `menu` VALUES (41, 1, 36, 'BUTTON', '新增字典数据', 100, 0, '', '', 1, 0, 'admin:dictionarydata:add', NULL, '2022-03-12 16:16:19', '2022-03-12 16:16:33');
INSERT INTO `menu` VALUES (42, 1, 36, 'BUTTON', '修改字典数据', 100, 0, '', '', 1, 0, 'admin:dictionarydata:edit', NULL, '2022-03-12 16:17:42', '2022-03-12 16:26:02');
INSERT INTO `menu` VALUES (43, 1, 36, 'BUTTON', '字典数据列表', 100, 0, '', '', 1, 0, 'admin:dictionarydata:list', NULL, '2022-03-12 16:24:40', '2022-03-13 23:18:59');
INSERT INTO `menu` VALUES (44, 1, 36, 'BUTTON', '删除字典数据', 100, 0, '', '', 1, 0, 'admin:dictionarydata:delete', NULL, '2022-03-12 16:25:47', '2022-03-13 23:19:04');
INSERT INTO `menu` VALUES (45, 1, 0, 'SECOND', 'Yeliheng的技术小站', 100, 1, 'https://www.yeliheng.com', NULL, 0, 0, NULL, 'fa-home', '2022-03-14 20:29:18', '2022-03-14 20:29:35');
INSERT INTO `menu` VALUES (46, 1, 0, 'SECOND', '个人中心', 100, 0, '/users/profile', 'profile/Profile', 1, 1, NULL, NULL, '2022-03-17 14:24:46', '2022-03-17 14:24:46');
INSERT INTO `menu` VALUES (47, 1, 17, 'BUTTON', '导出用户数据', 100, 0, NULL, NULL, 1, 0, 'admin:users:export', NULL, '2022-03-19 14:58:40', '2022-03-19 14:58:40');
INSERT INTO `menu` VALUES (48, 1, 2, 'BUTTON', '导出文章列表', 100, 0, NULL, NULL, 1, 0, 'admin:articles:export', NULL, '2022-03-19 14:59:39', '2022-03-19 14:59:39');
INSERT INTO `menu` VALUES (49, 1, 34, 'BUTTON', '导出操作日志', 100, 0, NULL, NULL, 1, 0, 'admin:operatelog:export', NULL, '2022-03-19 21:57:13', '2022-03-19 21:59:29');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

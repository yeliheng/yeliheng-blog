

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
  `menu_name` varchar(255) NOT NULL COMMENT '菜单名称',
  `order_num` int(11) DEFAULT '100' COMMENT '排序',
  `path` varchar(255) DEFAULT '/#' COMMENT 'URL',
  `component` varchar(255) DEFAULT 'Home' COMMENT '组件地址',
  `hidden` tinyint(1) DEFAULT '0' COMMENT '可见性',
  `permission` varchar(255) DEFAULT NULL COMMENT '菜单权限',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单字体图标',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` VALUES (1, 1, 0, '首页', 0, '/dashboard', 'Home', 0, NULL, 'fa-dashboard', '2021-11-02 06:26:04', '2022-02-10 14:54:49');
INSERT INTO `menu` VALUES (2, 1, 0, '文章管理', 0, '/articles', 'Layout', 0, 'admin:articles:view', 'fa-file', '2021-10-29 18:48:46', '2022-01-29 18:59:06');
INSERT INTO `menu` VALUES (3, 1, 2, '发布文章', 0, '/articles/publish', 'article/Article', 0, 'admin:articles:add', 'fa-paper-plane', '2021-10-29 18:55:14', '2021-10-29 18:55:14');
INSERT INTO `menu` VALUES (4, 1, 2, '文章列表', 0, '/articles/list', 'article/ArticleList', 0, 'admin:articles:list', 'fa-file-text', '2021-10-29 18:55:57', '2021-10-29 18:55:57');
INSERT INTO `menu` VALUES (5, 1, 2, '修改文章', 0, '/articles/:id', 'article/Edit', 1, 'admin:articles:edit', NULL, '2021-11-14 06:23:28', '2021-11-14 06:23:28');
INSERT INTO `menu` VALUES (6, 1, 2, '删除文章', 0, '/#', 'Home', 1, 'admin:articles:delete', NULL, '2021-11-14 06:23:36', '2021-11-14 06:23:36');
INSERT INTO `menu` VALUES (7, 1, 2, '分类管理', 1, '/categories', 'category/Category', 0, 'admin:categories:view', 'fa-th', '2021-10-29 18:56:09', '2021-10-29 18:56:09');
INSERT INTO `menu` VALUES (8, 1, 7, '新增分类', 0, '/#', 'Home', 1, 'admin:categories:add', NULL, '2021-11-14 06:21:30', '2021-11-14 06:21:44');
INSERT INTO `menu` VALUES (9, 1, 7, '分类列表', 0, '/#', 'Home', 1, 'admin:categories:list', NULL, '2021-11-14 06:21:30', '2021-11-14 06:21:44');
INSERT INTO `menu` VALUES (10, 1, 7, '修改分类', 0, '/#', 'Home', 1, 'admin:categories:edit', NULL, '2021-11-14 06:21:10', '2021-11-14 06:21:10');
INSERT INTO `menu` VALUES (11, 1, 7, '删除分类', 0, '/#', 'Home', 1, 'admin:categories:delete', NULL, '2021-11-14 06:22:03', '2021-11-14 06:22:03');
INSERT INTO `menu` VALUES (12, 1, 2, '标签管理', 2, '/tags', 'tag/Tag', 0, 'admin:tags:view', 'fa-tags', '2021-10-29 18:56:22', '2021-10-29 18:56:22');
INSERT INTO `menu` VALUES (13, 1, 12, '新增标签', 0, '/#', 'Home', 1, 'admin:tags:add', NULL, '2021-11-14 06:24:07', '2021-11-14 06:24:07');
INSERT INTO `menu` VALUES (14, 1, 12, '标签列表', 0, '/#', 'Home', 1, 'admin:tags:list', NULL, '2021-11-14 06:24:07', '2021-11-14 06:24:07');
INSERT INTO `menu` VALUES (15, 1, 12, '修改标签', 0, '/#', 'Home', 1, 'admin:tags:edit', NULL, '2021-11-14 06:24:15', '2021-11-14 06:24:15');
INSERT INTO `menu` VALUES (16, 1, 12, '删除标签', 0, '/#', 'Home', 1, 'admin:tags:delete', NULL, '2021-11-14 06:24:25', '2021-11-14 06:24:25');
INSERT INTO `menu` VALUES (17, 1, 0, '用户管理', 1, '/users', 'Layout', 0, 'admin:users:view', 'fa-user', '2021-10-29 18:57:10', '2022-01-29 18:59:21');
INSERT INTO `menu` VALUES (18, 1, 17, '用户列表', 1, '/users/list', 'user/User', 0, 'admin:users:list', 'fa-users', '2021-10-29 18:58:54', '2021-10-29 18:58:54');
INSERT INTO `menu` VALUES (19, 1, 17, '新增用户', 0, '/#', 'Home', 1, 'admin:users:add', NULL, '2021-11-14 22:37:32', '2021-11-14 22:37:32');
INSERT INTO `menu` VALUES (20, 1, 17, '修改用户', 0, '/#', 'Home', 1, 'admin:users:edit', NULL, '2021-11-14 22:37:32', '2021-11-14 22:37:32');
INSERT INTO `menu` VALUES (21, 1, 17, '删除用户', 0, '/#', 'Home', 1, 'admin:users:delete', NULL, '2021-11-14 22:37:32', '2021-11-14 22:37:32');
INSERT INTO `menu` VALUES (22, 1, 0, '权限管理', 3, '/perm', 'Layout', 0, 'admin:perm:view', 'fa-shield', '2021-11-14 22:41:59', '2022-01-29 18:55:46');
INSERT INTO `menu` VALUES (23, 1, 22, '角色管理', 0, '/roles', 'role/Role', 0, 'admin:roles:list', 'fa-id-badge', '2021-11-14 22:49:27', '2021-11-14 22:49:27');
INSERT INTO `menu` VALUES (24, 1, 22, '新增角色', 0, '/roles', 'role/Role', 1, 'admin:roles:add', '', '2021-11-14 22:49:27', '2021-11-14 22:49:27');
INSERT INTO `menu` VALUES (25, 1, 22, '修改角色', 0, '/roles', 'role/Role', 1, 'admin:roles:edit', '', '2021-11-14 22:49:27', '2021-11-14 22:49:27');
INSERT INTO `menu` VALUES (26, 1, 22, '删除角色', 0, '/roles', 'role/Role', 1, 'admin:roles:delete', '', '2021-11-14 22:49:27', '2021-11-14 22:49:27');
INSERT INTO `menu` VALUES (27, 1, 0, '菜单管理', 4, '/menus', 'menu/Menu', 0, 'admin:menus:view', 'fa-list-ul', '2021-11-15 22:23:41', '2022-01-29 19:00:01');
INSERT INTO `menu` VALUES (28, 1, 27, '新增菜单', 0, '/#', 'Home', 1, 'admin:menus:add', '', '2021-11-15 22:31:08', '2021-11-15 22:31:08');
INSERT INTO `menu` VALUES (29, 1, 27, '修改菜单', 0, '/#', 'Home', 1, 'admin:menus:edit', '', '2021-11-15 22:31:08', '2021-11-15 22:31:08');
INSERT INTO `menu` VALUES (30, 1, 27, '删除菜单', 0, '/#', 'Home', 1, 'admin:menus:delete', '', '2021-11-15 22:31:08', '2021-11-15 22:31:08');
INSERT INTO `menu` VALUES (31, 1, 0, '系统监控', 100, '/#', 'Layout', 0, NULL, 'fa-dashboard   ', '2022-01-28 18:59:14', '2022-01-28 19:08:29');
INSERT INTO `menu` VALUES (32, 1, 31, '数据库监控', 1, '/druid', 'monitor/Druid', 0, 'admin:monitor:druid', 'fa-database', '2022-01-28 19:03:18', '2022-01-28 19:08:38');
INSERT INTO `menu` VALUES (33, 1, 0, '日志管理', 7, '/log', 'Layout', 0, 'admin:log:view', 'fa-file-text-o', '2022-02-10 17:12:14', '2022-02-10 17:12:14');
INSERT INTO `menu` VALUES (34, 1, 33, '操作日志', 1, '/log/operateLog', 'log/OperateLog', 0, 'admin:operatelog:view', 'fa-file-text', '2022-02-10 17:13:48', '2022-02-10 17:13:48');
INSERT INTO `menu` VALUES (35, 1, 34, '删除操作日志', 100, '/#', 'Home', 1, 'admin:operatelog:delete', NULL, '2022-02-10 17:14:48', '2022-02-10 17:14:48');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

# Yeliheng-Blog

---

Yeliheng-Blog说明在[我的博客](https://www.yeliheng.com/)上同步更新，地址:[https://www.yeliheng.com/p/f1d1e1d9](https://www.yeliheng.com/p/f1d1e1d9)

欢迎Star，Issue，Pull requests以及任何对本项目的贡献。开发不易，感谢支持！
# 系统介绍

这个开源仓库是Yeliheng的技术小站的前后端实现。Yeliheng-Blog是一个基于SpringBoot + Vue3开发的前后端分离的完整博客系统。它并不仅仅是一个简单的博客系统，更是一个**后台权限管理系统**，通过**模块化**使其具有高拓展性，可拓展成任何您想要的管理系统，例如文章系统，OA办公系统，仓库管理系统等...

## 特性

- 轻量化系统，无外部框架的重度依赖

- 良好的可拓展性

- 响应式前端网页，完全适配移动端以及不同分辨率设备

- 基于RBAC模型的权限管理系统

- 优质的用户体验

  ...

## 在线地址

后台演示功能暂未完成，目前仅开放前台展示，后台系统可自行搭建体验。[点击查看搭建教程](#搭建教程)

前台演示地址: [https://www.yeliheng.com](https://www.yeliheng.com)

后台搭建教程：[点击查看搭建教程](#搭建教程)

# 功能详解

## 后台

### 目前已有功能

- 文章管理：实现文章的增删改查，以及文章分类、标签，隐私性的设置。

- 用户管理：用户登录、注册以及管理员对用户的增删改查操作。

- 角色管理：对角色系统的增删改查以及为用户分配多个角色。

- 权限管理：可为不同角色分配所需的不同权限。

- 菜单管理：为角色分配菜单权限，以及前端菜单的动态路由处理。

- 站点统计：目前仅实现对文章字数，阅读时长及字数的统计，后续将以模块化形式实现站点的完整统计功能。

### 正在开发中的功能

- 对菜单的动态增加以及修改，自定义菜单类型（如：放置外链或多媒体类型等功能）

- 评论管理模块

- 完整的站点统计模块

- 定时备份模块的开发

- API接口文档

---

## 前台

### 目前已有功能

- 基于Markdown和Github风格的文章查看器

- 按照分类索引文章

- 按照标签索引文章

- 基于Three.js实现的Header

- 站点信息的显示

- 关于页面

### 正在开发的功能

- 评论模块

- 日夜间模式的切换

- 站点统计模块

---

# 工程结构

```
yeliheng-blog
├── blog-common    --  后端系统通用模块（封装各种工具类、公共常量、异常类等）
├── blog-framework --  后端系统框架层（封装框架相关工具，如权限工具，框架配置文件，安全相关类）
├── blog-system    --  博客系统核心业务层（服务，数据库访问）
├── blog-web       --  博客系统API控制器（controller）
└── blog-vue       --  博客系统前端模块（Vue3）
    ├── admin   --  博客系统前端后台模块
    └── blog    --  博客系统前端前台模块
```

![模块图](https://cdn.yeliheng.com/yeliheng-blog/module.png)

# 技术栈

| 技术                 | 说明                |
| -------------------- | ------------------- |
| SpringBoot           | 容器+框架           |
| SpringSecurity       | 认证/权限框架       |
| MyBatis              | ORM框架             |
| RabbitMQ             | 消息队列            |
| Redis                | 分布式缓存          |
| Nginx                | 静态资源服务器      |
| Docker               | 应用容器引擎        |
| Jenkins              | 自动化部署工具      |
| Druid                | 数据库连接池        |
| OSS                  | 对象存储            |
| JWT                  | JWT登录支持         |
| PageHelper           | MyBatis物理分页插件 |
| Swagger-UI           | 文档生成工具        |
| Hibernator-Validator | 验证框架            |
| Vue3                 | 渐进式js框架        |
| Sass                 | css扩展语言         |
| Element-UI           | 饿了么UI库          |
| Markdown-it          | markdown解析工具    |

# 搭建教程

## 开发环境

在开始之前，请确认您的开发环境，尽量与此处保持一致，以免出现意料之外的问题。

### 后端

| 开发环境 | 版本   |
| -------- | ------ |
| JDK      | 1.8  |
| JRE      | 1.8  |
| MySQL    | 5.7  |
| Redis    | 6.x  |

### 前端

| 开发环境 | 版本     |
| -------- | -------- |
| Vue      |  3     |
| Node.js  | 14.x |
| npm      | 6.x  |

## 快速开始

### 后端

1、搭建如上表格所示的开发环境并确保所有环境处于正常运行状态。（具体过程此处不做赘述，可自行查阅相关资料）

2、克隆本项目

```
git clone https://github.com/yeliheng/yeliheng-blog.git
```

3、导入idea或其他IDE，等待Maven导入所有的包。（此过程可能需要一些时间，部分国外仓库加载较慢，建议使用国内镜像源，具体步骤可自行在搜索引擎上查看）

4、在blog-web包的resource目录下新建配置文件，文件名为：**application-dev.yml**，并将**application-example.yml**中的内容复制到刚刚创建的**application-dev.yml**中，并将datasource中的数据源修改为自己的数据库信息。

5、导入数据库

将项目根目录下的**yeliheng-blog.sql**文件导入到数据库中。

6、准备完成，开始运行项目！

当您看到如下提示，代表项目已成功运行。

![成功](https://cdn.yeliheng.com/yeliheng-blog/success.png)

### 前端

1、进入本项目中的blog-vue文件夹中，前端分为admin和blog两个模块，此处以admin模块为例进行说明。

2、**我们首先进入admin文件夹**，在admin文件夹中新建文件：**.env.development**，并将 **.env.example** 中的内容复制到刚刚创建的 **.env.development**中，并将**VUE_APP_BASE_API**地址修改为后端地址。若您没有改动后端地址则无需更改。

3、执行如下指令安装所需依赖：

```
npm install
```

4、等待上述指令执行完成后，执行以下指令：

```
npm run serve
```

5、项目成功运行，开始访问吧！

**管理员账号：admin**

**管理员密码: admin123**

**blog模块的安装方法与上述过程完全相同**

# 项目截图

![首页](https://cdn.yeliheng.com/yeliheng-blog/index.png)



![文章](https://cdn.yeliheng.com/yeliheng-blog/article.png)

![后台](https://cdn.yeliheng.com/yeliheng-blog/admin-article-list.png)

![权限管理](https://cdn.yeliheng.com/yeliheng-blog/admin-role-manage.png)


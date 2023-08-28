CREATE DATABASE IF NOT EXISTS erp_system;
USE erp_system;

# -----------------------------------------
# 菜单
CREATE TABLE IF NOT EXISTS `menu`
(
    `id`          INT          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `parent_id`   INT          DEFAULT NULL COMMENT '自身ID',
    `title`       VARCHAR(255) NOT NULL COMMENT '标题',
    `icon`        VARCHAR(255) DEFAULT NULL COMMENT '图标',
    `url`         VARCHAR(255) NOT NULL COMMENT '组件路径(虚拟字段, 由java自动生成, URL+View.vue)',
    `is_redirect` TINYINT(1)   DEFAULT NULL COMMENT '是否为重定向',
    `content`     TEXT         DEFAULT NULL COMMENT '介绍',
    `sort`        INT          DEFAULT 0 COMMENT '排序',
    `flag`        TINYINT(1)   DEFAULT 0 COMMENT '逻辑删除',
    `created_at`  DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `updated_at`  DATETIME     DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) COMMENT '菜单';

INSERT INTO `menu`
    (id, parent_id, title, icon, url, is_redirect)
VALUES (1, null, '首页', 'windows-outlined', 'home', false),
       (2, null, '运营管理', 'bar-chart-outlined', 'meta', false),
       (3, null, '会员管理', 'team-outlined', 'user', false),
       (4, null, '系统管理', 'sliders-outlined', 'system', false),
       (5, null, '系统监控', 'hdd-outlined', 'monitor', false),
       (6, null, '系统工具', 'tool-outlined', 'tool', false),
       (7, null, '开发文档', 'edit-outlined', 'document', false),
       (8, null, '系统设置', 'setting-outlined', 'setting', false),
       (9, null, '官网页面', 'chrome-outlined', 'https://qs2042.github.io', true),
       (10, null, '测试页面', 'code-outlined', 'test', false);

INSERT INTO `menu` (parent_id, title, url, is_redirect)
VALUES (2, '统计中心', 'operate', false),
       (2, '广告管理', 'advertisement', false),
       (2, '友情链接', 'friendlyLink', false),
       (2, '反馈管理', 'feedback', false),
       (2, '活动管理', 'activity', false),
       (2, '消息推送', 'messagePush', false),
       (2, '社交媒体', 'socialMedia', false),

       (3, '用户管理', 'user', false),
       (3, '角色管理', 'role', false),
       (3, '机构管理', 'organization', false),
       (3, '部门管理', 'department', false),
       (3, '岗位管理', 'position', false),

       (4, '菜单管理', 'menu', false),
       (4, '字典管理', 'dict', false),
       (4, '参数设置', 'params', false),
       (4, '通知公告', 'notice', false),
       (4, '日志管理', 'logs', false),
       (4, '开发日志', 'devLogs', false),

       (5, '在线用户', 'online', false),
       (5, '定时任务', 'job', false),
       (5, '数据监控', 'druid', false),
       (5, '服务监控', 'server', false),
       (5, '缓存监控', 'cache', false),
       (5, '缓存列表', 'cacheList', false),
       (5, 'Docker监控', 'docker', false),
       (5, 'Maven监控', 'maven', false),
       (5, 'Mysql监控', 'mysql', false),

       (6, '表单构建', 'build', false),
       (6, '代码生成', 'gen', false),
       (6, '在线编辑', 'edit', false),
       (6, '系统接口', 'interface', false);

# -----------------------------------------
# 字典
CREATE TABLE IF NOT EXISTS `dict`
(
    `id`          INT          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code`        VARCHAR(255) NOT NULL COMMENT '代码(唯一)',
    `name`        VARCHAR(255) NOT NULL COMMENT '名称',
    `description` VARCHAR(255) NOT NULL COMMENT '描述',
    `flag`        TINYINT(1) DEFAULT 0 COMMENT '逻辑删除',
    `created_at`  DATETIME   DEFAULT NOW() COMMENT '创建时间',
    `updated_at`  DATETIME   DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) COMMENT '字典';

INSERT INTO `dict` (code, name, description)
VALUES ('male', '男性', ''),
       ('female', '女性', ''),
       ('china', '中国', ''),
       ('usa', '美国', ''),
       ('japan', '日本', ''),
       ('gender', '性别', '表示个体的性别, 包括男、女、未知等'),
       ('education', '学历', '表示个体的最高学历, 包括小学、初中、高中、大学、硕士、博士等'),
       ('marriage', '婚姻状况', '表示个体的婚姻状况, 包括已婚、未婚、离异、丧偶等'),
       ('nation', '民族', '表示个体所属的民族, 包括汉族、藏族、维吾尔族、朝鲜族等'),
       ('occupation', '职业', '表示个体从事的职业, 包括医生、工程师、教师、会计师等'),
       ('industry', '行业', '表示个体所在的行业, 包括制造业、金融业、教育业、医疗卫生业等'),
       ('province', '省份', '表示个体所在的省份, 包括北京、上海、广东、四川等'),
       ('city', '城市', '表示个体所在的城市, 包括北京、上海、广州、深圳等'),
       ('district', '区县', '表示个体所在的区县, 包括海淀区、朝阳区、黄浦区、浦东新区等'),
       ('language', '语言', '表示个体掌握的语言, 包括汉语、英语、法语、德语等'),
       ('currency', '货币', '表示货币的种类, 包括人民币、美元、欧元、日元等'),
       ('time_zone', '时区', '表示个体所在的时区, 包括北京时间、东京时间、纽约时间、伦敦时间等'),
       ('continent', '洲', '表示个体所在的洲, 包括亚洲、欧洲、南美洲、非洲等'),
       ('blood_type', '血型', '表示个体的血型, 包括A型、B型、AB型、O型等'),
       ('zodiac', '生肖', '表示个体所属的生肖, 包括鼠、牛、虎、兔等'),
       ('constellation', '星座', '表示个体所属的星座, 包括白羊座、金牛座、双子座、巨蟹座等'),
       ('vehicle', '交通工具', '表示个体常用的交通工具, 包括汽车、自行车、地铁、公交车等'),
       ('fruit', '水果', '表示个体喜欢的水果, 包括苹果、香蕉、橙子、草莓等'),
       ('vegetable', '蔬菜', '表示个体喜欢的蔬菜, 包括西红柿、黄瓜、菠菜、芹菜等'),
       ('color', '颜色', '表示个体喜欢的颜色, 包括红色、蓝色、绿色、黄色等'),
       ('animal', '动物', '表示个体喜欢的动物, 包括猫、狗、兔子、老虎等'),
       ('movie_genre', '电影类型', '表示个体喜欢的电影类型, 包括动作片、喜剧片、爱情片、科幻片等'),
       ('music_genre', '音乐类型', '表示个体喜欢的音乐类型, 包括流行音乐、摇滚音乐、古典音乐、民谣等'),
       ('book_genre', '图书类型', '表示个体喜欢的图书类型, 包括小说、传记、历史、科幻等'),
       ('food_type', '美食类型', '表示个体喜欢的美食类型, 包括川菜、粤菜、西餐、日料等'),
       ('sports_type', '运动类型', '表示个体喜欢的运动类型, 包括篮球、足球、网球、游泳等'),
       ('hobby', '爱好', '表示个体的兴趣爱好, 包括旅游、摄影、钓鱼、阅读等'),
       ('personality', '个性特征', '表示个体的个性特征, 包括开朗、内向、坚韧、敏感等'),
       ('emotion', '情感状态', '表示个体当前的情感状态, 包括开心、悲伤、愤怒、平静等'),
       ('weather', '天气', '表示当前的天气情况, 包括晴天、雨天、多云、阴天等');

# -----------------------------------------
# 参数
CREATE TABLE IF NOT EXISTS `params`
(
    `id`          INT          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `key`         VARCHAR(255) NOT NULL COMMENT '键',
    `value`       VARCHAR(255) NOT NULL COMMENT '值',
    `description` VARCHAR(255) NOT NULL COMMENT '描述',
    `flag`        TINYINT(1) DEFAULT 0 COMMENT '逻辑删除',
    `created_at`  DATETIME   DEFAULT NOW() COMMENT '创建时间',
    `updated_at`  DATETIME   DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) COMMENT '参数';

INSERT INTO `params` (`key`, value, description)
VALUES ('page_size', '10', '分页大小'),
       ('max_page_size', '100', '最大分页大小'),
       ('image_max_size', '1024*2', '图片上传最大大小'),
       ('captcha_expiry', '1024*60*300', '验证码有效期(s)'),
       ('session_timeout', '1800', '会话超时时间(秒)'),
       ('max_login_attempts', '5', '最大登录尝试次数'),
       ('password_expiry', '30', '密码有效期(天)'),
       ('password_length_min', '6', '密码最小长度'),
       ('password_length_max', '16', '密码最大长度'),
       ('username_length_min', '4', '用户名最小长度'),
       ('username_length_max', '20', '用户名最大长度'),
       ('image_upload_max_size', '5', '图片上传最大大小(MB)'),
       ('file_upload_max_size', '10', '文件上传最大大小(MB)'),
       ('email_verification_expiry', '86400', '电子邮件验证链接有效期(秒)'),
       ('sms_verification_expiry', '300', '短信验证码有效期(秒)'),
       ('max_cart_items', '10', '购物车最大商品数量'),
       ('max_wishlist_items', '20', '心愿单最大商品数量'),
       ('order_status_expiry', '604800', '订单状态过期时间(秒)'),
       ('order_cancel_expiry', '600', '订单取消过期时间(秒)'),
       ('order_confirm_expiry', '1800', '订单确认过期时间(秒)'),
       ('refund_request_expiry', '259200', '退款申请过期时间(秒)'),
       ('return_request_expiry', '259200', '退货申请过期时间(秒)');

# -----------------------------------------
# 设置
CREATE TABLE IF NOT EXISTS `settings`
(
    `id`          INT          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `key`         VARCHAR(255) NOT NULL COMMENT '键',
    `value`       VARCHAR(255) NOT NULL COMMENT '值',
    `type`        VARCHAR(255) NOT NULL COMMENT '类型',
    `description` VARCHAR(255) COMMENT '描述',
    `flag`        TINYINT(1) DEFAULT 0 COMMENT '逻辑删除',
    `created_at`  DATETIME   DEFAULT NOW() COMMENT '创建时间',
    `updated_at`  DATETIME   DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) COMMENT '配置';

INSERT INTO `settings` (`key`, value, type, description)
VALUES ('site_title', 'my test site', 'string', '网站标题'),
       ('test', '...', 'json', '{"a":"1", "b":"2"}');

# -----------------------------------------
# 日志
CREATE TABLE IF NOT EXISTS `logs`
(
    `id`            INT NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`       INT          DEFAULT NULL COMMENT '用户ID',

    `req_uri`       VARCHAR(255) DEFAULT NULL COMMENT '请求地址',
    `req_method`    VARCHAR(255) DEFAULT NULL COMMENT '请求方式',
    `req_parameter` VARCHAR(255) DEFAULT NULL COMMENT '请求参数',

    `ip`            VARCHAR(255) DEFAULT NULL COMMENT 'IP',
    `city`          VARCHAR(255) DEFAULT NULL COMMENT '城市',
    `equipment`     VARCHAR(255) DEFAULT NULL COMMENT '设备机型',

    `module`        VARCHAR(255) DEFAULT NULL COMMENT '模块',
    `notes`         TEXT         DEFAULT NULL COMMENT '备注',

    `method`        VARCHAR(255) DEFAULT NULL COMMENT '操作方法的链路',
    `spend_time`    INT          DEFAULT NULL COMMENT '执行速度',
    `result`        TEXT         DEFAULT NULL COMMENT '返回结果',
    `status`        TINYINT(1)   DEFAULT NULL COMMENT '是否成功',

    `created_at`    DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `updated_at`    DATETIME     DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) COMMENT '日志';

INSERT INTO `logs` (user_id, req_uri, req_method, req_parameter, ip, city, equipment, module, notes, method, spend_time,
                    result, status)
VALUES (1, '/test', 'get', '', '198.126.1.107', '广东', 'android', 'monitor', '测试方法',
        'com.qing.erp.monitor.IndexController.test', '5', '{}', true),
       (1, '/user/login', 'POST', 'username=admin&password=123456', '192.168.1.101', '北京市', 'iPhone X', '用户模块', '用户登录',
        'UserController.login', 350, '{"code":200, "message":"登录成功"}', 1),
       (1, '/user/info', 'GET', '', '192.168.1.101', '北京市', 'iPhone X', '用户模块', '获取用户信息', 'UserController.info', 450,
        '{"code":200, "data":{"id":1, "name":"张三", "age":28}}', 1),
       (1, '/order/create', 'POST', 'product_id=1&quantity=2', '192.168.1.102', '上海市', '华为P30', '订单模块', '创建订单',
        'OrderController.create', 550, '{"code":200, "message":"订单创建成功"}', 1),
       (1, '/order/pay', 'POST', 'order_id=10001', '192.168.1.102', '上海市', '华为P30', '订单模块', '支付订单',
        'OrderController.pay', 650, '{"code":200, "message":"订单支付成功"}', 1),
       (1, '/product/list', 'GET', 'page=1&limit=10', '192.168.1.103', '深圳市', '小米9', '商品模块', '获取商品列表',
        'ProductController.list', 750,
        '{"code":200, "data":[{"id":1, "name":"商品1", "price":99.99}, {"id":2, "name":"商品2", "price":199.99}]}', 1),
       (1, '/product/detail', 'GET', 'product_id=1', '192.168.1.103', '深圳市', '小米9', '商品模块', '获取商品详情',
        'ProductController.detail', 350,
        '{"code":200, "data":{"id":1, "name":"商品1", "price":99.99, "description":"这是商品1的描述信息"}}', 1),
       (1, '/cart/add', 'POST', 'product_id=1&quantity=1', '192.168.1.104', '广州市', '三星S10', '购物车模块', '添加商品到购物车',
        'CartController.add', 250, '{"code":200, "message":"商品已添加到购物车"}', 1),
       (1, '/cart/list', 'GET', '', '192.168.1.104', '广州市', '三星S10', '购物车模块', '获取购物车列表', 'CartController.list', 450,
        '{"code":200, "data":[{"id":1, "product_id":1, "quantity":2, "product_name":"商品1"}, {"id":2, "product_id":2, "quantity":1, "product_name":"商品2"}]}',
        1),
       (1, '/user/register', 'POST', 'username=zhangsan&password=123456&age=20', '192.168.1.105', '成都市', '小米10', '用户模块',
        '用户注册', 'UserController.register', 950, '{"code":200, "message":"注册成功"}', 1),
       (1, '/user/update', 'POST', 'name=李四&age=25', '192.168.1.105', '成都市', '小米10', '用户模块', '更新用户信息',
        'UserController.update', 350, '{"code":200, "message":"用户信息更新成功"}', 1),
       (1, '/order/list', 'GET', 'status=1', '192.168.1.106', '武汉市', '华为Mate40', '订单模块', '获取订单列表',
        'OrderController.list', 550,
        '{"code":200, "data":[{"id":10001, "product_name":"商品1", "quantity":2, "price":99.99, "status":1}, {"id":10002, "product_name":"商品2", "quantity":1, "price":199.99, "status":1}]}',
        1),
       (1, '/order/cancel', 'POST', 'order_id=10001', '192.168.1.106', '武汉市', '华为Mate40', '订单模块', '取消订单',
        'OrderController.cancel', 750, '{"code":200, "message":"订单取消成功"}', 1),
       (1, '/product/search', 'GET', 'keyword=手机&page=1&limit=10', '192.168.1.107', '南京市', 'OPPO R17', '商品模块', '搜索商品',
        'ProductController.search', 350,
        '{"code":200, "data":[{"id":1, "name":"手机1", "price":199.99}, {"id":2, "name":"手机2", "price":399.99}]}', 1),
       (1, '/product/add', 'POST', 'name=手机3&price=299.99&description=这是手机3的描述信息', '192.168.1.107', '南京市', 'OPPO R17',
        '商品模块', '添加商品', 'ProductController.add', 650, '{"code":200, "message":"商品添加成功"}', 1),
       (1, '/product/update', 'POST', 'id=1&name=手机1&price=199.99&description=这是手机1的描述信息', '192.168.1.107', '南京市',
        'OPPO R17', '商品模块', '更新商品信息', 'ProductController.update', 450, '{"code":200, "message":"商品信息更新成功"}', 1),
       (1, '/cart/delete', 'POST', 'id=1', '192.168.1.108', '杭州市', 'vivo Y85', '购物车模块', '删除购物车中的商品',
        'CartController.delete', 250, '{"code":200, "message":"商品已从购物车中删除"}', 1),
       (1, '/user/delete', 'POST', 'id=2', '192.168.1.108', '杭州市', 'vivo Y85', '用户模块', '删除用户', 'UserController.delete',
        550, '{"code":200, "message":"用户已删除"}', 1),
       (1, '/order/confirm', 'POST', 'order_id=10002', '192.168.1.109', '郑州市', '小米8', '订单模块', '确认收货',
        'OrderController.confirm', 750, '{"code":200, "message":"订单已确认收货"}', 1),
       (1, '/product/delete', 'POST', 'id=2', '192.168.1.109', '郑州市', '小米8', '商品模块', '删除商品', 'ProductController.delete',
        950, '{"code":200, "message":"商品已删除"}', 1),
       (1, '/order/detail', 'GET', 'order_id=10001', '192.168.1.110', '长沙市', '华为Nova 3', '订单模块', '获取订单详情',
        'OrderController.detail', 350,
        '{"code":200, "data":{"id":10001, "product_name":"商品1", "quantity":2, "price":99.99, "status":2, "created_time":"2022-01-01 10:00:00"}}',
        1),
       (1, '/user/list', 'GET', 'page=1&limit=10', '192.168.1.110', '长沙市', '华为Nova 3', '用户模块', '获取用户列表',
        'UserController.list', 450,
        '{"code":200, "data":[{"id":1, "name":"张三", "age":28}, {"id":2, "name":"李四", "age":25}]}', 1);

# -----------------------------------------
# 开发日志
CREATE TABLE IF NOT EXISTS `dev_logs`
(
    `id`               INT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    `user_id`          INT          DEFAULT NULL COMMENT '用户ID',
    `project_id`       INT          DEFAULT NULL COMMENT '项目ID',
    `task_name`        VARCHAR(255) DEFAULT NULL COMMENT '任务名称',
    `task_description` TEXT         DEFAULT NULL COMMENT '任务描述',
    `hours_spent`      FLOAT        DEFAULT 0 COMMENT '花费的小时数',
    `created_at`       DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `updated_at`       DATETIME     DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间'
) COMMENT '开发日志';

INSERT INTO `dev_logs` (user_id, project_id, task_name, task_description, hours_spent)
VALUES (1, 1, '修复登录页面的样式', '将登录页面的样式进行修改, 使其更符合UI设计规范', 3.5),
       (1, 1, '增加用户注册功能', '在系统中增加用户注册功能, 包括前端和后端的代码编写', 8.25),
       (1, 2, '优化数据库查询效率', '通过对数据库索引的调整, 优化查询效率, 提高系统性能', 5.75),
       (1, 3, '编写API文档', '编写系统的API文档, 包括接口说明、请求参数、响应参数等', 4.5),
       (1, 1, '增加用户权限控制功能', '在系统中增加用户权限控制功能, 包括前端和后端的代码编写', 6.75),
       (1, 4, '修复订单统计页面的BUG', '修复订单统计页面中的一个BUG, 导致数据显示不正确', 2.25),
       (1, 2, '增加系统监控功能', '在系统中增加监控功能, 监控系统的运行状态, 及时发现并处理异常', 9),
       (1, 5, '编写邮件发送模块', '编写系统的邮件发送模块, 包括发送邮件的代码编写和邮件模板的设计', 7),
       (1, 3, '优化系统日志记录', '对系统的日志记录进行优化, 包括日志格式、日志等级等', 3.25),
       (1, 1, '修复订单管理页面的样式', '将订单管理页面的样式进行修改, 使其更符合UI设计规范', 4),
       (1, 4, '增加订单删除功能', '在系统中增加订单删除功能, 包括前端和后端的代码编写', 6.5),
       (1, 2, '优化系统缓存机制', '通过对系统缓存机制的调整, 优化系统的性能和稳定性', 8.75),
       (1, 5, '增加系统通知功能', '在系统中增加通知功能, 包括前端和后端的代码编写', 5.25),
       (1, 3, '修复用户管理页面的样式', '将用户管理页面的样式进行修改, 使其更符合UI设计规范', 2.75),
       (1, 1, '增加商品管理功能', '在系统中增加商品管理功能, 包括前端和后端的代码编写', 7.25),
       (1, 4, '修复商品详情页面的样式', '将商品详情页面的样式进行修改, 使其更符合UI设计规范', 3),
       (1, 2, '增加接口鉴权功能', '在系统中增加接口鉴权功能, 保护系统的接口安全', 6),
       (1, 5, '编写系统报表模块', '编写系统的报表模块, 包括报表的设计和代码编写', 9.5),
       (1, 3, '增加用户登录日志记录', '在系统中增加用户登录日志记录功能, 记录用户的登录信息', 4.75),
       (1, 1, '修复首页的样式', '将首页的样式进行修改, 使其更符合UI设计规范', 2.5);

# -----------------------------------------
# 公告
CREATE TABLE IF NOT EXISTS `notice`
(
    `id`         INT AUTO_INCREMENT COMMENT 'id',
    `title`      VARCHAR(255) NOT NULL COMMENT '标题',
    `content`    VARCHAR(255) NOT NULL COMMENT '内容',
    `type`       INT          NOT NULL COMMENT '类型',
    `flag`       TINYINT(1) DEFAULT 0 COMMENT '是否删除',
    `created_at` DATETIME   DEFAULT NOW() COMMENT '创建时间',
    `updated_at` DATETIME   DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) COMMENT '公告';

INSERT INTO `notice` (`title`, `content`, `type`)
VALUES ('网站维护公告', '本网站将于8月15日晚上10点至8月16日凌晨2点进行系统维护,给您带来的不便敬请谅解.', 1),
       ('公司变更公告', '尊敬的客户, 我们公司将于下周起停止营业, 感谢您一直以来对我们的支持。', 1),
       ('办公室搬迁通知', '为了更好地提供服务,本公司将于8月20日搬迁至新地址,地址为xxxx.', 1),
       ('系统升级公告', '本网站将于8月25日晚上10点至8月26日凌晨2点进行系统升级,给您带来的不便敬请谅解.', 1),
       ('退货政策更新公告', '尊敬的客户,感谢您一直以来对我们公司的支持,为了更好地满足您的需求,本公司退货政策有所更新,详情请查看官网公告.', 9),
       ('公司章程修订公告', '本公司章程进行了修订,详情请查看官网公告.', 1),
       ('客服热线变更公告', '本公司客服热线已经变更为xxxxxx,请知悉.', 1),

       ('公司裁员公告', '尊敬的员工, 因公司业务调整, 需要裁员, 请各位员工提前做好准备。', 2),
       ('新员工入职通知', '欢迎张三加入本公司,他将担任市场销售部门的销售经理,敬请大家支持和配合.', 2),
       ('员工调动通知', '尊敬的员工, 经过公司考虑, 您将从XX部门调入YY部门, 具体调动时间为下周一, 请做好准备。', 2),
       ('员工晋升通知', '尊敬的员工, 恭喜您获得晋升资格, 您将从XX职位晋升为YY职位, 请继续保持好的工作表现。', 2),
       ('假期放假通知', '根据国家法定假期规定,本公司将于10月1日至10月7日放假,10月8日正常上班.', 2),
       ('员工晋升通知', '恭喜李四成功晋升为市场销售部门的销售经理.', 2),
       ('员工培训通知', '本公司将于9月1日至9月3日举办员工培训,敬请大家准时参加.', 2),

       ('招聘Java工程师', '我们公司目前急需招聘一名Java工程师, 要求应届生, 精通Java开发, 有3年以上工作经验。', 3),
       ('招聘销售经理', '我们公司目前急需招聘一名销售经理, 要求具备良好的沟通能力和销售技巧, 有5年以上相关工作经验。', 3),
       ('招聘公告', '本公司招聘销售代表若干名,要求具有1年以上销售经验,待遇优厚,欢迎应聘.', 3),

       ('年度报告发布', '本公司的2021年度报告已经发布,欢迎关注并下载查看.', 4),
       ('2022年度报告发布', '我们公司2022年度报告已于昨日发布, 欢迎各位股东查阅。', 4),
       ('2021年度报告解读', '我们公司将于下周五举行2021年度报告解读会议, 欢迎各位股东参加。', 4),

       ('招标公告', '本公司招标采购一批办公设备,欢迎符合要求的供应商前来报名.', 5),

       ('业务合作通知', '本公司与xxxx公司达成业务合作协议,欢迎各位客户前来咨询.', 6),

       ('产品促销公告', '本公司的最新产品正在进行促销活动,折扣力度超大,欢迎选购.', 7),
       ('新产品发布', '本公司的最新产品已经发布,欢迎关注并购买.', 7),

       ('节日祝福', '本公司祝全体员工中秋快乐,阖家幸福!', 100),

       ('会员权益更新通知', '本公司会员权益有所调整,详情请查看官网公告.', 105);

# -----------------------------------------
# 网站广告数据表
CREATE TABLE IF NOT EXISTS `advertisements`
(
    `id`         INT AUTO_INCREMENT COMMENT '广告唯一标识符',
    `title`      VARCHAR(255) NOT NULL COMMENT '标题',
    `image_url`  VARCHAR(255) NOT NULL COMMENT '图片 URL 地址',
    `link_url`   VARCHAR(255) NOT NULL COMMENT '链接 URL 地址',
    `start_date` DATETIME DEFAULT NOW() COMMENT '展示开始日期',
    `end_date`   DATETIME     NOT NULL COMMENT '展示结束日期',
    `created_at` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT '网站广告数据表';

INSERT INTO `advertisements` (`title`, `image_url`, `link_url`, `start_date`, `end_date`)
VALUES ('Steam夏日特惠', 'steam.png', 'https://store.steampowered.com/', '2022-08-03', '2022-08-12'),
       ('猫咪日特别活动', 'cat.png', 'https://example.com/activity/cat', '2022-08-04', '2022-08-13'),
       ('女性节限时特惠', 'women.png', 'https://example.com/activity/women', '2022-08-05', '2022-08-14'),
       ('Apple全场满减', 'apple.png', 'https://www.apple.com/cn/', '2022-08-06', '2022-08-15'),
       ('华为MatePad 5G新品发布', 'huawei.png', 'https://consumer.huawei.com/cn/tablets/matepad-11-5g/', '2022-08-07',
        '2022-08-16'),
       ('亚马逊超级品牌日', 'amazon.png', 'https://www.amazon.cn/', '2022-08-08', '2022-08-17'),
       ('LOL英雄联盟冠军杯', 'lol.png', 'https://www.lol.com/', '2022-08-09', '2022-08-18'),
       ('绝地求生新地图上线', 'pubg.png', 'https://www.pubg.com/', '2022-08-10', '2022-08-19'),
       ('星巴克新品上市', 'starbucks.png', 'https://www.starbucks.com.cn/', '2022-08-11', '2022-08-20'),
       ('华硕灵耀X13新品发布', 'asus.png', 'https://www.asus.com.cn/Laptops/For-Home/All-series/ZenBook/', '2022-08-12',
        '2022-08-21'),
       ('小米11T Pro限时特惠', 'xiaomi.png', 'https://www.mi.com/', '2022-08-13', '2022-08-22'),
       ('海尔空调618大促', 'haier.png', 'https://www.haier.com/cn/', '2022-08-14', '2022-08-23'),
       ('美团外卖618狂欢节', 'meituan.png', 'https://waimai.meituan.com/', '2022-08-15', '2022-08-24'),
       ('京东618全民狂欢', 'jd.png', 'https://www.jd.com/', '2022-08-16', '2022-08-25'),
       ('知乎好物推荐', 'zhihu.png', 'https://www.zhihu.com/', '2022-08-17', '2022-08-26'),
       ('微软Surface新品发布', 'microsoft.png', 'https://www.microsoft.com/zh-cn/', '2022-08-18', '2022-08-27'),
       ('百度网盘限时优惠', 'baidu.png', 'https://pan.baidu.com/', '2022-08-19', '2022-08-28'),
       ('中国电信客户节优惠', 'telecom.png', 'https://www.189.cn/', '2022-08-20', '2022-08-29'),
       ('New iPhone 13 now available', 'iphone.png', 'https://www.apple.com/iphone-13/', '2022-08-01', '2022-08-10'),
       ('Amazon Prime Day is here', 'amazon.png', 'https://www.amazon.com/prime-day/', '2022-08-02', '2022-08-11'),
       ('Samsung Galaxy Z Flip3 now in stock', 'samsung.png',
        'https://www.samsung.com/us/smartphones/galaxy-z-flip3-5g/', '2022-08-03', '2022-08-12'),
       ('Get 20% off on all shoes at Nike', 'nike.png', 'https://www.nike.com/', '2022-08-04', '2022-08-13'),
       ('Join the Disney+ streaming service', 'disney.png', 'https://www.disneyplus.com/', '2022-08-05', '2022-08-14'),
       ('Netflix Originals: New releases every week', 'netflix.png', 'https://www.netflix.com/', '2022-08-06',
        '2022-08-15'),
       ('Shop the latest trends at Zara', 'zara.png', 'https://www.zara.com/', '2022-08-07', '2022-08-16'),
       ('The best deals on laptops at Best Buy', 'bestbuy.png',
        'https://www.bestbuy.com/site/computers-pcs/laptop-computers/abcat0502000.c?id=abcat0502000', '2022-08-08',
        '2022-08-17'),
       ('Save up to 30% on hotels at Booking.com', 'booking.png', 'https://www.booking.com/', '2022-08-09',
        '2022-08-18'),
       ('Get a free trial of Adobe Creative Cloud', 'adobe.png',
        'https://www.adobe.com/creativecloud/start-with-free-creativecloud.html', '2022-08-10', '2022-08-19'),
       ('Discover new music on Spotify', 'spotify.png', 'https://www.spotify.com/', '2022-08-11', '2022-08-20'),
       ('The latest gaming news at IGN', 'ign.png', 'https://www.ign.com/', '2022-08-12', '2022-08-21'),
       ('Shop the latest fashion at ASOS', 'asos.png', 'https://www.asos.com/', '2022-08-13', '2022-08-22'),
       ('The best deals on home appliances at Home Depot', 'homedepot.png', 'https://www.homedepot.com/c/Appliances',
        '2022-08-14', '2022-08-23'),
       ('Get a free trial of Amazon Prime Video', 'amazonprime.png', 'https://www.amazon.com/amazonprime', '2022-08-15',
        '2022-08-24'),
       ('Shop the latest tech at Newegg', 'newegg.png', 'https://www.newegg.com/', '2022-08-16', '2022-08-25'),
       ('The best deals on flights at Expedia', 'expedia.png', 'https://www.expedia.com/', '2022-08-17', '2022-08-26'),
       ('Join LinkedIn and find your dream job', 'linkedin.png', 'https://www.linkedin.com/', '2022-08-18',
        '2022-08-27'),
       ('Shop the latest home décor at Wayfair', 'wayfair.png', 'https://www.wayfair.com/', '2022-08-19', '2022-08-28'),
       ('Get a free trial of HBO Max', 'hbomax.png', 'https://www.hbomax.com/', '2022-08-20', '2022-08-29');

CREATE TABLE IF NOT EXISTS `advertisements_clicks`
(
    `id`               INT          NOT NULL AUTO_INCREMENT COMMENT '唯一标识符',
    `advertisement_id` INT          NOT NULL COMMENT '广告唯一标识符',
    `user_id`          INT          NOT NULL COMMENT '用户唯一标识符',
    `click_time`       DATETIME DEFAULT NOW() COMMENT '广告点击时间',
    `ip_address`       VARCHAR(255) NOT NULL COMMENT '用户 IP 地址',
    PRIMARY KEY (`id`)
) COMMENT '广告点击数据表';

# -----------------------------------------
# 任务
CREATE TABLE `task`
(
    `id`              INT AUTO_INCREMENT COMMENT 'ID',
    `name`            VARCHAR(255) NOT NULL COMMENT '名称',
    `cron_expression` VARCHAR(255) NOT NULL COMMENT '表达式',
    `invoke_target`   VARCHAR(255) NOT NULL COMMENT '调用目标',
    `cron_describe`   VARCHAR(50) DEFAULT NULL COMMENT '描述',
    `enabled`         TINYINT(1)  DEFAULT 0 COMMENT '是否启用',
    `created_at`      DATETIME    DEFAULT NOW() COMMENT '创建时间',
    `updated_at`      DATETIME    DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT '定时任务';

INSERT INTO `task` (name, cron_expression, invoke_target, cron_describe, enabled)
VALUES ('测试1', '0 0/1 * * * ?', 'com.qing.erp.monitor.taskP.TaskTest.aaa', '每分钟执行一次', false),
       ('测试2', '0/20 * * * * *', 'com.qing.erp.system.util.PrintContent.method', '每20s执行一次', true),
       ('测试2', '0/20 * * * * *', 'com.qing.erp.system.util.PrintContent.test', '每20s执行一次', true);

# -----------------------------------------
# 友情链接
CREATE TABLE IF NOT EXISTS `friend_link`
(
    `id`          INT AUTO_INCREMENT COMMENT 'ID',
    `name`        VARCHAR(50)  NOT NULL COMMENT '名称',
    `url`         VARCHAR(255) NOT NULL COMMENT 'URL',
    `logo_url`    VARCHAR(255) DEFAULT NULL COMMENT 'Logo URL',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
    `sort`        int          DEFAULT 0 COMMENT '排序',
    `created_at`  DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `updated_at`  DATETIME     DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT '友情链接';

INSERT INTO `friend_link` (name, url, logo_url, description)
VALUES ('花花', 'https://space.bilibili.com/51117533',
        'https://i2.hdslb.com/bfs/face/9a01bb2a161e2763b920879508fcf66309161fe4.jpg', '单片机大佬'),
       ('阿蚕', 'https://space.bilibili.com/33322892',
        'https://i2.hdslb.com/bfs/face/3438fa5e69cb27b1fb80b249fa703f32fddead55.jpg', '卷王老师'),

       ('阿白', 'https://space.bilibili.com/334374925', 'https://i0.hdslb.com/bfs/face/member/noface.jpg', '打拳高手'),
       ('鱼鱼', 'https://space.bilibili.com/18393600',
        'https://i0.hdslb.com/bfs/face/dd2f5c1d83c17c1edde13a6b3a5f554c96a0aa30.jpg', 'kamisama'),

       ('雨凡', 'https://space.bilibili.com/448536447',
        'https://i0.hdslb.com/bfs/face/4a2d96ae7fe6cebb001a7f33217978fd8a64a8d3.jpg', '文科理科双修的才女'),
       ('糕糕', 'https://space.bilibili.com/482058919',
        'https://i1.hdslb.com/bfs/face/530f1fea7af25766c53591bfe0ae62bbf9cdf122.jpg', '学霸糕神'),

       ('冬日', 'http://www.yucoc.xyz/log', 'https://s2.loli.net/2022/03/15/jiMvzN8Wfw47gcZ.jpg', '人工智能卷王乌冬面'),
       ('阿黑', 'https://space.bilibili.com/38949842',
        'https://i2.hdslb.com/bfs/face/132f4f3314e7d477edb9170db56c0fe5d7290630.jpg@240w_240h_1c_1s.webp', 'MCMod开发者'),

       ('白萌萌', '',
        'https://i2.hdslb.com/bfs/face/b2440b6549e9e5cc40d9b86fcb7f09f0e20af345.jpg@96w_96h_1c_1s_!web-avatar-space-list.webp',
        '什么都会的百科全书大佬'),
       ('羽于翼', 'https://space.bilibili.com/11609923',
        'https://i1.hdslb.com/bfs/face/da8489cdf6e71e0e3dc111aca56274f616d4d943.jpg@240w_240h_1c_1s.webp',
        'Unity游戏开发者'),

       ('小五', 'https://space.bilibili.com/12802243',
        'https://i1.hdslb.com/bfs/face/970e57f90d60ec4d8090b2280766e306719300ef.jpg@96w_96h_1c_1s_!web-avatar-space-list.webp',
        '什么都会的大佬'),
       ('阿水', '', '', '逆向大佬,还会写os,language,database'),
       ('云云', 'https://space.bilibili.com/215001961',
        'https://i1.hdslb.com/bfs/face/44a04bfdd2a1d4a9a5cb1f0626c57b5ba5a8427f.jpg@240w_240h_1c_1s_!web-avatar-space-header.webp',
        '学霸'),
       ('蛙蛙', '', '', 'csharp大佬'),
       ('吉尔', '', '', '机器人文游大佬'),
       ('Mary', '', '', 'cpp大佬'),
       ('天蔚', '', '', '前后端大佬'),
       ('小狐狸', '', '', '全栈大佬, 上到前后端, 下到单片机/模电/逆向');

# -----------------------------------------
# feedback
CREATE TABLE `feedback`
(
    `id`         INT  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`    INT  NOT NULL COMMENT '用户ID',
    `text`       TEXT NOT NULL COMMENT '反馈文本',
    `date`       DATETIME DEFAULT NOW() COMMENT '反馈提交日期',
    `rating`     INT      DEFAULT NULL COMMENT '反馈评分',
    `category`   INT      DEFAULT 0 COMMENT '反馈分类',
    `status`     INT      DEFAULT 0 COMMENT '反馈处理状态',
    `created_at` DATETIME DEFAULT NOW() COMMENT '创建时间',
    PRIMARY KEY (`id`)
) COMMENT '用户反馈表';

INSERT INTO `feedback` (`user_id`, `text`, `rating`, `category`, `status`)
VALUES (1, '这个应用非常好用, 非常感谢！', 5, 1, 1),
       (1, '我发现一个小问题, 希望能够修复', 3, 2, 0),
       (1, '这个应用太棒了, 没有任何问题！', 5, 1, 1),
       (1, '我有一个功能建议, 希望能够添加一个XX功能', 4, 3, 0),
       (1, '我不太喜欢这个应用的界面, 希望能够改进', 2, 2, 0),
       (1, '这个应用有时候会出现一些错误, 希望能够修复', 3, 4, 0),
       (1, '我发现了一个漏洞, 希望能够尽快修复', 2, 4, 0),
       (1, '我非常喜欢这个应用的颜色搭配, 非常好看！', 5, 1, 1),
       (1, '这个应用的响应速度非常快, 非常流畅', 5, 1, 1),
       (1, '我希望能够添加一个更好用的搜索功能', 4, 3, 0),
       (1, '这个应用的广告有点多, 希望能够减少一些', 2, 2, 0),
       (1, '我发现了一个优化建议, 希望能够考虑一下', 4, 3, 0),
       (1, '这个应用的安全性需要提高, 希望能够加强保护', 3, 4, 0),
       (1, '我非常喜欢这个应用的可定制性, 非常灵活！', 5, 1, 1),
       (1, '这个应用需要更好的文档和说明, 不太好理解', 2, 2, 0),
       (1, '我希望能够添加一个更好用的导出功能', 4, 3, 0),
       (1, '这个应用的界面有点老旧, 需要更新一下', 3, 2, 0),
       (1, '我发现了一个性能问题, 希望能够优化一下', 3, 4, 0),
       (1, '这个应用需要更好的多语言支持, 不太友好', 2, 2, 0),
       (1, '我非常喜欢这个应用的简洁风格, 非常美观！', 5, 1, 1);

# activity
CREATE TABLE `activity`
(
    `id`            INT          NOT NULL AUTO_INCREMENT COMMENT '活动ID',
    `name`          VARCHAR(255) NOT NULL COMMENT '活动名称',
    `description`   TEXT         DEFAULT NULL COMMENT '活动描述',
    `start_time`    DATETIME     DEFAULT NOW() COMMENT '活动开始时间',
    `end_time`      DATETIME     NOT NULL COMMENT '活动结束时间',
    `location`      VARCHAR(255) DEFAULT NULL COMMENT '活动地点',
    `organizer_id`  INT          NOT NULL COMMENT '组织者ID',
    `max_attendees` INT          DEFAULT 8 COMMENT '最大参与人数',
    `created_at`    DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `updated_at`    DATETIME     DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT '活动表';

INSERT INTO `activity` (name, description, start_time, end_time, location, organizer_id)
VALUES ('公司年会', '公司年度盛事, 由公司领导致辞, 有精彩的文艺节目和抽奖活动。', '2023-12-30 18:00:00', '2023-12-30 22:00:00', '公司会议厅', 1),
       ('新员工培训', '为新员工提供全面的公司文化和岗位培训, 以更好地适应公司环境。', '2024-01-15 09:00:00', '2024-01-17 17:00:00', '公司培训室', 1),
       ('团队建设活动', '员工之间互相了解、协作、信任和尊重, 增强团队凝聚力和战斗力。', '2024-02-10 09:00:00', '2024-02-11 17:00:00', '郊外度假村', 1),
       ('春节联欢晚会', '庆祝中国传统节日, 有节目表演、美食和红包派发。', '2024-02-11 18:00:00', '2024-02-11 22:00:00', '公司会议厅', 1),
       ('劳动节放假', '为员工提供休息时间, 放松身心, 让大家更充满活力地投入工作。', '2024-05-01 00:00:00', '2024-05-01 23:59:59', NULL, 1),
       ('母亲节礼物发放', '感恩母爱, 为员工送上母亲节礼物。', '2024-05-12 09:00:00', '2024-05-12 18:00:00', '公司办公室', 1),
       ('端午节庆祝活动', '庆祝中国传统节日, 有粽子和龙舟比赛活动。', '2024-06-01 09:00:00', '2024-06-01 17:00:00', '公司楼下和附近的湖泊', 1),
       ('父亲节礼物发放', '感恩父爱, 为员工送上父亲节礼物。', '2024-06-16 09:00:00', '2024-06-16 18:00:00', '公司办公室', 1),
       ('公司周年庆典', '庆祝公司成立周年, 回顾公司发展历程, 展望未来发展。', '2024-07-01 18:00:00', '2024-07-01 22:00:00', '公司会议厅', 1),
       ('暑假放假', '为员工提供休息时间, 放松身心, 让大家更充满活力地投入工作。', '2024-07-15 00:00:00', '2024-08-31 23:59:59', NULL, 1),
       ('中秋节庆祝活动', '庆祝中国传统节日, 有文艺节目和月饼抽奖活动。', '2024-09-13 09:00:00', '2024-09-13 17:00:00', '公司会议厅', 1),
       ('国庆节放假', '为员工提供休息时间, 放松身心, 让大家更充满活力地投入工作。', '2024-10-01 00:00:00', '2024-10-07 23:59:59', NULL, 1),
       ('双十一购物节', '员工可享受公司购物节的福利, 购买心仪的商品。', '2024-11-11 09:00:00', '2024-11-11 18:00:00', '公司办公室', 1),
       ('感恩节放假', '为员工提供休息时间, 让大家感恩家人和身边的人。', '2024-11-28 00:00:00', '2024-11-29 23:59:59', NULL, 1),
       ('圣诞节礼物发放', '为员工送上圣诞节礼物, 让大家感受节日的温馨和喜悦。', '2024-12-23 09:00:00', '2024-12-23 18:00:00', '公司办公室', 1),
       ('公司年会', '公司年度盛事, 由公司领导致辞, 有精彩的文艺节目和抽奖活动。', '2024-12-30 18:00:00', '2024-12-30 22:00:00', '公司会议厅', 1),
       ('新员工培训', '为新员工提供全面的公司文化和岗位培训, 以更好地适应公司环境。', '2025-01-15 09:00:00', '2025-01-17 17:00:00', '公司培训室', 1),
       ('团队建设活动', '员工之间互相了解、协作、信任和尊重, 增强团队凝聚力和战斗力。', '2025-02-10 09:00:00', '2025-02-11 17:00:00', '郊外度假村', 1),
       ('春节联欢晚会', '庆祝中国传统节日, 有节目表演、美食和红包派发。', '2025-02-11 18:00:00', '2025-02-11 22:00:00', '公司会议厅', 1),
       ('劳动节放假', '为员工提供休息时间, 放松身心, 让大家更充满活力地投入工作。', '2025-05-01 00:00:00', '2025-05-01 23:59:59', NULL, 1),
       ('母亲节礼物发放', '感恩母爱, 为员工送上母亲节礼物。', '2025-05-12 09:00:00', '2025-05-12 18:00:00', '公司办公室', 1),
       ('端午节庆祝活动', '庆祝中国传统节日, 有粽子和龙舟比赛活动。', '2025-06-01 09:00:00', '2025-06-01 17:00:00', '公司楼下和附近的湖泊', 1),
       ('父亲节礼物发放', '感恩父爱, 为员工送上父亲节礼物。', '2025-06-16 09:00:00', '2025-06-16 18:00:00', '公司办公室', 1),
       ('公司周年庆典', '庆祝公司成立周年, 回顾公司发展历程, 展望未来发展。', '2025-07-01 18:00:00', '2025-07-01 22:00:00', '公司会议厅', 1),
       ('暑假放假', '为员工提供休息时间, 放松身心, 让大家更充满活力地投入工作。', '2025-07-15 00:00:00', '2025-08-31 23:59:59', NULL, 1),
       ('中秋节庆祝活动', '庆祝中国传统节日, 有文艺节目和月饼抽奖活动。', '2025-09-13 09:00:00', '2025-09-13 17:00:00', '公司会议厅', 1);

CREATE TABLE `activity_user`
(
    `activity_id` INT NOT NULL COMMENT '活动ID',
    `user_id`     INT NOT NULL COMMENT '用户ID',
    `created_at`  DATETIME DEFAULT NOW() COMMENT '报名时间'
) COMMENT '参与表';

INSERT INTO `activity_user` (activity_id, user_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1);



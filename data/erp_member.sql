CREATE DATABASE IF NOT EXISTS erp_member;
USE erp_member;

-- ----------------------------
-- 用户&&用户信息
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user`
(
    `id`       INT          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `uuid`     VARCHAR(255) NOT NULL COMMENT 'UUID',
    `account`  VARCHAR(255) NOT NULL COMMENT '账号',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',
    `flag`     TINYINT(1) DEFAULT NULL COMMENT '逻辑删除',
    `notes`    TEXT       DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id)
) COMMENT '用户';

CREATE TABLE IF NOT EXISTS `user_info`
(
    `id`             INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`        INT NOT NULL COMMENT '用户ID',
    `nick`           VARCHAR(255)   DEFAULT NULL COMMENT '昵称',
    `face`           VARCHAR(255)   DEFAULT NULL COMMENT '头像',
    `age`            INT            DEFAULT NULL COMMENT '年龄',
    `sex`            INT            DEFAULT NULL COMMENT '性别',
    `introduce`      TEXT           DEFAULT NULL COMMENT '个人介绍',
    `register_ip`    VARCHAR(255)   DEFAULT NULL COMMENT '注册IP',
    `created_at`     DATETIME       DEFAULT NOW() COMMENT '注册时间',
    `updated_at`     DATETIME       DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT '用户(信息)';

CREATE TABLE IF NOT EXISTS `user_security`
(
    `id`                 INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`            INT NOT NULL COMMENT '用户ID',
    `email`              VARCHAR(255) DEFAULT NULL COMMENT '邮箱',
    `phone_number`       VARCHAR(255) DEFAULT NULL COMMENT '手机',
    `qq`                 VARCHAR(255) DEFAULT NULL COMMENT 'qq',
    `security_questions` JSON         DEFAULT NULL COMMENT '密保',
    `updated_at`         DATETIME     DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间',
    PRIMARY KEY (id)

) COMMENT '用户(安全)';

CREATE TABLE IF NOT EXISTS `user_inventory`
(
    `id`         INT PRIMARY KEY AUTO_INCREMENT COMMENT '唯一物品的唯一标识符',
    `user_id`    INT NOT NULL COMMENT '用户ID',
    `item_id`    INT NOT NULL COMMENT '物品ID',
    `quantity`   INT NOT NULL COMMENT '物品数量',
    `created_at` DATETIME DEFAULT NOW() COMMENT '获取时间',
    `updated_at` DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间'
) COMMENT '用户(背包)';

CREATE TABLE IF NOT EXISTS `user_inventor_unique`
(
    `id`          INT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    `user_id`     INT NOT NULL COMMENT '用户ID',
    `item_id`     INT NOT NULL COMMENT '物品ID',
    `name`        VARCHAR(255) DEFAULT NULL COMMENT '自定义名称',
    `description` TEXT         DEFAULT NULL COMMENT '自定义描述',
    `image_url`   VARCHAR(255) DEFAULT NULL COMMENT '自定义图片URL',
    `nbt`         JSON         DEFAULT NULL COMMENT 'Named Binary Tag',
    `created_at`  DATETIME     DEFAULT NOW() COMMENT '创建时间'
) COMMENT '用户(背包:唯一)';

CREATE TABLE IF NOT EXISTS `user_wallet`
(
    `id`            INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id         INT NOT NULL COMMENT '用户ID',
    coins           DECIMAL(10, 2) DEFAULT 0 COMMENT '纸币',
    gold            DECIMAL(10, 2) DEFAULT 0 COMMENT '金币',
    diamonds        DECIMAL(10, 2) DEFAULT 0 COMMENT '钻石',
    custom_currency JSON           DEFAULT NULL COMMENT '自定义货币({id, balance})',
    updated_at      DATETIME       DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT '用户(钱包)';

CREATE TABLE IF NOT EXISTS `user_level`
(
    `id`         INT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    `user_id`    INT NOT NULL COMMENT '用户ID',
    `level`      INT      DEFAULT 1 COMMENT '等级',
    `experience` INT      DEFAULT 0 COMMENT '经验值',
    `updated_at` DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间'
) COMMENT '用户(等级)';

INSERT INTO `user` (uuid, account, password, flag)
VALUES (UUID_SHORT(), 'admin', 'admin', false),
       (UUID_SHORT(), 'root', 'root', false),
       (UUID_SHORT(), 'halfRain', '123456789', false);

INSERT INTO `user_info` (user_id, nick)
VALUES (1, '管理员R'),
       (2, '开发者R'),
       (3, '普通用户R');

INSERT INTO `user_security` (user_id, email, phone_number, qq, security_questions)
VALUES (1, 'admin@qq.com', '123456', '2042', '{
  "1+1等于几": "不知道",
  "2**2等于几": "不知道"
}'),
       (2, 'root@qq.com', '654321', '136', '{}'),
       (3, 'halfRain@qq.com', '666666', '767', '{}');

INSERT INTO `user_inventory` (user_id, item_id, quantity)
VALUES (1, 1, 64),
       (1, 2, 128),
       (1, 3, 256),
       (1, 4, 512);

INSERT INTO `user_inventor_unique` (user_id, item_id, name, description, image_url, nbt)
VALUES (1, 1, '专属武器', '锻造', '', '{}');

INSERT INTO `user_wallet` (user_id, coins, gold, diamonds, custom_currency)
VALUES (1, 100, 200, 300, '{}'),
       (2, 100, 200, 300, '{}'),
       (3, 0, 0, 0, '{}');

INSERT INTO `user_level` (user_id, level, experience)
VALUES (1, 6, 9999999),
       (2, 6, 9999999),
       (3, 1, 0);


-- ----------------------------
-- 角色&&权限
-- ----------------------------
CREATE TABLE IF NOT EXISTS `role`
(
    `id`         INT NOT NULL AUTO_INCREMENT COMMENT 'id',
    `code`       VARCHAR(255) DEFAULT NULL COMMENT '代码(唯一)',
    `name`       VARCHAR(255) DEFAULT NULL COMMENT '名称',
    `label`      VARCHAR(255) DEFAULT NULL COMMENT '标签',
    `introduce`  VARCHAR(255) DEFAULT NULL COMMENT '介绍',
    `flag`       TINYINT(1)   DEFAULT 0 COMMENT '逻辑删除',
    `created_at` DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `updated_at` DATETIME     DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间',
    PRIMARY KEY (`id`)
) COMMENT '角色';

CREATE TABLE IF NOT EXISTS `permission`
(
    `id`         INT NOT NULL AUTO_INCREMENT COMMENT 'id',
    `code`       VARCHAR(255) DEFAULT NULL COMMENT '代码(唯一)',
    `name`       VARCHAR(255) DEFAULT NULL COMMENT '名称',
    `introduce`  VARCHAR(255) DEFAULT NULL COMMENT '介绍',
    `uri`        BIGINT(20)   DEFAULT NULL COMMENT 'URL规则',
    `flag`       TINYINT(1)   DEFAULT 0 COMMENT '逻辑删除',
    `created_at` DATETIME     DEFAULT NOW() COMMENT '创建时间',
    `updated_at` DATETIME     DEFAULT NOW() ON UPDATE NOW() COMMENT '修改时间',
    PRIMARY KEY (id)
) COMMENT '权限';

INSERT INTO role (code, name, label)
VALUES ('GUEST', '游客', '游客'),
       ('USER', '普通用户', '普通用户'),
       ('ADMIN', '管理员', '管理员'),
       ('DEV', '开发者', '开发者'),

       ('EMP', '员工', '员工'),
       ('ACC', '会计', '会计'),
       ('SALES', '销售', '销售人员'),
       ('EDITOR', '编辑', '编辑'),
       ('ENGINEER', '工程师', '工程师'),
       ('DESIGNER', '设计师', '设计师'),
       ('MARKETING', '市场营销', '市场营销人员'),
       ('SUPPORT', '技术支持', '技术支持人员'),

       ('MGR', '主管', '主管'),
       ('CUS', '客户', '客户'),
       ('ACCOUNT_MANAGER', '客户经理', '客户经理'),
       ('SUP', '供应商', '供应商'),

       ('OPERATOR', '操作员', '操作员'),
       ('CONSULTANT', '顾问', '顾问'),
       ('FINANCE', '财务', '财务人员'),
       ('HR', '人力资源', '人力资源专员');

INSERT INTO permission (code, name, introduce)
VALUES ('all', '访问全部', '允许用户访问所有模块'),
       ('read', '只读', '允许用户查看数据, 但不允许修改或删除'),
       ('write', '写入', '允许用户添加或修改数据, 但不允许删除'),
       ('delete', '删除', '允许用户删除数据'),
       ('create', '创建', '允许用户创建新的数据记录'),
       ('update', '更新', '允许用户更新现有的数据记录'),
       ('view', '查看', '允许用户查看数据记录的详细信息'),
       ('approve', '审核', '允许用户审核数据记录'),
       ('reject', '拒绝', '允许用户拒绝数据记录'),
       ('submit', '提交', '允许用户提交数据记录'),
       ('publish', '发布', '允许用户发布数据记录'),
       ('un_publish', '取消发布', '允许用户取消已发布的数据记录'),
       ('comment', '评论', '允许用户添加评论'),
       ('like', '点赞', '允许用户给数据记录点赞'),
       ('dislike', '踩', '允许用户给数据记录踩'),
       ('share', '分享', '允许用户分享数据记录'),
       ('download', '下载', '允许用户下载数据记录'),
       ('print', '打印', '允许用户打印数据记录'),
       ('export', '导出', '允许用户导出数据记录'),
       ('import', '导入', '允许用户导入数据记录');

-- ----------------------------
-- 机构&&部门&&岗位
-- ----------------------------
CREATE TABLE IF NOT EXISTS `organization`
(
    `id`        INT          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `uuid`      VARCHAR(255) NOT NULL COMMENT 'UUID',
    `name`      VARCHAR(255) DEFAULT NULL COMMENT '名称',
    `info`      TEXT         DEFAULT NULL COMMENT '信息',
    `notes`     TEXT         DEFAULT NULL COMMENT '备注',
    `parent_id` INT          DEFAULT NULL COMMENT '上级机构',
    PRIMARY KEY (id)
) COMMENT '机构';

CREATE TABLE IF NOT EXISTS `department`
(
    `id`              INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `organization_id` INT NOT NULL COMMENT '机构ID',
    `name`            VARCHAR(255) DEFAULT NULL COMMENT '名称',
    `info`            TEXT         DEFAULT NULL COMMENT '信息',
    `notes`           TEXT         DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id)
) COMMENT '部门';

CREATE TABLE IF NOT EXISTS `position`
(
    `id`            INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `department_id` INT NOT NULL COMMENT '部门ID',
    `name`          VARCHAR(255) DEFAULT NULL COMMENT '名称',
    `info`          TEXT         DEFAULT NULL COMMENT '信息',
    `notes`         TEXT         DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id)
) COMMENT '岗位';

-- ----------------------------
-- 中间表
-- ----------------------------

CREATE TABLE IF NOT EXISTS `map_role_permission`
(
    `id`            INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id`       INT NOT NULL COMMENT '角色ID',
    `permission_id` INT NOT NULL COMMENT '权限ID',
    PRIMARY KEY (id)
) COMMENT '角色-权限';

CREATE TABLE IF NOT EXISTS `map_user_role`
(
    `id`      INT NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id` INT NOT NULL COMMENT '用户ID',
    `role_id` INT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (id)
) COMMENT '用户-角色';

INSERT INTO `map_user_role` (user_id, role_id)
VALUES (1, 3),
       (2, 4),
       (3, 2);

CREATE TABLE IF NOT EXISTS `map_user_position`
(
    `id`          INT NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`     INT NOT NULL COMMENT '用户ID',
    `position_id` INT NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (id)
) COMMENT '用户-岗位';

# status: 0=待处理, 1=已接受, 2=已拒绝, 3=已删除
CREATE TABLE `map_user_user`
(
    id         INT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    user_id    INT NOT NULL COMMENT '发起请求的用户ID',
    friend_id  INT NOT NULL COMMENT '接受请求的用户ID',
    status     INT      DEFAULT 0 COMMENT '好友关系的状态',
    created_at DATETIME DEFAULT NOW() COMMENT '创建时间',
    updated_at DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '更新时间'
) COMMENT '好友表';


/*-- 根据用户ID查询用户信息表
SELECT *
FROM user_info
WHERE user_id = (
    -- 查看用户表, 如果id为NULL, 那就传一个1进去(这个1你可以替换为动态的变量)
    SELECT IFNULL(user.id, 1)
    FROM user
    -- 因为是演示, 所以这里是LIMIT, 实际上你用where或其他什么都行
    LIMIT 1
);*/

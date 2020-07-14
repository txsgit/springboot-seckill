/*
Navicat MySQL Data Transfer

Source Server         : 192.168.31.249
Source Server Version : 50730
Source Host           : 192.168.31.249:3306
Source Database       : db_second_kill

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-07-14 11:17:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `item`
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名' ,
`code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品编号' ,
`stock`  bigint(20) NULL DEFAULT NULL COMMENT '库存' ,
`purchase_time`  date NULL DEFAULT NULL COMMENT '采购时间' ,
`is_active`  int(11) NULL DEFAULT 1 COMMENT '是否有效（1=是；0=否）' ,
`create_time`  datetime NULL DEFAULT NULL ,
`update_time`  timestamp NULL DEFAULT NULL COMMENT '更新时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='商品表'
AUTO_INCREMENT=9

;

-- ----------------------------
-- Records of item
-- ----------------------------
BEGIN;
INSERT INTO `item` VALUES ('6', 'Java编程思想', 'book10010', '1000', '2019-05-18', '1', '2019-05-18 21:11:23', null), ('7', 'Spring实战第四版', 'book10011', '2000', '2019-05-18', '1', '2019-05-18 21:11:23', null), ('8', '深入分析JavaWeb', 'book10012', '2000', '2019-05-18', '1', '2019-05-18 21:11:23', null);
COMMIT;

-- ----------------------------
-- Table structure for `item_kill`
-- ----------------------------
DROP TABLE IF EXISTS `item_kill`;
CREATE TABLE `item_kill` (
`id`  int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id' ,
`item_id`  int(11) NULL DEFAULT NULL COMMENT '商品id' ,
`total`  int(11) NULL DEFAULT NULL COMMENT '可被秒杀的总数' ,
`start_time`  datetime NULL DEFAULT NULL COMMENT '秒杀开始时间' ,
`end_time`  datetime NULL DEFAULT NULL COMMENT '秒杀结束时间' ,
`is_active`  tinyint(11) NULL DEFAULT 1 COMMENT '是否有效（1=是；0=否）' ,
`create_time`  timestamp NULL DEFAULT NULL COMMENT '创建的时间' ,
`version`  int(10) NOT NULL DEFAULT 0 COMMENT '版本号，乐观锁' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='待秒杀商品表'
AUTO_INCREMENT=4

;

-- ----------------------------
-- Records of item_kill
-- ----------------------------
BEGIN;
INSERT INTO `item_kill` VALUES ('1', '6', '0', '2020-07-06 21:59:07', '2020-07-30 21:59:11', '1', '2019-05-20 21:59:41', '10'), ('2', '7', '12', '2020-07-01 21:59:19', '2020-07-30 21:59:11', '1', '2019-05-20 21:59:41', '0'), ('3', '8', '97', '2020-07-01 18:58:26', '2020-07-30 21:59:07', '1', '2019-05-20 21:59:41', '0');
COMMIT;

-- ----------------------------
-- Table structure for `item_kill_success`
-- ----------------------------
DROP TABLE IF EXISTS `item_kill_success`;
CREATE TABLE `item_kill_success` (
`code`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '秒杀成功生成的订单编号' ,
`item_id`  int(11) NULL DEFAULT NULL COMMENT '商品id' ,
`kill_id`  int(11) NULL DEFAULT NULL COMMENT '秒杀id' ,
`user_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id' ,
`status`  tinyint(4) NULL DEFAULT '-1' COMMENT '秒杀结果: -1无效  0成功(未付款)  1已付款  2已取消' ,
`create_time`  timestamp NULL DEFAULT NULL COMMENT '创建时间' ,
PRIMARY KEY (`code`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='秒杀成功订单表'

;

-- ----------------------------
-- Records of item_kill_success
-- ----------------------------
BEGIN;
INSERT INTO `item_kill_success` VALUES ('480137995471826944', '6', '1', '101008', '0', '2020-07-13 06:38:04'), ('480137998332342272', '6', '1', '101009', '0', '2020-07-13 06:38:05'), ('480138000563712000', '6', '1', '101031', '0', '2020-07-13 06:38:05'), ('480138001671008256', '6', '1', '101043', '0', '2020-07-13 06:38:06'), ('480138002019135488', '6', '1', '101098', '0', '2020-07-13 06:38:06'), ('480138002363068416', '6', '1', '101047', '0', '2020-07-13 06:38:06'), ('480138003281620992', '6', '1', '101033', '0', '2020-07-13 06:38:06'), ('480138004133064704', '6', '1', '101069', '0', '2020-07-13 06:38:06'), ('480138004791570432', '6', '1', '101013', '0', '2020-07-13 06:38:07'), ('480138005500407808', '6', '1', '101021', '0', '2020-07-13 06:38:07'), ('480138005873700864', '6', '1', '101036', '0', '2020-07-13 06:38:07'), ('480138006423154688', '6', '1', '101001', '0', '2020-07-13 06:38:07'), ('480138007341707264', '6', '1', '101007', '0', '2020-07-13 06:38:07'), ('480138007761137664', '6', '1', '101042', '0', '2020-07-13 06:38:07'), ('480138007916326912', '6', '1', '101044', '0', '2020-07-13 06:38:07'), ('480138008054738944', '6', '1', '101029', '0', '2020-07-13 06:38:07'), ('480138008881016832', '6', '1', '101058', '0', '2020-07-13 06:38:07'), ('480138010202222592', '6', '1', '101061', '0', '2020-07-13 06:38:08'), ('480138011112386560', '6', '1', '101024', '0', '2020-07-13 06:38:08'), ('480138012492312576', '6', '1', '101005', '0', '2020-07-13 06:38:08'), ('480138013079515136', '6', '1', '101032', '0', '2020-07-13 06:38:08'), ('480138013146624000', '6', '1', '101003', '0', '2020-07-13 06:38:08'), ('480138013402476544', '6', '1', '101026', '0', '2020-07-13 06:38:09'), ('480138013519917056', '6', '1', '101027', '0', '2020-07-13 06:38:09'), ('480138013616386048', '6', '1', '101064', '0', '2020-07-13 06:38:09'), ('480138013805129728', '6', '1', '101065', '0', '2020-07-13 06:38:09'), ('480138014195200000', '6', '1', '101068', '0', '2020-07-13 06:38:09'), ('480138015365410816', '6', '1', '101067', '0', '2020-07-13 06:38:09'), ('480138020490850304', '6', '1', '101072', '0', '2020-07-13 06:38:10'), ('480138020650233856', '6', '1', '101071', '0', '2020-07-13 06:38:10'), ('480138020784451584', '6', '1', '101004', '0', '2020-07-13 06:38:10'), ('480138022181154816', '6', '1', '101023', '0', '2020-07-13 06:38:11'), ('480138022474756096', '6', '1', '101099', '0', '2020-07-13 06:38:11'), ('480138023787573248', '6', '1', '101028', '0', '2020-07-13 06:38:11'), ('480138024014065664', '6', '1', '101041', '0', '2020-07-13 06:38:11'), ('480138024169254912', '6', '1', '101080', '0', '2020-07-13 06:38:11'), ('480138024303472640', '6', '1', '101016', '0', '2020-07-13 06:38:11'), ('480138024601268224', '6', '1', '101081', '0', '2020-07-13 06:38:11'), ('480138025226219520', '6', '1', '101015', '0', '2020-07-13 06:38:11'), ('480138025670815744', '6', '1', '101078', '0', '2020-07-13 06:38:11'), ('480138026035720192', '6', '1', '101086', '0', '2020-07-13 06:38:12'), ('480138026350292992', '6', '1', '101082', '0', '2020-07-13 06:38:12'), ('480138026576785408', '6', '1', '101087', '0', '2020-07-13 06:38:12'), ('480138027168182272', '6', '1', '101088', '0', '2020-07-13 06:38:12'), ('480138027407257600', '6', '1', '101089', '0', '2020-07-13 06:38:12'), ('480138027545669632', '6', '1', '101090', '0', '2020-07-13 06:38:12'), ('480138027730219008', '6', '1', '101091', '0', '2020-07-13 06:38:12'), ('480138027818299392', '6', '1', '101095', '0', '2020-07-13 06:38:12'), ('480138027931545600', '6', '1', '101052', '0', '2020-07-13 06:38:12'), ('480138028065763328', '6', '1', '101011', '0', '2020-07-13 06:38:12');
COMMIT;

-- ----------------------------
-- Table structure for `random_code`
-- ----------------------------
DROP TABLE IF EXISTS `random_code`;
CREATE TABLE `random_code` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
UNIQUE INDEX `idx_code` (`code`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of random_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`user_name`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名' ,
`password`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码' ,
`phone`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号' ,
`email`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱' ,
`is_active`  tinyint(11) NULL DEFAULT 1 COMMENT '是否有效(1=是；0=否)' ,
`create_time`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`update_time`  timestamp NULL DEFAULT NULL COMMENT '更新时间' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `idx_user_name` (`user_name`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='用户信息表'
AUTO_INCREMENT=11

;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('10', 'debug', '80bab46abb7b1c4013f9971b8bec3868', '15627280601', '909083393@qq.com', '1', null, null);
COMMIT;

-- ----------------------------
-- Auto increment value for `item`
-- ----------------------------
ALTER TABLE `item` AUTO_INCREMENT=9;

-- ----------------------------
-- Auto increment value for `item_kill`
-- ----------------------------
ALTER TABLE `item_kill` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for `random_code`
-- ----------------------------
ALTER TABLE `random_code` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `user`
-- ----------------------------
ALTER TABLE `user` AUTO_INCREMENT=11;

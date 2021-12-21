/*
 Navicat MySQL Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : image_laboratory

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 21/12/2021 22:53:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('1', '电脑');
INSERT INTO `device` VALUES ('2', '手机');

-- ----------------------------
-- Table structure for directory
-- ----------------------------
DROP TABLE IF EXISTS `directory`;
CREATE TABLE `directory`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of directory
-- ----------------------------
INSERT INTO `directory` VALUES ('1462761031404691456', '1102647596@qq.com', NULL, '1462760956070797312');
INSERT INTO `directory` VALUES ('1463117229735870464', '去雾', '1465497633994366976', '1462760956070797312');
INSERT INTO `directory` VALUES ('1463120991217045504', '对比度', '1463122391561007104', '1462760956070797312');
INSERT INTO `directory` VALUES ('1463122391561007104', 'free', '1462761031404691456', '1462760956070797312');
INSERT INTO `directory` VALUES ('1463122836903809024', 'nst', '1462761031404691456', '1462760956070797312');
INSERT INTO `directory` VALUES ('1465497633994366976', '白色', '1463122836903809024', '1462760956070797312');
INSERT INTO `directory` VALUES ('1469942028068052992', 'mm', '1463117229735870464', '1462760956070797312');
INSERT INTO `directory` VALUES ('1472876365641953280', 'guess', '1462761031404691456', '1462760956070797312');
INSERT INTO `directory` VALUES ('1472876493299789824', 'hello', '1472876365641953280', '1462760956070797312');
INSERT INTO `directory` VALUES ('1473245548728823808', '123456789@qq.com', NULL, '1473245488729305088');
INSERT INTO `directory` VALUES ('1473258988935675904', '长城', '1462761031404691456', '1462760956070797312');

-- ----------------------------
-- Table structure for image_label
-- ----------------------------
DROP TABLE IF EXISTS `image_label`;
CREATE TABLE `image_label`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `label_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for img
-- ----------------------------
DROP TABLE IF EXISTS `img`;
CREATE TABLE `img`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` double NULL DEFAULT NULL,
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dir_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_public` tinyint(2) NULL DEFAULT NULL COMMENT '是否公开',
  `insert_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_img_dir`(`dir_id`) USING BTREE,
  INDEX `fk_img_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_img_dir` FOREIGN KEY (`dir_id`) REFERENCES `directory` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_img_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of img
-- ----------------------------
INSERT INTO `img` VALUES ('1463121062042062848', '对比度.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data/1102647596@qq.com/对比度//对比度.jpg', '1463120991217045504', 0, '2021-12-01 09:33:12');
INSERT INTO `img` VALUES ('1465856696368308224', '5.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data/1102647596@qq.com/对比度/5.jpg', '1463120991217045504', 0, '2021-12-01 09:34:05');
INSERT INTO `img` VALUES ('1465857424893747200', '736b00.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data/1102647596@qq.com/guess/hello/736b00.jpg/', '1472876493299789824', 0, '2021-12-01 09:36:59');
INSERT INTO `img` VALUES ('1469941995822243840', 'forGuratuate.md', NULL, 'other', '1462760956070797312', 'D:\\opt\\server\\data\\1102647596@qq.com\\forGuratuate.md', '1462761031404691456', 0, '2021-12-12 16:07:37');
INSERT INTO `img` VALUES ('1472531054004355072', 'cc.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data/1102647596@qq.com/guess/cc.jpg/', '1472876365641953280', 0, '2021-12-19 19:35:36');
INSERT INTO `img` VALUES ('1472531259751743488', '字幕.ass', NULL, 'other', '1462760956070797312', '/opt/server/data/1102647596@qq.com//字幕.ass', '1462761031404691456', 0, '2021-12-19 19:36:25');
INSERT INTO `img` VALUES ('1472535502239940608', 'free.jpg', NULL, 'image', '1462760956070797312', 'D:\\opt\\server\\data\\1102647596@qq.com\\free\\白色\\free.jpg', '1465497633994366976', 0, '2021-12-19 19:53:17');
INSERT INTO `img` VALUES ('1472855415957549056', 'a.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data/1102647596@qq.com/a.jpg', '1462761031404691456', 0, '2021-12-20 17:04:30');
INSERT INTO `img` VALUES ('1472875524415594496', 'face.jpg', NULL, 'image', '1462760956070797312', 'D:\\opt\\server\\data\\1102647596@qq.com\\free\\白色\\去雾\\mm\\face.jpg', '1469942028068052992', 0, '2021-12-20 18:24:24');
INSERT INTO `img` VALUES ('1472929034570153984', 'fa1d89.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data//1102647596@qq.com/fa1d89.jpg', '1462761031404691456', 1, '2021-12-20 21:57:02');
INSERT INTO `img` VALUES ('1473125290261159936', '80f8ba.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data//1102647596@qq.com/80f8ba.jpg', '1462761031404691456', 0, '2021-12-21 10:56:53');
INSERT INTO `img` VALUES ('1473125957889515520', '9d5a08.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data//1102647596@qq.com/9d5a08.jpg', '1462761031404691456', 1, '2021-12-21 10:59:32');
INSERT INTO `img` VALUES ('1473128316048834560', '5aaa98.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data//1102647596@qq.com/5aaa98.jpg', '1462761031404691456', 0, '2021-12-21 11:08:55');
INSERT INTO `img` VALUES ('1473129848093896704', '62605a.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data/1102647596@qq.com/free/62605a.jpg', '1463122391561007104', 0, '2021-12-21 11:15:00');
INSERT INTO `img` VALUES ('1473246312742268928', '657914.jpg', NULL, 'image', '1473245488729305088', '/opt/server/data//123456789@qq.com/657914.jpg', '1473245548728823808', 1, '2021-12-21 18:57:47');
INSERT INTO `img` VALUES ('1473251444368412672', 'b7ae50.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data//1102647596@qq.com/b7ae50.jpg', '1462761031404691456', 0, '2021-12-21 19:18:11');
INSERT INTO `img` VALUES ('1473251444368412673', '8124d8.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data//1102647596@qq.com/8124d8.jpg', '1462761031404691456', 0, '2021-12-21 19:18:11');
INSERT INTO `img` VALUES ('1473251447207956480', 'a7c347.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data//1102647596@qq.com/a7c347.jpg', '1462761031404691456', 0, '2021-12-21 19:18:11');
INSERT INTO `img` VALUES ('1473252097815810048', '5a6efa.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data//1102647596@qq.com/5a6efa.jpg', '1462761031404691456', 0, '2021-12-21 19:20:46');
INSERT INTO `img` VALUES ('1473252403941281792', '可乐.png', NULL, 'image', '1462760956070797312', '/opt/server/data/1102647596@qq.com//可乐.png', '1462761031404691456', 0, '2021-12-21 19:21:59');
INSERT INTO `img` VALUES ('1473258885487362048', '2.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data/1102647596@qq.com/长城//2.jpg', '1473258988935675904', 1, '2021-12-21 19:47:45');
INSERT INTO `img` VALUES ('1473260187361574912', '下载.jpg', NULL, 'image', '1462760956070797312', 'D:\\opt\\server\\data\\1102647596@qq.com\\下载.jpg', '1462761031404691456', 0, '2021-12-21 19:52:55');
INSERT INTO `img` VALUES ('1473261558290808832', '5(1).jpg', NULL, 'image', '1462760956070797312', '/opt/server/data/1102647596@qq.com//5(1).jpg', '1462761031404691456', 1, '2021-12-21 19:58:22');
INSERT INTO `img` VALUES ('1473261968997056512', '5.jpg', NULL, 'image', '1462760956070797312', 'D:\\opt\\server\\data\\1102647596@qq.com\\5.jpg', '1462761031404691456', 0, '2021-12-21 20:00:00');
INSERT INTO `img` VALUES ('1473304445774741504', '191d5e.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data//1102647596@qq.com/191d5e.jpg', '1462761031404691456', 0, '2021-12-21 22:48:47');

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of label
-- ----------------------------
INSERT INTO `label` VALUES ('1', '蔬菜');
INSERT INTO `label` VALUES ('1448478223530201088', '网球');
INSERT INTO `label` VALUES ('1448581182230409216', '美女');
INSERT INTO `label` VALUES ('1448595175946891264', '甜椒');
INSERT INTO `label` VALUES ('1450299699002699776', '树');
INSERT INTO `label` VALUES ('1452266635739553792', '纸杯');
INSERT INTO `label` VALUES ('1452534286407753728', '浮尘天气');
INSERT INTO `label` VALUES ('1452544139473805312', '草原');
INSERT INTO `label` VALUES ('1452814335560183808', '表格');
INSERT INTO `label` VALUES ('1452815680488927232', '年轮');
INSERT INTO `label` VALUES ('1452835816792977408', '建筑');
INSERT INTO `label` VALUES ('1463122972962836480', '行政区划图');
INSERT INTO `label` VALUES ('1465855129481232384', '洞穴溶洞');
INSERT INTO `label` VALUES ('2', '水果');
INSERT INTO `label` VALUES ('3', '猫');
INSERT INTO `label` VALUES ('4', '狗');

-- ----------------------------
-- Table structure for login_record
-- ----------------------------
DROP TABLE IF EXISTS `login_record`;
CREATE TABLE `login_record`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `login_way_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `system_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `login_date` datetime(0) NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_record
-- ----------------------------
INSERT INTO `login_record` VALUES ('1', '1', '1', '1', '2021-10-27 14:57:08', '1');
INSERT INTO `login_record` VALUES ('1462761031828316160', '1', '1', '1', '2021-11-22 20:33:01', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1463117140246200320', '1', '1', '1', '2021-11-23 20:08:04', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1465494155658096640', '1', '1', '1', '2021-11-30 09:33:29', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1465572432783446016', '1', '1', '1', '2021-11-30 14:44:32', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1465590667591385088', '1', '1', '1', '2021-11-30 15:56:59', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1465854924732088320', '1', '1', '1', '2021-12-01 09:27:03', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1465873609358123008', '1', '1', '1', '2021-12-01 10:41:18', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1466386523945005056', '1', '1', '1', '2021-12-02 20:39:26', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1466386565229539328', '1', '1', '1', '2021-12-02 20:39:36', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1468193203343171584', '1', '1', '1', '2021-12-07 20:18:32', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1468916128601866240', '1', '1', '1', '2021-12-09 20:11:11', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1469842633112760320', '1', '1', '1', '2021-12-12 09:32:47', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1469843787188076544', '1', '1', '1', '2021-12-12 09:37:22', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1469941973747621888', '1', '1', '1', '2021-12-12 16:07:31', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472460455278116864', '1', '1', '1', '2021-12-19 14:55:04', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472470106883325952', '1', '1', '1', '2021-12-19 15:33:25', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472471406815580160', '1', '1', '1', '2021-12-19 15:38:35', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472472212444909568', '1', '1', '1', '2021-12-19 15:41:47', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472473342222635008', '1', '1', '1', '2021-12-19 15:46:17', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472475171769643008', '1', '1', '1', '2021-12-19 15:53:33', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472480048776310784', '1', '1', '1', '2021-12-19 16:12:56', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472480117952966656', '1', '1', '1', '2021-12-19 16:13:12', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472480153285783552', '1', '1', '1', '2021-12-19 16:13:21', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472489392771264512', '1', '1', '1', '2021-12-19 16:50:03', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472528056880553984', '1', '1', '1', '2021-12-19 19:23:42', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472530996492058624', '1', '1', '1', '2021-12-19 19:35:23', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472535195875393536', '1', '1', '1', '2021-12-19 19:52:04', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472540050882949120', '1', '1', '1', '2021-12-19 20:11:21', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472540254801620992', '1', '1', '1', '2021-12-19 20:12:10', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472542246055809024', '1', '1', '1', '2021-12-19 20:20:05', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472566299982401536', '1', '1', '1', '2021-12-19 21:55:40', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472653503396171776', '1', '1', '1', '2021-12-20 03:42:10', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472653898252046336', '1', '1', '1', '2021-12-20 03:43:45', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472654333197221888', '1', '1', '1', '2021-12-20 03:45:28', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472654821955301376', '1', '1', '1', '2021-12-20 03:47:25', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472655485838143488', '1', '1', '1', '2021-12-20 03:50:03', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472655978681462784', '1', '1', '1', '2021-12-20 03:52:01', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472656416533225472', '1', '1', '1', '2021-12-20 03:53:45', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472657812011704320', '1', '1', '1', '2021-12-20 03:59:18', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472658121350049792', '1', '1', '1', '2021-12-20 04:00:31', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472659061415194624', '1', '1', '1', '2021-12-20 04:04:16', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472848664638074880', '1', '1', '1', '2021-12-20 16:37:41', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472850072531726336', '1', '1', '1', '2021-12-20 16:43:16', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472850332939276288', '1', '1', '1', '2021-12-20 16:44:18', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472854338453454848', '1', '1', '1', '2021-12-20 17:00:13', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472854596264656896', '1', '1', '1', '2021-12-20 17:01:15', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472854799864623104', '1', '1', '1', '2021-12-20 17:02:03', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472856012211355648', '1', '1', '1', '2021-12-20 17:06:52', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472857079619866624', '1', '1', '1', '2021-12-20 17:11:07', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472928931608379392', '1', '1', '1', '2021-12-20 21:56:38', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1472930210271313920', '1', '1', '1', '2021-12-20 22:01:43', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473121082732519424', '1', '1', '1', '2021-12-21 10:40:10', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473121515484069888', '1', '1', '1', '2021-12-21 10:41:53', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473122187944218624', '1', '1', '1', '2021-12-21 10:44:34', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473124585186099200', '1', '1', '1', '2021-12-21 10:54:05', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473124985335259136', '1', '1', '1', '2021-12-21 10:55:40', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473125698752831488', '1', '1', '1', '2021-12-21 10:58:31', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473126512703676416', '1', '1', '1', '2021-12-21 11:01:45', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473127282425573376', '1', '1', '1', '2021-12-21 11:04:48', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473127777974145024', '1', '1', '1', '2021-12-21 11:06:46', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473128104429420544', '1', '1', '1', '2021-12-21 11:08:04', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473128637852643328', '1', '1', '1', '2021-12-21 11:10:11', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473131635752308736', '1', '1', '1', '2021-12-21 11:22:06', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473131969124036608', '1', '1', '1', '2021-12-21 11:23:26', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473204189116559360', '1', '1', '1', '2021-12-21 16:10:24', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473204648522895360', '1', '1', '1', '2021-12-21 16:12:14', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473206463763091456', '1', '1', '1', '2021-12-21 16:19:26', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473207434387972096', '1', '1', '1', '2021-12-21 16:23:18', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473207771819749376', '1', '1', '1', '2021-12-21 16:24:38', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473214571587649536', '1', '1', '1', '2021-12-21 16:51:40', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473215985206534144', '1', '1', '1', '2021-12-21 16:57:17', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473216489844240384', '1', '1', '1', '2021-12-21 16:59:17', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473217692573454336', '1', '1', '1', '2021-12-21 17:04:04', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473218237514227712', '1', '1', '1', '2021-12-21 17:06:14', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473231007978622976', '1', '1', '1', '2021-12-21 17:56:58', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473231539287920640', '1', '1', '1', '2021-12-21 17:59:05', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473245292754644992', '1', '1', '1', '2021-12-21 18:53:44', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473245549659959296', '1', '1', '1', '2021-12-21 18:54:45', '1473245488729305088');
INSERT INTO `login_record` VALUES ('1473246419009155072', '1', '1', '1', '2021-12-21 18:58:13', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473258809566265344', '1', '1', '1', '2021-12-21 19:47:27', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473263097470357504', '1', '1', '1', '2021-12-21 20:04:29', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473263699055149056', '1', '1', '1', '2021-12-21 20:06:52', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473287404149800960', '1', '1', '1', '2021-12-21 21:41:04', '1462760956070797312');
INSERT INTO `login_record` VALUES ('1473297628948615168', '1', '1', '1', '2021-12-21 22:21:42', '1462760956070797312');

-- ----------------------------
-- Table structure for login_way
-- ----------------------------
DROP TABLE IF EXISTS `login_way`;
CREATE TABLE `login_way`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_way
-- ----------------------------
INSERT INTO `login_way` VALUES ('1', '用户名登陆');

-- ----------------------------
-- Table structure for public
-- ----------------------------
DROP TABLE IF EXISTS `public`;
CREATE TABLE `public`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` double NULL DEFAULT NULL,
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dir_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_public` tinyint(2) NULL DEFAULT NULL,
  `insert_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of public
-- ----------------------------
INSERT INTO `public` VALUES ('1473243570229501952', '9d5a08.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data//public/9d5a08.jpg', '', 1, '2021-12-21 18:46:53');
INSERT INTO `public` VALUES ('1473246326650580992', '657914.jpg', NULL, 'image', '1473245488729305088', '/opt/server/data//public/657914.jpg', '', 1, '2021-12-21 18:57:51');
INSERT INTO `public` VALUES ('1473259049874718720', 'f889b2.jpg', NULL, 'image', '1462760956070797312', '/opt/server/data//public/f889b2.jpg', '', 1, '2021-12-21 19:48:24');
INSERT INTO `public` VALUES ('1473261477097472000', '1.mp4', NULL, 'video', '1462760956070797312', '/opt/server/data//public/1.mp4', '', 1, '2021-12-21 19:58:03');
INSERT INTO `public` VALUES ('1473294203464802304', '可乐.png', NULL, 'image', '1462760956070797312', '/opt/server/data//public/可乐.png', '', 1, '2021-12-21 22:08:05');

-- ----------------------------
-- Table structure for public_relative_img
-- ----------------------------
DROP TABLE IF EXISTS `public_relative_img`;
CREATE TABLE `public_relative_img`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `public_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of public_relative_img
-- ----------------------------
INSERT INTO `public_relative_img` VALUES ('1473297656891068416', '1473297656802988032', '1473261558290808832');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `label_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `count` int(11) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_record_label`(`label_id`) USING BTREE,
  CONSTRAINT `fk_record_label` FOREIGN KEY (`label_id`) REFERENCES `label` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES ('1463117363265732608', '1452534286407753728', 1, '2021-11-23 20:08:57', '1462760956070797312');
INSERT INTO `record` VALUES ('1463121066534162432', '1452544139473805312', 1, '2021-11-23 20:23:40', '1462760956070797312');
INSERT INTO `record` VALUES ('1463122973239660544', '1463122972962836480', 3, '2021-12-21 19:52:57', '1462760956070797312');
INSERT INTO `record` VALUES ('1465855129779027968', '1465855129481232384', 1, '2021-12-01 09:27:52', '1462760956070797312');
INSERT INTO `record` VALUES ('1465856750697127936', '1452815680488927232', 3, '2021-12-21 20:00:01', '1462760956070797312');
INSERT INTO `record` VALUES ('1466386653481889792', '1452835816792977408', 1, '2021-12-02 20:39:57', '1462760956070797312');
INSERT INTO `record` VALUES ('1472531108706467840', '1448478223530201088', 1, '2021-12-19 19:35:49', '1462760956070797312');
INSERT INTO `record` VALUES ('1472875577700032512', '1448581182230409216', 1, '2021-12-20 18:24:37', '1462760956070797312');
INSERT INTO `record` VALUES ('3', '1', 3, '2021-10-10 16:25:50', '111');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '用户');
INSERT INTO `role` VALUES (2, '管理员');

-- ----------------------------
-- Table structure for system_type
-- ----------------------------
DROP TABLE IF EXISTS `system_type`;
CREATE TABLE `system_type`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_type
-- ----------------------------
INSERT INTO `system_type` VALUES ('1', 'Win10');
INSERT INTO `system_type` VALUES ('2', 'Mac');
INSERT INTO `system_type` VALUES ('3', 'Linux');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `verify_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sms_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `verify_code_time` datetime(0) NULL DEFAULT NULL,
  `sms_code_time` datetime(0) NULL DEFAULT NULL,
  `role_id` int(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `idx_user_account`(`account`) USING BTREE,
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1462760956070797312', 'teqgin', 'bb8fa369ad711bf5d262c06845de2375', '1102647596@qq.com', '012353', NULL, '15960870736', '2021-12-19 15:37:53', NULL, NULL);
INSERT INTO `user` VALUES ('1473245488729305088', 'lisi', 'bb8fa369ad711bf5d262c06845de2375', '123456789@qq.com', NULL, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;

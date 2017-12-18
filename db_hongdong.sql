/*
Navicat MySQL Data Transfer

Source Server         : Game
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : db_hongdong

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2017-06-07 15:34:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(100) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `age` int(10) DEFAULT 0,
  `sex` int(10) DEFAULT 0,
  `address` varchar(30) DEFAULT NULL,
  `gold` int(10) DEFAULT 0,
  `crystal` int(10) DEFAULT 0,
  `vip` int(11) DEFAULT 0,
  `level` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('100', '0', '天王盖地虎', '1234', '/userIcon/userIcon1417775066608.png', '13480122949', '深圳', '23', '0', '上海浦东新区', '1245', '123', '0', '14');
INSERT INTO `user` VALUES ('101', '1', '宝塔镇河妖', '1234', '/userIcon/loading.png', '13480122949', null, '16', '1', null, '9245', '1233', '2', '9');
INSERT INTO `user` VALUES ('102', '2', '天生我材', '1234', '/userIcon/userIcon128.png', '13480122949', null, '45', '1', null, '120', '0', '0', '0');
INSERT INTO `user` VALUES ('103', '3', '必有用', '1234', null, '13480122949', null, '8', '0', null, '0', '0', '0', '0');
INSERT INTO `user` VALUES ('104', '4', '黄海波', '1234', null, '13480122949', null, '108', '1', null, '0', '0', '0', '0');

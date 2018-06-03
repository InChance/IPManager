/*
Navicat MySQL Data Transfer

Source Server         : tencent-cloud
Source Server Version : 80011
Source Host           : localhost2:3306
Source Database       : IPManager

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-06-03 19:56:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for IP
-- ----------------------------
DROP TABLE IF EXISTS `IP`;
CREATE TABLE `IP` (
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `collectTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Mask
-- ----------------------------
DROP TABLE IF EXISTS `Mask`;
CREATE TABLE `Mask` (
  `netAddress` varchar(255) NOT NULL COMMENT '网段',
  `maskAddress` varchar(255) DEFAULT NULL COMMENT '子网掩码',
  `recordTime` datetime DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`netAddress`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

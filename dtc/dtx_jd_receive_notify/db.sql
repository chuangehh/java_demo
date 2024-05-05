/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.7.28-log : Database - jd_receive
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jd_receive` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `jd_receive`;

/*Table structure for table `account_info` */

DROP TABLE IF EXISTS `account_info`;

CREATE TABLE `account_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_name` varchar(100) DEFAULT NULL COMMENT '户主姓名',
  `account_no` varchar(100) DEFAULT NULL COMMENT '银行卡号',
  `account_password` varchar(100) DEFAULT NULL COMMENT '帐户密码',
  `account_balance` double NOT NULL COMMENT '账户余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='账户信息';

/*Data for the table `account_info` */

insert  into `account_info`(`id`,`account_name`,`account_no`,`account_password`,`account_balance`) values (1,'z3','1','123456',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

CREATE TABLE `de_duplication` (
  `tx_no` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `create_time` DATETIME DEFAULT NULL,
  PRIMARY KEY (`tx_no`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC
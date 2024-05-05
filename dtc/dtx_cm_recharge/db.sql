/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.7.28-log : Database - cm_recharge
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cm_recharge` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `cm_recharge`;

/*Table structure for table `account_recharge` */

DROP TABLE IF EXISTS `account_recharge`;

CREATE TABLE `account_recharge` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `account_no` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `recharge_amount` double DEFAULT NULL COMMENT '充值金额',
  `result` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '充值结果:success，fail',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

/*Data for the table `account_recharge` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


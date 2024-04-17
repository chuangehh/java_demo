/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.7.28-log : Database - bank2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bank2` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `bank2`;

/*Table structure for table `account_info` */

DROP TABLE IF EXISTS `account_info`;

CREATE TABLE `account_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学号',
  `account_name` varchar(100) DEFAULT NULL COMMENT '户主姓名',
  `account_no` varchar(100) DEFAULT NULL COMMENT '银行卡号',
  `account_password` varchar(100) DEFAULT NULL COMMENT '帐户密码',
  `account_balance` double NOT NULL COMMENT '账户余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='账户信息';

/*Data for the table `account_info` */

insert  into `account_info`(`id`,`account_name`,`account_no`,`account_password`,`account_balance`) values (1,'li4','2','123456',50);

/*Table structure for table `de_duplication` */

DROP TABLE IF EXISTS `de_duplication`;

CREATE TABLE `de_duplication` (
  `tx_no` varchar(64) COLLATE utf8_bin NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`tx_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

/*Data for the table `de_duplication` */

insert  into `de_duplication`(`tx_no`,`create_time`) values ('277b93ac-923c-45f1-93d6-0b0794e09398','2023-03-19 21:14:39'),('58c97467-bb30-46c3-a930-4a45848fc5c8','2023-03-19 20:01:52'),('6dc575dc-6147-4267-9549-e2c64fab0c55','2023-03-19 21:06:17'),('89b49d49-5f3c-4676-af87-bcc3c7d4e685','2023-03-19 21:08:09'),('9e5224e0-c1f7-42be-834c-94fb1c7de3f0','2023-03-19 19:57:37'),('ae26d73c-6d52-4d88-bfa5-31a1b3c5c731','2023-03-19 21:02:24');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

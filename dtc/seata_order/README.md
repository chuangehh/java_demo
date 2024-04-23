# 

### 1、安装nacos-server-2.1.1
* 修改nacos-server-2.1.1\nacos\bin\startup.cmd set MODE="standalone"

### 2、nacos配置中心新增配置
* http://127.0.0.1:8848/nacos/index.html
* 代表使用本地Seata集群 cluster = "default"
* 参考 seata-server-1.4.2\conf\registry.conf
```
Data ID: service.vgroupMapping.my_test_tx_group
Group: SEATA_GROUP
配置内容：default
```

### 3、装Seata1.4.2
* 1、https://github.com/apache/incubator-seata/releases/download/v1.4.2/seata-server-1.4.2.tar.gz
* 2、create database seata;
* 3、建seata表 https://github.com/apache/incubator-seata/blob/v1.4.2/script/server/db/mysql.sql
* 4、修改seata-server-1.4.2\conf\file.conf  
    ```
    mode = "db"
  
    db {
      url = "jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&characterEncoding=utf8&useSSL=false&&serverTimezone=UTC"
      user = "root"
      password = "root"
    }
    ```
* 5、修改seata-server-1.4.2\conf\registry.conf  type = "nacos" 
* 6、下载jar包放lib目录下 https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.18/mysql-connector-java-8.0.18.jar
* 7、启动bin\seata-server.bat


### 4、建业务库表
```sql
CREATE DATABASE seata_order;
CREATE DATABASE seata_storage;

-- https://github.com/apache/incubator-seata/blob/v1.4.2/script/client/at/db/mysql.sql
CREATE TABLE IF NOT EXISTS orderdb.`undo_log`(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';

CREATE TABLE IF NOT EXISTS storagedb.`undo_log`(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';
```

### 5、启动测试
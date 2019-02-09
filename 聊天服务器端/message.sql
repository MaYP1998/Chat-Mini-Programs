# Host: localhost:port  (Version 5.1.47-community)
# Date: 2019-02-09 15:25:00
# Generator: MySQL-Front 6.1  (Build 1.26)


#
# Structure for table "message"
#

CREATE TABLE `message` (
  `mid` int(10) NOT NULL AUTO_INCREMENT,
  `userid` char(10) NOT NULL DEFAULT '',
  `touserid` char(10) NOT NULL DEFAULT '',
  `messagetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `messagecontent` varchar(1000) DEFAULT NULL,
  `isview` int(1) DEFAULT '0',
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=426 DEFAULT CHARSET=utf8;

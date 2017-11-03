-- This file is used to create the database for this application.

DROP DATABASE IF EXISTS `wallet_hub`;
CREATE DATABASE `wallet_hub`;
USE `wallet_hub`;

--
-- Table structure for table `blocked_ips`
--

DROP TABLE IF EXISTS `blocked_ips`;
CREATE TABLE `blocked_ips` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `message` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=latin1;


--
-- Table structure for table `http_logs`
--

DROP TABLE IF EXISTS `http_logs`;
CREATE TABLE `http_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `http_method` varchar(45) NOT NULL,
  `http_status_code` int(11) NOT NULL,
  `user_agent` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ip_and_created_on` (`created_on`,`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=131071 DEFAULT CHARSET=latin1;
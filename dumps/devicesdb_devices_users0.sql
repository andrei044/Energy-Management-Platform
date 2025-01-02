CREATE DATABASE  IF NOT EXISTS `devicesdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `devicesdb`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: devicesdb
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `devices_users`
--

DROP TABLE IF EXISTS `devices_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `devices_users` (
  `device_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`device_id`,`user_id`),
  KEY `FKn1q5go1q2ph7bfdhbf691pf0a` (`user_id`),
  CONSTRAINT `FKlxr21krnnmw3xn24wrx3hq9fi` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`),
  CONSTRAINT `FKn1q5go1q2ph7bfdhbf691pf0a` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices_users`
--

LOCK TABLES `devices_users` WRITE;
/*!40000 ALTER TABLE `devices_users` DISABLE KEYS */;
INSERT INTO `devices_users` VALUES (_binary '˛¨hv^M2å\—û‚Çõ\Z',_binary '\ƒ#’ÆwI—ß	o£‹±\ÁN'),(_binary '%	u©\Ì†Oè\ÍJMˇ*å_',_binary '\ƒ#’ÆwI—ß	o£‹±\ÁN'),(_binary '«îìÛŒ™E\ÏêiAI94÷í',_binary '\ƒ#’ÆwI—ß	o£‹±\ÁN'),(_binary '\œ˝Äì/ÛCò˜£^b\Î!.',_binary '\ƒ#’ÆwI—ß	o£‹±\ÁN'),(_binary 'JìU<\◊}Bí©øaø∞ó,∏',_binary '\Z\√;2JÛµ m\‘hGò'),(_binary 'ô†sáeA0ÅE\Ãˆºæ&',_binary 'πΩ&\ﬂ%Dv¥¢ïYJT'),(_binary '\0Åéu\ÊM◊ÑGmb∑≤',_binary '˛\ÿ¢©kBg∑\È®^[V6');
/*!40000 ALTER TABLE `devices_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-25 21:55:43

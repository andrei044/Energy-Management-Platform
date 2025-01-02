-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: monitoringdb
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
-- Table structure for table `hourly_measurement`
--

DROP TABLE IF EXISTS `hourly_measurement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hourly_measurement` (
  `energy_consumed` double DEFAULT NULL,
  `id` bigint NOT NULL,
  `timestamp` bigint DEFAULT NULL,
  `device_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfp7j22c71wqwqce2b1182rrcx` (`device_id`),
  CONSTRAINT `FKfp7j22c71wqwqce2b1182rrcx` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hourly_measurement`
--

LOCK TABLES `hourly_measurement` WRITE;
/*!40000 ALTER TABLE `hourly_measurement` DISABLE KEYS */;
INSERT INTO `hourly_measurement` VALUES (0.8365558333333334,152,1732050000000,_binary 'ô†sáeA0ÅE\Ãˆºæ&'),(23.747075145288935,153,1732053600000,_binary 'ô†sáeA0ÅE\Ãˆºæ&'),(44.94603822913031,154,1732057200000,_binary 'ô†sáeA0ÅE\Ãˆºæ&'),(51.61500472912642,155,1732060800000,_binary 'ô†sáeA0ÅE\Ãˆºæ&'),(74.43659513221417,156,1732064400000,_binary 'ô†sáeA0ÅE\Ãˆºæ&'),(68.59032365488217,157,1732068000000,_binary 'ô†sáeA0ÅE\Ãˆºæ&'),(61.11004581980899,158,1732071600000,_binary 'ô†sáeA0ÅE\Ãˆºæ&'),(84.21153922254852,159,1732075200000,_binary 'ô†sáeA0ÅE\Ãˆºæ&'),(133.52297302319167,160,1732078800000,_binary 'ô†sáeA0ÅE\Ãˆºæ&'),(138.40124404640662,161,1732082400000,_binary 'ô†sáeA0ÅE\Ãˆºæ&'),(19.861786664068916,162,1732086000000,_binary 'ô†sáeA0ÅE\Ãˆºæ&');
/*!40000 ALTER TABLE `hourly_measurement` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-25 21:55:00

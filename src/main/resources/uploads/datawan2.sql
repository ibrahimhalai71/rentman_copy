-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: datawan2
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `archive_job_required_skills`
--

DROP TABLE IF EXISTS `archive_job_required_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_job_required_skills` (
  `archive_job_id` bigint NOT NULL,
  `skill_id` bigint NOT NULL,
  PRIMARY KEY (`archive_job_id`,`skill_id`),
  KEY `FKc1bpnpoks64fq56h4wi2ibeyv` (`skill_id`),
  CONSTRAINT `FKc1bpnpoks64fq56h4wi2ibeyv` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`),
  CONSTRAINT `FKplied2dpdpphm3xn1fykq9ci1` FOREIGN KEY (`archive_job_id`) REFERENCES `archived_jobs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archive_job_required_skills`
--

LOCK TABLES `archive_job_required_skills` WRITE;
/*!40000 ALTER TABLE `archive_job_required_skills` DISABLE KEYS */;
INSERT INTO `archive_job_required_skills` VALUES (86,21),(89,23),(90,23),(91,23),(92,23);
/*!40000 ALTER TABLE `archive_job_required_skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `archive_job_service_provider_ids`
--

DROP TABLE IF EXISTS `archive_job_service_provider_ids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_job_service_provider_ids` (
  `archive_job_id` bigint NOT NULL,
  `service_provider_id` bigint NOT NULL,
  KEY `FK9tea9j1u33pg4ht04uat5m3f5` (`service_provider_id`),
  KEY `FKn2kb4rpd2tlmsiyn9muou0a4t` (`archive_job_id`),
  CONSTRAINT `FK9tea9j1u33pg4ht04uat5m3f5` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKn2kb4rpd2tlmsiyn9muou0a4t` FOREIGN KEY (`archive_job_id`) REFERENCES `archived_jobs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archive_job_service_provider_ids`
--

LOCK TABLES `archive_job_service_provider_ids` WRITE;
/*!40000 ALTER TABLE `archive_job_service_provider_ids` DISABLE KEYS */;
INSERT INTO `archive_job_service_provider_ids` VALUES (89,115),(90,59),(91,115),(86,107),(92,115);
/*!40000 ALTER TABLE `archive_job_service_provider_ids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `archive_potential_job_offers`
--

DROP TABLE IF EXISTS `archive_potential_job_offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_potential_job_offers` (
  `accepted` int DEFAULT NULL,
  `service_provider_id` bigint NOT NULL,
  `job_id` bigint NOT NULL,
  PRIMARY KEY (`job_id`,`service_provider_id`),
  KEY `FK313ktrror4l1ouojvks9x09wj` (`service_provider_id`),
  CONSTRAINT `FK313ktrror4l1ouojvks9x09wj` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKlmlk0dosjcox24s8iu2fs42e9` FOREIGN KEY (`job_id`) REFERENCES `archived_jobs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archive_potential_job_offers`
--

LOCK TABLES `archive_potential_job_offers` WRITE;
/*!40000 ALTER TABLE `archive_potential_job_offers` DISABLE KEYS */;
/*!40000 ALTER TABLE `archive_potential_job_offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `archived_jobs`
--

DROP TABLE IF EXISTS `archived_jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `archived_jobs` (
  `id` bigint NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `creation_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `job_other_skills` varchar(255) DEFAULT NULL,
  `moderator_approval` int DEFAULT NULL,
  `number_of_people` int DEFAULT NULL,
  `project_lead_name` varchar(255) DEFAULT NULL,
  `project_lead_number` varchar(255) DEFAULT NULL,
  `reviewed_by_client` bit(1) DEFAULT NULL,
  `reviewed_by_service_provider` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `client_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `moderator_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjm2pgqk3bktuld5gs05fh1iyb` (`client_id`),
  KEY `FK5ofjfn0is7apdjhfph4v8qakr` (`created_by`),
  KEY `FKjhe7825meqcqfi23p0t7hld4j` (`moderator_id`),
  CONSTRAINT `FK5ofjfn0is7apdjhfph4v8qakr` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjhe7825meqcqfi23p0t7hld4j` FOREIGN KEY (`moderator_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjm2pgqk3bktuld5gs05fh1iyb` FOREIGN KEY (`client_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archived_jobs`
--

LOCK TABLES `archived_jobs` WRITE;
/*!40000 ALTER TABLE `archived_jobs` DISABLE KEYS */;
INSERT INTO `archived_jobs` VALUES (86,_binary '','2025-02-28','.','Oss','2025-02-28','13:00:00','',1,1,'Martijn van der Lee','+316489088181',NULL,NULL,'Stagehand','2025-02-28','12:00:00','COMPLETED','Oss',100,36,NULL),(89,_binary '','2025-03-01','test','unknown','2025-03-05','00:17:00','ok',1,1,'lead','+3197010580426',NULL,NULL,'some role','2025-03-05','00:15:00','COMPLETED','test event',99,61,NULL),(90,_binary '','2025-04-23','sxsz','asdad','2025-04-23','23:07:00','',1,1,'lead','+3197010580426',NULL,NULL,'some role','2025-04-23','23:05:00','COMPLETED','some title',99,61,NULL),(91,_binary '','2025-04-29','sxsz','asdad','2025-04-29','00:26:00','',1,1,'lead','+3197010580426',NULL,NULL,'some role','2025-04-29','00:06:00','COMPLETED','some title 3',99,61,NULL),(92,_binary '','2025-05-20','asdsada','asdsa','2025-05-20','22:16:00','',1,1,'lead','+31970105804265',NULL,NULL,'some role','2025-05-20','22:14:00','COMPLETED','test4',99,61,NULL);
/*!40000 ALTER TABLE `archived_jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'client'),(2,'moderator'),(3,'student');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `extra_cost` double DEFAULT NULL,
  `extra_cost_description` varchar(255) DEFAULT NULL,
  `kilometer` double DEFAULT NULL,
  `job_id` bigint DEFAULT NULL,
  `service_provider_id` bigint DEFAULT NULL,
  `aantal` bigint DEFAULT NULL,
  `invoice_date` date DEFAULT NULL,
  `invoice_description` varchar(255) DEFAULT NULL,
  `invoice_type` varchar(255) DEFAULT NULL,
  `leverdatum` date DEFAULT NULL,
  `tarief` double DEFAULT NULL,
  `parent_invoice_id` bigint DEFAULT NULL,
  `invoice_number` bigint DEFAULT NULL,
  `day_or_hour` varchar(255) DEFAULT NULL,
  `dagen` varchar(255) DEFAULT NULL,
  `percentage_number` bigint DEFAULT NULL,
  `percentage_switch` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4idvjnoj3ie9vgwlc71desyww` (`job_id`),
  KEY `FKgx3b301npyqd3d9lutyn58l9m` (`service_provider_id`),
  KEY `FKqhasquq74l7tnk1x3x93hykl7` (`parent_invoice_id`),
  CONSTRAINT `FK4idvjnoj3ie9vgwlc71desyww` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
  CONSTRAINT `FKgx3b301npyqd3d9lutyn58l9m` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKqhasquq74l7tnk1x3x93hykl7` FOREIGN KEY (`parent_invoice_id`) REFERENCES `invoice` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (22,NULL,NULL,NULL,89,NULL,1,'2025-04-08',NULL,'INVOICE',NULL,NULL,NULL,NULL,'Day',NULL,NULL,NULL),(23,NULL,NULL,NULL,89,115,1,'2025-04-08',NULL,'PURCHASE','2025-03-05',2,NULL,NULL,'Day',NULL,NULL,NULL),(24,NULL,NULL,NULL,85,NULL,0,'2025-04-08',NULL,'INVOICE',NULL,NULL,NULL,123,'Day',NULL,NULL,NULL),(31,NULL,NULL,NULL,86,NULL,1,'2025-04-08',NULL,'INVOICE',NULL,NULL,NULL,123,'Day',NULL,NULL,NULL),(42,NULL,NULL,NULL,NULL,NULL,0,'2025-04-23',NULL,'INVOICE',NULL,NULL,NULL,25042322405,NULL,NULL,NULL,NULL),(43,NULL,NULL,NULL,NULL,NULL,1,'2025-04-27',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(44,NULL,NULL,NULL,90,59,1,'2025-04-27',NULL,'PURCHASE','2025-04-23',325,NULL,NULL,'Day',NULL,NULL,NULL),(45,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(46,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(47,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(48,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(49,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(50,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(51,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(52,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(53,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(54,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(55,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(56,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(57,NULL,NULL,NULL,NULL,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(58,NULL,NULL,NULL,90,NULL,1,'2025-04-28',NULL,'INVOICE',NULL,NULL,NULL,25042323050,'Day',NULL,NULL,NULL),(59,123,'123213',12,91,115,1,'2025-04-30','123213','PURCHASE','2025-04-29',2,NULL,NULL,'Day',NULL,NULL,NULL),(60,NULL,NULL,NULL,91,NULL,1,'2025-05-01',NULL,'INVOICE',NULL,NULL,NULL,25042900214,'Day',NULL,NULL,NULL),(62,NULL,NULL,NULL,86,107,1,'2025-05-08',NULL,'PURCHASE','2025-02-28',20,NULL,NULL,'Day',NULL,NULL,NULL),(63,NULL,NULL,NULL,92,NULL,1,'2025-05-20',NULL,'INVOICE',NULL,NULL,NULL,25052022145,'Day',NULL,NULL,NULL),(64,NULL,NULL,NULL,92,115,1,'2025-05-20',NULL,'PURCHASE','2025-05-20',2,NULL,NULL,'Day',NULL,NULL,NULL),(65,123,'123213',12,92,115,1,'2025-05-21','123213','PURCHASE','2025-05-20',2,NULL,NULL,'Day',NULL,NULL,NULL);
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_files`
--

DROP TABLE IF EXISTS `invoice_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_files` (
  `invoice_id` bigint NOT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  KEY `FK90m8p0mdc9ismihmq81vpnos5` (`invoice_id`),
  CONSTRAINT `FK90m8p0mdc9ismihmq81vpnos5` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_files`
--

LOCK TABLES `invoice_files` WRITE;
/*!40000 ALTER TABLE `invoice_files` DISABLE KEYS */;
INSERT INTO `invoice_files` VALUES (65,'50a4cd66-e53e-49c6-b83f-64c33f5cf796_IMG-20250205-WA0007.jpg'),(65,'55072d9a-5de1-44d8-8dc2-faa06991b51b_IMG-20250424-WA0004.jpg'),(65,'70a72ba3-074e-470e-8a1c-10b24cf152b6_Screenshot_20250424-232402.Drive.png');
/*!40000 ALTER TABLE `invoice_files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_lines`
--

DROP TABLE IF EXISTS `invoice_lines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_lines` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `aantal` varchar(255) DEFAULT NULL,
  `omschrijving` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `invoice_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7kk938lt30fhvrgfecsujd10o` (`invoice_id`),
  CONSTRAINT `FK7kk938lt30fhvrgfecsujd10o` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_lines`
--

LOCK TABLES `invoice_lines` WRITE;
/*!40000 ALTER TABLE `invoice_lines` DISABLE KEYS */;
INSERT INTO `invoice_lines` VALUES (15,'2.0','2025-04-23 / Project: 25042323050 / Audio A',350,43),(16,'3.0','2025-04-23 / Project: 25042323050 / Audio A',350,45),(17,'4.0','2025-04-23 / Project: 25042323050 / Audio A',350,46),(18,'5.0','2025-04-23 / Project: 25042323050 / Audio A',350,47),(19,'7.0','2025-04-23 / Project: 25042323050 / Audio A',350,48),(20,'8.0','2025-04-23 / Project: 25042323050 / Audio A',350,49),(21,'9.0','2025-04-23 / Project: 25042323050 / Audio A',350,50),(22,'10.0','2025-04-23 / Project: 25042323050 / Audio A',350,51),(23,'11.0','2025-04-23 / Project: 25042323050 / Audio A',350,52),(24,'12.0','2025-04-23 / Project: 25042323050 / Audio A',350,53),(25,'13.0','2025-04-23 / Project: 25042323050 / Audio A',350,54),(26,'14.0','2025-04-23 / Project: 25042323050 / Audio A',350,55),(27,'15.0','2025-04-23 / Project: 25042323050 / Audio A',350,56),(28,'16.0','2025-04-23 / Project: 25042323050 / Audio A',350,57),(30,'1.0','asdsa',123,NULL),(31,'1.0','asdasds',125,NULL),(32,'2.0','asdsad',123,NULL),(33,'12.0','asdasd',432,NULL),(34,'1.0','adsad',123,NULL),(35,'12.0','asdas',234,NULL),(36,'12.0','eesdfs',13,NULL),(37,'13.0','sdadsa',1231,44),(38,'14.0','asdas',42,44),(39,'0.0',NULL,NULL,44),(45,'1.0','2025-03-05 / Project: null / Audio A',350,22),(46,'1.0','2025-04-23 / Project: 25042323050 / Audio A',350,58),(47,'1.0','2025-04-29 / Project: 25042900214 / Audio A',350,60),(49,'4.0','343fes',34,60),(50,'1.0','2025-05-20 / Project: 25052022145 / Audio A',350,63);
/*!40000 ALTER TABLE `invoice_lines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `creation_date` date DEFAULT NULL,
  `description` longtext,
  `destination` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `payment` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `client_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `moderator_id` bigint DEFAULT NULL,
  `service_provider_id` bigint DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `moderator_approval` int DEFAULT NULL,
  `number_of_people` int DEFAULT NULL,
  `project_lead_name` varchar(255) DEFAULT NULL,
  `project_lead_number` varchar(255) DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `job_other_skills` varchar(255) NOT NULL,
  `reviewed_by_client` bit(1) DEFAULT NULL,
  `reviewed_by_service_provider` varchar(255) DEFAULT NULL,
  `invoice_number` bigint DEFAULT NULL,
  `is_expired` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnjwobp1p26mw8heba7eo4es66` (`client_id`),
  KEY `FKlw43c3u9ukxmnvr5rrtrqm5wc` (`created_by`),
  KEY `FKmsu9tj5a1sn622lwu0n96qm3a` (`moderator_id`),
  KEY `FKf7dg3f5f0hmu4q1ejjoedd5lq` (`service_provider_id`),
  CONSTRAINT `FKf7dg3f5f0hmu4q1ejjoedd5lq` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKlw43c3u9ukxmnvr5rrtrqm5wc` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKmsu9tj5a1sn622lwu0n96qm3a` FOREIGN KEY (`moderator_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKnjwobp1p26mw8heba7eo4es66` FOREIGN KEY (`client_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
INSERT INTO `job` VALUES (74,_binary '','2025-02-26','tot 06:00 \'s ochtends. ','Afas Live Amsterdam ','2025-03-08',NULL,'Stagehand','2025-03-08','Afas Live',104,36,NULL,NULL,'WAITING','23:55:00',0,1,'Martijn van der Lee','+316489088181','23:00:00','',NULL,NULL,NULL,NULL),(76,_binary '','2025-02-26','.','Soest','2025-03-14',NULL,'Stagehand','2025-03-14','Soest, NMM',100,36,NULL,NULL,'WAITING','20:00:00',0,3,'Martijn van der lee','+316489088181','09:00:00','',NULL,NULL,NULL,NULL),(78,_binary '','2025-02-26','.','Soest','2025-03-13',NULL,'Stagehand','2025-03-13','Soest, NMM',100,36,NULL,NULL,'WAITING','20:00:00',0,3,'Martijn van der Lee','+316489088181','09:00:00','',NULL,NULL,NULL,NULL),(79,_binary '','2025-02-26','.','Soest','2025-03-17',NULL,'Stagehand','2025-03-17','Soest, NMM',100,36,NULL,NULL,'WAITING','18:00:00',0,3,'Martijn van der Lee','+316489088181','08:00:00','',NULL,NULL,NULL,NULL),(80,_binary '','2025-02-26','.','Dronten','2025-03-20',NULL,'Stagehand','2025-03-20','Dronten, Klu',100,36,NULL,NULL,'WAITING','00:00:00',0,1,'Martijn van der Lee','+316489088181','13:00:00','',NULL,NULL,NULL,NULL),(81,_binary '','2025-02-26','.','Borculo','2025-03-27',NULL,'Stagehand','2025-03-27','Borculo (Berkelland)',100,36,NULL,NULL,'WAITING','00:00:00',0,2,'Martijn van der Lee','+316489088181','13:00:00','',NULL,NULL,NULL,NULL),(82,_binary '','2025-02-26','.','Tilburg','2025-03-29',NULL,'Stagehand','2025-03-29','Tilburg, klu',100,36,NULL,NULL,'WAITING','00:00:00',0,1,'Martijn van der Lee','+316489088181','13:00:00','',NULL,NULL,NULL,NULL),(83,_binary '','2025-02-26','.','Udenhout','2025-03-30',NULL,'Stagehand','2025-03-30','Udenhout',104,36,NULL,NULL,'WAITING','03:00:00',0,1,'Martijn van der Lee','+316489088181','22:30:00','',NULL,NULL,NULL,NULL),(84,_binary '','2025-02-26','.','\'s-Hertogenbosch','2025-03-30',NULL,'Stagehand','2025-03-30','Brabanthallen',104,36,NULL,NULL,'WAITING','17:00:00',0,1,'Martijn van der Lee','+316489088181','06:00:00','',NULL,NULL,NULL,NULL),(85,_binary '','2025-02-26','.','Westerbork','2025-03-29',NULL,'Stagehand','2025-03-29','Westerbork, KMKJWF',100,36,NULL,NULL,'COMPLETED','00:30:00',0,3,'Martijn van der Lee','+316489088181','12:00:00','',NULL,NULL,123,NULL),(86,_binary '','2025-02-28','.','Oss','2025-02-28',NULL,'Stagehand','2025-02-28','Oss',100,36,NULL,NULL,'COMPLETED','13:00:00',1,1,'Martijn van der Lee','+316489088181','12:00:00','',NULL,'107=false',123,NULL),(89,_binary '','2025-03-01','test','unknown','2025-03-05',NULL,'some role','2025-03-05','test event',99,61,NULL,NULL,'COMPLETED','00:17:00',1,1,'lead','+31970105804261','00:15:00','ok',NULL,NULL,NULL,NULL),(90,_binary '','2025-04-23','sxsz','asdad','2025-04-23',NULL,'some role','2025-04-23','some title',99,61,NULL,NULL,'COMPLETED','23:07:00',1,1,'lead','+31970105804261','23:05:00','',NULL,'59=false',25042323050,NULL),(91,_binary '','2025-04-29','sxsz','asdad','2025-04-29',NULL,'some role','2025-04-29','some title 3',99,61,NULL,NULL,'COMPLETED','00:26:00',1,1,'lead','+3197010580426','00:06:00','',NULL,NULL,25042900214,NULL),(92,_binary '','2025-05-20','asdsada','asdsa','2025-05-20',NULL,'some role','2025-05-20','test4',99,61,NULL,NULL,'COMPLETED','22:16:00',1,1,'lead','+31970105804265','22:14:00','',_binary '','115=true',25052022145,NULL);
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_required_skills`
--

DROP TABLE IF EXISTS `job_required_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_required_skills` (
  `job_id` bigint NOT NULL,
  `skill_id` bigint NOT NULL,
  PRIMARY KEY (`job_id`,`skill_id`),
  KEY `FK7ubrr9h3bhoklnq3ralx98vni` (`skill_id`),
  CONSTRAINT `FK7ubrr9h3bhoklnq3ralx98vni` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`),
  CONSTRAINT `FKgb1yppol0xr2e7cfm70ws680x` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_required_skills`
--

LOCK TABLES `job_required_skills` WRITE;
/*!40000 ALTER TABLE `job_required_skills` DISABLE KEYS */;
INSERT INTO `job_required_skills` VALUES (74,21),(76,21),(78,21),(79,21),(80,21),(81,21),(82,21),(83,21),(84,21),(85,21),(86,21),(89,23),(90,23),(91,23),(92,23);
/*!40000 ALTER TABLE `job_required_skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_service_provider_ids`
--

DROP TABLE IF EXISTS `job_service_provider_ids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_service_provider_ids` (
  `job_id` bigint NOT NULL,
  `service_provider_id` bigint NOT NULL,
  KEY `FKip6o08t9xlinf55knpsof4xo` (`service_provider_id`),
  KEY `FK257j9xnm7mtbui2hb69bir9vt` (`job_id`),
  CONSTRAINT `FK257j9xnm7mtbui2hb69bir9vt` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
  CONSTRAINT `FKip6o08t9xlinf55knpsof4xo` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_service_provider_ids`
--

LOCK TABLES `job_service_provider_ids` WRITE;
/*!40000 ALTER TABLE `job_service_provider_ids` DISABLE KEYS */;
INSERT INTO `job_service_provider_ids` VALUES (89,115),(90,59),(91,115),(86,107),(92,115);
/*!40000 ALTER TABLE `job_service_provider_ids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_service_providers`
--

DROP TABLE IF EXISTS `job_service_providers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_service_providers` (
  `job_id` bigint NOT NULL,
  `service_provider_id` bigint NOT NULL,
  PRIMARY KEY (`job_id`,`service_provider_id`),
  KEY `FKlymogns8m6sv4sfn63nyj3mru` (`service_provider_id`),
  CONSTRAINT `FK1diguqei5ijr3cbyv600om0rs` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
  CONSTRAINT `FKlymogns8m6sv4sfn63nyj3mru` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_service_providers`
--

LOCK TABLES `job_service_providers` WRITE;
/*!40000 ALTER TABLE `job_service_providers` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_service_providers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `potential_job_offered`
--

DROP TABLE IF EXISTS `potential_job_offered`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `potential_job_offered` (
  `accepted` bit(1) NOT NULL,
  `service_provider_id` bigint NOT NULL,
  `job_id` bigint NOT NULL,
  PRIMARY KEY (`job_id`,`service_provider_id`),
  KEY `FKrgl2vds0bhb1n7va3545mblet` (`service_provider_id`),
  CONSTRAINT `FK5w0h6uxdyrv138ivmuncxw7ra` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
  CONSTRAINT `FKrgl2vds0bhb1n7va3545mblet` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `potential_job_offered`
--

LOCK TABLES `potential_job_offered` WRITE;
/*!40000 ALTER TABLE `potential_job_offered` DISABLE KEYS */;
/*!40000 ALTER TABLE `potential_job_offered` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `potential_job_offers`
--

DROP TABLE IF EXISTS `potential_job_offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `potential_job_offers` (
  `accepted` bigint DEFAULT NULL,
  `service_provider_id` bigint NOT NULL,
  `job_id` bigint NOT NULL,
  PRIMARY KEY (`job_id`,`service_provider_id`),
  KEY `FKspf5uqvgi937te0soqw681lrp` (`service_provider_id`),
  CONSTRAINT `FK61aellbu1kh1egb78heaie3x3` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`),
  CONSTRAINT `FKspf5uqvgi937te0soqw681lrp` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `potential_job_offers`
--

LOCK TABLES `potential_job_offers` WRITE;
/*!40000 ALTER TABLE `potential_job_offers` DISABLE KEYS */;
INSERT INTO `potential_job_offers` VALUES (-1,106,85),(1,107,86),(1,115,89),(1,59,90),(1,115,91),(1,115,92);
/*!40000 ALTER TABLE `potential_job_offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_NORMAL');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_provider_availability_dates`
--

DROP TABLE IF EXISTS `service_provider_availability_dates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_provider_availability_dates` (
  `service_provider_id` bigint NOT NULL,
  `availability_dates` date DEFAULT NULL,
  KEY `FKjlkyd40ofr345v7uokleykeku` (`service_provider_id`),
  CONSTRAINT `FKjlkyd40ofr345v7uokleykeku` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_provider_availability_dates`
--

LOCK TABLES `service_provider_availability_dates` WRITE;
/*!40000 ALTER TABLE `service_provider_availability_dates` DISABLE KEYS */;
INSERT INTO `service_provider_availability_dates` VALUES (106,'2025-02-01'),(106,'2025-02-02'),(106,'2025-02-03'),(106,'2025-02-04'),(106,'2025-02-05'),(106,'2025-02-06'),(106,'2025-02-07'),(106,'2025-02-08'),(106,'2025-02-09'),(106,'2025-02-10'),(106,'2025-02-11'),(106,'2025-02-12'),(106,'2025-02-13'),(106,'2025-02-14'),(106,'2025-02-15'),(106,'2025-02-16'),(106,'2025-02-17'),(106,'2025-02-18'),(106,'2025-02-19'),(106,'2025-02-20'),(106,'2025-02-21'),(106,'2025-02-22'),(106,'2025-02-23'),(106,'2025-02-24'),(106,'2025-02-25'),(106,'2025-02-26'),(106,'2025-02-27'),(106,'2025-02-28'),(106,'2025-03-01'),(106,'2025-03-02'),(106,'2025-03-03'),(106,'2025-03-04'),(106,'2025-02-01');
/*!40000 ALTER TABLE `service_provider_availability_dates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_provider_skill`
--

DROP TABLE IF EXISTS `service_provider_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_provider_skill` (
  `service_provider_id` bigint NOT NULL,
  `skill_id` bigint NOT NULL,
  PRIMARY KEY (`service_provider_id`,`skill_id`),
  KEY `FKgp8f3hifpjgbh020akccmt8k2` (`skill_id`),
  CONSTRAINT `FK4l0hhlearl1m8eme5s48k35dk` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKgp8f3hifpjgbh020akccmt8k2` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_provider_skill`
--

LOCK TABLES `service_provider_skill` WRITE;
/*!40000 ALTER TABLE `service_provider_skill` DISABLE KEYS */;
INSERT INTO `service_provider_skill` VALUES (106,21),(107,21),(108,21),(109,21),(110,21),(111,21),(112,21),(113,21),(114,21),(115,23),(109,24),(109,28),(109,30),(109,31);
/*!40000 ALTER TABLE `service_provider_skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_provider_unavailability_dates`
--

DROP TABLE IF EXISTS `service_provider_unavailability_dates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_provider_unavailability_dates` (
  `service_provider_id` bigint NOT NULL,
  `unavailability_dates` date DEFAULT NULL,
  KEY `FKg32op6qhvfebic3by0i019r92` (`service_provider_id`),
  CONSTRAINT `FKg32op6qhvfebic3by0i019r92` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_provider_unavailability_dates`
--

LOCK TABLES `service_provider_unavailability_dates` WRITE;
/*!40000 ALTER TABLE `service_provider_unavailability_dates` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_provider_unavailability_dates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_provider_unknown_dates`
--

DROP TABLE IF EXISTS `service_provider_unknown_dates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_provider_unknown_dates` (
  `service_provider_id` bigint NOT NULL,
  `unknown_dates` date DEFAULT NULL,
  KEY `FKhdidgb3yq525qj878t9kgt1iv` (`service_provider_id`),
  CONSTRAINT `FKhdidgb3yq525qj878t9kgt1iv` FOREIGN KEY (`service_provider_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_provider_unknown_dates`
--

LOCK TABLES `service_provider_unknown_dates` WRITE;
/*!40000 ALTER TABLE `service_provider_unknown_dates` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_provider_unknown_dates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `skill_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill`
--

LOCK TABLES `skill` WRITE;
/*!40000 ALTER TABLE `skill` DISABLE KEYS */;
INSERT INTO `skill` VALUES (20,'Licht A'),(21,'Licht B'),(22,'Licht C'),(23,'Audio A'),(24,'Audio B'),(25,'Audio C'),(26,'Spoor A'),(27,'Spoor B'),(28,'Spoor C'),(29,'Heftruck'),(30,'VCA'),(31,'DVP');
/*!40000 ALTER TABLE `skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (99,1),(100,1),(101,1),(102,1),(103,1),(104,1),(105,1),(106,1),(107,1),(108,1),(109,1),(110,1),(111,1),(112,1),(113,1),(114,1),(115,1),(116,1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `about` longtext,
  `active` bit(1) DEFAULT NULL,
  `dep_id` int NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(100) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `dtype` varchar(31) NOT NULL,
  `billing` varchar(255) DEFAULT NULL,
  `preferences` varchar(255) DEFAULT NULL,
  `billing_info` varchar(255) DEFAULT NULL,
  `ratings` float DEFAULT NULL,
  `access_level` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `occupation` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `availability_status` bit(1) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `availability_end_date` date DEFAULT NULL,
  `availability_options` varchar(255) DEFAULT NULL,
  `availability_start_date` date DEFAULT NULL,
  `profile_image` blob,
  `vat` varchar(255) DEFAULT NULL,
  `agreement` varchar(255) DEFAULT NULL,
  `business_registration` varchar(255) DEFAULT NULL,
  `discussed_rate` double DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `contact_person` varchar(255) DEFAULT NULL,
  `invoice_mail` varchar(255) DEFAULT NULL,
  `kilometer_rate` double DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `phone_number_planning` varchar(255) DEFAULT NULL,
  `sector` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `drivers_license` varchar(255) DEFAULT NULL,
  `billable` varchar(255) DEFAULT NULL,
  `day_rate` double DEFAULT NULL,
  `night_rate` double DEFAULT NULL,
  `rate_type` varchar(255) DEFAULT NULL,
  `time_based_rate` bit(1) DEFAULT NULL,
  `weekend_rate` double DEFAULT NULL,
  `unavailable_end_date` date DEFAULT NULL,
  `unavailable_start_date` date DEFAULT NULL,
  `unknown_end_date` date DEFAULT NULL,
  `unknown_start_date` date DEFAULT NULL,
  `rail_work_rate` double DEFAULT NULL,
  `client_event_rate_type` varchar(255) DEFAULT NULL,
  `discussed_raterw` double DEFAULT NULL,
  `hour_rate` double DEFAULT NULL,
  `hour_raterw` double DEFAULT NULL,
  `night_raterw` double DEFAULT NULL,
  `weekend_raterw` double DEFAULT NULL,
  `minimum_payment_hour` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k8d0f2n7n88w1a16yhua64onx` (`first_name`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (36,'admin',_binary '',2,'administratie@eventico.nl','admin','$2a$10$CW/acNfxUqYJrV.5sKScwenfzd4.UKTFGOnN1RtngblpvQxvjeqE2','Moderator',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(59,'Projectleider',_binary '',3,'nadin.belkhiria@eventico.nl','Nadin','$2a$12$lltUw1Knfq1GoB/FEKJKd.TZj07EKTORWjZY3PoZv9o.0J5IsIZkW','ServiceProvider',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','+316534880381',1,NULL,'2024-08-17','CUSTOM','2024-08-06',NULL,NULL,NULL,'85762695',325,'Belkhiria',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2000-08-29','B',NULL,NULL,NULL,'DAY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(61,NULL,_binary '',2,'newModeratorTest@gmail.com','newModeratorTest','$2a$10$CARF3TCIaEsrx1.fuHLYCOmQBsF8FIfrSyF9cYjVUerGT1gT7iIiy','Moderator',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'newModeratorTest',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(99,'Avex Audiovisueel',_binary '',1,'Avex@eventico.nl','Michael','$2a$10$b6ItRKDSCyI70lgQ.mqVGeIPFwR.x1AUwUaiYZulZCTFgXiK580tK','Client',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'30086148',350,'van Kuik',NULL,'Avex','administratie@eventico.nl',0.23,'+316489088181','+316489088181','Audiovisueel',NULL,NULL,NULL,NULL,NULL,'DAY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(100,'Story Event Engineering',_binary '',1,'ousama.ghori@gmail.com','Story Event Engineering','$2a$10$CARF3TCIaEsrx1.fuHLYCOmQBsF8FIfrSyF9cYjVUerGT1gT7iIiy','Client',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'10033778',400,'',NULL,'Martijn','Administratie@eventico.nl',0.28,'+316489088181','+316489088181','Audiovisueel',NULL,NULL,NULL,NULL,NULL,'DAY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(101,'Spoorbouw',_binary '',1,'atr@eventico.nl','ATR Group B.V.','$2a$10$R5QLP2Y68iLSDQwINPc/keT6tLPsYPPNDF7auwypSSDlkzvav9vqG','Client',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'76670953',NULL,'',NULL,NULL,NULL,0.35,NULL,NULL,'Spoorbouw',NULL,NULL,NULL,38.5,48,'HOURLY',_binary '',54,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(102,'Kamping Kitsch Club',_binary '',1,'kkc@eventico.nl','KKC International B.V.','$2a$10$zAkhNaVIUO1N7AN7vgt9puaQRMgjoVJIfOm.NA18H8Sxs3g9BDvNK','Client',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'90745396',32.5,'',NULL,NULL,NULL,0.23,NULL,NULL,'Evenement Productie',NULL,NULL,NULL,NULL,NULL,'HOURLY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(103,'Par-T',_binary '',1,'part@eventico.nl','Par-T','$2a$10$DcZAGiScoXknbg5lFkWAlusDEfHo0VG0dUqNaC94pvmdGZ11ICN/m','Client',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'51513390',NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'HOURLY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(104,'Spectrum Audiovisual Productions',_binary '',1,'Spectrum@eventico.nl','Spectrum Audiovisual Productions B.V.','$2a$10$B.DQwkTYDT.ANG.lyDQPwuuLNtMQ17ELcZWDGIfB90LN/AXiBunf.','Client',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'85813737',NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,'Audiovisueel',NULL,NULL,NULL,NULL,NULL,'HOURLY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(105,'Thijs Standbouw',_binary '',1,'Thijsstandbouw@eventico.nl','Thijs Standbouw','$2a$10$vlTv8J/x2kmwq.KvIGW0nek9j2IHgopWNo5GxIVeHLrobAumUEoNq','Client',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'28104021',NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'HOURLY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(106,'Stagehand',_binary '',3,'justijn.v.zanten@gmail.com','Justijn test','$2a$10$pah6elhtNXXIxERwcos0W.Zn36CO8BPk7SBt4rRsJ6QWc4EXOBWQy','ServiceProvider',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','+316299682101',0,NULL,NULL,'MONTH',NULL,NULL,NULL,NULL,'92917933',20,'van Zanten',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'B',NULL,NULL,NULL,'HOURLY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(107,'Freelancer',_binary '',3,'tuana.sonmez@edu-kw1c.nl','Tuana','$2a$10$kqm7rA/6kxit0tZXHlg5eObEqdIHko7gaQj.c/0SfdcIMiUNfmFWi','ServiceProvider',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','+316403426461',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'000',20,'Sonmez',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'B',NULL,NULL,NULL,'HOURLY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(108,'Stagehand',_binary '',3,'martijn.vanderlee@eventico.nl','Martijn','$2a$10$bA9jC6MyiahIUKTjLBwKaeE66dvYYzKZHHF36wHJakLKSRn4MAcOq','ServiceProvider',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','+316489088181',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,20,'van der Lee',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'C',NULL,NULL,NULL,'HOURLY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(109,'Stagehand',_binary '',3,'camielb123@icloud.com','Camiel','$2a$10$si8nsTzSQ/bBbI.2pfVAvOsJmf3Q78eLdCOMbY3IBU6xb2aTSTuAW','ServiceProvider',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','+316362870211',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'91155576',25,'van de Beurcht',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2000-01-01','B',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(110,'stagehand',_binary '',3,'ivanlandman06@outlook.com','Ivan','$2a$10$dtMEBILt2ygOZ7xQXCrK7em0T83TYcK7U18jpDQWWW4NJoYcYVrBi','ServiceProvider',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','+316187585951',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'94675414',20,'Landman',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2000-01-01','B',NULL,NULL,NULL,'HOURLY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(111,'.',_binary '',3,'jvdbeurcht@icloud.com','Jules','$2a$10$IhXD4icigu.tu/OUF5RJYevu8CWwD3UucwyQ5Asj8NWKbohRM/LiW','ServiceProvider',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','+316109634911',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'94088071',22.5,'van de Beurcht',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2000-01-01','B',NULL,NULL,NULL,'HOURLY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(112,'stagehand',_binary '',3,'Jansolivierr@outlook.com','Olivier','$2a$10$ED7X33H9as2KVuum4mIp2uMtT0LgEXdmGGQ2gN3cpoO7HEix546ui','ServiceProvider',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','+316112313611',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'94536023',27.5,'Jans',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2000-01-01','B',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(113,'stagehand',_binary '',3,'rijkbroess@icloud.com','Rijk','$2a$10$K5/PV3osgZpLg/sM3NcFw.sX2KGkxLFDRZxkS81H4Gx6p4tqARTou','ServiceProvider',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','+316167947751',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0000',20,'Broess',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2025-01-01','B',NULL,NULL,NULL,'HOURLY',_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(114,'stagehand',_binary '',3,'lars20030917@gmail.com','Lars','$2a$10$YPCEQDxoZBFo9PsZmrAB0.bXr6MVCYFMuc/SrXutxQNi2fGX.2ajO','ServiceProvider',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','+316225430301',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'NL004087184B48',NULL,'Willems',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'B',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(115,'test',_binary '',3,'samghori9@gmail.com','sam','$2a$10$CARF3TCIaEsrx1.fuHLYCOmQBsF8FIfrSyF9cYjVUerGT1gT7iIiy','ServiceProvider',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,_binary '','+3197010580446',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'c123',2,'ghori',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'BE',NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL),(116,'asdasdas',_binary '',1,'test1@gmail.com','test1','$2a$10$Bf8pGUf4zY/IY0QBA.ynCeqpKQHKzHXG/LOc7QjAUu8fNN8G/8dAa','Client',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',1,'',NULL,'asdsad','test1@gmail.com',12,'+1231232131231','+34231231231','C',NULL,NULL,NULL,1,1,'DAY',_binary '',1,NULL,NULL,NULL,NULL,NULL,'EVENT',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-23 17:43:04

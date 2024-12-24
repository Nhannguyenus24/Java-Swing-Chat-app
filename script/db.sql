CREATE DATABASE IF NOT EXISTS `user_management`;

USE `user_management`;

-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: user_management
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `chat_id` int NOT NULL AUTO_INCREMENT,
  `is_group` tinyint(1) NOT NULL,
  `chat_name` varchar(255) NOT NULL,
  PRIMARY KEY (`chat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
INSERT INTO `chat` VALUES (1,0,''),(2,0,''),(3,0,''),(4,0,''),(5,0,''),(6,0,''),(7,0,''),(9,1,'Death come for us');
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_member`
--

DROP TABLE IF EXISTS `chat_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_member` (
  `chat_id` int NOT NULL,
  `user_id` int NOT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_member`
--

LOCK TABLES `chat_member` WRITE;
/*!40000 ALTER TABLE `chat_member` DISABLE KEYS */;
INSERT INTO `chat_member` VALUES (1,1,0),(1,2,0),(2,1,0),(2,3,0),(3,2,0),(3,3,0),(4,2,0),(4,1,0),(5,2,0),(5,1,0),(6,3,0),(6,2,0),(7,2,0),(7,3,0),(9,1,1),(9,2,0),(9,3,0);
/*!40000 ALTER TABLE `chat_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend_request`
--

DROP TABLE IF EXISTS `friend_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend_request` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `user_request_id` int NOT NULL,
  `user_accept_id` int NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `user_request_id` (`user_request_id`),
  KEY `user_accept_id` (`user_accept_id`),
  CONSTRAINT `friend_request_ibfk_1` FOREIGN KEY (`user_request_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `friend_request_ibfk_2` FOREIGN KEY (`user_accept_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_request`
--

LOCK TABLES `friend_request` WRITE;
/*!40000 ALTER TABLE `friend_request` DISABLE KEYS */;
INSERT INTO `friend_request` VALUES (6,2,4),(9,2,5),(10,2,6),(11,2,7),(12,2,8),(13,2,9),(14,2,10),(15,2,11),(16,2,12),(17,2,13),(18,1,4),(19,1,5),(20,1,6),(21,1,7),(22,1,8),(23,1,9);
/*!40000 ALTER TABLE `friend_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friends` (
  `user_id` int NOT NULL,
  `friend_id` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_block` tinyint(1) NOT NULL DEFAULT '0',
  `block` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`,`friend_id`),
  KEY `friend_id` (`friend_id`),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES (1,2,'2024-12-12 04:40:10',0,0),(1,3,'2024-12-11 22:41:12',0,1),(2,1,'2024-12-12 04:40:10',0,0),(2,3,'2024-12-12 21:02:25',0,0),(3,1,'2024-12-11 22:41:12',1,0),(3,2,'2024-12-12 21:02:25',0,0);
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_history`
--

DROP TABLE IF EXISTS `login_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_history` (
  `history_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `login_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`history_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `login_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_history`
--

LOCK TABLES `login_history` WRITE;
/*!40000 ALTER TABLE `login_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `login_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `chat_id` int NOT NULL,
  `sender_id` int NOT NULL,
  `content` text NOT NULL,
  `timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `owner_id` int NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `sender_id` (`sender_id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `message_ibfk_3` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (3,2,1,'hello','2024-12-11 23:32:38',1),(10,2,1,'wtfffff','2024-12-12 00:36:02',1),(20,2,1,'tungggggg o co 6 chu gay','2024-12-12 00:52:02',1),(33,3,3,'hello','2024-12-12 20:46:45',2),(34,3,3,'hello','2024-12-12 20:46:45',3),(35,3,3,'hiiiiiiiiiii','2024-12-12 20:46:50',2),(36,3,3,'hiiiiiiiiiii','2024-12-12 20:46:50',3),(38,3,2,'hiii cc','2024-12-12 20:47:09',2),(39,3,2,'choi nhu tui my','2024-12-12 20:47:26',3),(40,3,2,'choi nhu tui my','2024-12-12 20:47:26',2),(41,3,2,'oooo bi gay ha','2024-12-12 21:02:35',3),(42,3,2,'oooo bi gay ha','2024-12-12 21:02:35',2),(43,3,3,'dung roi o','2024-12-12 21:02:43',2),(44,3,3,'dung roi o','2024-12-12 21:02:43',3),(45,3,3,'tui bi gay vi ten tui co 6 chu gay','2024-12-12 21:02:52',2),(46,3,3,'tui bi gay vi ten tui co 6 chu gay','2024-12-12 21:02:52',3),(47,3,2,'oke Tung gay o','2024-12-12 21:03:03',3),(48,3,2,'oke Tung gay o','2024-12-12 21:03:03',2),(49,1,2,'hello','2024-12-12 21:07:38',1),(52,9,2,'hello','2024-12-20 10:56:51',2),(53,9,2,'hello','2024-12-20 10:56:51',3),(54,9,1,'hello','2024-12-20 11:42:38',1),(55,9,1,'hello','2024-12-20 11:42:38',2),(56,9,1,'hello','2024-12-20 11:42:38',3),(57,9,2,'wtfffffffff','2024-12-20 11:50:41',1),(58,9,2,'wtfffffffff','2024-12-20 11:50:41',2),(59,9,2,'wtfffffffff','2024-12-20 11:50:41',3),(60,1,1,'1\r\n2','2024-12-20 16:02:43',2),(61,1,1,'1\r\n2','2024-12-20 16:02:43',1),(62,1,1,'1','2024-12-20 16:02:46',2),(63,1,1,'1','2024-12-20 16:02:46',1),(64,1,1,'2','2024-12-20 16:02:47',2),(65,1,1,'2','2024-12-20 16:02:47',1),(66,1,1,'3','2024-12-20 16:02:49',2),(67,1,1,'3','2024-12-20 16:02:49',1),(68,1,1,'4','2024-12-20 16:02:51',2),(69,1,1,'4','2024-12-20 16:02:51',1),(70,1,1,'5','2024-12-20 16:02:53',2),(71,1,1,'5','2024-12-20 16:02:53',1),(72,1,1,'6','2024-12-20 16:02:54',2),(73,1,1,'6','2024-12-20 16:02:54',1),(74,1,1,'7','2024-12-20 16:02:56',2),(75,1,1,'7','2024-12-20 16:02:56',1),(76,1,1,'8','2024-12-20 16:02:57',2),(77,1,1,'8','2024-12-20 16:02:57',1),(78,1,1,'9','2024-12-20 16:02:59',2),(79,1,1,'9','2024-12-20 16:02:59',1),(80,1,2,'hello','2024-12-20 16:07:27',1),(81,1,2,'hello','2024-12-20 16:07:27',2),(82,3,2,'hi1','2024-12-20 16:11:30',3),(83,3,2,'hi1','2024-12-20 16:11:30',2),(84,3,2,'hi2','2024-12-20 16:11:33',3),(85,3,2,'hi2','2024-12-20 16:11:33',2),(86,3,2,'hihihihi','2024-12-20 16:11:39',3),(87,3,2,'hihihihi','2024-12-20 16:11:39',2),(88,3,2,'há','2024-12-20 16:11:45',3),(89,3,2,'há','2024-12-20 16:11:45',2),(90,9,1,'o o o o','2024-12-20 22:05:43',1),(91,9,1,'o o o o','2024-12-20 22:05:43',2),(93,9,1,'11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111','2024-12-20 22:06:06',2),(94,2,1,'123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789','2024-12-20 22:14:30',3),(96,9,3,'hello may o','2024-12-21 14:12:53',1),(97,9,3,'hello may o','2024-12-21 14:12:53',2),(98,9,3,'hello may o','2024-12-21 14:12:53',3),(99,9,1,'Tung o gay vcl','2024-12-21 15:12:27',1),(100,9,1,'Tung o gay vcl','2024-12-21 15:12:27',2),(101,9,1,'Tung o gay vcl','2024-12-21 15:12:27',3);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spam_reports`
--

DROP TABLE IF EXISTS `spam_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spam_reports` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `report_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `reason` text,
  PRIMARY KEY (`report_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `spam_reports_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spam_reports`
--

LOCK TABLES `spam_reports` WRITE;
/*!40000 ALTER TABLE `spam_reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `spam_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` enum('Male','Female','Other') NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` enum('Active','Locked','Inactive') DEFAULT 'Inactive',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `password` varchar(255) NOT NULL,
  `is_admin` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'loisme','Tran Tien Loi','Long An','2004-01-01','Male','loi@gmail.com','Inactive','2024-12-11 22:12:22','123456',0),(2,'calvados','NTN','Nha Trang','2004-02-02','Male','nhannguyentrong355@gmail.com','Inactive','2024-12-11 22:13:16','123456',1),(3,'tungggggg','Tung Tran','Quang Ngai','2004-03-03','Female','tung@gmail.com','Inactive','2024-12-11 22:40:40','123456',0),(4,'acc1','acc1','Binh Thuan','2004-03-03','Male','acc1@gmailc.com','Inactive','2024-12-12 15:00:25','123456',0),(5,'acc2','acc2','Ca Mau Phuong 9','2004-04-04','Male','acc2@gmail.com','Inactive','2024-12-12 15:01:03','123456',0),(6,'acc3','acc3','Ninh Thuan','2004-08-07','Male','acc3@gmail.com','Inactive','2024-12-12 15:01:45','123456',0),(7,'acc4','User 4','Hanoi','2004-05-05','Male','acc4@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(8,'acc5','User 5','Ho Chi Minh','2004-06-06','Female','acc5@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(9,'acc6','User 6','Da Nang','2004-07-07','Male','acc6@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(10,'acc7','User 7','Can Tho','2004-08-08','Female','acc7@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(11,'acc8','User 8','Hai Phong','2004-09-09','Other','acc8@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(12,'acc9','User 9','Vinh','2004-10-10','Male','acc9@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(13,'acc10','User 10','Hue','2004-11-11','Female','acc10@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(14,'acc11','User 11','Quang Nam','2004-12-12','Male','acc11@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(15,'acc12','User 12','Lang Son','2005-01-01','Female','acc12@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(16,'acc13','User 13','Lai Chau','2005-02-02','Other','acc13@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(17,'acc14','User 14','Dak Lak','2005-03-03','Male','acc14@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(18,'acc15','User 15','Soc Trang','2005-04-04','Female','acc15@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(19,'acc16','User 16','Ben Tre','2005-05-05','Male','acc16@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(20,'acc17','User 17','Kien Giang','2005-06-06','Female','acc17@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(21,'acc18','User 18','Dong Nai','2005-07-07','Other','acc18@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(22,'acc19','User 19','Binh Duong','2005-08-08','Male','acc19@gmail.com','Inactive','2024-12-12 15:05:54','123456',0),(23,'acc20','User 20','An Giang','2005-09-09','Female','acc20@gmail.com','Inactive','2024-12-12 15:05:54','123456',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_activity`
--

DROP TABLE IF EXISTS `user_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_activity` (
  `activity_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `activity_type` enum('App Open','Chat','Group Join') NOT NULL,
  `activity_count` int DEFAULT '0',
  `activity_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`activity_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_activity_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_activity`
--

LOCK TABLES `user_activity` WRITE;
/*!40000 ALTER TABLE `user_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_registrations`
--

DROP TABLE IF EXISTS `user_registrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_registrations` (
  `registration_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `registration_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`registration_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_registrations_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_registrations`
--

LOCK TABLES `user_registrations` WRITE;
/*!40000 ALTER TABLE `user_registrations` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_registrations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-21 15:44:11

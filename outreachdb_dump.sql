CREATE DATABASE  IF NOT EXISTS `outreachdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `outreachdb`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: outreachdb
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset` (
  `assetId` varchar(255) NOT NULL,
  `inventoryId` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `assetStatus` enum('AVAILABLE','CHECKED_OUT','IN_MAINTENANCE','RETIRED') DEFAULT NULL,
  `device_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`assetId`),
  KEY `fk_inventoryId` (`inventoryId`),
  CONSTRAINT `fk_inventoryId` FOREIGN KEY (`inventoryId`) REFERENCES `inventory` (`inventoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset`
--

LOCK TABLES `asset` WRITE;
/*!40000 ALTER TABLE `asset` DISABLE KEYS */;
INSERT INTO `asset` VALUES ('cellphone001',2100,'Samsung Galaxy','CHECKED_OUT','Cellpone'),('cellphone002',2100,'iPhone 14 Pro Max','CHECKED_OUT',NULL),('cellphone003',2100,'Google Pixel 11','CHECKED_OUT',NULL),('cellphone010',1201,'Google Pixel 11','CHECKED_OUT',NULL),('cellphone011',1201,'iPhone 16 Pr Max','CHECKED_OUT',NULL),('cellphone012',1201,'Samsung Galaxy Flip Z','IN_MAINTENANCE',NULL),('cellphone013',1201,'Samsung Galaxy A16 ','CHECKED_OUT',NULL),('cellphone997',NULL,'Google Pixel Test','AVAILABLE',NULL),('cellphoneTestWeb',NULL,'Web Test','IN_MAINTENANCE','Cellphone'),('LAP100',2100,'Macbook Air','AVAILABLE','Laptop'),('lap101',2100,'Lenovo Googlebook','IN_MAINTENANCE',NULL),('lap200',1201,'HP Omnibook X Flip','AVAILABLE',NULL),('lap201',1201,'Macboook Pro','IN_MAINTENANCE',NULL),('lap203',1201,'Samsung Galaxy Chromebook','AVAILABLE',NULL),('lap204',1201,'Macbook Air','AVAILABLE',NULL),('lap205',1201,'Lenovo IdeaPad Slim 3i','IN_MAINTENANCE',NULL),('tablet100',1201,'Lenovo Idea Tab Pro','AVAILABLE','Tablet'),('tablet101',1201,'iPad Pro','AVAILABLE',NULL),('tablet102',1201,'Samsung Galaxy Tab','AVAILABLE',NULL),('tablet103',1201,'iPad Air','AVAILABLE',NULL),('tablet200',2100,'iPad Air','AVAILABLE',NULL),('tablet201',2100,'Samsung Galaxy Tab A11+','AVAILABLE',NULL),('webTest2',2100,'Test2','RETIRED','Cellphone');
/*!40000 ALTER TABLE `asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `center`
--

DROP TABLE IF EXISTS `center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `center` (
  `centerId` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address_line1` varchar(255) DEFAULT NULL,
  `address_line2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `postal_code` char(5) DEFAULT NULL,
  PRIMARY KEY (`centerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `center`
--

LOCK TABLES `center` WRITE;
/*!40000 ALTER TABLE `center` DISABLE KEYS */;
INSERT INTO `center` VALUES (1000,'Orlando Cares','400 S Orange Ave','','Orlando','FL','32801'),(1001,'John H. Jackson Neighborhood Center','1002 Carter Street','','Orlando','FL','32805');
/*!40000 ALTER TABLE `center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credentials`
--

DROP TABLE IF EXISTS `credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credentials` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `username` varchar(155) NOT NULL,
  `password` varchar(155) NOT NULL,
  `roleId` int DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId` (`userId`),
  UNIQUE KEY `uq_credentials_username` (`username`),
  KEY `fk_credentials_role` (`roleId`),
  CONSTRAINT `fk_credentials_role` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credentials`
--

LOCK TABLES `credentials` WRITE;
/*!40000 ALTER TABLE `credentials` DISABLE KEYS */;
INSERT INTO `credentials` VALUES (1,'admin','$2a$10$TmYR5W9XV7X5cBNVqMREcOn7OmxAUsIpBAIv6ixaJ8Vm3uVn7Co0i',2);
/*!40000 ALTER TABLE `credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `inventoryId` int NOT NULL,
  `centerId` int DEFAULT NULL,
  PRIMARY KEY (`inventoryId`),
  KEY `centerId` (`centerId`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`centerId`) REFERENCES `center` (`centerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1201,1000),(2100,1001);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan` (
  `loanId` int NOT NULL AUTO_INCREMENT,
  `assetId` varchar(255) DEFAULT NULL,
  `participantId` int DEFAULT NULL,
  `checkoutDate` date DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `returnDate` date DEFAULT NULL,
  `loanStatus` enum('CHECKED_IN','CHECKED_OUT','READY') DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `LoanPurpose` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`loanId`),
  KEY `fk_participantId` (`participantId`),
  KEY `fk_loan_asset` (`assetId`),
  CONSTRAINT `fk_loan_asset` FOREIGN KEY (`assetId`) REFERENCES `asset` (`assetId`),
  CONSTRAINT `fk_participantId` FOREIGN KEY (`participantId`) REFERENCES `loanee` (`participantId`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` VALUES (1,'LAP100',2457,'2026-09-09','2026-09-16','2026-09-15','CHECKED_IN','OTHER','EDUCATION'),(2,'cellphone001',2457,'2026-10-01','2026-10-31',NULL,'CHECKED_OUT','OTHER','PERSONAL'),(3,'tablet201',2457,'2026-09-19','2026-10-19',NULL,'CHECKED_OUT','EDUCATION','EDUCATION'),(4,'lap201',2457,'2026-10-01','2026-10-07','2026-10-07','CHECKED_IN','OTHER','EDUCATION'),(5,'cellphone002',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(6,'cellphone003',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(7,'cellphone010',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(8,'cellphone011',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(9,'cellphone012',2457,'2026-09-05','2026-09-15','2026-09-14','READY','OTHER','PERSONAL'),(10,'cellphone013',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(11,'lap101',2457,'2026-09-05','2026-09-15','2026-09-14','READY','OTHER','PERSONAL'),(12,'lap200',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(13,'lap203',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(14,'lap204',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(15,'lap205',2457,'2026-09-05','2026-09-15','2026-09-14','READY','OTHER','PERSONAL'),(16,'tablet100',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(17,'tablet101',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(18,'tablet102',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(19,'tablet103',2457,'2026-09-01','2026-09-10','2026-09-08','CHECKED_IN','EDUCATION','EDUCATION'),(20,'tablet200',2457,'2026-09-02','2026-09-12','2026-09-10','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(21,'cellphone997',2039,'2026-11-07','2026-11-14','2026-11-07','CHECKED_IN','EDUCATION','EDUCATION'),(29,'cellphoneTestWeb',7830,'2026-11-08','2026-11-29','2026-11-08','CHECKED_IN','Testing stuff','PERSONAL'),(30,'cellphoneTestWeb',7830,'2026-11-08','2026-11-22','2026-11-08','CHECKED_IN','Test','EDUCATION'),(31,'cellphoneTestWeb',7830,'2026-11-08','2026-11-22','2026-11-08','CHECKED_IN','Test','EMPLOYMENT');
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanee`
--

DROP TABLE IF EXISTS `loanee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loanee` (
  `participantId` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `phoneNumber` varchar(30) DEFAULT NULL,
  `address_line1` varchar(150) NOT NULL,
  `address_line2` varchar(150) DEFAULT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(2) NOT NULL,
  `postal_code` varchar(100) NOT NULL,
  PRIMARY KEY (`participantId`)
) ENGINE=InnoDB AUTO_INCREMENT=900002 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanee`
--

LOCK TABLES `loanee` WRITE;
/*!40000 ALTER TABLE `loanee` DISABLE KEYS */;
INSERT INTO `loanee` VALUES (2039,'John','Doe','555-0101','123 Main St',NULL,'Orlando','FL','32801'),(2457,'Jane','Smith','555-0202','456 Orange Ave',NULL,'Orlando','FL','32802'),(7830,'Bob','Johnson','555-0303','789 Rosalind Ave',NULL,'Orlando','FL','32803');
/*!40000 ALTER TABLE `loanee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maintenance`
--

DROP TABLE IF EXISTS `maintenance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maintenance` (
  `maintenanceId` int NOT NULL AUTO_INCREMENT,
  `assetId` varchar(255) DEFAULT NULL,
  `issue` varchar(40) NOT NULL,
  `maintenanceStatus` enum('BROKEN','COMPLETED','IN_PROGRESS') DEFAULT NULL,
  `repairNotes` varchar(100) NOT NULL,
  `openedDate` date DEFAULT NULL,
  `completedDate` date DEFAULT NULL,
  PRIMARY KEY (`maintenanceId`),
  KEY `fk_maintenance_asset` (`assetId`),
  CONSTRAINT `fk_maintenance_asset` FOREIGN KEY (`assetId`) REFERENCES `asset` (`assetId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintenance`
--

LOCK TABLES `maintenance` WRITE;
/*!40000 ALTER TABLE `maintenance` DISABLE KEYS */;
INSERT INTO `maintenance` VALUES (1,'LAP100','No issues at this time','BROKEN','N/A','2026-09-01','2026-09-01'),(2,'cellphone012','Unresposnive screen','BROKEN','Ordered new screen','2026-10-07','2026-10-31'),(3,'lap101','Battery draines fast','BROKEN','Ordered new battery','2026-09-19','2026-10-25'),(4,'lap201','Software needs an update','BROKEN','Downloading software update','2026-10-07','2026-10-07'),(7,'cellphoneTestWeb','Returned: minor damage','IN_PROGRESS','Test','2026-11-08',NULL);
/*!40000 ALTER TABLE `maintenance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (2,'Admin'),(1,'Staff');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-08 14:41:08

-- MySQL dump 10.13  Distrib 8.2.0, for macos13 (arm64)
--
-- Host: localhost    Database: outreachDB
-- ------------------------------------------------------
-- Server version	8.2.0

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
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset` (
  `assetId` varchar(100) NOT NULL,
  `inventoryId` int DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `assetStatus` varchar(12) NOT NULL,
  `device_type` varchar(100) DEFAULT NULL,
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
INSERT INTO `asset` VALUES ('cellphone001',2100,'Samsung Galaxy','Operable','Cellpone'),('cellphone002',2100,'iPhone 14 Pro Max','Operable',NULL),('cellphone003',2100,'Google Pixel 11','Operable',NULL),('cellphone010',1201,'Google Pixel 11','Operable',NULL),('cellphone011',1201,'iPhone 16 Pr Max','Operable',NULL),('cellphone012',1201,'Samsung Galaxy Flip Z','Maintenance',NULL),('cellphone013',1201,'Samsung Galaxy A16 ','Operable',NULL),('LAP100',2100,'Macbook Air','Operable','Laptop'),('lap101',2100,'Lenovo Googlebook','Maintenance',NULL),('lap200',1201,'HP Omnibook X Flip','Operable',NULL),('lap201',1201,'Macboook Pro','Maintenance',NULL),('lap203',1201,'Samsung Galaxy Chromebook','Operable',NULL),('lap204',1201,'Macbook Air','Operable',NULL),('lap205',1201,'Lenovo IdeaPad Slim 3i','Maintenance',NULL),('tablet100',1201,'Lenovo Idea Tab Pro','Operable','Tablet'),('tablet101',1201,'iPad Pro','Operable',NULL),('tablet102',1201,'Samsung Galaxy Tab','Operable',NULL),('tablet103',1201,'iPad Air','Operable',NULL),('tablet200',2100,'iPad Air','Operable',NULL),('tablet201',2100,'Samsung Galaxy Tab A11+','Operable',NULL);
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
  `name` varchar(150) DEFAULT NULL,
  `address_line1` varchar(40) NOT NULL,
  `address_line2` varchar(30) DEFAULT NULL,
  `city` varchar(12) NOT NULL,
  `state` char(2) NOT NULL,
  `postal_code` char(5) NOT NULL,
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
  `userId` int NOT NULL,
  `username` varchar(155) NOT NULL,
  `password` varchar(155) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credentials`
--

LOCK TABLES `credentials` WRITE;
/*!40000 ALTER TABLE `credentials` DISABLE KEYS */;
INSERT INTO `credentials` VALUES (101,'CSanchez','cSpword123'),(102,'Amber123','AM123!@');
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
  `assetId` varchar(100) DEFAULT NULL,
  `participantId` int DEFAULT NULL,
  `checkoutDate` date DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `returnDate` date DEFAULT NULL,
  `loanStatus` varchar(15) NOT NULL,
  `reason` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`loanId`),
  KEY `fk_participantId` (`participantId`),
  KEY `fk_loan_asset` (`assetId`),
  CONSTRAINT `fk_loan_asset` FOREIGN KEY (`assetId`) REFERENCES `asset` (`assetId`),
  CONSTRAINT `fk_participantId` FOREIGN KEY (`participantId`) REFERENCES `loanee` (`participantId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` VALUES (1,'LAP100',2457,'2026-09-09','2026-09-16','2026-09-15','Available',NULL),(2,'cellphone001',2457,'2026-10-01','2026-10-31',NULL,'Checked out','Personal use'),(3,'tablet201',2457,'2026-09-19','2026-10-19',NULL,'Checked out','Education'),(4,'lap201',2457,'2026-10-01','2026-10-07','2026-10-07','Returned',NULL),(5,'cellphone002',NULL,NULL,NULL,NULL,'available',NULL),(6,'cellphone003',NULL,NULL,NULL,NULL,'available',NULL),(7,'cellphone010',NULL,NULL,NULL,NULL,'available',NULL),(8,'cellphone011',NULL,NULL,NULL,NULL,'available',NULL),(9,'cellphone012',NULL,NULL,NULL,NULL,'unavailable','Under maintenance'),(10,'cellphone013',NULL,NULL,NULL,NULL,'available',NULL),(11,'lap101',NULL,NULL,NULL,NULL,'unavailable','Under maintenance'),(12,'lap200',NULL,NULL,NULL,NULL,'available',NULL),(13,'lap203',NULL,NULL,NULL,NULL,'available',NULL),(14,'lap204',NULL,NULL,NULL,NULL,'available',NULL),(15,'lap205',NULL,NULL,NULL,NULL,'unavailable','Under maintenance'),(16,'tablet100',NULL,NULL,NULL,NULL,'available',NULL),(17,'tablet101',NULL,NULL,NULL,NULL,'available',NULL),(18,'tablet102',NULL,NULL,NULL,NULL,'available',NULL),(19,'tablet103',NULL,NULL,NULL,NULL,'available',NULL),(20,'tablet200',NULL,NULL,NULL,NULL,'available',NULL);
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanee`
--

DROP TABLE IF EXISTS `loanee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loanee` (
  `participantId` int NOT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `phoneNumber` varchar(30) DEFAULT NULL,
  `address_line1` varchar(150) NOT NULL,
  `address_line2` varchar(150) DEFAULT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(2) NOT NULL,
  `postal_code` varchar(100) NOT NULL,
  PRIMARY KEY (`participantId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanee`
--

LOCK TABLES `loanee` WRITE;
/*!40000 ALTER TABLE `loanee` DISABLE KEYS */;
INSERT INTO `loanee` VALUES (2039,NULL,NULL,NULL,'',NULL,'','',''),(2457,NULL,NULL,NULL,'',NULL,'','',''),(7830,NULL,NULL,NULL,'',NULL,'','','');
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
  `assetId` varchar(100) DEFAULT NULL,
  `issue` varchar(40) NOT NULL,
  `maintenanceStatus` varchar(155) DEFAULT NULL,
  `repairNotes` varchar(100) NOT NULL,
  `openedDate` date DEFAULT NULL,
  `completedDate` date DEFAULT NULL,
  PRIMARY KEY (`maintenanceId`),
  KEY `fk_maintenance_asset` (`assetId`),
  CONSTRAINT `fk_maintenance_asset` FOREIGN KEY (`assetId`) REFERENCES `asset` (`assetId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintenance`
--

LOCK TABLES `maintenance` WRITE;
/*!40000 ALTER TABLE `maintenance` DISABLE KEYS */;
INSERT INTO `maintenance` VALUES (1,'LAP100','No issues at this time','None','N/A','2026-09-01','2026-09-01'),(2,'cellphone012','Unresposnive screen','Scheduled maintenance','Ordered new screen','2026-10-07','2026-10-31'),(3,'lap101','Battery draines fast','Scheduled maintenance','Ordered new battery','2026-09-19','2026-10-25'),(4,'lap201','Software needs an update','Happening right now','Downloading software update','2026-10-07','2026-10-07');
/*!40000 ALTER TABLE `maintenance` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-23 11:02:11

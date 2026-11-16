-- MySQL dump 10.13  Distrib 8.0.41, for macos15 (arm64)
--
-- Host: 127.0.0.1    Database: outreachdb
-- ------------------------------------------------------
-- Server version	9.2.0

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
INSERT INTO `asset` VALUES ('DEMO-HOT-001',1209,'Netgear Nighthawk M6','CHECKED_OUT','Hotspot'),('DEMO-HOT-002',1210,'Verizon Orbic Speed 5G','CHECKED_OUT','Hotspot'),('DEMO-HOT-003',1211,'T-Mobile Franklin T10','CHECKED_OUT','Hotspot'),('DEMO-HOT-004',1212,'AT&T Turbo Hotspot 2','CHECKED_OUT','Hotspot'),('DEMO-HOT-005',1213,'Inseego MiFi X Pro','CHECKED_OUT','Hotspot'),('DEMO-HOT-006',1201,'Netgear Nighthawk M1','IN_MAINTENANCE','Hotspot'),('DEMO-HOT-007',1202,'Verizon MiFi 8800L','AVAILABLE','Hotspot'),('DEMO-HOT-008',1203,'T-Mobile Franklin T9','AVAILABLE','Hotspot'),('DEMO-HOT-009',1204,'Franklin A50 5G Hotspot','AVAILABLE','Hotspot'),('DEMO-HOT-010',1205,'Inseego MiFi M2000','AVAILABLE','Hotspot'),('DEMO-HOT-011',1206,'Netgear Nighthawk M6','AVAILABLE','Hotspot'),('DEMO-HOT-012',1207,'AT&T Turbo Hotspot 2','AVAILABLE','Hotspot'),('DEMO-HOT-013',1208,'Verizon Orbic Speed 5G','AVAILABLE','Hotspot'),('DEMO-HOT-014',1209,'T-Mobile Franklin T10','AVAILABLE','Hotspot'),('DEMO-HOT-015',1210,'Inseego MiFi X Pro','AVAILABLE','Hotspot'),('DEMO-HOT-016',1211,'Netgear Nighthawk M1','AVAILABLE','Hotspot'),('DEMO-HOT-017',1212,'Franklin A50 5G Hotspot','AVAILABLE','Hotspot'),('DEMO-HOT-018',1213,'Verizon MiFi 8800L','AVAILABLE','Hotspot'),('DEMO-HOT-019',1201,'T-Mobile Franklin T9','AVAILABLE','Hotspot'),('DEMO-HOT-020',1202,'Inseego MiFi M2000','AVAILABLE','Hotspot'),('DEMO-LAP-001',1201,'Dell Latitude 5450','CHECKED_OUT','Laptop'),('DEMO-LAP-002',1202,'Lenovo ThinkPad E14','CHECKED_OUT','Laptop'),('DEMO-LAP-003',1203,'HP ProBook 440 G10','CHECKED_OUT','Laptop'),('DEMO-LAP-004',1204,'Acer Chromebook Plus 514','CHECKED_OUT','Laptop'),('DEMO-LAP-005',1205,'ASUS Vivobook 15','CHECKED_OUT','Laptop'),('DEMO-LAP-006',1206,'Dell Inspiron 14','IN_MAINTENANCE','Laptop'),('DEMO-LAP-007',1207,'Lenovo IdeaPad Slim 3','AVAILABLE','Laptop'),('DEMO-LAP-008',1208,'HP Pavilion 15','AVAILABLE','Laptop'),('DEMO-LAP-009',1209,'Acer Aspire 5','AVAILABLE','Laptop'),('DEMO-LAP-010',1210,'Microsoft Surface Laptop Go','AVAILABLE','Laptop'),('DEMO-LAP-011',1211,'Dell Latitude 5440','AVAILABLE','Laptop'),('DEMO-LAP-012',1212,'Lenovo ThinkBook 14','AVAILABLE','Laptop'),('DEMO-LAP-013',1213,'HP EliteBook 640','AVAILABLE','Laptop'),('DEMO-LAP-014',1201,'ASUS Chromebook Plus','AVAILABLE','Laptop'),('DEMO-LAP-015',1202,'Acer Chromebook 514','AVAILABLE','Laptop'),('DEMO-LAP-016',1203,'Dell Vostro 3520','AVAILABLE','Laptop'),('DEMO-LAP-017',1204,'Lenovo V15','AVAILABLE','Laptop'),('DEMO-LAP-018',1205,'HP 255 G10','AVAILABLE','Laptop'),('DEMO-LAP-019',1206,'ASUS ExpertBook B1','AVAILABLE','Laptop'),('DEMO-LAP-020',1207,'Acer Aspire 3','AVAILABLE','Laptop'),('DEMO-TAB-001',1205,'Apple iPad 10th Generation','CHECKED_OUT','Tablet'),('DEMO-TAB-002',1206,'Samsung Galaxy Tab S9 FE','CHECKED_OUT','Tablet'),('DEMO-TAB-003',1207,'Lenovo Tab M11','CHECKED_OUT','Tablet'),('DEMO-TAB-004',1208,'Apple iPad Air','CHECKED_OUT','Tablet'),('DEMO-TAB-005',1209,'Samsung Galaxy Tab A9+','CHECKED_OUT','Tablet'),('DEMO-TAB-006',1210,'Amazon Fire HD 10','IN_MAINTENANCE','Tablet'),('DEMO-TAB-007',1211,'Lenovo Tab P12','AVAILABLE','Tablet'),('DEMO-TAB-008',1212,'Apple iPad 9th Generation','AVAILABLE','Tablet'),('DEMO-TAB-009',1213,'Samsung Galaxy Tab S6 Lite','AVAILABLE','Tablet'),('DEMO-TAB-010',1201,'Microsoft Surface Go','AVAILABLE','Tablet'),('DEMO-TAB-011',1202,'Apple iPad 10th Generation','AVAILABLE','Tablet'),('DEMO-TAB-012',1203,'Lenovo Tab M10','AVAILABLE','Tablet'),('DEMO-TAB-013',1204,'Samsung Galaxy Tab A9','AVAILABLE','Tablet'),('DEMO-TAB-014',1205,'Apple iPad Air','AVAILABLE','Tablet'),('DEMO-TAB-015',1206,'Amazon Fire HD 10','AVAILABLE','Tablet'),('DEMO-TAB-016',1207,'Samsung Galaxy Tab S9 FE','AVAILABLE','Tablet'),('DEMO-TAB-017',1208,'Lenovo Tab M11','AVAILABLE','Tablet'),('DEMO-TAB-018',1209,'Apple iPad 9th Generation','AVAILABLE','Tablet'),('DEMO-TAB-019',1210,'Samsung Galaxy Tab A9+','AVAILABLE','Tablet'),('DEMO-TAB-020',1211,'Lenovo Tab P12','AVAILABLE','Tablet');
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
INSERT INTO `center` VALUES (1000,'Dr. James R. Smith Neighborhood Center','1723 Bruton Boulevard',NULL,'Orlando','FL','32805'),(1001,'Engelwood Neighborhood Center','6123 La Costa Drive',NULL,'Orlando','FL','32807'),(1002,'John H. Jackson Neighborhood Center','1002 West Carter Street',NULL,'Orlando','FL','32805'),(1003,'Rosemont Neighborhood Center','4872 Rose Bay Drive',NULL,'Orlando','FL','32808'),(1004,'Office for Local Assistance (OLA)','595 North Primrose Drive',NULL,'Orlando','FL','32803'),(1005,'Callahan Neighborhood Center','101 North Parramore Avenue',NULL,'Orlando','FL','32801'),(1006,'Northwest Neighborhood Center','3955 W D Judge Drive',NULL,'Orlando','FL','32808'),(1007,'Dover Shores Neighborhood Center','1400 Gaston Foster Road',NULL,'Orlando','FL','32812'),(1008,'Grand Avenue Neighborhood Center','800 Grand Street',NULL,'Orlando','FL','32805'),(1009,'Ivey Lane Neighborhood Center','291 Silverton Street',NULL,'Orlando','FL','32811'),(1010,'Langford Neighborhood Center','1808 East Central Boulevard',NULL,'Orlando','FL','32803'),(1011,'Rock Lake Neighborhood Center','440 North Tampa Avenue',NULL,'Orlando','FL','32805'),(1012,'Citrus Square Neighborhood Center','5624 Hickey Drive',NULL,'Orlando','FL','32822');
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
INSERT INTO `inventory` VALUES (1201,1000),(1202,1001),(1203,1002),(1204,1003),(1205,1004),(1206,1005),(1207,1006),(1208,1007),(1209,1008),(1210,1009),(1211,1010),(1212,1011),(1213,1012);
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
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` VALUES (51,'DEMO-LAP-001',900100,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EDUCATION','EDUCATION'),(52,'DEMO-TAB-001',900100,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EDUCATION','EDUCATION'),(53,'DEMO-HOT-001',900100,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EDUCATION','EDUCATION'),(54,'DEMO-LAP-002',900101,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EMPLOYMENT','EMPLOYMENT'),(55,'DEMO-TAB-002',900101,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EMPLOYMENT','EMPLOYMENT'),(56,'DEMO-HOT-002',900101,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EMPLOYMENT','EMPLOYMENT'),(57,'DEMO-LAP-003',900102,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','PERSONAL','PERSONAL'),(58,'DEMO-TAB-003',900102,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','PERSONAL','PERSONAL'),(59,'DEMO-HOT-003',900102,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','PERSONAL','PERSONAL'),(60,'DEMO-LAP-004',900103,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EDUCATION','EDUCATION'),(61,'DEMO-TAB-004',900103,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EDUCATION','EDUCATION'),(62,'DEMO-HOT-004',900103,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EDUCATION','EDUCATION'),(63,'DEMO-LAP-005',900104,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EMPLOYMENT','EMPLOYMENT'),(64,'DEMO-TAB-005',900104,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EMPLOYMENT','EMPLOYMENT'),(65,'DEMO-HOT-005',900104,'2026-11-08','2026-11-22',NULL,'CHECKED_OUT','EMPLOYMENT','EMPLOYMENT'),(66,'DEMO-LAP-006',900105,'2026-09-01','2026-09-15','2026-09-09','CHECKED_IN','PERSONAL','PERSONAL'),(67,'DEMO-TAB-006',900105,'2026-09-01','2026-09-15','2026-09-09','CHECKED_IN','PERSONAL','PERSONAL'),(68,'DEMO-HOT-006',900105,'2026-09-01','2026-09-15','2026-09-09','CHECKED_IN','PERSONAL','PERSONAL'),(69,'DEMO-LAP-007',900106,'2026-09-03','2026-09-17','2026-09-11','CHECKED_IN','EDUCATION','EDUCATION'),(70,'DEMO-TAB-007',900106,'2026-09-03','2026-09-17','2026-09-11','CHECKED_IN','EDUCATION','EDUCATION'),(71,'DEMO-HOT-007',900106,'2026-09-03','2026-09-17','2026-09-11','CHECKED_IN','EDUCATION','EDUCATION'),(72,'DEMO-LAP-008',900107,'2026-09-05','2026-09-19','2026-09-13','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(73,'DEMO-TAB-008',900107,'2026-09-05','2026-09-19','2026-09-13','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(74,'DEMO-HOT-008',900107,'2026-09-05','2026-09-19','2026-09-13','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(75,'DEMO-LAP-009',900108,'2026-09-07','2026-09-21','2026-09-15','CHECKED_IN','EDUCATION','EDUCATION'),(76,'DEMO-TAB-009',900108,'2026-09-07','2026-09-21','2026-09-15','CHECKED_IN','EDUCATION','EDUCATION'),(77,'DEMO-HOT-009',900108,'2026-09-07','2026-09-21','2026-09-15','CHECKED_IN','EDUCATION','EDUCATION'),(78,'DEMO-LAP-010',900109,'2026-09-09','2026-09-23','2026-09-17','CHECKED_IN','PERSONAL','PERSONAL'),(79,'DEMO-TAB-010',900109,'2026-09-09','2026-09-23','2026-09-17','CHECKED_IN','PERSONAL','PERSONAL'),(80,'DEMO-HOT-010',900109,'2026-09-09','2026-09-23','2026-09-17','CHECKED_IN','PERSONAL','PERSONAL'),(81,'DEMO-LAP-011',900110,'2026-09-11','2026-09-25','2026-09-19','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(82,'DEMO-TAB-011',900110,'2026-09-11','2026-09-25','2026-09-19','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(83,'DEMO-HOT-011',900110,'2026-09-11','2026-09-25','2026-09-19','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(84,'DEMO-LAP-012',900111,'2026-09-13','2026-09-27','2026-09-21','CHECKED_IN','EDUCATION','EDUCATION'),(85,'DEMO-TAB-012',900111,'2026-09-13','2026-09-27','2026-09-21','CHECKED_IN','EDUCATION','EDUCATION'),(86,'DEMO-HOT-012',900111,'2026-09-13','2026-09-27','2026-09-21','CHECKED_IN','EDUCATION','EDUCATION'),(87,'DEMO-LAP-013',900112,'2026-09-15','2026-09-29','2026-09-23','CHECKED_IN','PERSONAL','PERSONAL'),(88,'DEMO-TAB-013',900112,'2026-09-15','2026-09-29','2026-09-23','CHECKED_IN','PERSONAL','PERSONAL'),(89,'DEMO-HOT-013',900112,'2026-09-15','2026-09-29','2026-09-23','CHECKED_IN','PERSONAL','PERSONAL'),(90,'DEMO-LAP-014',900113,'2026-09-17','2026-10-01','2026-09-25','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(91,'DEMO-TAB-014',900113,'2026-09-17','2026-10-01','2026-09-25','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(92,'DEMO-HOT-014',900113,'2026-09-17','2026-10-01','2026-09-25','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(93,'DEMO-LAP-015',900114,'2026-09-19','2026-10-03','2026-09-27','CHECKED_IN','EDUCATION','EDUCATION'),(94,'DEMO-TAB-015',900114,'2026-09-19','2026-10-03','2026-09-27','CHECKED_IN','EDUCATION','EDUCATION'),(95,'DEMO-HOT-015',900114,'2026-09-19','2026-10-03','2026-09-27','CHECKED_IN','EDUCATION','EDUCATION'),(96,'DEMO-LAP-016',900115,'2026-09-21','2026-10-05','2026-09-29','CHECKED_IN','PERSONAL','PERSONAL'),(97,'DEMO-TAB-016',900115,'2026-09-21','2026-10-05','2026-09-29','CHECKED_IN','PERSONAL','PERSONAL'),(98,'DEMO-HOT-016',900115,'2026-09-21','2026-10-05','2026-09-29','CHECKED_IN','PERSONAL','PERSONAL'),(99,'DEMO-LAP-017',900116,'2026-09-23','2026-10-07','2026-10-01','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(100,'DEMO-TAB-017',900116,'2026-09-23','2026-10-07','2026-10-01','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(101,'DEMO-HOT-017',900116,'2026-09-23','2026-10-07','2026-10-01','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(102,'DEMO-LAP-018',900117,'2026-09-25','2026-10-09','2026-10-03','CHECKED_IN','EDUCATION','EDUCATION'),(103,'DEMO-TAB-018',900117,'2026-09-25','2026-10-09','2026-10-03','CHECKED_IN','EDUCATION','EDUCATION'),(104,'DEMO-HOT-018',900117,'2026-09-25','2026-10-09','2026-10-03','CHECKED_IN','EDUCATION','EDUCATION'),(105,'DEMO-LAP-019',900118,'2026-09-27','2026-10-11','2026-10-05','CHECKED_IN','PERSONAL','PERSONAL'),(106,'DEMO-TAB-019',900118,'2026-09-27','2026-10-11','2026-10-05','CHECKED_IN','PERSONAL','PERSONAL'),(107,'DEMO-HOT-019',900118,'2026-09-27','2026-10-11','2026-10-05','CHECKED_IN','PERSONAL','PERSONAL'),(108,'DEMO-LAP-020',900119,'2026-09-29','2026-10-13','2026-10-07','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(109,'DEMO-TAB-020',900119,'2026-09-29','2026-10-13','2026-10-07','CHECKED_IN','EMPLOYMENT','EMPLOYMENT'),(110,'DEMO-HOT-020',900119,'2026-09-29','2026-10-13','2026-10-07','CHECKED_IN','EMPLOYMENT','EMPLOYMENT');
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
) ENGINE=InnoDB AUTO_INCREMENT=900120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanee`
--

LOCK TABLES `loanee` WRITE;
/*!40000 ALTER TABLE `loanee` DISABLE KEYS */;
INSERT INTO `loanee` VALUES (900100,'Maria','Rivera','407-555-0100','100 Demo Street',NULL,'Orlando','FL','32801'),(900101,'James','Carter','407-555-0101','101 Sample Avenue',NULL,'Orlando','FL','32803'),(900102,'Alicia','Brooks','407-555-0102','102 Example Road',NULL,'Orlando','FL','32805'),(900103,'Daniel','Thompson','407-555-0103','103 Cypress Lane',NULL,'Orlando','FL','32807'),(900104,'Sophia','Martinez','407-555-0104','104 Orange Blossom Trail',NULL,'Orlando','FL','32808'),(900105,'Ethan','Walker','407-555-0105','105 Pine Street',NULL,'Orlando','FL','32811'),(900106,'Olivia','Bennett','407-555-0106','106 Lakeview Drive',NULL,'Orlando','FL','32812'),(900107,'Noah','Hughes','407-555-0107','107 Magnolia Avenue',NULL,'Orlando','FL','32822'),(900108,'Isabella','Flores','407-555-0108','108 Gardenia Street',NULL,'Orlando','FL','32801'),(900109,'Mason','Price','407-555-0109','109 Oak Ridge Road',NULL,'Orlando','FL','32803'),(900110,'Mia','Cooper','407-555-0110','110 Palm Way',NULL,'Orlando','FL','32805'),(900111,'Lucas','Reed','407-555-0111','111 Meadowbrook Avenue',NULL,'Orlando','FL','32807'),(900112,'Amelia','Bailey','407-555-0112','112 Willow Bend',NULL,'Orlando','FL','32808'),(900113,'Logan','Kelly','407-555-0113','113 Sunset Drive',NULL,'Orlando','FL','32811'),(900114,'Harper','Ward','407-555-0114','114 Lake Shore Road',NULL,'Orlando','FL','32812'),(900115,'Elijah','Perry','407-555-0115','115 Birch Avenue',NULL,'Orlando','FL','32822'),(900116,'Evelyn','Long','407-555-0116','116 Highland Street',NULL,'Orlando','FL','32801'),(900117,'Aiden','Gray','407-555-0117','117 Riverwalk Lane',NULL,'Orlando','FL','32803'),(900118,'Charlotte','James','407-555-0118','118 Spring Street',NULL,'Orlando','FL','32805'),(900119,'Benjamin','Scott','407-555-0119','119 Parkside Avenue',NULL,'Orlando','FL','32807');
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintenance`
--

LOCK TABLES `maintenance` WRITE;
/*!40000 ALTER TABLE `maintenance` DISABLE KEYS */;
INSERT INTO `maintenance` VALUES (10,'DEMO-LAP-006','Battery not holding charge','IN_PROGRESS','Replacement battery requested','2026-11-10',NULL),(11,'DEMO-TAB-006','Damaged charging port','IN_PROGRESS','Device sent for charging port inspection','2026-11-11',NULL),(12,'DEMO-HOT-006','Intermittent network connection','IN_PROGRESS','Network diagnostics in progress','2026-11-12',NULL);
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

-- Dump completed on 2026-11-15 22:09:35

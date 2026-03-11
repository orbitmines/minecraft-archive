-- MySQL dump 10.13  Distrib 5.7.18, for Win64 (x86_64)
--
-- Host: localhost    Database: OrbitMines
-- ------------------------------------------------------
-- Server version	5.7.18-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `2fa`
--

DROP TABLE IF EXISTS `2fa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `2fa` (
  `UUID` varchar(36) DEFAULT NULL,
  `Secret` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `2fa`
--

LOCK TABLES `2fa` WRITE;
/*!40000 ALTER TABLE `2fa` DISABLE KEYS */;
-- OrbitMines (Public) Archive: Removed '2fa' for privacy reasons.
/*!40000 ALTER TABLE `2fa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `achievements`
--

DROP TABLE IF EXISTS `achievements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `achievements` (
  `UUID` varchar(36) DEFAULT NULL,
  `Server` varchar(32) DEFAULT NULL,
  `Achievement` varchar(32) DEFAULT NULL,
  `Completed` tinyint(4) DEFAULT NULL,
  `Progress` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `achievements`
--

LOCK TABLES `achievements` WRITE;
/*!40000 ALTER TABLE `achievements` DISABLE KEYS */;
INSERT INTO `achievements` VALUES ('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','SURVIVAL','SALESMAN',1,20),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','SURVIVAL','CROWDED',0,2),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','SURVIVAL','DIAMONDS',0,36),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','SURVIVAL','LOADS_OF_EXPERIENCE',0,7),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','HUB','ORBITMINES_SUPPORTER',1,0),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','HUB','DISCORD_LINK',1,0),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','HUB','ORBITMINES_SUPPORTER',1,0),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','HUB','DISCORD_LINK',1,0),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','SURVIVAL','LOADS_OF_EXPERIENCE',0,2),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','SURVIVAL','CROWDED',0,2),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','SURVIVAL','SALESMAN',0,90),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','KITPVP','MERLIN',1,0),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','KITPVP','DRUNKEN_FOOL',1,0),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','KITPVP','DRUNKEN_FOOL',1,0),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','KITPVP','THOR',0,2),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','KITPVP','KINGSLAYER',0,1),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','KITPVP','HUNTER',0,1);
/*!40000 ALTER TABLE `achievements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chestshops`
--

DROP TABLE IF EXISTS `chestshops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chestshops` (
  `Server` varchar(32) DEFAULT NULL,
  `Id` bigint(20) DEFAULT NULL,
  `Owner` varchar(36) DEFAULT NULL,
  `Location` varchar(100) DEFAULT NULL,
  `MaterialId` varchar(32) DEFAULT NULL,
  `Type` varchar(32) DEFAULT NULL,
  `Amount` smallint(6) DEFAULT NULL,
  `Price` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chestshops`
--

LOCK TABLES `chestshops` WRITE;
/*!40000 ALTER TABLE `chestshops` DISABLE KEYS */;
INSERT INTO `chestshops` VALUES ('SURVIVAL',7,'33ee168b-5c2c-42c5-b3b2-d841ceb76b70','world|-15.0|93.0|3.0|0.0|0.0','STONE','BUY',1,10);
/*!40000 ALTER TABLE `chestshops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discord`
--

DROP TABLE IF EXISTS `discord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discord` (
  `UUID` varchar(36) DEFAULT NULL,
  `Id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discord`
--

LOCK TABLES `discord` WRITE;
/*!40000 ALTER TABLE `discord` DISABLE KEYS */;
INSERT INTO `discord` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',215213594202079232);
/*!40000 ALTER TABLE `discord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discordgroup`
--

DROP TABLE IF EXISTS `discordgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discordgroup` (
  `UUID` varchar(36) DEFAULT NULL,
  `CategoryId` bigint(20) DEFAULT NULL,
  `TextChannelId` bigint(20) DEFAULT NULL,
  `VoiceChannelId` bigint(20) DEFAULT NULL,
  `RoleId` bigint(20) DEFAULT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `Color` varchar(32) DEFAULT NULL,
  `Members` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discordgroup`
--

LOCK TABLES `discordgroup` WRITE;
/*!40000 ALTER TABLE `discordgroup` DISABLE KEYS */;
INSERT INTO `discordgroup` VALUES ('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',477187345469145089,477187346618646538,477187347365101578,477187344882204683,'Rush_matthias_SQUAD','BLUE',''),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',487334554336755723,487334555506835476,487334556148563970,487334553921257482,'O_o_Fadi_o_O_SQUAD','YELLOW','');
/*!40000 ALTER TABLE `discordgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discordgroupdata`
--

DROP TABLE IF EXISTS `discordgroupdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discordgroupdata` (
  `UUID` varchar(36) DEFAULT NULL,
  `Selected` varchar(36) DEFAULT NULL,
  `SentInvites` text,
  `Invites` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discordgroupdata`
--

LOCK TABLES `discordgroupdata` WRITE;
/*!40000 ALTER TABLE `discordgroupdata` DISABLE KEYS */;
INSERT INTO `discordgroupdata` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','33ee168b-5c2c-42c5-b3b2-d841ceb76b70','',''),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','','');
/*!40000 ALTER TABLE `discordgroupdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discordsquad`
--

DROP TABLE IF EXISTS `discordsquad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discordsquad` (
  `UUID` varchar(36) DEFAULT NULL,
  `CategoryId` bigint(20) DEFAULT NULL,
  `TextChannelId` bigint(20) DEFAULT NULL,
  `VoiceChannelId` bigint(20) DEFAULT NULL,
  `RoleId` bigint(20) DEFAULT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `Color` varchar(32) DEFAULT NULL,
  `Members` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discordsquad`
--

LOCK TABLES `discordsquad` WRITE;
/*!40000 ALTER TABLE `discordsquad` DISABLE KEYS */;
/*!40000 ALTER TABLE `discordsquad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donations`
--

DROP TABLE IF EXISTS `donations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donations` (
  `UUID` varchar(36) DEFAULT NULL,
  `PackageID` int(11) DEFAULT NULL,
  `AmountSpent` int(11) DEFAULT NULL,
  `Currency` varchar(16) DEFAULT NULL,
  `Date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donations`
--

LOCK TABLES `donations` WRITE;
/*!40000 ALTER TABLE `donations` DISABLE KEYS */;
INSERT INTO `donations` VALUES ('01a5412b-275b-4f42-aea9-1bef163210b9',16,40,NULL,'2018-02-27 12:46:32'),('a8933f18-3825-4453-9c8a-bdaba7db8334',19,18,NULL,'2018-02-27 12:46:32'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',4,50,NULL,'2018-02-27 12:47:32'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',2,5,'EUR','2018-07-02 12:56:00'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',2,5,'EUR','2018-07-02 12:56:00'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',6,5,'EUR','2018-07-02 13:55:00'),('7d84bdbf-c169-433f-82c1-7ee71f7d29fa',1,1,'EUR','2018-07-09 13:23:00'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',11,25,'EUR','2018-07-19 23:54:00'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',2,5,'EUR','2018-07-20 09:46:00'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',6,5,'EUR','2018-07-20 09:50:00'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',9,15,'EUR','2018-07-20 09:54:00'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',11,25,'EUR','2018-07-20 10:15:00'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',11,25,'EUR','2018-07-20 10:17:00'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',4,25,'EUR','2018-08-31 21:40:00');
/*!40000 ALTER TABLE `donations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends` (
  `UUID` varchar(36) DEFAULT NULL,
  `Friends` text,
  `Favorite` text,
  `SentInvites` text,
  `Invites` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','','','',''),('01a5412b-275b-4f42-aea9-1bef163210b9','','','',''),('69626b0c-6910-49b0-9a1d-8cf83141d60a','','','',''),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','','','',''),('a8933f18-3825-4453-9c8a-bdaba7db8334','','','',''),('6625bd11-1a1f-4bd0-b46f-a49fb9c71e7d','','','','');
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ips`
--

DROP TABLE IF EXISTS `ips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ips` (
  `UUID` varchar(36) DEFAULT NULL,
  `CurrentServer` varchar(32) DEFAULT NULL,
  `LastIp` varchar(32) DEFAULT NULL,
  `LastLogin` datetime DEFAULT NULL,
  `History` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ips`
--

LOCK TABLES `ips` WRITE;
/*!40000 ALTER TABLE `ips` DISABLE KEYS */;
-- OrbitMines (Public) Archive: Removed 'ips' for privacy reasons.
/*!40000 ALTER TABLE `ips` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kitpvpkitstats`
--

DROP TABLE IF EXISTS `kitpvpkitstats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kitpvpkitstats` (
  `UUID` varchar(36) DEFAULT NULL,
  `KitId` bigint(20) DEFAULT NULL,
  `UnlockedLevel` int(11) DEFAULT NULL,
  `Kills` int(11) DEFAULT NULL,
  `Deaths` int(11) DEFAULT NULL,
  `BestStreak` int(11) DEFAULT NULL,
  `DamageDealt` float(12,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kitpvpkitstats`
--

LOCK TABLES `kitpvpkitstats` WRITE;
/*!40000 ALTER TABLE `kitpvpkitstats` DISABLE KEYS */;
INSERT INTO `kitpvpkitstats` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',0,3,4,2,2,12303.40),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',1,3,1,2,1,12303.40),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',2,3,4,2,2,12358.86),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',3,3,0,1,0,12303.40),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',4,3,0,0,0,12303.40),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',5,3,0,1,0,12303.40),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',6,3,0,2,0,12303.40),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',7,3,0,1,0,12303.40),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',8,3,0,1,0,12303.40),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',0,3,1,4,1,12314.10),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',1,3,0,2,0,12303.40),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',2,3,0,3,0,12303.40),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',3,3,0,0,0,12303.40),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',4,3,0,0,0,12303.40),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',5,3,0,1,0,12303.40),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',6,3,0,0,0,12303.40),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',7,3,0,0,0,12303.40),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',8,3,1,1,1,12324.14),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',9,3,89,3,24,12685.61),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',9,0,3,83,1,12354.15);
/*!40000 ALTER TABLE `kitpvpkitstats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kitpvpplayers`
--

DROP TABLE IF EXISTS `kitpvpplayers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kitpvpplayers` (
  `UUID` varchar(36) DEFAULT NULL,
  `Coins` int(11) DEFAULT NULL,
  `Experience` int(11) DEFAULT NULL,
  `Kills` int(11) DEFAULT NULL,
  `Deaths` int(11) DEFAULT NULL,
  `BestStreak` int(11) DEFAULT NULL,
  `DamageDealt` float(12,2) DEFAULT NULL,
  `LastSelectedId` bigint(20) DEFAULT NULL,
  `LastSelectedLevel` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kitpvpplayers`
--

LOCK TABLES `kitpvpplayers` WRITE;
/*!40000 ALTER TABLE `kitpvpplayers` DISABLE KEYS */;
INSERT INTO `kitpvpplayers` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',7142,2350,98,15,24,24.19,9,3),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',200,40,5,94,1,10.70,0,3);
/*!40000 ALTER TABLE `kitpvpplayers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loot`
--

DROP TABLE IF EXISTS `loot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loot` (
  `UUID` varchar(36) DEFAULT NULL,
  `Loot` varchar(32) DEFAULT NULL,
  `Rarity` varchar(32) DEFAULT NULL,
  `Count` int(11) DEFAULT NULL,
  `Description` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loot`
--

LOCK TABLES `loot` WRITE;
/*!40000 ALTER TABLE `loot` DISABLE KEYS */;
INSERT INTO `loot` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','BUYCRAFT_VOUCHER','LEGENDARY',20,'&9&l&o#1 Voter (June 2018)'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','BUYCRAFT_VOUCHER','LEGENDARY',20,'&9&l&o#1 Voter (June 2018)'),('69626b0c-6910-49b0-9a1d-8cf83141d60a','BUYCRAFT_VOUCHER','LEGENDARY',40,'&9&l&o#1 Voter (June 2018)'),('01a5412b-275b-4f42-aea9-1bef163210b9','BUYCRAFT_VOUCHER','LEGENDARY',16,'&9&l&o#2 Voter (June 2018)'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','BUYCRAFT_VOUCHER','LEGENDARY',8,'&9&l&o#3 Voter (June 2018)'),('a8933f18-3825-4453-9c8a-bdaba7db8334','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('9b6d3590-a9f5-4e33-a3a5-d45571f22b10','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('01a5412b-275b-4f42-aea9-1bef163210b9','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('69626b0c-6910-49b0-9a1d-8cf83141d60a','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('69626b0c-6910-49b0-9a1d-8cf83141d60a','BUYCRAFT_VOUCHER','LEGENDARY',40,'&9&l&o#1 Voter (June 2018)'),('01a5412b-275b-4f42-aea9-1bef163210b9','BUYCRAFT_VOUCHER','LEGENDARY',16,'&9&l&o#2 Voter (June 2018)'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','BUYCRAFT_VOUCHER','LEGENDARY',8,'&9&l&o#3 Voter (June 2018)'),('a8933f18-3825-4453-9c8a-bdaba7db8334','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('9b6d3590-a9f5-4e33-a3a5-d45571f22b10','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('01a5412b-275b-4f42-aea9-1bef163210b9','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('69626b0c-6910-49b0-9a1d-8cf83141d60a','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('69626b0c-6910-49b0-9a1d-8cf83141d60a','BUYCRAFT_VOUCHER','LEGENDARY',40,'&9&l&o#1 Voter (June 2018)'),('01a5412b-275b-4f42-aea9-1bef163210b9','BUYCRAFT_VOUCHER','LEGENDARY',16,'&9&l&o#2 Voter (June 2018)'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','BUYCRAFT_VOUCHER','LEGENDARY',8,'&9&l&o#3 Voter (June 2018)'),('a8933f18-3825-4453-9c8a-bdaba7db8334','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('9b6d3590-a9f5-4e33-a3a5-d45571f22b10','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('01a5412b-275b-4f42-aea9-1bef163210b9','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('69626b0c-6910-49b0-9a1d-8cf83141d60a','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('69626b0c-6910-49b0-9a1d-8cf83141d60a','BUYCRAFT_VOUCHER','LEGENDARY',40,'&9&l&o#1 Voter (June 2018)'),('01a5412b-275b-4f42-aea9-1bef163210b9','BUYCRAFT_VOUCHER','LEGENDARY',16,'&9&l&o#2 Voter (June 2018)'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','BUYCRAFT_VOUCHER','LEGENDARY',8,'&9&l&o#3 Voter (June 2018)'),('a8933f18-3825-4453-9c8a-bdaba7db8334','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('9b6d3590-a9f5-4e33-a3a5-d45571f22b10','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('01a5412b-275b-4f42-aea9-1bef163210b9','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('69626b0c-6910-49b0-9a1d-8cf83141d60a','SOLARS','RARE',400,'&a&l&oCommunity Goal June 2018 (Votes)'),('69626b0c-6910-49b0-9a1d-8cf83141d60a','BUYCRAFT_VOUCHER','LEGENDARY',5,'&9&l&o#1 Voter (September 2018)'),('01a5412b-275b-4f42-aea9-1bef163210b9','BUYCRAFT_VOUCHER','LEGENDARY',3,'&9&l&o#2 Voter (September 2018)'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','BUYCRAFT_VOUCHER','LEGENDARY',2,'&9&l&o#3 Voter (September 2018)'),('6625bd11-1a1f-4bd0-b46f-a49fb9c71e7d','SOLARS','RARE',250,'&a&l&oWelcome to &7&l&oOrbit&8&l&oMines&a&l&o!'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','PRISMS','RARE',6500,'&d&l&oAchievement (Merlin)'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','SOLARS','RARE',100,'&d&l&oAchievement (Merlin)'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','PRISMS','EPIC',11000,'&d&l&oAchievement (Drunken Fool)'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','SOLARS','EPIC',125,'&d&l&oAchievement (Drunken Fool)'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','PRISMS','EPIC',11000,'&d&l&oAchievement (Drunken Fool)'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','SOLARS','EPIC',125,'&d&l&oAchievement (Drunken Fool)');
/*!40000 ALTER TABLE `loot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maps`
--

DROP TABLE IF EXISTS `maps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maps` (
  `WorldName` varchar(32) DEFAULT NULL,
  `WorldGenerator` varchar(32) DEFAULT NULL,
  `Name` varchar(32) DEFAULT NULL,
  `Type` varchar(32) DEFAULT NULL,
  `Server` varchar(32) DEFAULT NULL,
  `Enabled` tinyint(1) DEFAULT NULL,
  `Authors` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maps`
--

LOCK TABLES `maps` WRITE;
/*!40000 ALTER TABLE `maps` DISABLE KEYS */;
INSERT INTO `maps` VALUES ('Lobby_HUB','VOID','Hub','LOBBY','HUB',1,'ReddReaperz'),('Lobby_SURVIVAL','VOID','Survival','LOBBY','SURVIVAL',1,'ReddReaperz'),('Lobby_KITPVP','VOID','KitPvP','LOBBY','KITPVP',1,'Joep01'),('KitPvP_Desert','VOID','Desert','GAMEMAP','KITPVP',1,'Alderius');
/*!40000 ALTER TABLE `maps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodloot`
--

DROP TABLE IF EXISTS `periodloot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `periodloot` (
  `UUID` varchar(36) DEFAULT NULL,
  `Daily` bigint(20) DEFAULT NULL,
  `Monthly` bigint(20) DEFAULT NULL,
  `MonthlyVIP` bigint(20) DEFAULT NULL,
  `SurvivalBackCharges` bigint(20) DEFAULT NULL,
  `SurvivalSpawnerItem` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodloot`
--

LOCK TABLES `periodloot` WRITE;
/*!40000 ALTER TABLE `periodloot` DISABLE KEYS */;
INSERT INTO `periodloot` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',1539120537,1538658669,1538658670,1538665721,1538665719),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',1544278484,1544278488,1532082224,1533974172,1533974498),('6625bd11-1a1f-4bd0-b46f-a49fb9c71e7d',0,0,0,0,0);
/*!40000 ALTER TABLE `periodloot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `players` (
  `UUID` varchar(36) DEFAULT NULL,
  `Name` varchar(16) DEFAULT NULL,
  `Nick` varchar(32) DEFAULT NULL,
  `StaffRank` varchar(32) DEFAULT NULL,
  `VipRank` varchar(32) DEFAULT NULL,
  `FirstLogin` datetime DEFAULT NULL,
  `Language` varchar(32) DEFAULT NULL,
  `Scoreboard` tinyint(4) DEFAULT NULL,
  `PrivateMessages` varchar(32) DEFAULT NULL,
  `PlayerVisibility` varchar(32) DEFAULT NULL,
  `Gadgets` varchar(32) DEFAULT NULL,
  `Stats` varchar(32) DEFAULT NULL,
  `Silent` tinyint(1) DEFAULT NULL,
  `Solars` int(11) DEFAULT NULL,
  `Prisms` int(11) DEFAULT NULL,
  `MonthlyBonus` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
INSERT INTO `players` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','O_o_Fadi_o_O','','OWNER','EMERALD','2000-01-01 00:00:00','ENGLISH',1,'ENABLED','ENABLED','ENABLED','ONLY_FRIENDS',0,91000,413700,'null'),('9b6d3590-a9f5-4e33-a3a5-d45571f22b10','sharewoods',NULL,'MODERATOR','NONE','2000-01-01 00:00:00','ENGLISH',1,'ENABLED','ENABLED','ENABLED','ONLY_FRIENDS',0,100000,0,'null'),('01a5412b-275b-4f42-aea9-1bef163210b9','eekhoorn2000',NULL,'MODERATOR','NONE','2000-01-01 00:00:00','ENGLISH',1,'ENABLED','ENABLED','ENABLED','ONLY_FRIENDS',0,100000,0,'null'),('a8933f18-3825-4453-9c8a-bdaba7db8334','Alderius',NULL,'MODERATOR','NONE','2000-01-01 00:00:00','ENGLISH',1,'ENABLED','ENABLED','ENABLED','ONLY_FRIENDS',0,100000,0,'null'),('69626b0c-6910-49b0-9a1d-8cf83141d60a','playwarrior',NULL,'DEVELOPER','NONE','2000-01-01 00:00:00','ENGLISH',1,'ENABLED','ENABLED','ENABLED','ONLY_FRIENDS',0,100000,0,NULL),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','Rush_matthias','','NONE','NONE','2000-01-01 00:00:00','ENGLISH',1,'ENABLED','ENABLED','ENABLED','ONLY_FRIENDS',0,200,36000,NULL),('6625bd11-1a1f-4bd0-b46f-a49fb9c71e7d','Nielsi400','','NONE','NONE','2000-01-01 00:00:00','ENGLISH',1,'ENABLED','ENABLED','ENABLED','ONLY_FRIENDS',0,0,0,NULL),('f1cd7f08-4762-482e-88f7-a237beba1a89','joep01','','DEVELOPER','NONE','2000-01-01 00:00:00','ENGLISH',1,'ENABLED','ENABLED','ENABLED','ONLY_FRIENDS',0,100000,100000,'null');
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playtime`
--

DROP TABLE IF EXISTS `playtime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playtime` (
  `UUID` varchar(36) DEFAULT NULL,
  `KITPVP` bigint(20) DEFAULT NULL,
  `PRISON` bigint(20) DEFAULT NULL,
  `CREATIVE` bigint(20) DEFAULT NULL,
  `HUB` bigint(20) DEFAULT NULL,
  `SURVIVAL` bigint(20) DEFAULT NULL,
  `SKYBLOCK` bigint(20) DEFAULT NULL,
  `FOG` bigint(20) DEFAULT NULL,
  `MINIGAMES` bigint(20) DEFAULT NULL,
  `UHSURVIVAL` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playtime`
--

LOCK TABLES `playtime` WRITE;
/*!40000 ALTER TABLE `playtime` DISABLE KEYS */;
INSERT INTO `playtime` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',77394,0,0,47159,24852,0,0,0,0),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',40574,0,0,14278,4760,0,0,0,0),('01a5412b-275b-4f42-aea9-1bef163210b9',0,0,0,0,0,0,0,0,0),('9b6d3590-a9f5-4e33-a3a5-d45571f22b10',0,0,0,0,0,0,0,0,0),('69626b0c-6910-49b0-9a1d-8cf83141d60a',0,0,0,0,0,0,0,0,0),('a8933f18-3825-4453-9c8a-bdaba7db8334',0,0,0,0,0,0,0,0,0),('6625bd11-1a1f-4bd0-b46f-a49fb9c71e7d',0,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `playtime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `punishments`
--

DROP TABLE IF EXISTS `punishments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `punishments` (
  `UUID` varchar(36) DEFAULT NULL,
  `Offence` varchar(32) DEFAULT NULL,
  `Severity` varchar(32) DEFAULT NULL,
  `From` varchar(32) DEFAULT NULL,
  `To` varchar(32) DEFAULT NULL,
  `PunishedBy` varchar(36) DEFAULT NULL,
  `Reason` varchar(128) DEFAULT NULL,
  `Pardoned` tinyint(1) DEFAULT NULL,
  `PardonedOn` varchar(32) DEFAULT NULL,
  `PardonedBy` varchar(36) DEFAULT NULL,
  `PardonedReason` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `punishments`
--

LOCK TABLES `punishments` WRITE;
/*!40000 ALTER TABLE `punishments` DISABLE KEYS */;
-- OrbitMines (Public) Archive: Removed 'punishments' for privacy reasons.
/*!40000 ALTER TABLE `punishments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reports` (
  `UUID` varchar(36) DEFAULT NULL,
  `Server` varchar(32) DEFAULT NULL,
  `ReportedOn` varchar(32) DEFAULT NULL,
  `ReportedBy` varchar(36) DEFAULT NULL,
  `Reason` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
-- OrbitMines (Public) Archive: Removed 'reports' for privacy reasons.
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serverdata`
--

DROP TABLE IF EXISTS `serverdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `serverdata` (
  `Server` varchar(32) DEFAULT NULL,
  `Type` varchar(32) DEFAULT NULL,
  `Data` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serverdata`
--

LOCK TABLES `serverdata` WRITE;
/*!40000 ALTER TABLE `serverdata` DISABLE KEYS */;
INSERT INTO `serverdata` VALUES ('BUNGEE','MOTD','&8&lOrbit&7&lMines &8- &9&lTest Server'),('BUNGEE','TITLES','&8&lOrbit&7&lMines|&a&lNEW SURVIVAL UPDATE~&8&lOrbit&7&lMines|&c&lNETHER &8&l& &7&lEND &a&lCLAIMING'),('SURVIVAL','TELEPORTABLE','100'),('SURVIVAL','NEXT_ID','84'),('BUNGEE','LAST_VOTE_MONTH','December2018'),('HUB','LATEST_PATCH','v1.2.0'),('SURVIVAL','LATEST_PATCH','v1.2.0'),('SURVIVAL','NEXT_SHOP_ID','8'),('SURVIVAL','HOLOGRAM|newplayer','Lobby_SURVIVAL|24.505734997790924|106.0|-152.5731685704473|42.897736|90.0~&7&lWelcome to;&8&lOrbit&7&lMines &a&lSurvival;&7Join the world with &a&l/region random&r&7.'),('SURVIVAL','NETHER_RESET','1549721129542'),('SURVIVAL','END_RESET','1548605754992'),('DISCORD','SERVER_ID','473472016092233746'),('KITPVP','LATEST_PATCH','v1.1.0');
/*!40000 ALTER TABLE `serverdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servers`
--

DROP TABLE IF EXISTS `servers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servers` (
  `IP` varchar(15) DEFAULT NULL,
  `Port` smallint(6) DEFAULT NULL,
  `Server` varchar(32) DEFAULT NULL,
  `Status` varchar(32) DEFAULT NULL,
  `Players` int(11) DEFAULT NULL,
  `MaxPlayers` int(11) DEFAULT NULL,
  `LastUpdate` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servers`
--

LOCK TABLES `servers` WRITE;
/*!40000 ALTER TABLE `servers` DISABLE KEYS */;
INSERT INTO `servers` VALUES ('127.0.0.1',25566,'HUB','OFFLINE',0,100,1541952691157),('127.0.0.1',25567,'SURVIVAL','OFFLINE',0,100,1541952486976),('127.0.0.1',25568,'KITPVP','ONLINE',0,100,1544295710362);
/*!40000 ALTER TABLE `servers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statsonline`
--

DROP TABLE IF EXISTS `statsonline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statsonline` (
  `Time` bigint(20) DEFAULT NULL,
  `Total` int(11) DEFAULT NULL,
  `OWNER` int(11) DEFAULT NULL,
  `ADMIN` int(11) DEFAULT NULL,
  `DEVELOPER` int(11) DEFAULT NULL,
  `MODERATOR` int(11) DEFAULT NULL,
  `BUILDER` int(11) DEFAULT NULL,
  `EMERALD` int(11) DEFAULT NULL,
  `DIAMOND` int(11) DEFAULT NULL,
  `GOLD` int(11) DEFAULT NULL,
  `IRON` int(11) DEFAULT NULL,
  `USER` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statsonline`
--

LOCK TABLES `statsonline` WRITE;
/*!40000 ALTER TABLE `statsonline` DISABLE KEYS */;
INSERT INTO `statsonline` VALUES (1534849542468,0,0,0,0,0,0,0,0,0,0,0),(1534944192405,0,0,0,0,0,0,0,0,0,0,0),(1534944491909,1,1,0,0,0,0,0,0,0,0,0),(1534944692505,0,0,0,0,0,0,0,0,0,0,0),(1535222015982,0,0,0,0,0,0,0,0,0,0,0),(1535222315791,1,1,0,0,0,0,0,0,0,0,0),(1535313455548,0,0,0,0,0,0,0,0,0,0,0),(1535314291149,0,0,0,0,0,0,0,0,0,0,0),(1535314591262,4,4,0,0,0,0,0,0,0,0,0),(1535395355295,0,0,0,0,0,0,0,0,0,0,0),(1535395655374,0,0,0,0,0,0,0,0,0,0,0),(1535395955724,1,1,0,0,0,0,0,0,0,0,0),(1535397455698,1,1,0,0,0,0,0,0,0,0,0),(1535397755254,0,0,0,0,0,0,0,0,0,0,0),(1535398056245,2,1,0,0,0,0,1,0,0,0,0),(1535398355634,2,1,0,0,0,0,1,0,0,0,0),(1535453526322,0,0,0,0,0,0,0,0,0,0,0),(1535453827260,1,1,0,0,0,0,0,0,0,0,0),(1535455026839,1,1,0,0,0,0,0,0,0,0,0),(1535455327003,0,0,0,0,0,0,0,0,0,0,0),(1535455626693,1,1,0,0,0,0,0,0,0,0,0),(1535456526861,1,1,0,0,0,0,0,0,0,0,0),(1535728832435,0,0,0,0,0,0,0,0,0,0,0),(1535729132555,1,1,0,0,0,0,0,0,0,0,0),(1535740233840,1,1,0,0,0,0,0,0,0,0,0),(1535740658228,0,0,0,0,0,0,0,0,0,0,0),(1535740958226,0,0,0,0,0,0,0,0,0,0,0),(1535741258659,1,1,0,0,0,0,0,0,0,0,0),(1535741558221,2,1,0,0,0,0,1,0,0,0,0),(1535742758653,2,1,0,0,0,0,1,0,0,0,0),(1535743097249,0,0,0,0,0,0,0,0,0,0,0),(1535743397306,2,1,0,0,0,0,1,0,0,0,0),(1535743697477,1,1,0,0,0,0,0,0,0,0,0),(1535743997125,1,1,0,0,0,0,0,0,0,0,0),(1535746273252,0,0,0,0,0,0,0,0,0,0,0),(1535746573295,1,1,0,0,0,0,0,0,0,0,0),(1535747174859,1,1,0,0,0,0,0,0,0,0,0),(1535747474465,2,1,0,0,0,0,1,0,0,0,0),(1535747773310,2,1,0,0,0,0,1,0,0,0,0),(1535895228375,0,0,0,0,0,0,0,0,0,0,0),(1535895683304,0,0,0,0,0,0,0,0,0,0,0),(1535895983332,1,0,0,0,0,0,0,0,0,0,1),(1535896272070,0,0,0,0,0,0,0,0,0,0,0),(1535896572212,1,0,0,0,0,0,0,0,0,0,1),(1535907972414,1,0,0,0,0,0,0,0,0,0,1),(1535908272388,0,0,0,0,0,0,0,0,0,0,0),(1535969442511,0,0,0,0,0,0,0,0,0,0,0),(1535969742490,1,1,0,0,0,0,0,0,0,0,0),(1535970042901,2,1,0,0,0,0,0,0,0,0,1),(1535970343001,2,1,0,0,0,0,0,0,0,0,1),(1535970642847,1,0,0,0,0,0,0,0,0,0,1),(1535970943178,1,0,0,0,0,0,0,0,0,0,1),(1535971249697,2,1,0,0,0,0,0,0,0,0,1),(1535972149574,2,1,0,0,0,0,0,0,0,0,1),(1535972450309,1,0,0,0,0,0,0,0,0,0,1),(1535972749483,2,1,0,0,0,0,0,0,0,0,1),(1535973349458,2,1,0,0,0,0,0,0,0,0,1),(1535973649851,1,0,0,0,0,0,0,0,0,0,1),(1535976350138,1,0,0,0,0,0,0,0,0,0,1),(1535976650144,2,1,0,0,0,0,0,0,0,0,1),(1535976949734,1,0,0,0,0,0,0,0,0,0,1),(1535977249868,2,1,0,0,0,0,0,0,0,0,1),(1535978149497,2,1,0,0,0,0,0,0,0,0,1),(1535978450726,1,0,0,0,0,0,0,0,0,0,1),(1535978749805,2,1,0,0,0,0,0,0,0,0,1),(1535982350550,2,1,0,0,0,0,0,0,0,0,1),(1535982650065,1,0,0,0,0,0,0,0,0,0,1),(1535982950022,2,1,0,0,0,0,0,0,0,0,1),(1535985050165,2,1,0,0,0,0,0,0,0,0,1),(1535985391762,0,0,0,0,0,0,0,0,0,0,0),(1535985691656,1,1,0,0,0,0,0,0,0,0,0),(1535985991730,1,1,0,0,0,0,0,0,0,0,0),(1535986168112,0,0,0,0,0,0,0,0,0,0,0),(1536137313232,0,0,0,0,0,0,0,0,0,0,0),(1536137613218,1,1,0,0,0,0,0,0,0,0,0),(1536141213286,1,1,0,0,0,0,0,0,0,0,0),(1536259037557,0,0,0,0,0,0,0,0,0,0,0),(1536259337660,1,1,0,0,0,0,0,0,0,0,0),(1536259517164,0,0,0,0,0,0,0,0,0,0,0),(1536259872635,0,0,0,0,0,0,0,0,0,0,0),(1536260172659,1,1,0,0,0,0,0,0,0,0,0),(1536267972965,1,1,0,0,0,0,0,0,0,0,0),(1536431971651,0,0,0,0,0,0,0,0,0,0,0),(1536432271823,1,1,0,0,0,0,0,0,0,0,0),(1536433771745,1,1,0,0,0,0,0,0,0,0,0),(1536434053281,0,0,0,0,0,0,0,0,0,0,0),(1536434353405,1,1,0,0,0,0,0,0,0,0,0),(1536434499172,0,0,0,0,0,0,0,0,0,0,0),(1536434799165,1,1,0,0,0,0,0,0,0,0,0),(1536441699114,1,1,0,0,0,0,0,0,0,0,0),(1537454302077,0,0,0,0,0,0,0,0,0,0,0),(1537454602140,1,1,0,0,0,0,0,0,0,0,0),(1537454902053,1,1,0,0,0,0,0,0,0,0,0),(1537455241116,0,0,0,0,0,0,0,0,0,0,0),(1537457165260,0,0,0,0,0,0,0,0,0,0,0),(1537457465300,1,1,0,0,0,0,0,0,0,0,0),(1537457495899,0,0,0,0,0,0,0,0,0,0,0),(1537611156043,0,0,0,0,0,0,0,0,0,0,0),(1537611456606,1,1,0,0,0,0,0,0,0,0,0),(1537613256271,1,1,0,0,0,0,0,0,0,0,0),(1537613556403,0,0,0,0,0,0,0,0,0,0,0),(1537613856011,2,1,0,0,0,0,0,0,0,0,1),(1537616856092,2,1,0,0,0,0,0,0,0,0,1),(1537617156013,1,0,0,0,0,0,0,0,0,0,1),(1537617755877,1,0,0,0,0,0,0,0,0,0,1),(1537618055909,2,1,0,0,0,0,0,0,0,0,1),(1537618956163,2,1,0,0,0,0,0,0,0,0,1),(1538553950423,0,0,0,0,0,0,0,0,0,0,0),(1538554250575,1,1,0,0,0,0,0,0,0,0,0),(1538554552120,2,1,0,0,0,0,0,0,0,0,1),(1538554850437,1,1,0,0,0,0,0,0,0,0,0),(1538555150866,2,1,0,0,0,0,0,0,0,0,1),(1538555450719,0,0,0,0,0,0,0,0,0,0,0),(1538555751132,1,1,0,0,0,0,0,0,0,0,0),(1538556051057,0,0,0,0,0,0,0,0,0,0,0),(1538556350861,0,0,0,0,0,0,0,0,0,0,0),(1538556650440,2,1,0,0,0,0,0,0,0,0,1),(1538556950470,0,0,0,0,0,0,0,0,0,0,0),(1538557250436,2,1,0,0,0,0,0,0,0,0,1),(1538557550869,2,1,0,0,0,0,0,0,0,0,1),(1538557852033,1,1,0,0,0,0,0,0,0,0,0),(1538558150466,0,0,0,0,0,0,0,0,0,0,0),(1538558450689,0,0,0,0,0,0,0,0,0,0,0),(1538558751477,1,1,0,0,0,0,0,0,0,0,0),(1538559050423,0,0,0,0,0,0,0,0,0,0,0),(1538559350665,1,1,0,0,0,0,0,0,0,0,0),(1538559651129,0,0,0,0,0,0,0,0,0,0,0),(1538560250809,0,0,0,0,0,0,0,0,0,0,0),(1538560551110,1,1,0,0,0,0,0,0,0,0,0),(1538561450751,1,1,0,0,0,0,0,0,0,0,0),(1538561751275,0,0,0,0,0,0,0,0,0,0,0),(1538562051465,1,1,0,0,0,0,0,0,0,0,0),(1538562350804,0,0,0,0,0,0,0,0,0,0,0),(1538562650878,1,1,0,0,0,0,0,0,0,0,0),(1538562951309,0,0,0,0,0,0,0,0,0,0,0),(1538563250459,1,1,0,0,0,0,0,0,0,0,0),(1538564750924,1,1,0,0,0,0,0,0,0,0,0),(1538587728479,0,0,0,0,0,0,0,0,0,0,0),(1538588028630,1,1,0,0,0,0,0,0,0,0,0),(1538590728564,1,1,0,0,0,0,0,0,0,0,0),(1538600864316,0,0,0,0,0,0,0,0,0,0,0),(1538658576850,0,0,0,0,0,0,0,0,0,0,0),(1538658877267,1,1,0,0,0,0,0,0,0,0,0),(1538663676853,1,1,0,0,0,0,0,0,0,0,0),(1538663843553,0,0,0,0,0,0,0,0,0,0,0),(1538664143676,1,1,0,0,0,0,0,0,0,0,0),(1538665343869,1,1,0,0,0,0,0,0,0,0,0),(1538665463251,0,0,0,0,0,0,0,0,0,0,0),(1538665762069,1,1,0,0,0,0,0,0,0,0,0),(1538762593666,0,0,0,0,0,0,0,0,0,0,0),(1538765362227,0,0,0,0,0,0,0,0,0,0,0),(1538765662801,1,1,0,0,0,0,0,0,0,0,0),(1538770462449,1,1,0,0,0,0,0,0,0,0,0),(1538770763050,0,0,0,0,0,0,0,0,0,0,0),(1538771062454,0,0,0,0,0,0,0,0,0,0,0),(1538771362487,1,1,0,0,0,0,0,0,0,0,0),(1538771962224,1,1,0,0,0,0,0,0,0,0,0),(1538772262561,0,0,0,0,0,0,0,0,0,0,0),(1538772562875,0,0,0,0,0,0,0,0,0,0,0),(1538772862347,1,1,0,0,0,0,0,0,0,0,0),(1538775562264,1,1,0,0,0,0,0,0,0,0,0),(1538775862082,0,0,0,0,0,0,0,0,0,0,0),(1538826824379,0,0,0,0,0,0,0,0,0,0,0),(1538827124584,1,1,0,0,0,0,0,0,0,0,0),(1538827424569,1,1,0,0,0,0,0,0,0,0,0),(1538827724750,0,0,0,0,0,0,0,0,0,0,0),(1538828024910,1,1,0,0,0,0,0,0,0,0,0),(1538828324767,0,0,0,0,0,0,0,0,0,0,0),(1538828624627,1,1,0,0,0,0,0,0,0,0,0),(1538831624337,1,1,0,0,0,0,0,0,0,0,0),(1538831926705,0,0,0,0,0,0,0,0,0,0,0),(1538832224642,0,0,0,0,0,0,0,0,0,0,0),(1538832525226,1,1,0,0,0,0,0,0,0,0,0),(1538833724414,1,1,0,0,0,0,0,0,0,0,0),(1538834024651,0,0,0,0,0,0,0,0,0,0,0),(1538834325085,1,1,0,0,0,0,0,0,0,0,0),(1538834624910,0,0,0,0,0,0,0,0,0,0,0),(1538834924586,1,1,0,0,0,0,0,0,0,0,0),(1538835824724,1,1,0,0,0,0,0,0,0,0,0),(1538836125674,0,0,0,0,0,0,0,0,0,0,0),(1538836424829,1,1,0,0,0,0,0,0,0,0,0),(1538837624654,1,1,0,0,0,0,0,0,0,0,0),(1538837924671,0,0,0,0,0,0,0,0,0,0,0),(1538840024722,0,0,0,0,0,0,0,0,0,0,0),(1538840325186,2,1,0,0,0,0,0,0,0,0,1),(1538841524571,2,1,0,0,0,0,0,0,0,0,1),(1538842636221,0,0,0,0,0,0,0,0,0,0,0),(1538842936920,2,1,0,0,0,0,0,0,0,0,1),(1538843236687,2,1,0,0,0,0,0,0,0,0,1),(1538843536650,0,0,0,0,0,0,0,0,0,0,0),(1538847369909,0,0,0,0,0,0,0,0,0,0,0),(1538847670686,1,1,0,0,0,0,0,0,0,0,0),(1538850669978,1,1,0,0,0,0,0,0,0,0,0),(1538850970341,0,0,0,0,0,0,0,0,0,0,0),(1538851270384,1,1,0,0,0,0,0,0,0,0,0),(1538852170202,1,1,0,0,0,0,0,0,0,0,0),(1538852469887,0,0,0,0,0,0,0,0,0,0,0),(1538852771926,2,1,0,0,0,0,0,0,0,0,1),(1538855170138,2,1,0,0,0,0,0,0,0,0,1),(1538988835276,0,0,0,0,0,0,0,0,0,0,0),(1538989135952,2,1,0,0,0,0,0,0,0,0,1),(1538989737079,2,1,0,0,0,0,0,0,0,0,1),(1538989809059,0,0,0,0,0,0,0,0,0,0,0),(1538989818973,0,0,0,0,0,0,0,0,0,0,0),(1538990118950,2,1,0,0,0,0,0,0,0,0,1),(1538991919428,2,1,0,0,0,0,0,0,0,0,1),(1538992219406,0,0,0,0,0,0,0,0,0,0,0),(1538992519107,2,1,0,0,0,0,0,0,0,0,1),(1539006019193,2,1,0,0,0,0,0,0,0,0,1),(1539006320134,1,0,0,0,0,0,0,0,0,0,1),(1539006619237,3,1,0,0,0,0,0,0,0,0,2),(1539009319389,3,1,0,0,0,0,0,0,0,0,2),(1539009619487,2,1,0,0,0,0,0,0,0,0,1),(1539009919367,3,1,0,0,0,0,0,0,0,0,2),(1539012021484,3,1,0,0,0,0,0,0,0,0,2),(1539012322819,2,1,0,0,0,0,0,0,0,0,1),(1539012622106,3,1,0,0,0,0,0,0,0,0,2),(1539014122076,3,1,0,0,0,0,0,0,0,0,2),(1539072345796,0,0,0,0,0,0,0,0,0,0,0),(1539072645669,1,1,0,0,0,0,0,0,0,0,0),(1539072945817,1,1,0,0,0,0,0,0,0,0,0),(1539073245821,0,0,0,0,0,0,0,0,0,0,0),(1539073545980,1,1,0,0,0,0,0,0,0,0,0),(1539075345801,1,1,0,0,0,0,0,0,0,0,0),(1539075645875,0,0,0,0,0,0,0,0,0,0,0),(1539107378616,0,0,0,0,0,0,0,0,0,0,0),(1539107679160,1,1,0,0,0,0,0,0,0,0,0),(1539108279383,1,1,0,0,0,0,0,0,0,0,0),(1539108579147,2,1,0,0,0,0,0,0,0,0,1),(1539111579761,2,1,0,0,0,0,0,0,0,0,1),(1539111892463,0,0,0,0,0,0,0,0,0,0,0),(1539112192164,1,1,0,0,0,0,0,0,0,0,0),(1539112364284,0,0,0,0,0,0,0,0,0,0,0),(1539112492249,1,1,0,0,0,0,0,0,0,0,0),(1539112792183,0,0,0,0,0,0,0,0,0,0,0),(1539113092586,1,1,0,0,0,0,0,0,0,0,0),(1539114892528,1,1,0,0,0,0,0,0,0,0,0),(1539115192599,0,0,0,0,0,0,0,0,0,0,0),(1539115492215,1,1,0,0,0,0,0,0,0,0,0),(1539116692413,1,1,0,0,0,0,0,0,0,0,0),(1539116992770,2,1,0,0,0,0,0,0,0,0,1),(1539119092556,2,1,0,0,0,0,0,0,0,0,1),(1539119392710,1,0,0,0,0,0,0,0,0,0,1),(1539119992537,1,0,0,0,0,0,0,0,0,0,1),(1539120292494,0,0,0,0,0,0,0,0,0,0,0),(1539120592884,1,1,0,0,0,0,0,0,0,0,0),(1539120892226,2,1,0,0,0,0,0,0,0,0,1),(1539121192833,1,1,0,0,0,0,0,0,0,0,0),(1539122392577,1,1,0,0,0,0,0,0,0,0,0),(1539122693279,1,0,0,0,0,0,0,0,0,0,1),(1539122992568,1,0,0,0,0,0,0,0,0,0,1),(1539123292426,2,1,0,0,0,0,0,0,0,0,1),(1539125092828,2,1,0,0,0,0,0,0,0,0,1),(1539125392621,1,0,0,0,0,0,0,0,0,0,1),(1539125692483,1,0,0,0,0,0,0,0,0,0,1),(1539125992269,2,1,0,0,0,0,0,0,0,0,1),(1539127192667,2,1,0,0,0,0,0,0,0,0,1),(1539127504666,0,0,0,0,0,0,0,0,0,0,0),(1539127804556,1,1,0,0,0,0,0,0,0,0,0),(1539128104744,1,1,0,0,0,0,0,0,0,0,0),(1539128404977,2,1,0,0,0,0,0,0,0,0,1),(1539157690006,0,0,0,0,0,0,0,0,0,0,0),(1539157990811,0,0,0,0,0,0,0,0,0,0,0),(1539158290237,1,1,0,0,0,0,0,0,0,0,0),(1539158590737,0,0,0,0,0,0,0,0,0,0,0),(1539158890354,1,1,0,0,0,0,0,0,0,0,0),(1539165490121,1,1,0,0,0,0,0,0,0,0,0),(1539165790646,0,0,0,0,0,0,0,0,0,0,0),(1539166090168,1,1,0,0,0,0,0,0,0,0,0),(1539166990524,1,1,0,0,0,0,0,0,0,0,0),(1539167290586,0,0,0,0,0,0,0,0,0,0,0),(1539169690463,0,0,0,0,0,0,0,0,0,0,0),(1539169990157,1,1,0,0,0,0,0,0,0,0,0),(1539170290178,1,1,0,0,0,0,0,0,0,0,0),(1539170590078,0,0,0,0,0,0,0,0,0,0,0),(1539172090505,0,0,0,0,0,0,0,0,0,0,0),(1539172390190,1,1,0,0,0,0,0,0,0,0,0),(1539172690124,1,1,0,0,0,0,0,0,0,0,0),(1539172990560,0,0,0,0,0,0,0,0,0,0,0),(1539173290160,0,0,0,0,0,0,0,0,0,0,0),(1539173590298,1,1,0,0,0,0,0,0,0,0,0),(1539174492774,1,1,0,0,0,0,0,0,0,0,0),(1539174790156,0,0,0,0,0,0,0,0,0,0,0),(1539176591737,0,0,0,0,0,0,0,0,0,0,0),(1539176890131,1,1,0,0,0,0,0,0,0,0,0),(1539177190190,1,1,0,0,0,0,0,0,0,0,0),(1539177491383,2,1,0,0,0,0,0,0,0,0,1),(1539177737602,0,0,0,0,0,0,0,0,0,0,0),(1539177833566,0,0,0,0,0,0,0,0,0,0,0),(1539178133675,2,1,0,0,0,0,0,0,0,0,1),(1539181433652,2,1,0,0,0,0,0,0,0,0,1),(1539181734655,1,0,0,0,0,0,0,0,0,0,1),(1539342448688,0,0,0,0,0,0,0,0,0,0,0),(1539342748942,1,1,0,0,0,0,0,0,0,0,0),(1539343048924,1,1,0,0,0,0,0,0,0,0,0),(1539343348681,2,1,0,0,0,0,0,0,0,0,1),(1539344756255,0,0,0,0,0,0,0,0,0,0,0),(1539345056227,1,0,0,0,0,0,0,0,0,0,1),(1539345357347,2,1,0,0,0,0,0,0,0,0,1),(1539345956163,2,1,0,0,0,0,0,0,0,0,1),(1539346256984,1,0,0,0,0,0,0,0,0,0,1),(1539346558089,2,1,0,0,0,0,0,0,0,0,1),(1539347456362,2,1,0,0,0,0,0,0,0,0,1),(1539347675848,0,0,0,0,0,0,0,0,0,0,0),(1539347975904,2,1,0,0,0,0,0,0,0,0,1),(1539350976421,2,1,0,0,0,0,0,0,0,0,1),(1539351276310,1,0,0,0,0,0,0,0,0,0,1),(1539352475947,1,0,0,0,0,0,0,0,0,0,1),(1539352776569,2,1,0,0,0,0,0,0,0,0,1),(1539353076772,1,0,0,0,0,0,0,0,0,0,1),(1540298204396,0,0,0,0,0,0,0,0,0,0,0),(1540298579934,0,0,0,0,0,0,0,0,0,0,0),(1540298804388,1,1,0,0,0,0,0,0,0,0,0),(1540300944359,0,0,0,0,0,0,0,0,0,0,0),(1541943545780,0,0,0,0,0,0,0,0,0,0,0),(1541943845906,1,1,0,0,0,0,0,0,0,0,0),(1541944745736,1,1,0,0,0,0,0,0,0,0,0),(1541945046207,0,0,0,0,0,0,0,0,0,0,0),(1541945346143,1,1,0,0,0,0,0,0,0,0,0),(1541945645835,1,1,0,0,0,0,0,0,0,0,0),(1541945783727,0,0,0,0,0,0,0,0,0,0,0),(1541952683849,0,0,0,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `statsonline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statsplaytime`
--

DROP TABLE IF EXISTS `statsplaytime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statsplaytime` (
  `Time` bigint(20) DEFAULT NULL,
  `Total` bigint(20) DEFAULT NULL,
  `HUB` bigint(20) DEFAULT NULL,
  `SURVIVAL` bigint(20) DEFAULT NULL,
  `KITPVP` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statsplaytime`
--

LOCK TABLES `statsplaytime` WRITE;
/*!40000 ALTER TABLE `statsplaytime` DISABLE KEYS */;
INSERT INTO `statsplaytime` VALUES (1534886011067,0,0,0,NULL),(1534886310980,35294,25888,9406,NULL),(1534944491010,35294,25888,9406,NULL),(1534944692393,25888,25888,0,NULL),(1535012816128,25888,25888,0,NULL),(1535013116582,25890,25890,0,NULL),(1535013416008,25992,25992,0,NULL),(1535222316796,25992,25992,0,NULL),(1535314591721,26023,26023,0,NULL),(1535395658768,26201,26201,0,NULL),(1535397455807,26201,26201,0,NULL),(1535397755716,28176,28176,0,NULL),(1535398356057,28176,28176,0,NULL),(1535453827669,28847,28847,0,NULL),(1535454126996,29248,29248,0,NULL),(1535455027229,29248,29248,0,NULL),(1535455327329,30422,30422,0,NULL),(1535455627000,30487,30487,0,NULL),(1535456527505,30487,30487,0,NULL),(1535729134723,30730,30730,0,NULL),(1535743397979,30730,30730,0,NULL),(1535743697504,30758,30730,28,NULL),(1535746574813,30758,30730,28,NULL),(1535746874100,31000,30730,270,NULL),(1535747173688,31000,30730,270,NULL),(1535747473802,31019,30730,289,NULL),(1535747773825,31019,30730,289,NULL),(1535895984162,31122,30833,289,NULL),(1535907972737,31122,30833,289,NULL),(1535908273034,42964,42675,289,NULL),(1535970343220,42964,42675,289,NULL),(1535970643342,44018,42675,1343,NULL),(1535970943371,44018,42675,1343,NULL),(1535971250369,45437,42675,2762,NULL),(1535972150309,45437,42675,2762,NULL),(1535972450953,46584,42675,3909,NULL),(1535972750187,47841,42675,5166,NULL),(1535973050298,47841,42675,5166,NULL),(1535973350621,48659,42675,5984,NULL),(1535973650105,49003,42675,6328,NULL),(1535976650250,49003,42675,6328,NULL),(1535976950359,49304,42675,6629,NULL),(1535977850151,49304,42675,6629,NULL),(1535978150292,49374,42675,6699,NULL),(1535978450145,49841,42675,7166,NULL),(1535979350214,49841,42675,7166,NULL),(1535979650175,50753,42675,8078,NULL),(1535980250388,50753,42675,8078,NULL),(1535980550610,51658,42675,8983,NULL),(1535982050996,51658,42675,8983,NULL),(1535982350961,53353,42675,10678,NULL),(1535982650839,53784,42675,11109,NULL),(1535985050428,53784,42675,11109,NULL),(1535985692357,56410,42675,13735,NULL),(1535986469722,56410,42675,13735,NULL),(1536137614626,56420,42675,13745,NULL),(1536259339175,56420,42675,13745,NULL),(1536260173149,56590,42845,13745,NULL),(1536265873697,56590,42845,13745,NULL),(1536266173261,62848,49103,13745,NULL),(1536267973598,62848,49103,13745,NULL),(1536432272865,63028,49283,13745,NULL),(1536433772352,63028,49283,13745,NULL),(1536434354174,64826,51081,13745,NULL),(1536435699876,64826,51081,13745,NULL),(1536435999579,66035,52290,13745,NULL),(1536437199596,66035,52290,13745,NULL),(1536437500095,67615,53870,13745,NULL),(1536437800192,67615,53870,13745,NULL),(1536438100004,67776,53870,13906,NULL),(1536438399539,67864,53870,13994,NULL),(1536440499981,67864,53870,13994,NULL),(1536440799354,70268,53870,16398,NULL),(1536441099663,70268,53870,16398,NULL),(1536441401045,70789,53870,16919,NULL),(1536441699778,70817,53870,16947,NULL),(1537454602909,71167,53901,17266,NULL),(1537454902748,71436,54170,17266,NULL),(1537457466259,71452,54186,17266,NULL),(1537457797027,71505,54239,17266,NULL),(1537612056624,71505,54239,17266,NULL),(1537612356581,72282,54239,18043,NULL),(1537612656893,72282,54239,18043,NULL),(1537612957102,72746,54239,18507,NULL),(1537613256698,72990,54239,18751,NULL),(1537613556723,73223,54239,18984,NULL),(1537613856605,73223,54239,18984,NULL),(1537614156660,73330,54239,19091,NULL),(1537614756862,73330,54239,19091,NULL),(1537615057219,74566,54239,20327,NULL),(1537616856531,74566,54239,20327,NULL),(1537617156774,76388,54239,22149,NULL),(1537618956950,76388,54239,22149,NULL),(1538554252346,77676,54239,23437,NULL),(1538554551844,77889,54239,23650,NULL),(1538554851137,78336,54239,24097,NULL),(1538555151571,78564,54239,24325,NULL),(1538555451158,79424,54239,25185,NULL),(1538555751552,79424,54239,25185,NULL),(1538556051118,79870,54239,25631,NULL),(1538556352008,79990,54239,25751,NULL),(1538556651379,79990,54239,25751,NULL),(1538556951181,80543,54239,26304,NULL),(1538557551668,80543,54239,26304,NULL),(1538557852138,81098,54239,26859,NULL),(1538558151113,81475,54239,27236,NULL),(1538558451256,81675,54239,27436,NULL),(1538558752027,81786,54239,27547,NULL),(1538559052085,81873,54239,27634,NULL),(1538559351148,81913,54239,27674,NULL),(1538559651561,82142,54239,27903,NULL),(1538560251433,82142,54239,27903,NULL),(1538560551404,82266,54239,28027,NULL),(1538560851739,82410,54239,28171,NULL),(1538561151041,82607,54239,28368,NULL),(1538561450986,82607,54239,28368,NULL),(1538561750881,83297,54239,29058,NULL),(1538562051326,83297,54239,29058,NULL),(1538562351250,83753,54239,29514,NULL),(1538564751137,83753,54239,29514,NULL),(1538588029954,83757,54239,29518,NULL),(1538663677680,83757,54239,29518,NULL),(1538664144388,89028,59510,29518,NULL),(1538664444715,89416,59898,29518,NULL),(1540298505401,89416,59898,29518,NULL),(1540298807148,89580,60062,29518,NULL),(1540301290527,186065,60062,29612,96391),(1541944746730,186065,60062,29612,96391),(1541945046379,187440,61437,29612,96391),(1541952684103,187440,61437,29612,96391);
/*!40000 ALTER TABLE `statsplaytime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statstps`
--

DROP TABLE IF EXISTS `statstps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statstps` (
  `Time` bigint(20) DEFAULT NULL,
  `Server` varchar(32) DEFAULT NULL,
  `TPS` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statstps`
--

LOCK TABLES `statstps` WRITE;
/*!40000 ALTER TABLE `statstps` DISABLE KEYS */;
INSERT INTO `statstps` VALUES (1534893383208,'HUB',200000),(1534893684861,'HUB',190000),(1534893985663,'HUB',190000),(1534934469595,'HUB',200000),(1534943526118,'HUB',200000),(1534943828605,'HUB',190000),(1534944196408,'SURVIVAL',200000),(1534944510506,'SURVIVAL',190000),(1534944730005,'SURVIVAL',200000),(1534944992000,'SURVIVAL',200000),(1535012936029,'HUB',200000),(1535013297219,'HUB',200000),(1535013613465,'HUB',190000),(1535210503187,'HUB',200000),(1535210725531,'HUB',200000),(1535210941866,'SURVIVAL',200000),(1535211251056,'SURVIVAL',190000),(1535211560033,'SURVIVAL',190000),(1535211869457,'SURVIVAL',190000),(1535212178863,'SURVIVAL',190000),(1535212488604,'SURVIVAL',190000),(1535212798367,'SURVIVAL',190000),(1535213107126,'SURVIVAL',190000),(1535213494604,'SURVIVAL',200000),(1535213716848,'HUB',200000),(1535213803536,'SURVIVAL',190000),(1535214024617,'HUB',190000),(1535214110593,'SURVIVAL',190000),(1535214332453,'HUB',190000),(1535214419007,'SURVIVAL',190000),(1535214643639,'SURVIVAL',200000),(1535214962400,'SURVIVAL',200000),(1535215242203,'SURVIVAL',200000),(1535222058990,'SURVIVAL',200000),(1535222382113,'SURVIVAL',190000),(1535313472645,'HUB',200000),(1535313558512,'SURVIVAL',200000),(1535313778096,'HUB',190000),(1535313862199,'SURVIVAL',190000),(1535314174293,'HUB',200000),(1535314197893,'SURVIVAL',200000),(1535314518663,'HUB',200000),(1535314715551,'SURVIVAL',200000),(1535314829408,'HUB',190000),(1535395417021,'HUB',200000),(1535395754174,'HUB',200000),(1535396061423,'HUB',190000),(1535397571002,'HUB',190000),(1535397818179,'HUB',200000),(1535398130329,'HUB',190000),(1535398431333,'HUB',190000),(1535444947069,'HUB',200000),(1535453558706,'HUB',200000),(1535453867910,'HUB',190000),(1535454069152,'HUB',200000),(1535454374530,'HUB',190000),(1535454978784,'HUB',190000),(1535455321955,'HUB',200000),(1535455508179,'HUB',200000),(1535455813017,'HUB',190000),(1535456114562,'HUB',190000),(1535456352247,'HUB',200000),(1535728877075,'SURVIVAL',200000),(1535729223362,'SURVIVAL',200000),(1535729526670,'SURVIVAL',190000),(1535740093782,'SURVIVAL',190000),(1535740396170,'SURVIVAL',190000),(1535740701481,'SURVIVAL',200000),(1535741021873,'SURVIVAL',200000),(1535741326104,'SURVIVAL',190000),(1535741629968,'SURVIVAL',190000),(1535743162458,'SURVIVAL',200000),(1535743471795,'SURVIVAL',190000),(1535743773477,'SURVIVAL',190000),(1535744075451,'SURVIVAL',190000),(1535746335059,'SURVIVAL',200000),(1535747160950,'SURVIVAL',200000),(1535747467442,'SURVIVAL',190000),(1535747769930,'SURVIVAL',190000),(1535895275894,'HUB',200000),(1535895735730,'HUB',200000),(1535896041165,'HUB',190000),(1535907841808,'HUB',190000),(1535969503297,'SURVIVAL',200000),(1535969813774,'SURVIVAL',190000),(1535970421065,'SURVIVAL',190000),(1535970656725,'SURVIVAL',200000),(1535970965478,'SURVIVAL',190000),(1535971880972,'SURVIVAL',190000),(1535972194816,'SURVIVAL',200000),(1535972437744,'SURVIVAL',200000),(1535972749079,'SURVIVAL',190000),(1535973051996,'SURVIVAL',190000),(1535973271108,'SURVIVAL',200000),(1535973578304,'SURVIVAL',190000),(1535976493834,'SURVIVAL',200000),(1535976802320,'SURVIVAL',190000),(1535977117777,'SURVIVAL',200000),(1535977421360,'SURVIVAL',190000),(1535977725074,'SURVIVAL',190000),(1535977833225,'SURVIVAL',200000),(1535978136333,'SURVIVAL',190000),(1535978468864,'SURVIVAL',200000),(1535978771557,'SURVIVAL',190000),(1535979987951,'SURVIVAL',190000),(1535980288770,'SURVIVAL',190000),(1535980403146,'SURVIVAL',200000),(1535980709458,'SURVIVAL',190000),(1535981918252,'SURVIVAL',190000),(1535982166861,'SURVIVAL',200000),(1535982476274,'SURVIVAL',190000),(1535982698271,'SURVIVAL',200000),(1535983004125,'SURVIVAL',190000),(1535984820762,'SURVIVAL',190000),(1535985123056,'SURVIVAL',190000),(1535985434542,'HUB',200000),(1535985739923,'HUB',190000),(1535986042965,'HUB',190000),(1535986223796,'SURVIVAL',200000),(1536137684483,'SURVIVAL',200000),(1536137990585,'SURVIVAL',190000),(1536141015429,'SURVIVAL',190000),(1536141317114,'SURVIVAL',190000),(1536259088420,'HUB',200000),(1536259395719,'HUB',190000),(1536265753174,'HUB',190000),(1536266056060,'HUB',190000),(1536266135702,'SURVIVAL',200000),(1536266361368,'HUB',190000),(1536266441146,'SURVIVAL',190000),(1536266663816,'HUB',190000),(1536266744696,'SURVIVAL',190000),(1536266967573,'HUB',190000),(1536267048895,'SURVIVAL',190000),(1536267271241,'HUB',190000),(1536267352664,'SURVIVAL',190000),(1536267576028,'HUB',190000),(1536267657311,'SURVIVAL',190000),(1536267880788,'HUB',190000),(1536267962282,'SURVIVAL',190000),(1536431965493,'HUB',200000),(1536432276392,'HUB',190000),(1536433487361,'HUB',190000),(1536433790641,'HUB',190000),(1536434092111,'HUB',200000),(1536434396679,'HUB',190000),(1536435609415,'HUB',190000),(1536435825793,'HUB',200000),(1536436130246,'HUB',190000),(1536437035955,'HUB',190000),(1536437339424,'HUB',190000),(1536437489234,'SURVIVAL',200000),(1536438235540,'SURVIVAL',200000),(1536438538980,'SURVIVAL',190000),(1536440958645,'SURVIVAL',190000),(1536441367652,'SURVIVAL',200000),(1536441672073,'SURVIVAL',190000),(1537454339375,'HUB',200000),(1537454644243,'HUB',190000),(1537454946549,'HUB',190000),(1537455248414,'HUB',190000),(1537455549336,'HUB',190000),(1537455849761,'HUB',200000),(1537457381518,'HUB',200000),(1537610900591,'SURVIVAL',200000),(1537611372154,'SURVIVAL',200000),(1537611675844,'SURVIVAL',190000),(1537611981455,'SURVIVAL',190000),(1537612302957,'SURVIVAL',200000),(1537612607682,'SURVIVAL',190000),(1537612915249,'SURVIVAL',200000),(1537614123208,'SURVIVAL',200000),(1537614425990,'SURVIVAL',190000),(1537614729230,'SURVIVAL',190000),(1537614913547,'SURVIVAL',200000),(1537615250608,'SURVIVAL',200000),(1537615557941,'SURVIVAL',190000),(1537617065623,'SURVIVAL',190000),(1537617762880,'SURVIVAL',200000),(1537618073756,'SURVIVAL',190000),(1537618375304,'SURVIVAL',190000),(1537618679109,'SURVIVAL',190000),(1537618926920,'SURVIVAL',200000),(1538554890740,'SURVIVAL',200000),(1538555203326,'SURVIVAL',190000),(1538555593074,'SURVIVAL',200000),(1538555901299,'SURVIVAL',190000),(1538556143947,'SURVIVAL',200000),(1538557063678,'SURVIVAL',200000),(1538557367494,'SURVIVAL',190000),(1538557633275,'SURVIVAL',200000),(1538557937039,'SURVIVAL',190000),(1538558123746,'SURVIVAL',200000),(1538561000140,'SURVIVAL',200000),(1538561304456,'SURVIVAL',190000),(1538561606449,'SURVIVAL',190000),(1538561806907,'SURVIVAL',200000),(1538562426946,'SURVIVAL',200000),(1538562729553,'SURVIVAL',190000),(1538562994452,'SURVIVAL',200000),(1538563296856,'SURVIVAL',190000),(1538564514909,'SURVIVAL',190000),(1538564816213,'SURVIVAL',190000),(1538587774458,'SURVIVAL',200000),(1538588099071,'SURVIVAL',190000),(1538590511822,'SURVIVAL',190000),(1538590813069,'SURVIVAL',190000),(1538600914124,'HUB',200000),(1538601003890,'SURVIVAL',200000),(1538601217347,'HUB',190000),(1538601370471,'SURVIVAL',200000),(1538658590263,'HUB',200000),(1538658898738,'HUB',190000),(1538663438673,'HUB',190000),(1538663742609,'HUB',190000),(1538663871750,'HUB',200000),(1538664177875,'HUB',190000),(1538664424469,'SURVIVAL',200000),(1538664736447,'SURVIVAL',190000),(1538665346511,'SURVIVAL',190000),(1538665484871,'SURVIVAL',200000),(1538665790515,'SURVIVAL',190000),(1538762630999,'KITPVP',200000),(1538762935920,'KITPVP',190000),(1538763069019,'KITPVP',200000),(1538763372855,'KITPVP',190000),(1538763576126,'KITPVP',200000),(1538764222288,'KITPVP',200000),(1538764525311,'KITPVP',190000),(1538764826396,'KITPVP',190000),(1538765095017,'KITPVP',200000),(1538765411387,'KITPVP',200000),(1538765714806,'KITPVP',190000),(1538770549051,'KITPVP',190000),(1538770804662,'KITPVP',200000),(1538771159157,'KITPVP',200000),(1538771462218,'KITPVP',190000),(1538771541319,'KITPVP',200000),(1538771844294,'KITPVP',190000),(1538772652443,'KITPVP',200000),(1538772939761,'KITPVP',200000),(1538773242759,'KITPVP',190000),(1538773544847,'KITPVP',190000),(1538773886279,'KITPVP',200000),(1538774511249,'KITPVP',200000),(1538774814330,'KITPVP',190000),(1538776322727,'KITPVP',190000),(1538826846175,'KITPVP',200000),(1538827151002,'KITPVP',190000),(1538827452213,'KITPVP',190000),(1538827770824,'KITPVP',200000),(1538828073895,'KITPVP',190000),(1538828322796,'KITPVP',200000),(1538828625826,'KITPVP',190000),(1538831038099,'KITPVP',190000),(1538831339630,'KITPVP',190000),(1538831509471,'KITPVP',200000),(1538831812802,'KITPVP',190000),(1538831936666,'KITPVP',200000),(1538832624969,'KITPVP',200000),(1538832928151,'KITPVP',190000),(1538833229317,'KITPVP',190000),(1538833346728,'KITPVP',200000),(1538834761268,'KITPVP',200000),(1538835063861,'KITPVP',190000),(1538835666473,'KITPVP',190000),(1538835962546,'KITPVP',200000),(1538836182882,'KITPVP',200000),(1538836485151,'KITPVP',190000),(1538837389328,'KITPVP',190000),(1538840150781,'KITPVP',200000),(1538840212257,'KITPVP',200000),(1538840517816,'KITPVP',190000),(1538840803252,'KITPVP',200000),(1538841107206,'KITPVP',190000),(1538842485217,'KITPVP',200000),(1538842654589,'KITPVP',200000),(1538842960784,'KITPVP',190000),(1538843262264,'KITPVP',190000),(1538843377426,'KITPVP',200000),(1538847899014,'KITPVP',200000),(1538848205905,'KITPVP',190000),(1538848341634,'KITPVP',200000),(1538848487577,'KITPVP',200000),(1538848790669,'KITPVP',190000),(1538849092388,'KITPVP',190000),(1538849407369,'KITPVP',200000),(1538849710077,'KITPVP',190000),(1538850615726,'KITPVP',190000),(1538850979076,'KITPVP',200000),(1538852995751,'KITPVP',200000),(1538853300353,'KITPVP',190000),(1538855111333,'KITPVP',190000),(1538988856066,'KITPVP',200000),(1538989160925,'KITPVP',190000),(1538989463332,'KITPVP',190000),(1538989850777,'KITPVP',200000),(1538990153870,'KITPVP',190000),(1538990455800,'KITPVP',190000),(1538990800043,'KITPVP',200000),(1538991102887,'KITPVP',190000),(1538992007532,'KITPVP',190000),(1538992247485,'KITPVP',200000),(1538992416686,'KITPVP',200000),(1538992720106,'KITPVP',190000),(1538993928304,'KITPVP',190000),(1538994211233,'KITPVP',200000),(1538995685991,'KITPVP',200000),(1538995990119,'KITPVP',190000),(1538996292296,'KITPVP',190000),(1538996416983,'KITPVP',200000),(1538996720374,'KITPVP',190000),(1538997624791,'KITPVP',190000),(1538997926225,'KITPVP',190000),(1538998016985,'KITPVP',200000),(1538998169364,'KITPVP',200000),(1538998472971,'KITPVP',190000),(1539005118036,'KITPVP',190000),(1539005421088,'KITPVP',190000),(1539005539462,'KITPVP',200000),(1539005844517,'KITPVP',190000),(1539006146827,'KITPVP',190000),(1539006437738,'KITPVP',200000),(1539006744300,'KITPVP',190000),(1539007651859,'KITPVP',190000),(1539007924736,'KITPVP',200000),(1539008229907,'KITPVP',190000),(1539008862105,'KITPVP',200000),(1539009167025,'KITPVP',190000),(1539009470020,'KITPVP',190000),(1539009590072,'KITPVP',200000),(1539009893874,'KITPVP',190000),(1539010103783,'KITPVP',200000),(1539010405946,'KITPVP',190000),(1539011015382,'KITPVP',190000),(1539011453166,'KITPVP',200000),(1539012314653,'KITPVP',200000),(1539012620572,'KITPVP',190000),(1539072364148,'KITPVP',200000),(1539072670526,'KITPVP',190000),(1539072971886,'KITPVP',190000),(1539073133182,'KITPVP',200000),(1539073435612,'KITPVP',190000),(1539075554593,'KITPVP',190000),(1539107103849,'KITPVP',200000),(1539107810200,'KITPVP',200000),(1539108113695,'KITPVP',190000),(1539110235234,'KITPVP',190000),(1539110542934,'KITPVP',200000),(1539110846841,'KITPVP',190000),(1539111076298,'KITPVP',200000),(1539112892714,'KITPVP',200000),(1539113196552,'KITPVP',190000),(1539113574397,'KITPVP',200000),(1539114121195,'KITPVP',200000),(1539114424579,'KITPVP',190000),(1539114561349,'KITPVP',200000),(1539115241856,'KITPVP',200000),(1539115546993,'KITPVP',190000),(1539115679069,'KITPVP',200000),(1539115750286,'KITPVP',200000),(1539116053331,'KITPVP',190000),(1539118778132,'KITPVP',190000),(1539118986222,'KITPVP',200000),(1539119290431,'KITPVP',200000),(1539119598068,'KITPVP',190000),(1539120202746,'KITPVP',190000),(1539120333256,'KITPVP',200000),(1539120637641,'KITPVP',190000),(1539120939857,'KITPVP',190000),(1539121090301,'KITPVP',200000),(1539121393330,'KITPVP',190000),(1539121998180,'KITPVP',190000),(1539122289507,'KITPVP',200000),(1539122594444,'KITPVP',190000),(1539124411274,'KITPVP',190000),(1539125197893,'KITPVP',200000),(1539125841386,'KITPVP',200000),(1539126145272,'KITPVP',190000),(1539127052456,'KITPVP',190000),(1539127355282,'KITPVP',190000),(1539127525159,'KITPVP',200000),(1539127834190,'KITPVP',190000),(1539127975533,'KITPVP',200000),(1539158597142,'KITPVP',200000),(1539158900662,'KITPVP',190000),(1539159027281,'KITPVP',200000),(1539159329941,'KITPVP',190000),(1539159631937,'KITPVP',190000),(1539159907258,'KITPVP',200000),(1539160169396,'KITPVP',200000),(1539160473632,'KITPVP',190000),(1539160801961,'KITPVP',200000),(1539161551811,'KITPVP',200000),(1539161855222,'KITPVP',190000),(1539162112125,'KITPVP',200000),(1539162262009,'KITPVP',200000),(1539162565005,'KITPVP',190000),(1539164379030,'KITPVP',190000),(1539164679359,'KITPVP',200000),(1539164982915,'KITPVP',190000),(1539165182281,'KITPVP',200000),(1539165279410,'KITPVP',200000),(1539165582435,'KITPVP',190000),(1539165832138,'KITPVP',200000),(1539166134873,'KITPVP',190000),(1539166436601,'KITPVP',190000),(1539166739810,'KITPVP',190000),(1539166930004,'KITPVP',200000),(1539167232810,'KITPVP',190000),(1539169647599,'KITPVP',190000),(1539169805943,'KITPVP',200000),(1539170109470,'KITPVP',190000),(1539170411157,'KITPVP',200000),(1539170713144,'KITPVP',190000),(1539172222418,'KITPVP',190000),(1539172541514,'KITPVP',200000),(1539173307851,'KITPVP',200000),(1539173610545,'KITPVP',190000),(1539174214126,'KITPVP',190000),(1539174536963,'KITPVP',200000),(1539174795958,'KITPVP',200000),(1539175098333,'KITPVP',190000),(1539175417714,'KITPVP',200000),(1539176598468,'KITPVP',200000),(1539176902087,'KITPVP',190000),(1539177206033,'KITPVP',190000),(1539177390717,'KITPVP',200000),(1539179260319,'KITPVP',200000),(1539179564157,'KITPVP',190000),(1539179815201,'KITPVP',200000),(1539180117380,'KITPVP',190000),(1539180721928,'KITPVP',190000),(1539181005420,'KITPVP',200000),(1539181308372,'KITPVP',190000),(1539181606254,'KITPVP',200000),(1539342482731,'KITPVP',200000),(1539342797288,'KITPVP',190000),(1539343099300,'KITPVP',190000),(1539343403245,'KITPVP',190000),(1539343704447,'KITPVP',190000),(1539344005976,'KITPVP',190000),(1539344307887,'KITPVP',190000),(1539344610343,'KITPVP',190000),(1539344714694,'KITPVP',200000),(1539344850509,'KITPVP',200000),(1539345155004,'KITPVP',190000),(1539345798380,'KITPVP',200000),(1539346838406,'KITPVP',200000),(1539347146402,'KITPVP',190000),(1539347637604,'KITPVP',200000),(1539348509370,'KITPVP',200000),(1539348813090,'KITPVP',190000),(1539350932437,'KITPVP',190000),(1539352774236,'KITPVP',200000),(1539353028759,'KITPVP',200000),(1539353332696,'KITPVP',190000),(1540298259258,'HUB',200000),(1540298370148,'HUB',200000),(1540298665267,'KITPVP',200000),(1540298681728,'HUB',190000),(1540298702784,'SURVIVAL',200000),(1541942579839,'HUB',200000),(1541942882276,'HUB',190000),(1541943435534,'HUB',200000),(1541943743520,'HUB',190000),(1541944715537,'HUB',190000),(1541945221783,'SURVIVAL',200000),(1541945371285,'HUB',200000),(1541945543097,'SURVIVAL',190000),(1541945819251,'HUB',200000),(1541945834441,'SURVIVAL',200000),(1541946120821,'HUB',190000),(1541946136255,'SURVIVAL',190000),(1541946244996,'HUB',200000),(1541946438808,'SURVIVAL',190000),(1541946546509,'HUB',190000),(1541946789263,'SURVIVAL',170000),(1541946848205,'HUB',190000),(1541949263134,'HUB',190000),(1541949565130,'HUB',190000),(1541949629192,'SURVIVAL',90000),(1541949867146,'HUB',190000),(1541952284254,'HUB',190000),(1541952436023,'SURVIVAL',70000),(1541952586641,'HUB',190000),(1543662364290,'KITPVP',200000),(1543662666794,'KITPVP',190000),(1543662968024,'KITPVP',190000),(1543663269328,'KITPVP',190000),(1543663571359,'KITPVP',190000),(1543663872670,'KITPVP',190000),(1543664173818,'KITPVP',190000),(1543664474906,'KITPVP',190000),(1543664776022,'KITPVP',190000),(1543665077141,'KITPVP',190000),(1543665378300,'KITPVP',190000),(1543665679329,'KITPVP',190000),(1543665980687,'KITPVP',190000),(1543666281996,'KITPVP',190000),(1543666583059,'KITPVP',190000),(1543666884277,'KITPVP',190000),(1543667185445,'KITPVP',190000),(1543667486599,'KITPVP',190000),(1543667787834,'KITPVP',190000),(1543668088978,'KITPVP',190000),(1543668390116,'KITPVP',190000),(1543668692796,'KITPVP',190000),(1543668994234,'KITPVP',190000),(1543669296115,'KITPVP',190000),(1543669515384,'KITPVP',200000),(1543669819139,'KITPVP',190000),(1543669967482,'KITPVP',200000),(1543670270135,'KITPVP',190000),(1543670572100,'KITPVP',190000),(1543670650308,'KITPVP',200000),(1543750507737,'KITPVP',200000),(1543750810740,'KITPVP',190000),(1543751112303,'KITPVP',190000),(1543751413924,'KITPVP',190000),(1543751715228,'KITPVP',190000),(1543752016683,'KITPVP',190000),(1543752318048,'KITPVP',190000),(1543752619542,'KITPVP',190000),(1543752920947,'KITPVP',190000),(1543753222393,'KITPVP',190000),(1543753523923,'KITPVP',190000),(1544269642555,'KITPVP',200000),(1544269949959,'KITPVP',190000),(1544270239720,'KITPVP',200000),(1544270542696,'KITPVP',190000),(1544270850543,'KITPVP',190000),(1544271153008,'KITPVP',190000),(1544271310318,'KITPVP',200000),(1544271612681,'KITPVP',190000),(1544271914523,'KITPVP',190000),(1544272215993,'KITPVP',190000),(1544272517828,'KITPVP',190000),(1544272819258,'KITPVP',190000),(1544273120929,'KITPVP',190000),(1544273422616,'KITPVP',190000),(1544273724828,'KITPVP',190000),(1544274027160,'KITPVP',190000),(1544274329736,'KITPVP',190000),(1544274633656,'KITPVP',190000),(1544274936402,'KITPVP',190000),(1544275239156,'KITPVP',190000),(1544275542410,'KITPVP',190000),(1544275844931,'KITPVP',190000),(1544276147586,'KITPVP',190000),(1544276450581,'KITPVP',190000),(1544276754010,'KITPVP',190000),(1544276998138,'KITPVP',200000),(1544277228950,'KITPVP',200000),(1544277444446,'KITPVP',200000),(1544277565554,'KITPVP',200000),(1544277678896,'KITPVP',200000),(1544278334339,'KITPVP',200000),(1544278638149,'KITPVP',190000),(1544278941090,'KITPVP',190000),(1544279111274,'KITPVP',200000),(1544279416693,'KITPVP',190000),(1544280373196,'KITPVP',200000),(1544280775636,'KITPVP',200000),(1544281012264,'KITPVP',200000),(1544281591355,'KITPVP',200000),(1544281897041,'KITPVP',190000),(1544282053177,'KITPVP',200000),(1544282281371,'KITPVP',200000),(1544282812944,'KITPVP',200000),(1544283056540,'KITPVP',200000),(1544286230831,'KITPVP',200000),(1544292893058,'KITPVP',200000),(1544293200453,'KITPVP',190000),(1544293503374,'KITPVP',190000),(1544293821649,'KITPVP',200000),(1544294127248,'KITPVP',190000),(1544294428928,'KITPVP',190000),(1544294730882,'KITPVP',190000),(1544295032727,'KITPVP',190000),(1544295544201,'KITPVP',200000);
/*!40000 ALTER TABLE `statstps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statsunique`
--

DROP TABLE IF EXISTS `statsunique`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statsunique` (
  `Time` bigint(20) DEFAULT NULL,
  `Unique` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statsunique`
--

LOCK TABLES `statsunique` WRITE;
/*!40000 ALTER TABLE `statsunique` DISABLE KEYS */;
INSERT INTO `statsunique` VALUES (1534849542517,0),(1534849602325,6),(1539006320098,6),(1539006620449,7),(1541952684153,7);
/*!40000 ALTER TABLE `statsunique` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statsvotes`
--

DROP TABLE IF EXISTS `statsvotes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statsvotes` (
  `Time` bigint(20) DEFAULT NULL,
  `Total` int(11) DEFAULT NULL,
  `Monthly` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statsvotes`
--

LOCK TABLES `statsvotes` WRITE;
/*!40000 ALTER TABLE `statsvotes` DISABLE KEYS */;
INSERT INTO `statsvotes` VALUES (1534850899686,0,0),(1534885659927,1500,0),(1535986469130,1500,0),(1536137614221,1500,660),(1537618956142,1500,660),(1538554251882,1500,0),(1541952683639,1500,0);
/*!40000 ALTER TABLE `statsvotes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survivalclaims`
--

DROP TABLE IF EXISTS `survivalclaims`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survivalclaims` (
  `ID` int(15) DEFAULT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `CreatedOn` varchar(32) DEFAULT NULL,
  `Corner1` varchar(100) DEFAULT NULL,
  `Corner2` varchar(100) DEFAULT NULL,
  `Owner` varchar(36) DEFAULT NULL,
  `Members` text,
  `Settings` text,
  `Parent` int(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survivalclaims`
--

LOCK TABLES `survivalclaims` WRITE;
/*!40000 ALTER TABLE `survivalclaims` DISABLE KEYS */;
INSERT INTO `survivalclaims` VALUES (83,'Claim #83','2000-01-01 00:00:00','world|1495.0|0.0|1493.0|0.0|0.0','world|1499.0|256.0|1495.0|0.0|0.0','33ee168b-5c2c-42c5-b3b2-d841ceb76b70','','',-1);
/*!40000 ALTER TABLE `survivalclaims` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survivalhomes`
--

DROP TABLE IF EXISTS `survivalhomes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survivalhomes` (
  `UUID` varchar(36) DEFAULT NULL,
  `Name` varchar(40) DEFAULT NULL,
  `Location` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survivalhomes`
--

LOCK TABLES `survivalhomes` WRITE;
/*!40000 ALTER TABLE `survivalhomes` DISABLE KEYS */;
INSERT INTO `survivalhomes` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','test','world|-266.5813867759285|64.0|3.5077976759208775|-209.61879|28.499573'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','home1','world|-275.0994270345137|64.0|-4.1213230399048015|-167.61877|29.849585'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','home2','world|-271.26685656980453|64.0|-8.499176325242946|-104.918816|21.299597'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','home3','world|-265.14073566264085|64.0|-12.275241407731313|-251.31871|11.849605'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','home1928ue12','world|-279.0735820643405|66.0|11.218674793479893|-75.36908|20.699585'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','home1928ue12123123','world|-272.0455465407654|64.0|12.614803994040154|-75.36908|20.699585'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','Mountain','world|-5971.346690561244|100.0|2988.8357964358356|-94.050415|25.500088'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9','home','world|-9.184198406283189|94.0|8.575680770432102|-111.90037|66.00046'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','h','world|-5.712204582047939|93.0|6.5200389792162285|58.650085|42.900112'),('33ee168b-5c2c-42c5-b3b2-d841ceb76b70','end','world_the_end|-10.597910731676448|2.0|-10.428362207108632|-172.80023|67.79983');
/*!40000 ALTER TABLE `survivalhomes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survivalplayers`
--

DROP TABLE IF EXISTS `survivalplayers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survivalplayers` (
  `UUID` varchar(36) DEFAULT NULL,
  `EarthMoney` int(11) DEFAULT NULL,
  `ClaimBlocks` int(11) DEFAULT NULL,
  `BackLocation` varchar(100) DEFAULT NULL,
  `BackCharges` int(11) DEFAULT NULL,
  `LastBedEnter` bigint(20) DEFAULT NULL,
  `LogoutLocation` varchar(100) DEFAULT NULL,
  `LogoutFly` tinyint(1) DEFAULT NULL,
  `ExtraHomes` int(11) DEFAULT NULL,
  `WarpSlotShop` tinyint(1) DEFAULT NULL,
  `WarpSlotPrisms` tinyint(1) DEFAULT NULL,
  `FavoriteWarps` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survivalplayers`
--

LOCK TABLES `survivalplayers` WRITE;
/*!40000 ALTER TABLE `survivalplayers` DISABLE KEYS */;
INSERT INTO `survivalplayers` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',950790,350,'world|-99.69999998807907|77.0|158.29338873053317|116.10148|23.99935',419,1533988113601,'world|-61.45981485578442|92.0|131.61691207066093|-126.89938|34.649723',0,4,0,1,'0|1'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',981000,250,'world|-19.42530822496201|85.0|115.4164312844145|-248.40399|27.749884',307,0,'world|7495.549853767586|71.0|-6011.427518387704|-253.05113|33.900227',0,0,0,0,'');
/*!40000 ALTER TABLE `survivalplayers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survivalwarps`
--

DROP TABLE IF EXISTS `survivalwarps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survivalwarps` (
  `ID` int(15) DEFAULT NULL,
  `UUID` varchar(36) DEFAULT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `Enabled` tinyint(1) DEFAULT NULL,
  `Type` varchar(32) DEFAULT NULL,
  `IconID` int(8) DEFAULT NULL,
  `Location` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survivalwarps`
--

LOCK TABLES `survivalwarps` WRITE;
/*!40000 ALTER TABLE `survivalwarps` DISABLE KEYS */;
INSERT INTO `survivalwarps` VALUES (0,'33ee168b-5c2c-42c5-b3b2-d841ceb76b70','O_o_Fadi_o_Os Warp',0,'VIP_SLOT',37,'world|990.8506286226564|82.0|978.9663962989972|-110.76611|36.449467'),(1,'33ee168b-5c2c-42c5-b3b2-d841ceb76b70','test',0,'PRISMS_SLOT',23,'null');
/*!40000 ALTER TABLE `survivalwarps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `votes`
--

DROP TABLE IF EXISTS `votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `votes` (
  `UUID` varchar(36) DEFAULT NULL,
  `December2018` int(11) DEFAULT NULL,
  `November2018` int(11) DEFAULT NULL,
  `October2018` int(11) DEFAULT NULL,
  `September2018` int(11) DEFAULT NULL,
  `August2018` int(11) DEFAULT NULL,
  `July2018` int(11) DEFAULT NULL,
  `June2018` int(11) DEFAULT '0',
  `TotalVotes` int(11) DEFAULT NULL,
  `CachedVotes` int(11) DEFAULT NULL,
  `VoteTimeStamps` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `votes`
--

LOCK TABLES `votes` WRITE;
/*!40000 ALTER TABLE `votes` DISABLE KEYS */;
INSERT INTO `votes` VALUES ('33ee168b-5c2c-42c5-b3b2-d841ceb76b70',0,0,0,110,0,0,200,250,0,'null'),('69626b0c-6910-49b0-9a1d-8cf83141d60a',0,0,0,110,0,0,200,250,110,'null'),('01a5412b-275b-4f42-aea9-1bef163210b9',0,0,0,110,0,0,200,250,110,'null'),('9b6d3590-a9f5-4e33-a3a5-d45571f22b10',0,0,0,110,0,0,200,250,110,'null'),('a8933f18-3825-4453-9c8a-bdaba7db8334',0,0,0,110,0,0,200,250,110,'null'),('cef4ba59-f58c-47b3-b3b9-ebd2f83029b9',0,0,0,110,0,0,200,250,0,'null'),('6625bd11-1a1f-4bd0-b46f-a49fb9c71e7d',0,0,0,NULL,NULL,NULL,0,0,0,'null');
/*!40000 ALTER TABLE `votes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-09 10:45:33

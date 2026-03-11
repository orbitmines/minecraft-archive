-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u1
-- http://www.phpmyadmin.net
--
-- Machine: localhost
-- Gegenereerd op: 11 sep 2016 om 00:50
-- Serverversie: 5.5.46-0+deb8u1
-- PHP-versie: 5.6.17-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databank: `OrbitMines`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors`
--

CREATE TABLE IF NOT EXISTS `ChatColors` (
  `uuid` varchar(50) NOT NULL,
  `color` varchar(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-Black`
--

CREATE TABLE IF NOT EXISTS `ChatColors-Black` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-Blue`
--

CREATE TABLE IF NOT EXISTS `ChatColors-Blue` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-Bold`
--

CREATE TABLE IF NOT EXISTS `ChatColors-Bold` (
  `uuid` varchar(50) NOT NULL,
  `bold` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-Cursive`
--

CREATE TABLE IF NOT EXISTS `ChatColors-Cursive` (
  `uuid` varchar(50) NOT NULL,
  `cursive` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-DarkBlue`
--

CREATE TABLE IF NOT EXISTS `ChatColors-DarkBlue` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-DarkGray`
--

CREATE TABLE IF NOT EXISTS `ChatColors-DarkGray` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-DarkRed`
--

CREATE TABLE IF NOT EXISTS `ChatColors-DarkRed` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-Green`
--

CREATE TABLE IF NOT EXISTS `ChatColors-Green` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-LightBlue`
--

CREATE TABLE IF NOT EXISTS `ChatColors-LightBlue` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-LightGreen`
--

CREATE TABLE IF NOT EXISTS `ChatColors-LightGreen` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-Pink`
--

CREATE TABLE IF NOT EXISTS `ChatColors-Pink` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-Red`
--

CREATE TABLE IF NOT EXISTS `ChatColors-Red` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChatColors-White`
--

CREATE TABLE IF NOT EXISTS `ChatColors-White` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `CheckedForOldUser`
--

CREATE TABLE IF NOT EXISTS `CheckedForOldUser` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChickenFight-BestStreak`
--

CREATE TABLE IF NOT EXISTS `ChickenFight-BestStreak` (
  `uuid` varchar(50) NOT NULL,
  `beststreak` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChickenFight-Kills`
--

CREATE TABLE IF NOT EXISTS `ChickenFight-Kills` (
  `uuid` varchar(50) NOT NULL,
  `kills` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChickenFight-Loses`
--

CREATE TABLE IF NOT EXISTS `ChickenFight-Loses` (
  `uuid` varchar(50) NOT NULL,
  `loses` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ChickenFight-Wins`
--

CREATE TABLE IF NOT EXISTS `ChickenFight-Wins` (
  `uuid` varchar(50) NOT NULL,
  `wins` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-ArmorStand`
--

CREATE TABLE IF NOT EXISTS `Dis-ArmorStand` (
  `uuid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Bat`
--

CREATE TABLE IF NOT EXISTS `Dis-Bat` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-CaveSpider`
--

CREATE TABLE IF NOT EXISTS `Dis-CaveSpider` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Chicken`
--

CREATE TABLE IF NOT EXISTS `Dis-Chicken` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Cow`
--

CREATE TABLE IF NOT EXISTS `Dis-Cow` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Creeper`
--

CREATE TABLE IF NOT EXISTS `Dis-Creeper` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Enderman`
--

CREATE TABLE IF NOT EXISTS `Dis-Enderman` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Ghast`
--

CREATE TABLE IF NOT EXISTS `Dis-Ghast` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Horse`
--

CREATE TABLE IF NOT EXISTS `Dis-Horse` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-IronGolem`
--

CREATE TABLE IF NOT EXISTS `Dis-IronGolem` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-MagmaCube`
--

CREATE TABLE IF NOT EXISTS `Dis-MagmaCube` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-MushroomCow`
--

CREATE TABLE IF NOT EXISTS `Dis-MushroomCow` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Ocelot`
--

CREATE TABLE IF NOT EXISTS `Dis-Ocelot` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Rabbit`
--

CREATE TABLE IF NOT EXISTS `Dis-Rabbit` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Sheep`
--

CREATE TABLE IF NOT EXISTS `Dis-Sheep` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Silverfish`
--

CREATE TABLE IF NOT EXISTS `Dis-Silverfish` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Skeleton`
--

CREATE TABLE IF NOT EXISTS `Dis-Skeleton` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Slime`
--

CREATE TABLE IF NOT EXISTS `Dis-Slime` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Snowman`
--

CREATE TABLE IF NOT EXISTS `Dis-Snowman` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Spider`
--

CREATE TABLE IF NOT EXISTS `Dis-Spider` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Squid`
--

CREATE TABLE IF NOT EXISTS `Dis-Squid` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Witch`
--

CREATE TABLE IF NOT EXISTS `Dis-Witch` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-Wolf`
--

CREATE TABLE IF NOT EXISTS `Dis-Wolf` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Dis-ZombiePigman`
--

CREATE TABLE IF NOT EXISTS `Dis-ZombiePigman` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Fireworks-Passes`
--

CREATE TABLE IF NOT EXISTS `Fireworks-Passes` (
  `uuid` varchar(50) NOT NULL,
  `passes` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Fireworks-Settings`
--

CREATE TABLE IF NOT EXISTS `Fireworks-Settings` (
  `uuid` varchar(50) NOT NULL,
  `settings` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Bank`
--

CREATE TABLE IF NOT EXISTS `FoG-Bank` (
  `uuid` varchar(50) NOT NULL,
  `bank` varchar(10000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-CurrentQuests`
--

CREATE TABLE IF NOT EXISTS `FoG-CurrentQuests` (
  `uuid` varchar(50) NOT NULL,
  `currentquests` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-CurrentShield`
--

CREATE TABLE IF NOT EXISTS `FoG-CurrentShield` (
  `uuid` varchar(50) NOT NULL,
  `currentshield` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Deaths`
--

CREATE TABLE IF NOT EXISTS `FoG-Deaths` (
  `uuid` varchar(50) NOT NULL,
  `deaths` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Exp`
--

CREATE TABLE IF NOT EXISTS `FoG-Exp` (
  `uuid` varchar(50) NOT NULL,
  `exp` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-ExploredMaps`
--

CREATE TABLE IF NOT EXISTS `FoG-ExploredMaps` (
  `uuid` varchar(50) NOT NULL,
  `exploredmaps` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Faction`
--

CREATE TABLE IF NOT EXISTS `FoG-Faction` (
  `uuid` varchar(50) NOT NULL,
  `faction` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-FactionPlayers`
--

CREATE TABLE IF NOT EXISTS `FoG-FactionPlayers` (
  `faction` varchar(10) NOT NULL,
  `players` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Kills`
--

CREATE TABLE IF NOT EXISTS `FoG-Kills` (
  `uuid` varchar(50) NOT NULL,
  `kills` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Levels`
--

CREATE TABLE IF NOT EXISTS `FoG-Levels` (
  `uuid` varchar(50) NOT NULL,
  `levels` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Quests`
--

CREATE TABLE IF NOT EXISTS `FoG-Quests` (
  `uuid` varchar(50) NOT NULL,
  `quests` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Shields`
--

CREATE TABLE IF NOT EXISTS `FoG-Shields` (
  `uuid` varchar(50) NOT NULL,
  `shields` varchar(6000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Silver`
--

CREATE TABLE IF NOT EXISTS `FoG-Silver` (
  `uuid` varchar(50) NOT NULL,
  `silver` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Suit`
--

CREATE TABLE IF NOT EXISTS `FoG-Suit` (
  `uuid` varchar(50) NOT NULL,
  `suit` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Swords`
--

CREATE TABLE IF NOT EXISTS `FoG-Swords` (
  `uuid` varchar(50) NOT NULL,
  `swords` varchar(2000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Tools`
--

CREATE TABLE IF NOT EXISTS `FoG-Tools` (
  `uuid` varchar(50) NOT NULL,
  `tools` varchar(2000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoG-Tutorial`
--

CREATE TABLE IF NOT EXISTS `FoG-Tutorial` (
  `uuid` varchar(50) NOT NULL,
  `tutorial` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Friends`
--

CREATE TABLE IF NOT EXISTS `Friends` (
  `uuid` varchar(50) NOT NULL,
  `friends` varchar(2000) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Gadgets-BookExplosion`
--

CREATE TABLE IF NOT EXISTS `Gadgets-BookExplosion` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Gadgets-CreeperLauncher`
--

CREATE TABLE IF NOT EXISTS `Gadgets-CreeperLauncher` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Gadgets-FlameThrower`
--

CREATE TABLE IF NOT EXISTS `Gadgets-FlameThrower` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Gadgets-GrapplingHook`
--

CREATE TABLE IF NOT EXISTS `Gadgets-GrapplingHook` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Gadgets-MagmaCubeSoccer`
--

CREATE TABLE IF NOT EXISTS `Gadgets-MagmaCubeSoccer` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Gadgets-Paintballs`
--

CREATE TABLE IF NOT EXISTS `Gadgets-Paintballs` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Gadgets-PetRide`
--

CREATE TABLE IF NOT EXISTS `Gadgets-PetRide` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Gadgets-SnowmanAttack`
--

CREATE TABLE IF NOT EXISTS `Gadgets-SnowmanAttack` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Gadgets-SwapTeleporter`
--

CREATE TABLE IF NOT EXISTS `Gadgets-SwapTeleporter` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `GhostAttack-BestStreak`
--

CREATE TABLE IF NOT EXISTS `GhostAttack-BestStreak` (
  `uuid` varchar(50) NOT NULL,
  `beststreak` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `GhostAttack-GhostKills`
--

CREATE TABLE IF NOT EXISTS `GhostAttack-GhostKills` (
  `uuid` varchar(50) NOT NULL,
  `ghostkills` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `GhostAttack-GhostWins`
--

CREATE TABLE IF NOT EXISTS `GhostAttack-GhostWins` (
  `uuid` varchar(50) NOT NULL,
  `ghostwins` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `GhostAttack-Kills`
--

CREATE TABLE IF NOT EXISTS `GhostAttack-Kills` (
  `uuid` varchar(50) NOT NULL,
  `kills` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `GhostAttack-Loses`
--

CREATE TABLE IF NOT EXISTS `GhostAttack-Loses` (
  `uuid` varchar(50) NOT NULL,
  `loses` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `GhostAttack-Wins`
--

CREATE TABLE IF NOT EXISTS `GhostAttack-Wins` (
  `uuid` varchar(50) NOT NULL,
  `wins` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `griefprevention_claimdata`
--

CREATE TABLE IF NOT EXISTS `griefprevention_claimdata` (
  `id` int(15) DEFAULT NULL,
  `owner` varchar(50) DEFAULT NULL,
  `lessercorner` varchar(100) DEFAULT NULL,
  `greatercorner` varchar(100) DEFAULT NULL,
  `builders` varchar(1000) DEFAULT NULL,
  `containers` varchar(1000) DEFAULT NULL,
  `accessors` varchar(1000) DEFAULT NULL,
  `managers` varchar(1000) DEFAULT NULL,
  `parentid` int(15) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `griefprevention_nextclaimid`
--

CREATE TABLE IF NOT EXISTS `griefprevention_nextclaimid` (
  `nextid` int(15) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `griefprevention_playerdata`
--

CREATE TABLE IF NOT EXISTS `griefprevention_playerdata` (
  `name` varchar(50) DEFAULT NULL,
  `lastlogin` datetime DEFAULT NULL,
  `accruedblocks` int(15) DEFAULT NULL,
  `bonusblocks` int(15) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `griefprevention_schemaversion`
--

CREATE TABLE IF NOT EXISTS `griefprevention_schemaversion` (
  `version` int(15) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-AcaciaWood`
--

CREATE TABLE IF NOT EXISTS `Hats-AcaciaWood` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Andesite`
--

CREATE TABLE IF NOT EXISTS `Hats-Andesite` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Bedrock`
--

CREATE TABLE IF NOT EXISTS `Hats-Bedrock` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-BlackGlass`
--

CREATE TABLE IF NOT EXISTS `Hats-BlackGlass` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-BlockTrail`
--

CREATE TABLE IF NOT EXISTS `Hats-BlockTrail` (
  `uuid` varchar(50) NOT NULL,
  `blocktrail` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-BlueGlass`
--

CREATE TABLE IF NOT EXISTS `Hats-BlueGlass` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Bookshelf`
--

CREATE TABLE IF NOT EXISTS `Hats-Bookshelf` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-BrownGlass`
--

CREATE TABLE IF NOT EXISTS `Hats-BrownGlass` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Cactus`
--

CREATE TABLE IF NOT EXISTS `Hats-Cactus` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Chest`
--

CREATE TABLE IF NOT EXISTS `Hats-Chest` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-ChiselledStoneBricks`
--

CREATE TABLE IF NOT EXISTS `Hats-ChiselledStoneBricks` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-CoalBlock`
--

CREATE TABLE IF NOT EXISTS `Hats-CoalBlock` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-CoalOre`
--

CREATE TABLE IF NOT EXISTS `Hats-CoalOre` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-CyanGlass`
--

CREATE TABLE IF NOT EXISTS `Hats-CyanGlass` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-DarkPrismarine`
--

CREATE TABLE IF NOT EXISTS `Hats-DarkPrismarine` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Diorite`
--

CREATE TABLE IF NOT EXISTS `Hats-Diorite` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Furnace`
--

CREATE TABLE IF NOT EXISTS `Hats-Furnace` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Glass`
--

CREATE TABLE IF NOT EXISTS `Hats-Glass` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Glowstone`
--

CREATE TABLE IF NOT EXISTS `Hats-Glowstone` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Granite`
--

CREATE TABLE IF NOT EXISTS `Hats-Granite` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Grass`
--

CREATE TABLE IF NOT EXISTS `Hats-Grass` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-GreenGlass`
--

CREATE TABLE IF NOT EXISTS `Hats-GreenGlass` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-HayBale`
--

CREATE TABLE IF NOT EXISTS `Hats-HayBale` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Ice`
--

CREATE TABLE IF NOT EXISTS `Hats-Ice` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-LapisBlock`
--

CREATE TABLE IF NOT EXISTS `Hats-LapisBlock` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-LapisOre`
--

CREATE TABLE IF NOT EXISTS `Hats-LapisOre` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Leaves`
--

CREATE TABLE IF NOT EXISTS `Hats-Leaves` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-MagentaGlass`
--

CREATE TABLE IF NOT EXISTS `Hats-MagentaGlass` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Melon`
--

CREATE TABLE IF NOT EXISTS `Hats-Melon` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Mycelium`
--

CREATE TABLE IF NOT EXISTS `Hats-Mycelium` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Netherrack`
--

CREATE TABLE IF NOT EXISTS `Hats-Netherrack` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-OrangeGlass`
--

CREATE TABLE IF NOT EXISTS `Hats-OrangeGlass` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-PrismarineBricks`
--

CREATE TABLE IF NOT EXISTS `Hats-PrismarineBricks` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-QuartzBlock`
--

CREATE TABLE IF NOT EXISTS `Hats-QuartzBlock` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-QuartzOre`
--

CREATE TABLE IF NOT EXISTS `Hats-QuartzOre` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-RedGlass`
--

CREATE TABLE IF NOT EXISTS `Hats-RedGlass` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-RedstoneBlock`
--

CREATE TABLE IF NOT EXISTS `Hats-RedstoneBlock` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-RedstoneOre`
--

CREATE TABLE IF NOT EXISTS `Hats-RedstoneOre` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-RedWool`
--

CREATE TABLE IF NOT EXISTS `Hats-RedWool` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-SeaLantern`
--

CREATE TABLE IF NOT EXISTS `Hats-SeaLantern` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-SlimeBlock`
--

CREATE TABLE IF NOT EXISTS `Hats-SlimeBlock` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Snow`
--

CREATE TABLE IF NOT EXISTS `Hats-Snow` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-SoulSand`
--

CREATE TABLE IF NOT EXISTS `Hats-SoulSand` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Sponge`
--

CREATE TABLE IF NOT EXISTS `Hats-Sponge` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-StoneBricks`
--

CREATE TABLE IF NOT EXISTS `Hats-StoneBricks` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-TNT`
--

CREATE TABLE IF NOT EXISTS `Hats-TNT` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-WetSponge`
--

CREATE TABLE IF NOT EXISTS `Hats-WetSponge` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-Workbench`
--

CREATE TABLE IF NOT EXISTS `Hats-Workbench` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hats-YellowGlass`
--

CREATE TABLE IF NOT EXISTS `Hats-YellowGlass` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hub-Players`
--

CREATE TABLE IF NOT EXISTS `Hub-Players` (
  `uuid` varchar(50) NOT NULL,
  `players` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hub-Scoreboard`
--

CREATE TABLE IF NOT EXISTS `Hub-Scoreboard` (
  `uuid` varchar(50) NOT NULL,
  `scoreboard` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Hub-Stacker`
--

CREATE TABLE IF NOT EXISTS `Hub-Stacker` (
  `uuid` varchar(50) NOT NULL,
  `stacker` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `KitPvP-BestStreak`
--

CREATE TABLE IF NOT EXISTS `KitPvP-BestStreak` (
  `uuid` varchar(50) NOT NULL,
  `beststreak` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `KitPvP-Deaths`
--

CREATE TABLE IF NOT EXISTS `KitPvP-Deaths` (
  `uuid` varchar(50) NOT NULL,
  `deaths` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `KitPvP-Exp`
--

CREATE TABLE IF NOT EXISTS `KitPvP-Exp` (
  `uuid` varchar(50) NOT NULL,
  `exp` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `KitPvP-Kills`
--

CREATE TABLE IF NOT EXISTS `KitPvP-Kills` (
  `uuid` varchar(50) NOT NULL,
  `kills` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `KitPvP-Levels`
--

CREATE TABLE IF NOT EXISTS `KitPvP-Levels` (
  `uuid` varchar(50) NOT NULL,
  `levels` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `KitPvP-Masteries`
--

CREATE TABLE IF NOT EXISTS `KitPvP-Masteries` (
  `uuid` varchar(50) NOT NULL,
  `masteries` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `KitPvP-Money`
--

CREATE TABLE IF NOT EXISTS `KitPvP-Money` (
  `uuid` varchar(50) NOT NULL,
  `money` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Archer`
--

CREATE TABLE IF NOT EXISTS `Kits-Archer` (
  `uuid` varchar(50) NOT NULL,
  `archer` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Assassin`
--

CREATE TABLE IF NOT EXISTS `Kits-Assassin` (
  `uuid` varchar(50) NOT NULL,
  `assassin` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Beast`
--

CREATE TABLE IF NOT EXISTS `Kits-Beast` (
  `uuid` varchar(50) NOT NULL,
  `beast` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Blaze`
--

CREATE TABLE IF NOT EXISTS `Kits-Blaze` (
  `uuid` varchar(50) NOT NULL,
  `blaze` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Bunny`
--

CREATE TABLE IF NOT EXISTS `Kits-Bunny` (
  `uuid` varchar(50) NOT NULL,
  `bunny` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-DarkMage`
--

CREATE TABLE IF NOT EXISTS `Kits-DarkMage` (
  `uuid` varchar(50) NOT NULL,
  `darkmage` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Drunk`
--

CREATE TABLE IF NOT EXISTS `Kits-Drunk` (
  `uuid` varchar(50) NOT NULL,
  `drunk` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Fish`
--

CREATE TABLE IF NOT EXISTS `Kits-Fish` (
  `uuid` varchar(50) NOT NULL,
  `fish` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Fisherman`
--

CREATE TABLE IF NOT EXISTS `Kits-Fisherman` (
  `uuid` varchar(50) NOT NULL,
  `fisherman` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-GrimReaper`
--

CREATE TABLE IF NOT EXISTS `Kits-GrimReaper` (
  `uuid` varchar(50) NOT NULL,
  `grimreaper` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Heavy`
--

CREATE TABLE IF NOT EXISTS `Kits-Heavy` (
  `uuid` varchar(50) NOT NULL,
  `heavy` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-King`
--

CREATE TABLE IF NOT EXISTS `Kits-King` (
  `uuid` varchar(50) NOT NULL,
  `king` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Knight`
--

CREATE TABLE IF NOT EXISTS `Kits-Knight` (
  `uuid` varchar(50) NOT NULL,
  `knight` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Librarian`
--

CREATE TABLE IF NOT EXISTS `Kits-Librarian` (
  `uuid` varchar(50) NOT NULL,
  `librarian` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Lord`
--

CREATE TABLE IF NOT EXISTS `Kits-Lord` (
  `uuid` varchar(50) NOT NULL,
  `lord` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Miner`
--

CREATE TABLE IF NOT EXISTS `Kits-Miner` (
  `uuid` varchar(50) NOT NULL,
  `miner` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Necromancer`
--

CREATE TABLE IF NOT EXISTS `Kits-Necromancer` (
  `uuid` varchar(50) NOT NULL,
  `necromancer` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Pyro`
--

CREATE TABLE IF NOT EXISTS `Kits-Pyro` (
  `uuid` varchar(50) NOT NULL,
  `pyro` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-SnowGolem`
--

CREATE TABLE IF NOT EXISTS `Kits-SnowGolem` (
  `uuid` varchar(50) NOT NULL,
  `snowgolem` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Soldier`
--

CREATE TABLE IF NOT EXISTS `Kits-Soldier` (
  `uuid` varchar(50) NOT NULL,
  `soldier` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Spider`
--

CREATE TABLE IF NOT EXISTS `Kits-Spider` (
  `uuid` varchar(50) NOT NULL,
  `spider` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Tank`
--

CREATE TABLE IF NOT EXISTS `Kits-Tank` (
  `uuid` varchar(50) NOT NULL,
  `tank` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-TNT`
--

CREATE TABLE IF NOT EXISTS `Kits-TNT` (
  `uuid` varchar(50) NOT NULL,
  `tnt` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Tree`
--

CREATE TABLE IF NOT EXISTS `Kits-Tree` (
  `uuid` varchar(50) NOT NULL,
  `tree` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Vampire`
--

CREATE TABLE IF NOT EXISTS `Kits-Vampire` (
  `uuid` varchar(50) NOT NULL,
  `vampire` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Villager`
--

CREATE TABLE IF NOT EXISTS `Kits-Villager` (
  `uuid` varchar(50) NOT NULL,
  `villager` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Kits-Wizard`
--

CREATE TABLE IF NOT EXISTS `Kits-Wizard` (
  `uuid` varchar(50) NOT NULL,
  `wizard` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Language`
--

CREATE TABLE IF NOT EXISTS `Language` (
  `uuid` varchar(50) NOT NULL,
  `language` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `MasterMind-BestGame`
--

CREATE TABLE IF NOT EXISTS `MasterMind-BestGame` (
  `uuid` varchar(50) NOT NULL,
  `turns` int(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `MasterMind-Wins`
--

CREATE TABLE IF NOT EXISTS `MasterMind-Wins` (
  `uuid` varchar(50) NOT NULL,
  `wins` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `MiniGameCoins`
--

CREATE TABLE IF NOT EXISTS `MiniGameCoins` (
  `uuid` varchar(50) NOT NULL,
  `coins` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `MiniGames-MGTickets`
--

CREATE TABLE IF NOT EXISTS `MiniGames-MGTickets` (
  `uuid` varchar(50) NOT NULL,
  `mgtickets` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `MiniGames-Tickets`
--

CREATE TABLE IF NOT EXISTS `MiniGames-Tickets` (
  `uuid` varchar(50) NOT NULL,
  `tickets` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `OrbitMinesTokens`
--

CREATE TABLE IF NOT EXISTS `OrbitMinesTokens` (
  `uuid` varchar(50) NOT NULL,
  `omt` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ParkourCompleted`
--

CREATE TABLE IF NOT EXISTS `ParkourCompleted` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Chicken`
--

CREATE TABLE IF NOT EXISTS `Pets-Chicken` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Cow`
--

CREATE TABLE IF NOT EXISTS `Pets-Cow` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Creeper`
--

CREATE TABLE IF NOT EXISTS `Pets-Creeper` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Horse`
--

CREATE TABLE IF NOT EXISTS `Pets-Horse` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-MagmaCube`
--

CREATE TABLE IF NOT EXISTS `Pets-MagmaCube` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-MushroomCow`
--

CREATE TABLE IF NOT EXISTS `Pets-MushroomCow` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Ocelot`
--

CREATE TABLE IF NOT EXISTS `Pets-Ocelot` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Pig`
--

CREATE TABLE IF NOT EXISTS `Pets-Pig` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Sheep`
--

CREATE TABLE IF NOT EXISTS `Pets-Sheep` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Silverfish`
--

CREATE TABLE IF NOT EXISTS `Pets-Silverfish` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Slime`
--

CREATE TABLE IF NOT EXISTS `Pets-Slime` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Spider`
--

CREATE TABLE IF NOT EXISTS `Pets-Spider` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Squid`
--

CREATE TABLE IF NOT EXISTS `Pets-Squid` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Pets-Wolf`
--

CREATE TABLE IF NOT EXISTS `Pets-Wolf` (
  `uuid` varchar(50) NOT NULL,
  `petname` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `PlayerBans`
--

CREATE TABLE IF NOT EXISTS `PlayerBans` (
  `uuid` varchar(50) NOT NULL,
  `bannedby` varchar(48) NOT NULL,
  `reason` varchar(150) NOT NULL,
  `bannedon` varchar(20) NOT NULL,
  `banneduntil` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `PlayerIPBans`
--

CREATE TABLE IF NOT EXISTS `PlayerIPBans` (
  `ip` varchar(100) NOT NULL,
  `bannedby` varchar(48) NOT NULL,
  `reason` varchar(200) NOT NULL,
  `bannedon` varchar(20) NOT NULL,
  `banneduntil` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `PlayerIPs`
--

CREATE TABLE IF NOT EXISTS `PlayerIPs` (
  `uuid` varchar(50) NOT NULL,
  `ip` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `PlayerLastOnline`
--

CREATE TABLE IF NOT EXISTS `PlayerLastOnline` (
  `uuid` varchar(50) NOT NULL,
  `date` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `PlayerMonthlyVIPPoints`
--

CREATE TABLE IF NOT EXISTS `PlayerMonthlyVIPPoints` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `PlayerNicknames`
--

CREATE TABLE IF NOT EXISTS `PlayerNicknames` (
  `uuid` varchar(50) NOT NULL,
  `nick` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `PlayerReports`
--

CREATE TABLE IF NOT EXISTS `PlayerReports` (
  `uuid` varchar(50) NOT NULL,
  `reportedby` varchar(50) NOT NULL,
  `reason` varchar(200) NOT NULL,
  `reportedon` varchar(20) NOT NULL,
  `server` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `PlayerSilentJoin`
--

CREATE TABLE IF NOT EXISTS `PlayerSilentJoin` (
  `uuid` varchar(50) NOT NULL,
  `silentjoin` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `PlayerUUIDs`
--

CREATE TABLE IF NOT EXISTS `PlayerUUIDs` (
  `name` varchar(16) NOT NULL,
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Poll`
--

CREATE TABLE IF NOT EXISTS `Poll` (
  `uuid` varchar(50) NOT NULL,
  `vote` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Prison-ClockEnabled`
--

CREATE TABLE IF NOT EXISTS `Prison-ClockEnabled` (
  `uuid` varchar(50) NOT NULL,
  `clockenabled` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Prison-GambleTickets`
--

CREATE TABLE IF NOT EXISTS `Prison-GambleTickets` (
  `uuid` varchar(50) NOT NULL,
  `gambletickets` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Prison-Gold`
--

CREATE TABLE IF NOT EXISTS `Prison-Gold` (
  `uuid` varchar(50) NOT NULL,
  `gold` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Prison-Rank`
--

CREATE TABLE IF NOT EXISTS `Prison-Rank` (
  `uuid` varchar(50) NOT NULL,
  `rank` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Rank-Staff`
--

CREATE TABLE IF NOT EXISTS `Rank-Staff` (
  `uuid` varchar(50) NOT NULL,
  `staff` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Rank-VIP`
--

CREATE TABLE IF NOT EXISTS `Rank-VIP` (
  `uuid` varchar(50) NOT NULL,
  `vip` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Ranks`
--

CREATE TABLE IF NOT EXISTS `Ranks` (
  `uuid` varchar(50) NOT NULL,
  `rank` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Skywars-BestStreak`
--

CREATE TABLE IF NOT EXISTS `Skywars-BestStreak` (
  `uuid` varchar(50) NOT NULL,
  `beststreak` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Skywars-Cage`
--

CREATE TABLE IF NOT EXISTS `Skywars-Cage` (
  `uuid` varchar(50) NOT NULL,
  `cage` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Skywars-Kills`
--

CREATE TABLE IF NOT EXISTS `Skywars-Kills` (
  `uuid` varchar(50) NOT NULL,
  `kills` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Skywars-Loses`
--

CREATE TABLE IF NOT EXISTS `Skywars-Loses` (
  `uuid` varchar(50) NOT NULL,
  `loses` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Skywars-Wins`
--

CREATE TABLE IF NOT EXISTS `Skywars-Wins` (
  `uuid` varchar(50) NOT NULL,
  `wins` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Splatcraft-BestStreak`
--

CREATE TABLE IF NOT EXISTS `Splatcraft-BestStreak` (
  `uuid` varchar(50) NOT NULL,
  `beststreak` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Splatcraft-BlocksColored`
--

CREATE TABLE IF NOT EXISTS `Splatcraft-BlocksColored` (
  `uuid` varchar(50) NOT NULL,
  `blockscolored` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Splatcraft-Kills`
--

CREATE TABLE IF NOT EXISTS `Splatcraft-Kills` (
  `uuid` varchar(50) NOT NULL,
  `kills` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Splatcraft-Loses`
--

CREATE TABLE IF NOT EXISTS `Splatcraft-Loses` (
  `uuid` varchar(50) NOT NULL,
  `loses` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Splatcraft-Wins`
--

CREATE TABLE IF NOT EXISTS `Splatcraft-Wins` (
  `uuid` varchar(50) NOT NULL,
  `wins` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Spleef-BestStreak`
--

CREATE TABLE IF NOT EXISTS `Spleef-BestStreak` (
  `uuid` varchar(50) NOT NULL,
  `beststreak` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Spleef-BlocksBroken`
--

CREATE TABLE IF NOT EXISTS `Spleef-BlocksBroken` (
  `uuid` varchar(50) NOT NULL,
  `blocksbroken` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Spleef-Kills`
--

CREATE TABLE IF NOT EXISTS `Spleef-Kills` (
  `uuid` varchar(50) NOT NULL,
  `kills` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Spleef-Loses`
--

CREATE TABLE IF NOT EXISTS `Spleef-Loses` (
  `uuid` varchar(50) NOT NULL,
  `loses` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Spleef-Wins`
--

CREATE TABLE IF NOT EXISTS `Spleef-Wins` (
  `uuid` varchar(50) NOT NULL,
  `wins` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Survival-ExtraHomes`
--

CREATE TABLE IF NOT EXISTS `Survival-ExtraHomes` (
  `uuid` varchar(50) NOT NULL,
  `extrahomes` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Survival-ExtraShops`
--

CREATE TABLE IF NOT EXISTS `Survival-ExtraShops` (
  `uuid` varchar(50) NOT NULL,
  `extrashops` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Survival-ExtraWarps`
--

CREATE TABLE IF NOT EXISTS `Survival-ExtraWarps` (
  `uuid` varchar(50) NOT NULL,
  `extrawarps` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Survival-Money`
--

CREATE TABLE IF NOT EXISTS `Survival-Money` (
  `uuid` varchar(50) NOT NULL,
  `money` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `SurvivalGames-BestStreak`
--

CREATE TABLE IF NOT EXISTS `SurvivalGames-BestStreak` (
  `uuid` varchar(50) NOT NULL,
  `beststreak` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `SurvivalGames-Color`
--

CREATE TABLE IF NOT EXISTS `SurvivalGames-Color` (
  `uuid` varchar(50) NOT NULL,
  `color` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `SurvivalGames-Kills`
--

CREATE TABLE IF NOT EXISTS `SurvivalGames-Kills` (
  `uuid` varchar(50) NOT NULL,
  `kills` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `SurvivalGames-Loses`
--

CREATE TABLE IF NOT EXISTS `SurvivalGames-Loses` (
  `uuid` varchar(50) NOT NULL,
  `loses` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `SurvivalGames-Wins`
--

CREATE TABLE IF NOT EXISTS `SurvivalGames-Wins` (
  `uuid` varchar(50) NOT NULL,
  `wins` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trail`
--

CREATE TABLE IF NOT EXISTS `Trail` (
  `uuid` varchar(50) NOT NULL,
  `trail` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-AngryVillager`
--

CREATE TABLE IF NOT EXISTS `Trails-AngryVillager` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Bubble`
--

CREATE TABLE IF NOT EXISTS `Trails-Bubble` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Crit`
--

CREATE TABLE IF NOT EXISTS `Trails-Crit` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-ETable`
--

CREATE TABLE IF NOT EXISTS `Trails-ETable` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Explode`
--

CREATE TABLE IF NOT EXISTS `Trails-Explode` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Firework`
--

CREATE TABLE IF NOT EXISTS `Trails-Firework` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-HappyVillager`
--

CREATE TABLE IF NOT EXISTS `Trails-HappyVillager` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Hearts`
--

CREATE TABLE IF NOT EXISTS `Trails-Hearts` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-MobSpawner`
--

CREATE TABLE IF NOT EXISTS `Trails-MobSpawner` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Music`
--

CREATE TABLE IF NOT EXISTS `Trails-Music` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Slime`
--

CREATE TABLE IF NOT EXISTS `Trails-Slime` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Smoke`
--

CREATE TABLE IF NOT EXISTS `Trails-Smoke` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Snow`
--

CREATE TABLE IF NOT EXISTS `Trails-Snow` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Type`
--

CREATE TABLE IF NOT EXISTS `Trails-Type` (
  `uuid` varchar(50) NOT NULL,
  `type` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-TypeBig`
--

CREATE TABLE IF NOT EXISTS `Trails-TypeBig` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-TypeBody`
--

CREATE TABLE IF NOT EXISTS `Trails-TypeBody` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-TypeCylinder`
--

CREATE TABLE IF NOT EXISTS `Trails-TypeCylinder` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-TypeGround`
--

CREATE TABLE IF NOT EXISTS `Trails-TypeGround` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-TypeHead`
--

CREATE TABLE IF NOT EXISTS `Trails-TypeHead` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-TypeOrbit`
--

CREATE TABLE IF NOT EXISTS `Trails-TypeOrbit` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-TypeParticlesAmount`
--

CREATE TABLE IF NOT EXISTS `Trails-TypeParticlesAmount` (
  `uuid` varchar(50) NOT NULL,
  `amount` int(2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-TypeSnake`
--

CREATE TABLE IF NOT EXISTS `Trails-TypeSnake` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-TypeSpecial`
--

CREATE TABLE IF NOT EXISTS `Trails-TypeSpecial` (
  `uuid` varchar(50) NOT NULL,
  `special` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-TypeVertical`
--

CREATE TABLE IF NOT EXISTS `Trails-TypeVertical` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Water`
--

CREATE TABLE IF NOT EXISTS `Trails-Water` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Trails-Witch`
--

CREATE TABLE IF NOT EXISTS `Trails-Witch` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `UHC-BestStreak`
--

CREATE TABLE IF NOT EXISTS `UHC-BestStreak` (
  `uuid` varchar(50) NOT NULL,
  `beststreak` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `UHC-Kills`
--

CREATE TABLE IF NOT EXISTS `UHC-Kills` (
  `uuid` varchar(50) NOT NULL,
  `kills` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `UHC-Loses`
--

CREATE TABLE IF NOT EXISTS `UHC-Loses` (
  `uuid` varchar(50) NOT NULL,
  `loses` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `UHC-Wins`
--

CREATE TABLE IF NOT EXISTS `UHC-Wins` (
  `uuid` varchar(50) NOT NULL,
  `wins` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `VIPPoints`
--

CREATE TABLE IF NOT EXISTS `VIPPoints` (
  `uuid` varchar(50) NOT NULL,
  `points` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Votes`
--

CREATE TABLE IF NOT EXISTS `Votes` (
  `uuid` varchar(50) NOT NULL,
  `votes` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-Black`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-Black` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-Blue`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-Blue` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-Cyan`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-Cyan` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-DarkBlue`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-DarkBlue` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-Disco`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-Disco` (
  `uuid` varchar(50) NOT NULL,
  `disco` varchar(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-Gray`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-Gray` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-Green`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-Green` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-LightBlue`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-LightBlue` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-LightGreen`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-LightGreen` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-Orange`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-Orange` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-Pink`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-Pink` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-Purple`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-Purple` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-Red`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-Red` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-White`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-White` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Wardrobe-Yellow`
--

CREATE TABLE IF NOT EXISTS `Wardrobe-Yellow` (
  `uuid` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

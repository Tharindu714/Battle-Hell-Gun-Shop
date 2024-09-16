-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.32 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for battlehell
CREATE DATABASE IF NOT EXISTS `battlehell` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `battlehell`;

-- Dumping structure for table battlehell.address
CREATE TABLE IF NOT EXISTS `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `line1` text NOT NULL,
  `line2` text NOT NULL,
  `postal_code` varchar(10) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `first_name` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'Not Set',
  `last_name` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'Not Set',
  `user_id` int NOT NULL,
  `city_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_address_user1_idx` (`user_id`),
  KEY `fk_address_city1_idx` (`city_id`),
  CONSTRAINT `fk_address_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `fk_address_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.address: ~4 rows (approximately)
REPLACE INTO `address` (`id`, `line1`, `line2`, `postal_code`, `mobile`, `first_name`, `last_name`, `user_id`, `city_id`) VALUES
	(1, '8853 ', 'Reading Rd', '45215', '0751441764', 'David', 'Smith Jr.', 1, 1),
	(2, '371 7th Ave', 'New York', '10001', '0751441764', 'Tharindu', 'Chanaka', 1, 3),
	(3, '13 Washington Square S', 'New York', '10012', '0766135782', 'Tharindu', 'Chanaka', 1, 3),
	(4, '240 Mercer St', 'New York', '10012', '0743217890', 'Tharindu', 'Chanaka', 1, 3),
	(5, '16260 Paramount Blvd', 'Paramount', '90723', '0754326789', 'Not Set', 'Not Set', 2, 4),
	(6, '4368 Santa Anita Avenue', 'EL Monte ', '91731', '0713456789', 'Not Set', 'Not Set', 3, 3);

-- Dumping structure for table battlehell.barrel
CREATE TABLE IF NOT EXISTS `barrel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chamber` varchar(25) NOT NULL,
  `gun_sights_id` int NOT NULL,
  `muzzle_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_barrel_gun_sights1_idx` (`gun_sights_id`),
  KEY `fk_barrel_muzzle1_idx` (`muzzle_id`),
  CONSTRAINT `fk_barrel_gun_sights1` FOREIGN KEY (`gun_sights_id`) REFERENCES `gun_sights` (`id`),
  CONSTRAINT `fk_barrel_muzzle1` FOREIGN KEY (`muzzle_id`) REFERENCES `muzzle` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.barrel: ~6 rows (approximately)
REPLACE INTO `barrel` (`id`, `chamber`, `gun_sights_id`, `muzzle_id`) VALUES
	(1, 'Barrel Chamber', 7, 1),
	(2, 'Cylinder Chamber', 3, 2),
	(3, 'Fluted chamber', 2, 1),
	(4, 'Ported chamber', 16, 1),
	(5, 'Multi chamber', 11, 1),
	(6, 'Forensics', 15, 1);

-- Dumping structure for table battlehell.brand
CREATE TABLE IF NOT EXISTS `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.brand: ~11 rows (approximately)
REPLACE INTO `brand` (`id`, `name`) VALUES
	(2, 'Smith & Wesson'),
	(3, 'Ruger'),
	(4, 'SpringField Armory'),
	(5, 'Benelli'),
	(6, 'Savage Arm'),
	(7, 'Kimber'),
	(8, 'Taurus'),
	(9, 'Walther'),
	(10, 'Heckler & Koch'),
	(12, 'Mossberg & Sons');

-- Dumping structure for table battlehell.bullets
CREATE TABLE IF NOT EXISTS `bullets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `powder` varchar(25) NOT NULL,
  `bullet_case` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `primer` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.bullets: ~1 rows (approximately)
REPLACE INTO `bullets` (`id`, `powder`, `bullet_case`, `primer`) VALUES
	(1, 'None', 'None', 'None');

-- Dumping structure for table battlehell.bullet_type
CREATE TABLE IF NOT EXISTS `bullet_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(15) NOT NULL,
  `bullets_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_bullet_type_bullets1_idx` (`bullets_id`),
  CONSTRAINT `fk_bullet_type_bullets1` FOREIGN KEY (`bullets_id`) REFERENCES `bullets` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.bullet_type: ~0 rows (approximately)
REPLACE INTO `bullet_type` (`id`, `type`, `bullets_id`) VALUES
	(1, 'None', 1);

-- Dumping structure for table battlehell.cart
CREATE TABLE IF NOT EXISTS `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` int NOT NULL,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cart_user1_idx` (`user_id`),
  KEY `fk_cart_product1_idx` (`product_id`),
  CONSTRAINT `fk_cart_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_cart_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.cart: ~4 rows (approximately)
REPLACE INTO `cart` (`id`, `qty`, `user_id`, `product_id`) VALUES
	(21, 2, 1, 9),
	(22, 1, 1, 6),
	(23, 1, 1, 1),
	(24, 2, 1, 7),
	(27, 1, 1, 5),
	(28, 1, 1, 2),
	(29, 1, 1, 10),
	(30, 1, 1, 8),
	(33, 4, 3, 11);

-- Dumping structure for table battlehell.city
CREATE TABLE IF NOT EXISTS `city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.city: ~4 rows (approximately)
REPLACE INTO `city` (`id`, `name`) VALUES
	(1, 'Ohio'),
	(2, 'Chicago'),
	(3, 'New York'),
	(4, 'LA');

-- Dumping structure for table battlehell.forestock
CREATE TABLE IF NOT EXISTS `forestock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.forestock: ~4 rows (approximately)
REPLACE INTO `forestock` (`id`, `name`) VALUES
	(1, 'Wooden'),
	(2, 'Pure Steel'),
	(3, 'Gold Plated Metal'),
	(4, 'Solid Metal'),
	(5, 'Silver Plated Metal');

-- Dumping structure for table battlehell.gun_action
CREATE TABLE IF NOT EXISTS `gun_action` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bolt` varchar(25) NOT NULL,
  `safetyclip` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.gun_action: ~6 rows (approximately)
REPLACE INTO `gun_action` (`id`, `bolt`, `safetyclip`) VALUES
	(1, 'Bolt Action', 'Pivoting Lever'),
	(2, 'Lever Action', 'Cross Bolt Safety'),
	(3, 'Pump Action', 'Located near the Trigger'),
	(4, 'Pump Action', 'Located near the Trigger'),
	(5, 'Revolving Action', 'No Safety Clip'),
	(6, 'Hinge/Break Action', 'Tang Safety Clip'),
	(7, 'Semi Automatic Action', 'Located On the Frame or Side');

-- Dumping structure for table battlehell.gun_condition
CREATE TABLE IF NOT EXISTS `gun_condition` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.gun_condition: ~4 rows (approximately)
REPLACE INTO `gun_condition` (`id`, `name`) VALUES
	(1, 'Deadly Range'),
	(2, 'Normal Use'),
	(3, 'Hunter Use'),
	(4, 'Light Use'),
	(5, 'Sport use');

-- Dumping structure for table battlehell.gun_sights
CREATE TABLE IF NOT EXISTS `gun_sights` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.gun_sights: ~14 rows (approximately)
REPLACE INTO `gun_sights` (`id`, `name`) VALUES
	(1, 'Laser'),
	(2, 'Iron Sights'),
	(3, 'Holographic Sights'),
	(4, 'Reflex Sights'),
	(5, 'Telescopic Sights'),
	(6, 'BlackOut Sights'),
	(7, 'Adjustable Sights'),
	(8, 'Scopes'),
	(9, 'Red Dot Scopes'),
	(10, 'Tritium Sights'),
	(11, 'Night Sights'),
	(12, 'Pistol Sights'),
	(13, 'Rear Sight'),
	(14, 'speed and accuracy'),
	(15, 'Fixed Sights'),
	(16, 'Peep Sights');

-- Dumping structure for table battlehell.gun_status
CREATE TABLE IF NOT EXISTS `gun_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.gun_status: ~2 rows (approximately)
REPLACE INTO `gun_status` (`id`, `name`) VALUES
	(1, 'Active'),
	(2, 'Inactive');

-- Dumping structure for table battlehell.magazine
CREATE TABLE IF NOT EXISTS `magazine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.magazine: ~8 rows (approximately)
REPLACE INTO `magazine` (`id`, `name`) VALUES
	(1, 'Detachable magazines'),
	(2, 'Internal magazine'),
	(3, 'Tubular magazine'),
	(4, 'Drum magazine'),
	(5, 'Guns magazine'),
	(6, 'Moon clip magazine'),
	(7, 'Stripper clip  magazine'),
	(8, 'Box magazine'),
	(9, 'Rotary magazine');

-- Dumping structure for table battlehell.model
CREATE TABLE IF NOT EXISTS `model` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `brand_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_model_brand_idx` (`brand_id`),
  CONSTRAINT `fk_model_brand` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.model: ~21 rows (approximately)
REPLACE INTO `model` (`id`, `name`, `brand_id`) VALUES
	(1, 'Emissary', 4),
	(2, 'TRP', 4),
	(3, 'M1A', 4),
	(4, 'SAINT', 4),
	(5, '911', 4),
	(6, 'Armor Hellcat', 4),
	(7, 'Ruger SR1911', 3),
	(8, 'Ruger EC9s', 3),
	(9, 'Ruger SR9', 3),
	(10, 'Security 9', 3),
	(11, 'Ruger LCR', 3),
	(12, 'Ruger Blackhawk', 3),
	(13, 'Savage 1907', 6),
	(14, 'Stance 9mm', 6),
	(15, '45 ACP', 6),
	(16, 'SA-35', 4),
	(17, 'MC1sc Centennial', 12),
	(18, 'Hellcat Series', 4),
	(19, '940 Pro', 12),
	(20, 'HK 512', 10),
	(21, 'Taurus 856 Special', 12),
	(22, 'SMG Model 76', 2),
	(23, 'Mp15pc', 2),
	(24, 'Revolver 986', 2),
	(25, 'Revolver 350', 2);

-- Dumping structure for table battlehell.muzzle
CREATE TABLE IF NOT EXISTS `muzzle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.muzzle: ~2 rows (approximately)
REPLACE INTO `muzzle` (`id`, `name`) VALUES
	(1, 'Basket Muzzle'),
	(2, 'Sleeve Muzzle');

-- Dumping structure for table battlehell.person
CREATE TABLE IF NOT EXISTS `person` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.person: ~5 rows (approximately)
REPLACE INTO `person` (`id`, `type`) VALUES
	(1, 'VIP'),
	(2, 'Hunter'),
	(3, 'Professional'),
	(4, 'Sportman'),
	(5, 'Normal User');

-- Dumping structure for table battlehell.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `description` text NOT NULL,
  `model_id` int NOT NULL,
  `stock_id` int NOT NULL,
  `barrel_id` int NOT NULL,
  `action_id` int NOT NULL,
  `bullet_type_id` int NOT NULL,
  `price` double NOT NULL,
  `qty` int NOT NULL,
  `reg_date` datetime NOT NULL,
  `user_id` int NOT NULL,
  `status_id` int NOT NULL,
  `person_id` int NOT NULL,
  `gun_condition_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_model1_idx` (`model_id`),
  KEY `fk_product_gunstock1_idx` (`stock_id`),
  KEY `fk_product_barrel1_idx` (`barrel_id`),
  KEY `fk_product_action1_idx` (`action_id`),
  KEY `fk_product_bullet_type1_idx` (`bullet_type_id`),
  KEY `fk_product_user1_idx` (`user_id`),
  KEY `fk_product_status1_idx` (`status_id`),
  KEY `fk_product_person1_idx` (`person_id`),
  KEY `fk_product_gun_condition1_idx` (`gun_condition_id`),
  CONSTRAINT `fk_product_action1` FOREIGN KEY (`action_id`) REFERENCES `gun_action` (`id`),
  CONSTRAINT `fk_product_barrel1` FOREIGN KEY (`barrel_id`) REFERENCES `barrel` (`id`),
  CONSTRAINT `fk_product_bullet_type1` FOREIGN KEY (`bullet_type_id`) REFERENCES `bullet_type` (`id`),
  CONSTRAINT `fk_product_gun_condition1` FOREIGN KEY (`gun_condition_id`) REFERENCES `gun_condition` (`id`),
  CONSTRAINT `fk_product_gunstock1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
  CONSTRAINT `fk_product_model1` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`),
  CONSTRAINT `fk_product_person1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `fk_product_status1` FOREIGN KEY (`status_id`) REFERENCES `gun_status` (`id`),
  CONSTRAINT `fk_product_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.product: ~9 rows (approximately)
REPLACE INTO `product` (`id`, `title`, `description`, `model_id`, `stock_id`, `barrel_id`, `action_id`, `bullet_type_id`, `price`, `qty`, `reg_date`, `user_id`, `status_id`, `person_id`, `gun_condition_id`) VALUES
	(1, 'Springfield Armory SA 35', 'Based on one of the most prolific and popular pistols in small arms history, the Springfield Armory SA-35 gives today\'s shooters a modern take on a revered classic. Featuring subtle but significant upgrades to John Moses Browning\'s original P-35 design, the 9mm SA-35 captures the appeal of the wood and steel era of arms making while offering the upgraded enhancements of today s defensive pistols. Made in the U.S.A. The SA-35 features rugged forged steel parts for strength and durability, improved ergonomics and enhanced controls, a factory tuned trigger, modern sights, an improved feed ramp design and an increased 15-round capacity. Model: SPHP9201.', 16, 3, 3, 6, 1, 780, 11, '2024-09-10 14:14:57', 1, 1, 3, 1),
	(2, 'Ruger Security 9 Compact', 'Country of Origin:China <br/>\r\nPackage weight:1.0 lb  <br/>\r\nPackage quantity: 1  <br/>\r\nProduct Type: PILLOW  <br/>', 10, 6, 4, 2, 1, 972, 20, '2024-09-10 15:07:09', 1, 1, 2, 2),
	(3, 'Mossberg Launches MC1sc', 'O.F. Mossberg & Sons launched a Centennial Sweepstakes in which enthusiasts can register to win a limited-edition MC1sc Centennial 9 mm pistol. No purchase is necessary to enter the sweepstakes, which ends May 31, 2019. Visit the promotions web page to sign up. Certain limitations apply.', 17, 2, 2, 6, 1, 2130, 50, '2024-09-12 15:04:14', 1, 1, 1, 1),
	(4, 'Springfield Hellcat Handguns', 'The Hellcat series of 9mm pistols offers CCW enthusiasts a pistol that combines impressive capacity with utterly capable performance. The micro 9mm Hellcat features best in class capacity of 11+1 and 13+1 with included extended magazine, and the RDP (Rapid Defense Package) variant adds in a high-performance Self Indexing Compensator and included HEX Wasp optic. The Hellcat Pro extends the barrel to 3.7 inches and lengthens the grip to offer an astounding 15+1 capacity. The result? A Hellcat for any need.', 18, 6, 6, 6, 1, 986, 20, '2024-09-12 15:28:07', 1, 1, 2, 3),
	(5, 'New Mossberg 940 Pro', 'Mossberg s ever-growing 940 line of semi-automatic shotguns just keeps growing. The latest additions will probably be some of their most popular. The 940 Pro Sporting has beautiful walnut furniture and the 940 Pro Sporting Super Bantam is great for smaller-framed shooters. Here s Mossberg s announcement', 19, 9, 1, 4, 1, 3500, 45, '2024-09-12 18:09:33', 1, 1, 2, 3),
	(6, 'Heckler & Koch HK512', 'The HK 512 was developed and produced circa the 1970s-1980s by Franchi as the first semi-automatic shotgun developed for law enforcement use. Adopted by German law enforcement and counter-terrorism units such as GSG9, these shotguns were sold in limited quantities in the United States (a total of approximately 1,500 made with only 343 Imported into the U.S.).', 20, 2, 1, 6, 1, 5800, 26, '2024-09-12 18:16:39', 1, 1, 3, 1),
	(7, 'Ruger SA80 A2 Rifle', 'The ICS SA80 or L85 A2 is rated as one of the best SA80 replica s money can buy, full metal with excellent attention to detail in the stamping and weld seams give this airsoft rifle a great feel and look', 11, 5, 6, 7, 1, 2100, 59, '2024-09-12 18:56:41', 1, 1, 1, 1),
	(8, 'Moosberg Taurus 856 Special Revolver', 'The UL 856, by Taurus, is a six-shot, 2-inch barreled .38 Special that is just over 6.5 inches long and 1.41 inches wide. Before you think that seems to be a bit large for a concealed-carry gun, given today s micro semiauto s, the 856 is shorter than a lot of pistols that are considered concealable compacts. This includes the popular Smith & Wesson M&P9 M2.0 and SIG Sauer P320. (The UL 856 actually matches the P320 Compact in width.)', 21, 1, 5, 5, 1, 400, 99, '2024-09-12 19:16:00', 1, 1, 1, 3),
	(9, 'Smith & Wesson SMG 76', 'This Smith and Wesson S&W 76 is a pre-1986 transferable machine gun and is in like new / excellent condition. The exterior shows minimal handling marks on the tube and frame. Inside, the firing pin is smooth and rounded, barrel is shinny and the rifling is sharp. The extractor is straight and sharp and the sear appears to be the correct angle with zero wear. Function tests work correct for safe, semi and full auto use.', 22, 8, 2, 7, 1, 19000, 29, '2024-09-12 19:50:48', 1, 1, 3, 1),
	(10, 'Smith & Wesson 350 Powerful', 'Winchester made the 350 Legend to be a saving grace for those who are restricted to straight-walled cartridges whilst hunting, which is why it flies off the shelves in states like Ohio and Indiana. Here in New York and most of Pennsylvania, we are free to hunt with whatever we wish, including handguns. In the dense woods that are native to these areas, handgun hunting is the way to go. With most shots being inside of 50 yards, there is no reason to bring a rifle into this.', 25, 9, 3, 6, 1, 1030, 100, '2024-09-14 12:47:48', 2, 1, 2, 1),
	(11, 'Smith & Wesson Model 986', 'The 986 is an all-business, made-to-perform revolver that offered excellent accuracy with Hornady Critical Duty ammo. This highly modified L-frame had a smooth DA trigger due to the titanium cylinder. The SA trigger was crisp. We liked the large bold sights and full-size grip, which offered good recoil management. The 986 takes effort to conceal and carry.', 24, 1, 5, 6, 1, 1529, 50, '2024-09-16 01:19:47', 3, 1, 1, 2);

-- Dumping structure for table battlehell.puchase
CREATE TABLE IF NOT EXISTS `puchase` (
  `id` int NOT NULL AUTO_INCREMENT,
  `datetime` datetime NOT NULL,
  `user_id` int NOT NULL,
  `address_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_puchase_user1_idx` (`user_id`),
  KEY `fk_puchase_address1_idx` (`address_id`),
  CONSTRAINT `fk_puchase_address1` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `fk_puchase_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.puchase: ~1 rows (approximately)
REPLACE INTO `puchase` (`id`, `datetime`, `user_id`, `address_id`) VALUES
	(2, '2024-09-11 21:45:26', 1, 1),
	(3, '2024-09-14 12:41:38', 2, 5),
	(4, '2024-09-16 01:14:02', 3, 6);

-- Dumping structure for table battlehell.purchase_items
CREATE TABLE IF NOT EXISTS `purchase_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` int NOT NULL,
  `puchase_id` int NOT NULL,
  `product_id` int NOT NULL,
  `purchase_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_purchase_items_puchase1_idx` (`puchase_id`),
  KEY `fk_purchase_items_product1_idx` (`product_id`),
  KEY `fk_purchase_items_purchase_status1_idx` (`purchase_status_id`),
  CONSTRAINT `fk_purchase_items_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_purchase_items_puchase1` FOREIGN KEY (`puchase_id`) REFERENCES `puchase` (`id`),
  CONSTRAINT `fk_purchase_items_purchase_status1` FOREIGN KEY (`purchase_status_id`) REFERENCES `purchase_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.purchase_items: ~4 rows (approximately)
REPLACE INTO `purchase_items` (`id`, `qty`, `puchase_id`, `product_id`, `purchase_status_id`) VALUES
	(1, 10, 2, 2, 1),
	(2, 9, 2, 1, 3),
	(3, 1, 3, 4, 1),
	(4, 1, 3, 9, 1),
	(5, 1, 4, 8, 1),
	(6, 1, 4, 7, 1);

-- Dumping structure for table battlehell.purchase_status
CREATE TABLE IF NOT EXISTS `purchase_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.purchase_status: ~4 rows (approximately)
REPLACE INTO `purchase_status` (`id`, `name`) VALUES
	(1, 'Pending'),
	(2, 'Packing'),
	(3, 'Dispatching'),
	(4, 'Shipping'),
	(5, 'Deliverd');

-- Dumping structure for table battlehell.stock
CREATE TABLE IF NOT EXISTS `stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `w_trigger` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `magazine_id` int NOT NULL,
  `forestock_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_magazine1_idx` (`magazine_id`),
  KEY `fk_stock_forestock1_idx` (`forestock_id`),
  CONSTRAINT `fk_stock_forestock1` FOREIGN KEY (`forestock_id`) REFERENCES `forestock` (`id`),
  CONSTRAINT `fk_stock_magazine1` FOREIGN KEY (`magazine_id`) REFERENCES `magazine` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.stock: ~9 rows (approximately)
REPLACE INTO `stock` (`id`, `w_trigger`, `magazine_id`, `forestock_id`) VALUES
	(1, 'Double action revolver', 1, 2),
	(2, 'Single action', 9, 3),
	(3, 'Double action', 1, 4),
	(4, 'Striker fire', 7, 5),
	(5, 'Striker action', 3, 3),
	(6, 'Attacker triggers', 5, 5),
	(7, 'Double-action trigger', 6, 2),
	(8, 'Single action semi automatics', 4, 4),
	(9, 'Single-stage trigger', 9, 5);

-- Dumping structure for table battlehell.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(60) NOT NULL,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `password` varchar(30) NOT NULL,
  `verification` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.user: ~0 rows (approximately)
REPLACE INTO `user` (`id`, `email`, `first_name`, `last_name`, `password`, `verification`) VALUES
	(1, 'tharinduchanaka6@gmail.com', 'Tharindu', 'Chanaka', 'tharinduCHA@8754', 'Verified'),
	(2, 'johnwick2024@gmail.com', 'John', 'Wick', 'johnwick@2024Gun', 'Verified'),
	(3, 'kalhara139@gmail.com', 'Geeth', 'Kalhara', 'geethKALHARA@24', 'Verified');

-- Dumping structure for table battlehell.wishlist
CREATE TABLE IF NOT EXISTS `wishlist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_wishlist_product1_idx` (`product_id`),
  KEY `fk_wishlist_user1_idx` (`user_id`),
  CONSTRAINT `fk_wishlist_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_wishlist_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table battlehell.wishlist: ~5 rows (approximately)
REPLACE INTO `wishlist` (`id`, `product_id`, `user_id`) VALUES
	(53, 2, 1),
	(54, 4, 1),
	(55, 7, 1),
	(56, 3, 2),
	(57, 2, 2),
	(58, 10, 1),
	(59, 5, 1),
	(60, 8, 1),
	(61, 3, 1),
	(62, 7, 1),
	(63, 10, 3),
	(64, 5, 3),
	(65, 3, 3),
	(66, 11, 3);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

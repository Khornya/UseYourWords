-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: useyourwords
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Dumping data for table `element`
--

LOCK TABLES `element` WRITE;
/*!40000 ALTER TABLE `element` DISABLE KEYS */;
INSERT INTO `element` (`element_id`, `element_defaultresponse`, `element_name`, `element_tofilltext`, `element_type`, `element_url`, `element_uuid`) VALUES (2,'Hier j\'ai rencontré un alien, la première que je lui ai dit est : Heeeeyyyy salut à tous les amis, c\'est David Lafarge Pokémon !','Alien','Hier j\'ai rencontré un alien, la première chose que je lui ai dit est : [...]',1,NULL,'534c4d1ad660475ba414cc138384519b'),(5,'Le meilleur moyen de contraception reste quand même les frères Bogdanoff','Contraception','Le meilleur moyen de contraception reste quand même [...]',1,NULL,'f5d51b8493744831841a60960ed2133a'),(10,'Quand je suis saoul, j\'ai tendance à confondre le sol et le plafond','Alcool','Quand je suis saoul, j\'ai tendance à confondre [...] ',1,NULL,'f5d8d9dd01b24187b4d9177bcf3aeb7a'),(7,'Pour me féliciter d\'avoir eu le BAC, mes parents m\'ont offert : un Pin\'s','Le BAC','Pour me féliciter d\'avoir eu le BAC, mes parents m\'ont offert : [...]',1,NULL,'a4801f20ce4445fa8da92d208d9c3992'),(8,'L\'Egypte a les pyramides, la France a : Jean-Marie Le Pen','Géographie','L\'Egypte a les pyramides, la France a : [...]',1,NULL,'c5e6ba94f02d47669d13137f86063ac9'),(9,'Ce matin je me suis réveillé avec une terrible envie de me coucher','Matin','Ce matin je me suis réveillé avec une terrible envie de [...]',1,NULL,'8e50a16559084081a707660092d6f13f'),(11,'Le V\'n\'B, le chemin le plus rapide vers l\'école.','Direction','[...], le chemin le plus rapide vers l\'école.',1,NULL,'2cde21ab73274c1798c3aa5e43cc094c'),(12,'Ca fait 10 minutes qu\'on se connaît, je pense que me larguer est un peu prématuré','Rencontre','Ca fait 10 minutes qu\'on se connaît, je pense que [...] est un peu prématuré',1,NULL,'63446cf7c14f401d8e0f97f1251edfaa'),(13,'Un autocollant \"Bébé à bord\", ça ne fait pas de toi un mauvais parent.','Parent','[...], ça ne fait pas de toi un mauvais parent.',1,NULL,'7389400211c64510b92cce0e8e99a670'),(14,'Papa, c\'est quoi la danse des canards ?','Qu\'est-ce ? ','Papa, c\'est quoi [...] ?',1,NULL,'1ee65ee804824afc849f9849c1792804'),(15,'Deux ados découvrent un PC !','Ados',NULL,0,'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603400310/Photos/Ados_3394eb3e2cbf47b2bbc1009b7c70a46f.jpg','3394eb3e2cbf47b2bbc1009b7c70a46f'),(16,'Moi allant tranquillement en cours','BreakDance',NULL,0,'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603400379/Photos/BreakDance_79fcbefbc7ca42c9b89c1d1d03c4fa55.jpg','79fcbefbc7ca42c9b89c1d1d03c4fa55'),(17,'Incroyable ! Jésus démissionne !','Jesus',NULL,0,'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603400483/Photos/Jesus_266a0e41b5334e5dad051a7611482a50.jpg','266a0e41b5334e5dad051a7611482a50'),(18,'Cette personne tente d\'attirer les enfants, méfiez-vous !','Peluches',NULL,0,'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603400548/Photos/Peluches_1d7130d3a946493689ca24d685ec7056.jpg','1d7130d3a946493689ca24d685ec7056'),(19,'WAOW !','Intéressant ',NULL,0,'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603400584/Photos/Int%C3%A9ressant%20_8ce68edcfd354a7dae950cc085a0313c.jpg','8ce68edcfd354a7dae950cc085a0313c'),(26,'Parce qu\'il se cache derrière la fenêtre des gens pour espionner les conversations','',NULL,2,'https://res.cloudinary.com/dfkcs4rxq/video/upload/v1603535052/Vid%C3%A9os/_af65e06fa09d41b68c8f0961b5f07337.mp4','af65e06fa09d41b68c8f0961b5f07337'),(21,'Bonjour ! Je vous appelle pour vous arnaquer ;)','Allo !',NULL,0,'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603525409/Photos/Allo%20%21_7f812131006b41a4b8c80d1065e561f7.jpg','7f812131006b41a4b8c80d1065e561f7'),(22,'Moi, mon petit plaisir le matin, c\'est lécher des cactus','Cactus',NULL,0,'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603526366/Photos/Cactus_d619b9ca779449fb985572ac785e584b.jpg','d619b9ca779449fb985572ac785e584b'),(23,'Boire ou conduire, pas la peine de choisir !','Alcool',NULL,0,'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603526513/Photos/Alcool_6716a4e18d3f4bacb920389e8fede962.jpg','6716a4e18d3f4bacb920389e8fede962'),(24,'Mes amis me traitent de crâne d\'oeuf','Oeuf',NULL,0,'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603527846/Photos/Oeuf_7fa117ea01664a31adf1fc33adc1a7f4.jpg','7fa117ea01664a31adf1fc33adc1a7f4'),(27,'Blblblblblblblblblblbbl','Chant',NULL,2,'https://res.cloudinary.com/dfkcs4rxq/video/upload/v1603535091/Vid%C3%A9os/Chant_beb1330bc64144d0bd7a498ac4d440e6.mp4','beb1330bc64144d0bd7a498ac4d440e6'),(28,'Quoi ? Tu veux te battre ?! Reviens ici ! :miam: miam: :miam:','Dino',NULL,2,'https://res.cloudinary.com/dfkcs4rxq/video/upload/v1603621607/Vid%C3%A9os/Dino_d32f328252e94b9887a75b7d82760fcc.mp4','d32f328252e94b9887a75b7d82760fcc'),(29,'Aïe !','Monstre',NULL,2,'https://res.cloudinary.com/dfkcs4rxq/video/upload/v1603621728/Vid%C3%A9os/Monstre_db0ee87a5b3c4519b627bc43571fced5.mp4','db0ee87a5b3c4519b627bc43571fced5'),(32,'J\'suis dans ma paranoïa','Vilain garçon',NULL,2,'https://res.cloudinary.com/dfkcs4rxq/video/upload/v1603640190/Vid%C3%A9os/Vilain%20gar%C3%A7on_2426f890d63b4f66b1623431e742cbc9.mp4','2426f890d63b4f66b1623431e742cbc9'),(33,'Ha ha, j\'ai bien peur de devoir dire oui, mais non','Grand mère',NULL,2,'https://res.cloudinary.com/dfkcs4rxq/video/upload/v1603640485/Vid%C3%A9os/Grand%20m%C3%A8re_28c7aba51e074b4e9a931a3145fc4667.mp4','28c7aba51e074b4e9a931a3145fc4667'),(34,'Je suis pas Emmanuel Macron haha lol','Peuple',NULL,2,'https://res.cloudinary.com/dfkcs4rxq/video/upload/v1603656074/Vid%C3%A9os/Peuple_7ffc41bc81734d4e8ffde56a557e441d.mp4','7ffc41bc81734d4e8ffde56a557e441d'),(35,'En revanche je suis nue sous mes vêtements','Nature',NULL,2,'https://res.cloudinary.com/dfkcs4rxq/video/upload/v1603656134/Vid%C3%A9os/Nature_839164cf1a434ac290c4bbda54e1fdd9.mp4','839164cf1a434ac290c4bbda54e1fdd9'),(36,'Ha ouais c\'est Bande Organisée je crois','Music',NULL,2,'https://res.cloudinary.com/dfkcs4rxq/video/upload/v1603656327/Vid%C3%A9os/Music_4222a8da4b9f453f8a68f7e9108c7f09.mp4','4222a8da4b9f453f8a68f7e9108c7f09'),(37,'Nan même pas vrai t\'sais','Argent',NULL,2,'https://res.cloudinary.com/dfkcs4rxq/video/upload/v1603656395/Vid%C3%A9os/Argent_fe59fcafe23b4a0a84c6dc8cbfb93e14.mp4','fe59fcafe23b4a0a84c6dc8cbfb93e14');
/*!40000 ALTER TABLE `element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
INSERT INTO `score` (`score_id`, `score_date`, `score_name`, `score_score`) VALUES (1,'2020-10-31 15:02:16','Marine',1000),(2,'2020-10-31 15:02:16','Yannig',1500);
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'useyourwords'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-31 17:20:31

-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 22 oct. 2020 à 23:07
-- Version du serveur :  10.4.14-MariaDB
-- Version de PHP : 7.2.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `useyourwords`
--

-- --------------------------------------------------------

--
-- Structure de la table `element`
--

CREATE TABLE `element` (
  `element_id` int(11) NOT NULL,
  `element_defaultresponse` varchar(500) DEFAULT NULL,
  `element_name` varchar(200) DEFAULT NULL,
  `element_tofilltext` varchar(500) DEFAULT NULL,
  `element_type` int(11) DEFAULT NULL,
  `element_url` varchar(500) DEFAULT NULL,
  `element_uuid` varchar(500) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `element`
--

INSERT INTO `element` (`element_id`, `element_defaultresponse`, `element_name`, `element_tofilltext`, `element_type`, `element_url`, `element_uuid`) VALUES
(2, 'Heeeeyyyy salut à tous les amis, c\'est David Lafarge Pokémon !', 'Alien', 'Hier j\'ai rencontré un alien, la première que je lui ai dit est : […]', 1, NULL, '534c4d1ad660475ba414cc138384519b'),
(5, 'les frères Bogdanoff', 'Contraception', 'Le meilleur moyen de contraception reste quand même […]', 1, NULL, 'f5d51b8493744831841a60960ed2133a'),
(10, 'le sol et le plafond', 'Alcool', 'Quand je suis saoul, j\'ai tendance à confondre […] ', 1, NULL, 'f5d8d9dd01b24187b4d9177bcf3aeb7a'),
(7, 'un Pin\'s', 'Le BAC', 'Pour me féliciter d\'avoir eu le BAC, mes parents m\'ont offert : […]', 1, NULL, 'a4801f20ce4445fa8da92d208d9c3992'),
(8, 'Jean-Marie Le Pen', 'Géographie', 'L\'Egypte a les pyramides, la France a : […]', 1, NULL, 'c5e6ba94f02d47669d13137f86063ac9'),
(9, 'me coucher', 'Matin', 'Ce matin je me suis réveillé avec une terrible envie de […]', 1, NULL, '8e50a16559084081a707660092d6f13f'),
(11, 'Le V\'n\'B', 'Direction', '[…], le chemin le plus rapide vers l\'école.', 1, NULL, '2cde21ab73274c1798c3aa5e43cc094c'),
(12, 'me larguer', 'Rencontre', 'Ca fait 10 minutes qu\'on se connaît, je pense que […] est un peu prématuré', 1, NULL, '63446cf7c14f401d8e0f97f1251edfaa'),
(13, 'Un autocollant \"Bébé à bord\"', 'Parent', '[…], ça ne fait pas de toi un mauvais parent.', 1, NULL, '7389400211c64510b92cce0e8e99a670'),
(14, 'la danse des canards', 'Qu\'est-ce ? ', 'Papa, c\'est quoi […] ?', 1, NULL, '1ee65ee804824afc849f9849c1792804'),
(15, 'Deux ados découvrent un PC !', 'Ados', NULL, 0, 'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603400310/Photos/Ados_3394eb3e2cbf47b2bbc1009b7c70a46f.jpg', '3394eb3e2cbf47b2bbc1009b7c70a46f'),
(16, 'Moi allant tranquillement en cours', 'BreakDance', NULL, 0, 'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603400379/Photos/BreakDance_79fcbefbc7ca42c9b89c1d1d03c4fa55.jpg', '79fcbefbc7ca42c9b89c1d1d03c4fa55'),
(17, 'Incroyable ! Jésus démissionne !', 'Jesus', NULL, 0, 'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603400483/Photos/Jesus_266a0e41b5334e5dad051a7611482a50.jpg', '266a0e41b5334e5dad051a7611482a50'),
(18, 'Cette personne tente d\'attirer les enfants, méfiez-vous !', 'Peluches', NULL, 0, 'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603400548/Photos/Peluches_1d7130d3a946493689ca24d685ec7056.jpg', '1d7130d3a946493689ca24d685ec7056'),
(19, 'WAOW !', 'Intéressant ', NULL, 0, 'https://res.cloudinary.com/dfkcs4rxq/image/upload/v1603400584/Photos/Int%C3%A9ressant%20_8ce68edcfd354a7dae950cc085a0313c.jpg', '8ce68edcfd354a7dae950cc085a0313c'),
(20, 'ÉNORME JE VAIS M’ÉCLATER SUR CE PANNEAU !', 'Vélo panneau', NULL, 2, 'https://res.cloudinary.com/dfkcs4rxq/video/upload/v1603400694/Vid%C3%A9os/V%C3%A9lo%20panneau_cee234fc5ca7455dbc2258cf96026c9e.mp4', 'cee234fc5ca7455dbc2258cf96026c9e');

-- --------------------------------------------------------

--
-- Structure de la table `score`
--

CREATE TABLE `score` (
  `score_id` int(11) NOT NULL,
  `score_date` datetime DEFAULT NULL,
  `score_name` varchar(50) DEFAULT NULL,
  `score_score` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `element`
--
ALTER TABLE `element`
  ADD PRIMARY KEY (`element_id`);

--
-- Index pour la table `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`score_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `element`
--
ALTER TABLE `element`
  MODIFY `element_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `score`
--
ALTER TABLE `score`
  MODIFY `score_id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

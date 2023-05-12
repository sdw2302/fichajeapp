-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 12, 2023 at 09:45 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bd_fichaje`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizar_numero_trabajadores` (IN `id` INT)   BEGIN
    UPDATE empresa SET numero_trabajadores = (
        SELECT COUNT(*) FROM trabajador WHERE id_empresa = id
    )
    WHERE id_empresa = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sumar_horas_fichadas` (IN `id` INT)   BEGIN
  DECLARE horas_fichadas DOUBLE;
  SELECT SUM(horas_trabajadas_fichaje) INTO horas_fichadas FROM fichaje WHERE id_horario_trabajador IN (SELECT id_horario_trabajador FROM horario_trabajador WHERE id_trabajador = id);
  UPDATE trabajador SET horas_fichadas_trabajador = horas_fichadas WHERE id_trabajador = id;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `dia`
--

CREATE TABLE `dia` (
  `id_dia` int(1) NOT NULL,
  `nombre_dia` varchar(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dia`
--

INSERT INTO `dia` (`id_dia`, `nombre_dia`) VALUES
(1, 'Lunes'),
(2, 'Martes'),
(3, 'Miércoles'),
(4, 'Jueves'),
(5, 'Viernes'),
(6, 'Sabado'),
(7, 'Domingo');

-- --------------------------------------------------------

--
-- Table structure for table `empresa`
--

CREATE TABLE `empresa` (
  `id_empresa` int(8) NOT NULL,
  `nombre_empresa` varchar(30) DEFAULT NULL,
  `nif_empresa` varchar(12) DEFAULT NULL,
  `ciudad_empresa` varchar(20) DEFAULT NULL,
  `numero_trabajadores` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `empresa`
--

INSERT INTO `empresa` (`id_empresa`, `nombre_empresa`, `nif_empresa`, `ciudad_empresa`, `numero_trabajadores`) VALUES
(1, 'Acme Inc.', 'A12345678', 'Madrid', 5),
(2, 'Globex Corporation', 'G23456789', 'Barcelona', 2),
(3, 'Umbrella Corporation', 'U34567890', 'Valencia', 3);

-- --------------------------------------------------------

--
-- Table structure for table `fichaje`
--

CREATE TABLE `fichaje` (
  `id_fichaje` int(16) NOT NULL,
  `id_horario_trabajador` int(8) DEFAULT NULL,
  `horas_trabajadas_fichaje` float(4,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fichaje`
--

INSERT INTO `fichaje` (`id_fichaje`, `id_horario_trabajador`, `horas_trabajadas_fichaje`) VALUES
(1, 1, 6.50),
(2, 2, 7.50),
(3, 3, 8.00),
(4, 4, 5.00),
(6, 3, 6.50),
(7, 2, 1.75),
(8, 5, 1.75);

--
-- Triggers `fichaje`
--
DELIMITER $$
CREATE TRIGGER `actualizar_horas_fichadas_trabajador` AFTER INSERT ON `fichaje` FOR EACH ROW BEGIN
    CALL sumar_horas_fichadas(NEW.id_horario_trabajador);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `horario`
--

CREATE TABLE `horario` (
  `id_horario` int(8) NOT NULL,
  `hora_inicio_horario` float(4,2) DEFAULT NULL,
  `hora_final_horario` float(4,2) DEFAULT NULL,
  `tiempo_descanso_horario` float(4,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `horario`
--

INSERT INTO `horario` (`id_horario`, `hora_inicio_horario`, `hora_final_horario`, `tiempo_descanso_horario`) VALUES
(1, 8.00, 14.00, 1.00),
(2, 9.00, 17.00, 17.00),
(3, 14.00, 22.00, 1.00),
(4, 8.00, 14.50, 14.50),
(5, 14.50, 20.00, 20.00),
(6, 8.00, 17.00, 1.00),
(7, 7.00, 12.50, 1.00);

-- --------------------------------------------------------

--
-- Table structure for table `horario_trabajador`
--

CREATE TABLE `horario_trabajador` (
  `id_horario_trabajador` int(8) NOT NULL,
  `id_trabajador` int(8) DEFAULT NULL,
  `id_horario` int(8) DEFAULT NULL,
  `id_dia` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `horario_trabajador`
--

INSERT INTO `horario_trabajador` (`id_horario_trabajador`, `id_trabajador`, `id_horario`, `id_dia`) VALUES
(1, 1, 1, 1),
(2, 2, 2, 2),
(3, 3, 3, 3),
(4, 4, 3, 1),
(5, 5, 4, 2),
(6, 6, 5, 3),
(7, 9, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `trabajador`
--

CREATE TABLE `trabajador` (
  `id_trabajador` int(8) NOT NULL,
  `nombre_trabajador` varchar(20) DEFAULT NULL,
  `apellido_trabajador` varchar(40) DEFAULT NULL,
  `dni_trabajador` varchar(12) DEFAULT NULL,
  `fecha_nacimiento_trabajador` date DEFAULT NULL,
  `id_empresa` int(8) DEFAULT NULL,
  `horas_fichadas_trabajador` double(10,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `trabajador`
--

INSERT INTO `trabajador` (`id_trabajador`, `nombre_trabajador`, `apellido_trabajador`, `dni_trabajador`, `fecha_nacimiento_trabajador`, `id_empresa`, `horas_fichadas_trabajador`) VALUES
(1, 'Juan', 'Pérez García', '12345678A', '1990-05-12', 1, 6.50),
(2, 'María', 'González López', '23456789B', '1995-07-25', 2, 9.25),
(3, 'Pedro', 'Sánchez Martínez', '34567890C', '1988-03-17', 3, 14.50),
(4, 'Sara', 'Hernández', '55544411J', '1995-09-12', 1, 5.00),
(5, 'Juan', 'Ramírez', '66655522K', '1992-04-20', 1, 1.75),
(6, 'María', 'García', '77766633L', '1989-12-31', 2, 0.00),
(7, 'Pedro', 'Puentes Puyalto', '84653876F', '1989-03-11', 3, 0.00),
(9, 'Javier', 'Lopez Calderon', '14773234W', '1989-08-15', 1, 0.00),
(10, 'Miguel', 'Zambrano Contreras', '72345678A', '2004-05-01', 3, 0.00);

--
-- Triggers `trabajador`
--
DELIMITER $$
CREATE TRIGGER `actualizar_numero_trabajadores_trigger` AFTER INSERT ON `trabajador` FOR EACH ROW BEGIN
    CALL actualizar_numero_trabajadores(NEW.id_empresa);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `trabajadores_por_empresa`
-- (See below for the actual view)
--
CREATE TABLE `trabajadores_por_empresa` (
`nombre_empresa` varchar(30)
,`trabajador` varchar(61)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `vista_horarios_trabajadores`
-- (See below for the actual view)
--
CREATE TABLE `vista_horarios_trabajadores` (
`nombre_trabajador` varchar(20)
,`apellido_trabajador` varchar(40)
,`nombre_dia` varchar(9)
,`hora_inicio_horario` float(4,2)
,`hora_final_horario` float(4,2)
,`tiempo_descanso_horario` float(4,2)
);

-- --------------------------------------------------------

--
-- Structure for view `trabajadores_por_empresa`
--
DROP TABLE IF EXISTS `trabajadores_por_empresa`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `trabajadores_por_empresa`  AS SELECT `empresa`.`nombre_empresa` AS `nombre_empresa`, concat(`trabajador`.`nombre_trabajador`,' ',`trabajador`.`apellido_trabajador`) AS `trabajador` FROM (`empresa` join `trabajador` on(`empresa`.`id_empresa` = `trabajador`.`id_empresa`)) ORDER BY `empresa`.`nombre_empresa` ASC ;

-- --------------------------------------------------------

--
-- Structure for view `vista_horarios_trabajadores`
--
DROP TABLE IF EXISTS `vista_horarios_trabajadores`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vista_horarios_trabajadores`  AS SELECT `t`.`nombre_trabajador` AS `nombre_trabajador`, `t`.`apellido_trabajador` AS `apellido_trabajador`, `d`.`nombre_dia` AS `nombre_dia`, `h`.`hora_inicio_horario` AS `hora_inicio_horario`, `h`.`hora_final_horario` AS `hora_final_horario`, `h`.`tiempo_descanso_horario` AS `tiempo_descanso_horario` FROM (((`trabajador` `t` join `horario_trabajador` `ht` on(`t`.`id_trabajador` = `ht`.`id_trabajador`)) join `horario` `h` on(`ht`.`id_horario` = `h`.`id_horario`)) join `dia` `d` on(`ht`.`id_dia` = `d`.`id_dia`)) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dia`
--
ALTER TABLE `dia`
  ADD PRIMARY KEY (`id_dia`);

--
-- Indexes for table `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`id_empresa`);

--
-- Indexes for table `fichaje`
--
ALTER TABLE `fichaje`
  ADD PRIMARY KEY (`id_fichaje`),
  ADD KEY `id_horario_trabajador` (`id_horario_trabajador`);

--
-- Indexes for table `horario`
--
ALTER TABLE `horario`
  ADD PRIMARY KEY (`id_horario`);

--
-- Indexes for table `horario_trabajador`
--
ALTER TABLE `horario_trabajador`
  ADD PRIMARY KEY (`id_horario_trabajador`),
  ADD KEY `id_trabajador` (`id_trabajador`),
  ADD KEY `id_horario` (`id_horario`),
  ADD KEY `id_dia` (`id_dia`);

--
-- Indexes for table `trabajador`
--
ALTER TABLE `trabajador`
  ADD PRIMARY KEY (`id_trabajador`),
  ADD KEY `id_empresa` (`id_empresa`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `fichaje`
--
ALTER TABLE `fichaje`
  ADD CONSTRAINT `fichaje_ibfk_1` FOREIGN KEY (`id_horario_trabajador`) REFERENCES `horario_trabajador` (`id_horario_trabajador`);

--
-- Constraints for table `horario_trabajador`
--
ALTER TABLE `horario_trabajador`
  ADD CONSTRAINT `horario_trabajador_ibfk_1` FOREIGN KEY (`id_trabajador`) REFERENCES `trabajador` (`id_trabajador`),
  ADD CONSTRAINT `horario_trabajador_ibfk_2` FOREIGN KEY (`id_horario`) REFERENCES `horario` (`id_horario`),
  ADD CONSTRAINT `horario_trabajador_ibfk_3` FOREIGN KEY (`id_dia`) REFERENCES `dia` (`id_dia`);

--
-- Constraints for table `trabajador`
--
ALTER TABLE `trabajador`
  ADD CONSTRAINT `trabajador_ibfk_1` FOREIGN KEY (`id_empresa`) REFERENCES `empresa` (`id_empresa`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

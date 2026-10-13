-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-05-2024 a las 13:51:38
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `dbpfp3`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administradores`
--

CREATE TABLE `administradores` (
  `id` varchar(50) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `contrasena` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `administradores`
--

INSERT INTO `administradores` (`id`, `nombre`, `contrasena`) VALUES
('A159283', 'Ricardo', 'RicardoA159283'),
('A426665', 'Gilberto', 'GilbertoA426665');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asientos`
--

CREATE TABLE `asientos` (
  `id_asiento` int(11) NOT NULL,
  `id_evento` int(11) DEFAULT NULL,
  `numero_asiento` varchar(10) DEFAULT NULL,
  `estado` varchar(10) DEFAULT 'disponible',
  `id_usuario` int(11) DEFAULT NULL,
  `tipo_evento` varchar(50) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `asientos`
--

INSERT INTO `asientos` (`id_asiento`, `id_evento`, `numero_asiento`, `estado`, `id_usuario`, `tipo_evento`, `precio`) VALUES
(211, 18003, '1', 'reservado', 1, 'Deportivo', 25.00),
(212, 18003, '2', 'reservado', 1, 'Deportivo', 25.00),
(213, 18003, '3', 'reservado', 1, 'Deportivo', 25.00),
(214, 18003, '4', 'disponible', NULL, 'Deportivo', 25.00),
(215, 18003, '5', 'disponible', NULL, 'Deportivo', 25.00),
(216, 18003, '6', 'disponible', NULL, 'Deportivo', 25.00),
(217, 18003, '7', 'disponible', NULL, 'Deportivo', 25.00),
(218, 18003, '8', 'disponible', NULL, 'Deportivo', 25.00),
(219, 18003, '9', 'disponible', NULL, 'Deportivo', 25.00),
(220, 18003, '10', 'disponible', NULL, 'Deportivo', 25.00),
(221, 18003, '11', 'disponible', NULL, 'Deportivo', 25.00),
(222, 18003, '12', 'disponible', NULL, 'Deportivo', 25.00),
(223, 18003, '13', 'disponible', NULL, 'Deportivo', 25.00),
(224, 18003, '14', 'disponible', NULL, 'Deportivo', 25.00),
(225, 18003, '15', 'disponible', NULL, 'Deportivo', 25.00),
(226, 18003, '16', 'disponible', NULL, 'Deportivo', 25.00),
(227, 18003, '17', 'disponible', NULL, 'Deportivo', 25.00),
(228, 18003, '18', 'disponible', NULL, 'Deportivo', 25.00),
(229, 18003, '19', 'disponible', NULL, 'Deportivo', 25.00),
(230, 18003, '20', 'disponible', NULL, 'Deportivo', 25.00),
(231, 18003, '21', 'disponible', NULL, 'Deportivo', 25.00),
(232, 18003, '22', 'disponible', NULL, 'Deportivo', 25.00),
(233, 18003, '23', 'disponible', NULL, 'Deportivo', 25.00),
(234, 18003, '24', 'disponible', NULL, 'Deportivo', 25.00),
(235, 18003, '25', 'disponible', NULL, 'Deportivo', 25.00),
(236, 18003, '26', 'disponible', NULL, 'Deportivo', 25.00),
(237, 18003, '27', 'disponible', NULL, 'Deportivo', 25.00),
(238, 18003, '28', 'disponible', NULL, 'Deportivo', 25.00),
(239, 18003, '29', 'disponible', NULL, 'Deportivo', 25.00),
(240, 18003, '30', 'disponible', NULL, 'Deportivo', 25.00),
(241, 18003, '31', 'disponible', NULL, 'Deportivo', 25.00),
(242, 18003, '32', 'disponible', NULL, 'Deportivo', 25.00),
(243, 18003, '33', 'disponible', NULL, 'Deportivo', 25.00),
(244, 18003, '34', 'reservado', 1, 'Deportivo', 25.00),
(245, 18003, '35', 'disponible', NULL, 'Deportivo', 25.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `puntos` int(11) DEFAULT 0,
  `edad` int(11) DEFAULT NULL,
  `movimientos` int(11) DEFAULT NULL,
  `contrasena` varchar(255) NOT NULL DEFAULT 'defaultPassword'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `nombre`, `apellido`, `telefono`, `email`, `puntos`, `edad`, `movimientos`, `contrasena`) VALUES
(1, 'David', 'Blanco', '444356909', 'DavidBlanco@gmail.com', 0, 22, 0, '1'),
(732898, 'Daniel', 'Gonzalez', '4444879654', 'DanielGonzalez@gmail.com', 0, 20, 0, 'defaultPassword');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eventos`
--

CREATE TABLE `eventos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `tipo_evento` varchar(255) NOT NULL,
  `fecha` datetime NOT NULL,
  `capacidad` int(11) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `eventos`
--

INSERT INTO `eventos` (`id`, `nombre`, `tipo_evento`, `fecha`, `capacidad`, `precio`) VALUES
(18003, 'Tenis', 'Deportivo', '2024-07-01 09:00:00', 35, 25.00),
(18004, 'Maratón Ciudad', 'Deportivo', '2024-07-10 06:00:00', 35, 20.00),
(18005, 'Torneo de Golf', 'Deportivo', '2024-08-05 10:00:00', 35, 100.00),
(18006, 'Partido de Basketball', 'Deportivo', '2024-08-15 19:00:00', 35, 45.00),
(18007, 'Competencia de Natación', 'Deportivo', '2024-09-01 08:00:00', 35, 25.00),
(18008, 'Torneo de Tenis', 'Deportivo', '2024-09-10 18:00:00', 35, 35.00),
(18009, 'Campeonato de Boxeo', 'Deportivo', '2024-10-05 21:00:00', 35, 75.00),
(18010, 'Tenis', 'Deportivo', '2024-10-20 14:00:00', 35, 15.00),
(18011, 'Torneo de Tenis', 'Deportivo', '2024-11-05 17:00:00', 35, 40.00),
(18012, 'Rugby', 'Deportivo', '2024-11-20 16:00:00', 35, 50.00),
(18013, 'Fútbol Americano', 'Deportivo', '2024-12-05 20:00:00', 35, 60.00),
(18015, 'Partido de Fútbol', 'Deportivo', '2025-01-10 16:00:00', 35, 50.00),
(442206, 'Futbol Final', 'Futbol', '2024-05-25 17:00:00', 35, 120.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE `reservas` (
  `id_reserva` int(11) NOT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `id_evento` int(11) DEFAULT NULL,
  `id_asiento` int(11) DEFAULT NULL,
  `fecha_reserva` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administradores`
--
ALTER TABLE `administradores`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `asientos`
--
ALTER TABLE `asientos`
  ADD PRIMARY KEY (`id_asiento`),
  ADD KEY `fk_asientos_eventos` (`id_evento`),
  ADD KEY `fk_asientos_usuarios` (`id_usuario`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `eventos`
--
ALTER TABLE `eventos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`id_reserva`),
  ADD KEY `fk_reservas_clientes` (`id_cliente`),
  ADD KEY `fk_reservas_eventos` (`id_evento`),
  ADD KEY `fk_reservas_asientos` (`id_asiento`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `asientos`
--
ALTER TABLE `asientos`
  MODIFY `id_asiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=246;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=732899;

--
-- AUTO_INCREMENT de la tabla `eventos`
--
ALTER TABLE `eventos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=442207;

--
-- AUTO_INCREMENT de la tabla `reservas`
--
ALTER TABLE `reservas`
  MODIFY `id_reserva` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asientos`
--
ALTER TABLE `asientos`
  ADD CONSTRAINT `fk_asientos_eventos` FOREIGN KEY (`id_evento`) REFERENCES `eventos` (`id`),
  ADD CONSTRAINT `fk_asientos_usuarios` FOREIGN KEY (`id_usuario`) REFERENCES `clientes` (`id`);

--
-- Filtros para la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `fk_reservas_asientos` FOREIGN KEY (`id_asiento`) REFERENCES `asientos` (`id_asiento`),
  ADD CONSTRAINT `fk_reservas_clientes` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`),
  ADD CONSTRAINT `fk_reservas_eventos` FOREIGN KEY (`id_evento`) REFERENCES `eventos` (`id`),
  ADD CONSTRAINT `reservas_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`),
  ADD CONSTRAINT `reservas_ibfk_2` FOREIGN KEY (`id_evento`) REFERENCES `eventos` (`id`),
  ADD CONSTRAINT `reservas_ibfk_3` FOREIGN KEY (`id_asiento`) REFERENCES `asientos` (`id_asiento`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

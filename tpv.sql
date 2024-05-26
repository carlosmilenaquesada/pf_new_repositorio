-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-05-2024 a las 21:20:08
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
-- Base de datos: `tpv`
--
CREATE DATABASE IF NOT EXISTS `tpv` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tpv`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articles`
--

CREATE TABLE `articles` (
  `article_id` varchar(50) NOT NULL,
  `article_name` varchar(50) NOT NULL,
  `article_category_id` varchar(50) NOT NULL,
  `purchase_base_price` decimal(20,6) NOT NULL,
  `unit_sale_base_price` decimal(20,6) NOT NULL,
  `vat_id` varchar(50) NOT NULL,
  `units_in_stock` decimal(20,6) DEFAULT NULL,
  `units_sold` decimal(20,6) DEFAULT NULL,
  `offer_start_date` date DEFAULT NULL,
  `offer_end_date` date DEFAULT NULL,
  `offer_unit_sale_base_price` decimal(20,6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `articles`
--

INSERT INTO `articles` (`article_id`, `article_name`, `article_category_id`, `purchase_base_price`, `unit_sale_base_price`, `vat_id`, `units_in_stock`, `units_sold`, `offer_start_date`, `offer_end_date`, `offer_unit_sale_base_price`) VALUES
('COD000001', 'Producto A', 'CAT001', 0.000000, 15.990000, 'IVA001', 100.000000, 20.000000, '2024-04-01', '2025-04-15', 12.590000),
('COD000002', 'Producto B', 'CAT002', 0.000000, 25.490000, 'IVA002', 50.000000, 10.000000, NULL, NULL, NULL),
('COD000003', 'Producto C', 'CAT003', 0.000000, 9.990000, 'IVA001', 80.000000, 5.000000, NULL, NULL, NULL),
('COD000004', 'Producto D', 'CAT004', 0.000000, 39.990000, 'IVA002', 30.000000, 8.000000, '2024-03-15', '2024-03-30', 34.990000),
('COD000005', 'Producto E', 'CAT005', 0.000000, 19.990000, 'IVA001', 120.000000, 15.000000, NULL, NULL, NULL),
('COD000006', 'Producto F', 'CAT006', 0.000000, 29.990000, 'IVA001', 60.000000, 12.000000, '2024-04-01', '2024-04-15', 24.990000),
('COD000007', 'Producto G', 'CAT007', 0.000000, 49.990000, 'IVA002', 40.000000, 3.000000, NULL, NULL, NULL),
('COD000008', 'Producto H', 'CAT008', 0.000000, 14.990000, 'IVA001', 90.000000, 10.000000, NULL, NULL, NULL),
('COD000009', 'Producto I', 'CAT009', 0.000000, 35.990000, 'IVA002', 70.000000, 6.000000, NULL, NULL, NULL),
('COD000010', 'Producto J', 'CAT010', 0.000000, 12.490000, 'IVA001', 110.000000, 18.000000, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articles_categories`
--

CREATE TABLE `articles_categories` (
  `article_category_id` varchar(50) NOT NULL,
  `article_family_id` varchar(50) NOT NULL,
  `category_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `articles_categories`
--

INSERT INTO `articles_categories` (`article_category_id`, `article_family_id`, `category_name`) VALUES
('CAT001', 'FAM001', 'Categoría 1'),
('CAT002', 'FAM002', 'Categoría 2'),
('CAT003', 'FAM003', 'Categoría 3'),
('CAT004', 'FAM004', 'Categoría 4'),
('CAT005', 'FAM005', 'Categoría 5'),
('CAT006', 'FAM001', 'Categoría 6'),
('CAT007', 'FAM002', 'Categoría 7'),
('CAT008', 'FAM003', 'Categoría 8'),
('CAT009', 'FAM004', 'Categoría 9'),
('CAT010', 'FAM005', 'Categoría 10'),
('CAT011', 'FAM001', 'Categoría 11'),
('CAT012', 'FAM002', 'Categoría 12'),
('CAT013', 'FAM003', 'Categoría 13'),
('CAT014', 'FAM004', 'Categoría 14'),
('CAT015', 'FAM005', 'Categoría 15');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articles_families`
--

CREATE TABLE `articles_families` (
  `article_family_id` varchar(50) NOT NULL,
  `family_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `articles_families`
--

INSERT INTO `articles_families` (`article_family_id`, `family_name`) VALUES
('FAM001', 'Familia 1'),
('FAM002', 'Familia 2'),
('FAM003', 'Familia 3'),
('FAM004', 'Familia 4'),
('FAM005', 'Familia 5');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `barcodes`
--

CREATE TABLE `barcodes` (
  `barcode` varchar(50) NOT NULL,
  `article_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `barcodes`
--

INSERT INTO `barcodes` (`barcode`, `article_id`) VALUES
('8999900000017', 'COD000001'),
('8999900000024', 'COD000001'),
('8999900000031', 'COD000001'),
('8999900000048', 'COD000002'),
('8999900000055', 'COD000002'),
('8999900000062', 'COD000002'),
('8999900000079', 'COD000003'),
('8999900000086', 'COD000003'),
('8999900000093', 'COD000003'),
('8999900000109', 'COD000004'),
('COD000001', 'COD000001'),
('COD000002', 'COD000002'),
('COD000003', 'COD000003'),
('COD000004', 'COD000004');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `customers_taxables`
--

CREATE TABLE `customers_taxables` (
  `customer_tax_id` varchar(50) NOT NULL,
  `legal_company_name` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `legal_company_address` varchar(50) NOT NULL,
  `legal_country` varchar(50) NOT NULL,
  `legal_location` varchar(50) NOT NULL,
  `legal_zip_code` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `customers_taxables`
--

INSERT INTO `customers_taxables` (`customer_tax_id`, `legal_company_name`, `name`, `legal_company_address`, `legal_country`, `legal_location`, `legal_zip_code`) VALUES
('A11223344', 'company 888', NULL, 'abc', 'cba', 'acb', 'bca'),
('A12345678', 'Tech Solutions Inc.', 'Tech Solutions', 'Calle Principal, 3', 'España', 'Sevilla', '80088'),
('A13334455', 'Data Innovations', NULL, '852 Maple Avenue', 'Spain', 'Madrid', '28001'),
('A14432211', 'Global Ventures', NULL, '321 Pine Lane', 'Australia', 'Sydney', '2000'),
('A15678900', 'Tech Solutions2', NULL, '456 Elm Avenue', 'Canada', 'Toronto', 'M5A 1E1'),
('A15678901', 'Tech Solutions', NULL, '456 Elm Avenue', 'Canada', 'Toronto', 'M5A 1E1'),
('a15678912', 'Soluciones Informáticas SL', 'Soluciones Informáticas', 'Calle Mayor, 22', 'España', 'Madrid', '33255'),
('A17654321', 'Consultoría y Servicios SA de CV', 'Consultoría y Servicios', 'Av. Reforma, 555', 'España', 'Ciudad Real', '25522'),
('A18887777', 'Digital Creations', NULL, '741 Birch Street', 'France', 'Paris', '75001'),
('A18889999', 'Future Trends', NULL, '369 Walnut Street', 'Italy', 'Rome', '00184'),
('A19988776', 'Innovative Designs', NULL, '789 Oak Street', 'UK', 'London', 'SW1A 1AA'),
('A19998765', 'Innovative Tech', NULL, '987 Cedar Road', 'Germany', 'Berlin', '10115'),
('D89123456', 'Consultoría Integral Argentina', 'Consultoría Integral', 'Av. Corrientes, 123', 'España', 'Cáceres', '55664'),
('E32165498', 'Services Informatiques SAS', 'Services Informatiques', 'Calle De La Paz, 7', 'España', 'Almería', '22122'),
('PRUEBAAA', 'asdf', 'asdf', 'adsf', 'asdf', 'asdf', 'asdf');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `customers_types`
--

CREATE TABLE `customers_types` (
  `customer_type_id` varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `customers_types`
--

INSERT INTO `customers_types` (`customer_type_id`, `description`) VALUES
('CUST_TYPE001', 'Cliente anónimo'),
('CUST_TYPE002', 'Cliente VIP'),
('CUST_TYPE003', 'Cliente fiscal');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `payment_methods`
--

CREATE TABLE `payment_methods` (
  `payment_method_id` varchar(50) NOT NULL,
  `payment_method_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `payment_methods`
--

INSERT INTO `payment_methods` (`payment_method_id`, `payment_method_name`) VALUES
('PAYMET001', 'undefined'),
('PAYMET002', 'cash'),
('PAYMET003', 'bankcard');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tickets`
--

CREATE TABLE `tickets` (
  `ticket_id` varchar(50) NOT NULL,
  `sale_date` date NOT NULL,
  `customer_tax_id` varchar(50) DEFAULT NULL,
  `ticket_status_id` varchar(50) NOT NULL,
  `payment_method_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tickets`
--

INSERT INTO `tickets` (`ticket_id`, `sale_date`, `customer_tax_id`, `ticket_status_id`, `payment_method_id`) VALUES
('asdf', '2024-05-31', 'A11223344', 'STAT001', 'PAYMET001'),
('asdf2', '2024-05-31', 'A11223344', 'STAT001', 'PAYMET001');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tickets_lines`
--

CREATE TABLE `tickets_lines` (
  `ticket_line_id` varchar(50) NOT NULL,
  `ticket_id` varchar(50) NOT NULL,
  `article_id` varchar(50) NOT NULL,
  `article_quantity` decimal(20,6) NOT NULL,
  `purchase_base_price_at_sale_moment` decimal(20,6) NOT NULL,
  `sale_base_price_at_sale_moment` decimal(20,6) NOT NULL,
  `vat_id` varchar(50) NOT NULL,
  `sold_during_offer` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tickets_status`
--

CREATE TABLE `tickets_status` (
  `ticket_status_id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tickets_status`
--

INSERT INTO `tickets_status` (`ticket_status_id`, `name`) VALUES
('STAT001', 'processing'),
('STAT002', 'paid'),
('STAT003', 'reserved'),
('STAT004', 'due'),
('STAT005', 'cancelled');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `user_id` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `privileges` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`user_id`, `password`, `privileges`) VALUES
('anonimos', 'anonimos', 'basic'),
('basic', 'basic', 'basic'),
('carlos', 'carlos', 'admin'),
('manager', 'manager', 'manager'),
('super', 'super', 'super');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vats`
--

CREATE TABLE `vats` (
  `vat_id` varchar(50) NOT NULL,
  `vat_description` varchar(50) NOT NULL,
  `vat_fraction` decimal(20,6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vats`
--

INSERT INTO `vats` (`vat_id`, `vat_description`, `vat_fraction`) VALUES
('IVA001', 'IVA GENERAL 21%', 0.210000),
('IVA002', 'IVA REDUCIDO 10%', 0.100000),
('IVA003', 'IVA SUPERREDUCIDO 5%', 0.050000),
('IVA004', 'IVA SUPERREDUCIDO 4%', 0.040000),
('IVA005', 'IVA SUPERREDUCIDO 0%', 0.000000),
('IVA006', 'SIN IVA', 0.000000);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articles`
--
ALTER TABLE `articles`
  ADD PRIMARY KEY (`article_id`) USING BTREE;

--
-- Indices de la tabla `articles_categories`
--
ALTER TABLE `articles_categories`
  ADD PRIMARY KEY (`article_category_id`),
  ADD KEY `article_family_id` (`article_family_id`);

--
-- Indices de la tabla `articles_families`
--
ALTER TABLE `articles_families`
  ADD PRIMARY KEY (`article_family_id`);

--
-- Indices de la tabla `barcodes`
--
ALTER TABLE `barcodes`
  ADD PRIMARY KEY (`barcode`);

--
-- Indices de la tabla `customers_taxables`
--
ALTER TABLE `customers_taxables`
  ADD PRIMARY KEY (`customer_tax_id`) USING BTREE;

--
-- Indices de la tabla `customers_types`
--
ALTER TABLE `customers_types`
  ADD PRIMARY KEY (`customer_type_id`) USING BTREE;

--
-- Indices de la tabla `payment_methods`
--
ALTER TABLE `payment_methods`
  ADD PRIMARY KEY (`payment_method_id`);

--
-- Indices de la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`ticket_id`);

--
-- Indices de la tabla `tickets_lines`
--
ALTER TABLE `tickets_lines`
  ADD PRIMARY KEY (`ticket_line_id`,`ticket_id`) USING BTREE;

--
-- Indices de la tabla `tickets_status`
--
ALTER TABLE `tickets_status`
  ADD PRIMARY KEY (`ticket_status_id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Indices de la tabla `vats`
--
ALTER TABLE `vats`
  ADD PRIMARY KEY (`vat_id`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `articles`
--
ALTER TABLE `articles`
  ADD CONSTRAINT `articles_ibfk_1` FOREIGN KEY (`vat_id`) REFERENCES `vats` (`vat_id`);

--
-- Filtros para la tabla `articles_categories`
--
ALTER TABLE `articles_categories`
  ADD CONSTRAINT `articles_categories_ibfk_1` FOREIGN KEY (`article_family_id`) REFERENCES `articles_families` (`article_family_id`);

--
-- Filtros para la tabla `barcodes`
--
ALTER TABLE `barcodes`
  ADD CONSTRAINT `barcodes_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `articles` (`article_id`);

--
-- Filtros para la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`customer_tax_id`) REFERENCES `customers_taxables` (`customer_tax_id`);

--
-- Filtros para la tabla `tickets_lines`
--
ALTER TABLE `tickets_lines`
  ADD CONSTRAINT `tickets_lines_ibfk_1` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`ticket_id`),
  ADD CONSTRAINT `tickets_lines_ibfk_2` FOREIGN KEY (`vat_id`) REFERENCES `vats` (`vat_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

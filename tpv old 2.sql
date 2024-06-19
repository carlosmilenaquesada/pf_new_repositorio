-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-06-2024 a las 10:13:10
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
  `unit_sale_base_price` decimal(20,6) NOT NULL,
  `vat_id` varchar(50) NOT NULL,
  `article_family_id` varchar(50) NOT NULL,
  `offer_start_date` date DEFAULT NULL,
  `offer_end_date` date DEFAULT NULL,
  `offer_unit_sale_base_price` decimal(20,6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `articles`
--

INSERT INTO `articles` (`article_id`, `article_name`, `unit_sale_base_price`, `vat_id`, `article_family_id`, `offer_start_date`, `offer_end_date`, `offer_unit_sale_base_price`) VALUES
('AAA000', 'Manzana', 1.200000, 'IVA003', 'AF001', '2024-07-01', '2024-07-10', 1.100000),
('AAA001', 'Banana', 1.100000, 'IVA003', 'AF001', NULL, NULL, NULL),
('AAA002', 'Lechuga', 0.900000, 'IVA003', 'AF001', '2024-07-15', '2024-07-20', 0.850000),
('AAA003', 'Tomate', 1.500000, 'IVA003', 'AF001', NULL, NULL, NULL),
('AAA004', 'Zanahoria', 0.800000, 'IVA003', 'AF001', NULL, NULL, NULL),
('AAA005', 'Pimiento', 2.000000, 'IVA003', 'AF001', '2024-08-01', '2024-08-07', 1.900000),
('AAA006', 'Pera', 1.300000, 'IVA003', 'AF001', NULL, NULL, NULL),
('AAA007', 'Naranja', 1.000000, 'IVA003', 'AF001', NULL, NULL, NULL),
('AAA008', 'Cebolla', 0.700000, 'IVA003', 'AF001', '2024-09-01', '2024-09-05', 0.650000),
('AAA009', 'Patata', 0.600000, 'IVA003', 'AF001', NULL, NULL, NULL),
('AAA010', 'Leche', 0.950000, 'IVA003', 'AF002', NULL, NULL, NULL),
('AAA011', 'Yogur', 0.600000, 'IVA003', 'AF002', NULL, NULL, NULL),
('AAA012', 'Queso', 3.500000, 'IVA003', 'AF002', '2024-07-10', '2024-07-15', 3.300000),
('AAA013', 'Mantequilla', 1.700000, 'IVA003', 'AF002', NULL, NULL, NULL),
('AAA014', 'Crema de leche', 1.200000, 'IVA003', 'AF002', '2024-07-20', '2024-07-25', 1.100000),
('AAA015', 'Helado', 2.500000, 'IVA003', 'AF002', NULL, NULL, NULL),
('AAA016', 'Kéfir', 1.000000, 'IVA003', 'AF002', NULL, NULL, NULL),
('AAA017', 'Requesón', 1.800000, 'IVA003', 'AF002', '2024-08-05', '2024-08-10', 1.700000),
('AAA018', 'Leche condensada', 1.400000, 'IVA003', 'AF002', NULL, NULL, NULL),
('AAA019', 'Natillas', 0.900000, 'IVA003', 'AF002', NULL, NULL, NULL),
('AAA020', 'Pechuga de Pollo', 4.500000, 'IVA002', 'AF003', '2024-09-01', '2024-09-07', 4.300000),
('AAA021', 'Carne de Res', 6.000000, 'IVA002', 'AF003', NULL, NULL, NULL),
('AAA022', 'Chorizo', 3.200000, 'IVA002', 'AF003', NULL, NULL, NULL),
('AAA023', 'Salchichas', 2.500000, 'IVA002', 'AF003', '2024-07-15', '2024-07-20', 2.400000),
('AAA024', 'Bacon', 3.000000, 'IVA002', 'AF003', NULL, NULL, NULL),
('AAA025', 'Jamón', 5.500000, 'IVA002', 'AF003', '2024-08-01', '2024-08-05', 5.300000),
('AAA026', 'Lomo de Cerdo', 4.800000, 'IVA002', 'AF003', NULL, NULL, NULL),
('AAA027', 'Morcilla', 2.700000, 'IVA002', 'AF003', NULL, NULL, NULL),
('AAA028', 'Salami', 3.600000, 'IVA002', 'AF003', '2024-09-10', '2024-09-15', 3.500000),
('AAA029', 'Ternera', 6.500000, 'IVA002', 'AF003', NULL, NULL, NULL),
('AAA030', 'Pan Integral', 1.200000, 'IVA002', 'AF004', NULL, NULL, NULL),
('AAA031', 'Croissant', 0.800000, 'IVA002', 'AF004', NULL, NULL, NULL),
('AAA032', 'Pastel de Chocolate', 3.000000, 'IVA002', 'AF004', '2024-08-15', '2024-08-20', 2.800000),
('AAA033', 'Magdalenas', 2.200000, 'IVA002', 'AF004', NULL, NULL, NULL),
('AAA034', 'Rosquillas', 1.500000, 'IVA002', 'AF004', '2024-07-05', '2024-07-10', 1.400000),
('AAA035', 'Pan de Molde', 1.000000, 'IVA002', 'AF004', NULL, NULL, NULL),
('AAA036', 'Bizcocho', 2.800000, 'IVA002', 'AF004', NULL, NULL, NULL),
('AAA037', 'Pan de Centeno', 1.500000, 'IVA002', 'AF004', NULL, NULL, NULL),
('AAA038', 'Donuts', 1.700000, 'IVA002', 'AF004', '2024-09-01', '2024-09-05', 1.600000),
('AAA039', 'Empanada', 3.500000, 'IVA002', 'AF004', NULL, NULL, NULL),
('AAA040', 'Agua Mineral', 0.500000, 'IVA002', 'AF005', NULL, NULL, NULL),
('AAA041', 'Refresco', 1.000000, 'IVA002', 'AF005', NULL, NULL, NULL),
('AAA042', 'Zumo de Naranja', 1.200000, 'IVA002', 'AF005', '2024-08-01', '2024-08-05', 1.100000),
('AAA043', 'Cerveza', 1.500000, 'IVA002', 'AF005', NULL, NULL, NULL),
('AAA044', 'Vino Tinto', 5.000000, 'IVA002', 'AF005', NULL, NULL, NULL),
('AAA045', 'Vino Blanco', 4.500000, 'IVA002', 'AF005', '2024-07-15', '2024-07-20', 4.300000),
('AAA046', 'Té', 1.000000, 'IVA002', 'AF005', NULL, NULL, NULL),
('AAA047', 'Café', 3.000000, 'IVA002', 'AF005', NULL, NULL, NULL),
('AAA048', 'Limonada', 1.100000, 'IVA002', 'AF005', '2024-09-01', '2024-09-05', 1.000000),
('AAA049', 'Bebida Energética', 2.000000, 'IVA002', 'AF005', NULL, NULL, NULL),
('AAA050', 'Patatas Fritas', 1.200000, 'IVA002', 'AF006', NULL, NULL, NULL),
('AAA051', 'Chocolate', 2.000000, 'IVA002', 'AF006', '2024-07-20', '2024-07-25', 1.900000),
('AAA052', 'Chicles', 0.800000, 'IVA002', 'AF006', NULL, NULL, NULL),
('AAA053', 'Galletas', 1.500000, 'IVA002', 'AF006', '2024-08-10', '2024-08-15', 1.400000),
('AAA054', 'Caramelos', 0.600000, 'IVA002', 'AF006', NULL, NULL, NULL),
('AAA055', 'Barritas de Cereal', 1.000000, 'IVA002', 'AF006', NULL, NULL, NULL),
('AAA056', 'Frutos Secos', 2.500000, 'IVA002', 'AF006', NULL, NULL, NULL),
('AAA057', 'Palomitas', 1.200000, 'IVA002', 'AF006', NULL, NULL, NULL),
('AAA058', 'Gomitas', 0.700000, 'IVA002', 'AF006', NULL, NULL, NULL),
('AAA059', 'Tarta', 3.000000, 'IVA002', 'AF006', '2024-09-01', '2024-09-05', 2.800000),
('AAA060', 'Detergente', 3.500000, 'IVA001', 'AF007', NULL, NULL, NULL),
('AAA061', 'Suavizante', 2.000000, 'IVA001', 'AF007', NULL, NULL, NULL),
('AAA062', 'Lejía', 1.000000, 'IVA001', 'AF007', '2024-07-10', '2024-07-15', 0.900000),
('AAA063', 'Desinfectante', 2.500000, 'IVA001', 'AF007', NULL, NULL, NULL),
('AAA064', 'Limpiacristales', 1.800000, 'IVA001', 'AF007', NULL, NULL, NULL),
('AAA065', 'Jabón en Polvo', 3.000000, 'IVA001', 'AF007', '2024-08-01', '2024-08-07', 2.800000),
('AAA066', 'Esponjas', 0.800000, 'IVA001', 'AF007', NULL, NULL, NULL),
('AAA067', 'Lavavajillas', 2.200000, 'IVA001', 'AF007', NULL, NULL, NULL),
('AAA068', 'Quitamanchas', 2.000000, 'IVA001', 'AF007', '2024-09-01', '2024-09-07', 1.900000),
('AAA069', 'Ambientador', 1.500000, 'IVA001', 'AF007', NULL, NULL, NULL),
('AAA070', 'Champú', 2.500000, 'IVA001', 'AF008', NULL, NULL, NULL),
('AAA071', 'Acondicionador', 2.700000, 'IVA001', 'AF008', '2024-08-10', '2024-08-15', 2.500000),
('AAA072', 'Jabón Líquido', 1.500000, 'IVA001', 'AF008', NULL, NULL, NULL),
('AAA073', 'Pasta de Dientes', 1.200000, 'IVA001', 'AF008', NULL, NULL, NULL),
('AAA074', 'Cepillo de Dientes', 1.000000, 'IVA001', 'AF008', NULL, NULL, NULL),
('AAA075', 'Desodorante', 2.000000, 'IVA001', 'AF008', '2024-07-15', '2024-07-20', 1.900000),
('AAA076', 'Toallas Femeninas', 3.000000, 'IVA001', 'AF008', NULL, NULL, NULL),
('AAA077', 'Pañuelos', 0.800000, 'IVA001', 'AF008', NULL, NULL, NULL),
('AAA078', 'Cuchillas de Afeitar', 2.500000, 'IVA001', 'AF008', '2024-09-01', '2024-09-07', 2.300000),
('AAA079', 'Gel de Ducha', 1.700000, 'IVA001', 'AF008', NULL, NULL, NULL),
('AAA080', 'Atún en Lata', 2.000000, 'IVA002', 'AF009', NULL, NULL, NULL),
('AAA081', 'Tomate Triturado', 1.000000, 'IVA002', 'AF009', '2024-07-20', '2024-07-25', 0.950000),
('AAA082', 'Maíz en Lata', 1.200000, 'IVA002', 'AF009', NULL, NULL, NULL),
('AAA083', 'Guisantes en Lata', 1.100000, 'IVA002', 'AF009', NULL, NULL, NULL),
('AAA084', 'Sardinas en Lata', 1.500000, 'IVA002', 'AF009', '2024-08-01', '2024-08-07', 1.400000),
('AAA085', 'Alubias en Lata', 1.000000, 'IVA002', 'AF009', NULL, NULL, NULL),
('AAA086', 'Chícharos en Lata', 1.100000, 'IVA002', 'AF009', NULL, NULL, NULL),
('AAA087', 'Aceitunas en Lata', 1.500000, 'IVA002', 'AF009', '2024-09-01', '2024-09-05', 1.400000),
('AAA088', 'Piña en Almíbar', 1.700000, 'IVA002', 'AF009', NULL, NULL, NULL),
('AAA089', 'Melocotón en Almíbar', 1.800000, 'IVA002', 'AF009', NULL, NULL, NULL),
('AAA090', 'Helado de Vainilla', 3.000000, 'IVA002', 'AF010', '2024-07-10', '2024-07-15', 2.900000),
('AAA091', 'Guisantes Congelados', 1.500000, 'IVA002', 'AF010', NULL, NULL, NULL),
('AAA092', 'Pizza Congelada', 4.000000, 'IVA002', 'AF010', NULL, NULL, NULL),
('AAA093', 'Pollo Congelado', 5.000000, 'IVA002', 'AF010', '2024-08-01', '2024-08-05', 4.800000),
('AAA094', 'Pescado Congelado', 6.000000, 'IVA002', 'AF010', NULL, NULL, NULL),
('AAA095', 'Verduras Congeladas', 2.000000, 'IVA002', 'AF010', NULL, NULL, NULL),
('AAA096', 'Mariscos Congelados', 7.000000, 'IVA002', 'AF010', '2024-09-01', '2024-09-05', 6.800000),
('AAA097', 'Croquetas Congeladas', 3.500000, 'IVA002', 'AF010', NULL, NULL, NULL),
('AAA098', 'Lasagna Congelada', 4.500000, 'IVA002', 'AF010', '2024-07-15', '2024-07-20', 4.300000),
('AAA099', 'Hamburguesa Congelada', 3.000000, 'IVA002', 'AF010', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `article_families`
--

CREATE TABLE `article_families` (
  `article_family_id` varchar(50) NOT NULL,
  `family_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `article_families`
--

INSERT INTO `article_families` (`article_family_id`, `family_name`) VALUES
('AF001', 'Frutas y Verduras'),
('AF002', 'Lácteos'),
('AF003', 'Carnes y Embutidos'),
('AF004', 'Panadería y Pastelería'),
('AF005', 'Bebidas'),
('AF006', 'Snacks y Golosinas'),
('AF007', 'Productos de Limpieza'),
('AF008', 'Higiene Personal'),
('AF009', 'Alimentos Enlatados'),
('AF010', 'Congelados');

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
('8999900000017', 'AAA003'),
('8999900000024', 'AAA003'),
('8999900000109', 'AAA021'),
('8999900000079', 'AAA033'),
('8999900000031', 'AAA046'),
('8999900000055', 'AAA057'),
('8999900000062', 'AAA062'),
('8999900000048', 'AAA072'),
('8999900000093', 'AAA081'),
('8999900000086', 'AAA097');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `customers_taxables`
--

CREATE TABLE `customers_taxables` (
  `customer_tax_id` varchar(50) NOT NULL,
  `legal_company_name` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `legal_company_address` varchar(50) NOT NULL,
  `legal_country` varchar(50) NOT NULL,
  `legal_location` varchar(50) NOT NULL,
  `legal_zip_code` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `customers_taxables`
--

INSERT INTO `customers_taxables` (`customer_tax_id`, `legal_company_name`, `name`, `legal_company_address`, `legal_country`, `legal_location`, `legal_zip_code`) VALUES
('A01234567', 'Monarch Sciences', 'Sara Hernández', 'Calle San Juan 567', 'España', 'Las Palmas', '35001'),
('A12345670', 'Cyberdyne Systems', 'Pablo Fernández', 'Rambla Catalunya 890', 'España', 'Santa Cruz de Tenerife', '38001'),
('A12345678', 'Globex Corporation', 'Juan Pérez', 'Calle Principal 123', 'España', 'Madrid', '28001'),
('A23456701', 'Oceanic Airlines', 'Carmen López', 'Plaza España 234', 'España', 'Valladolid', '47001'),
('A23456789', 'Acme Corporation', 'María Gómez', 'Avenida Central 456', 'España', 'Barcelona', '08002'),
('A34567812', 'Virtucon', 'Diego Pérez', 'Calle Granada 456', 'España', 'Córdoba', '14001'),
('A34567890', 'Wayne Enterprises', 'Pedro Martínez', 'Plaza Mayor 789', 'España', 'Valencia', '46001'),
('A45678123', 'Sirius Cybernetics Corporation', 'Eva Martínez', 'Avenida del Mar 789', 'España', 'Granada', '18001'),
('A45678901', 'Stark Industries', 'Ana López', 'Calle Gran Vía 567', 'España', 'Sevilla', '41001'),
('A56789012', 'LexCorp', 'Carlos Rodríguez', 'Avenida Diagonal 890', 'España', 'Zaragoza', '50001'),
('A67890123', 'Umbrella Corporation', 'Elena Sánchez', 'Paseo de Gracia 234', 'España', 'Málaga', '29001'),
('A78901234', 'Tyrell Corporation', 'Luis García', 'Calle Mayor 456', 'España', 'Murcia', '30001'),
('A89012345', 'Gringotts Wizarding Bank', 'Laura Martín', 'Avenida Libertad 789', 'España', 'Bilbao', '48001'),
('A90123456', 'Weyland-Yutani Corporation', 'Javier Ruiz', 'Calle Real 123', 'España', 'Alicante', '03001'),
('B12345678', 'Spacely Space Sprockets', 'Natalia López', 'Rambla Nova 567', 'España', 'San Sebastián', '20001'),
('B23456789', 'Richie Rich Enterprises', 'Raul Fernández', 'Plaza del Castillo 890', 'España', 'Oviedo', '33001'),
('B56781234', 'InGen', 'Mario Sánchez', 'Calle Mayor 123', 'España', 'Salamanca', '37001'),
('B67812345', 'Gringotts Wizarding Bank', 'Andrea García', 'Paseo de la Castellana 456', 'España', 'Toledo', '45001'),
('B78123456', 'Oscorp', 'Isabel Martín', 'Avenida de la Paz 789', 'España', 'Pamplona', '31001'),
('B81234567', 'Aperture Science', 'Marcos Rodríguez', 'Calle Principal 234', 'España', 'Logroño', '26001');

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
('PAY001', 'cash'),
('PAY002', 'card'),
('PAY003', 'bank_transfer');

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ticket_lines`
--

CREATE TABLE `ticket_lines` (
  `ticket_line_id` varchar(50) NOT NULL,
  `ticket_id` varchar(50) NOT NULL,
  `article_id` varchar(50) NOT NULL,
  `article_family_id` varchar(50) NOT NULL,
  `article_quantity` decimal(20,6) NOT NULL,
  `applicated_sale_base_price` decimal(20,6) NOT NULL,
  `vat_id` varchar(50) NOT NULL,
  `vat_fraction` decimal(20,6) NOT NULL,
  `sold_during_offer` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ticket_status`
--

CREATE TABLE `ticket_status` (
  `ticket_status_id` varchar(50) NOT NULL,
  `status_description` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ticket_status`
--

INSERT INTO `ticket_status` (`ticket_status_id`, `status_description`) VALUES
('STA001', 'paid'),
('STA002', 'canceled'),
('STA003', 'reserved');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `user_id` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`user_id`, `password`) VALUES
('admin', 'admin'),
('employee001', 'employee001'),
('super', 'super');

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
('IVA001', 'GENERAL 21%', 0.210000),
('IVA002', 'REDUCIDO 10%', 0.100000),
('IVA003', 'SUPERREDUCIDO 4%', 0.040000);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articles`
--
ALTER TABLE `articles`
  ADD PRIMARY KEY (`article_id`),
  ADD KEY `article_family_id` (`article_family_id`),
  ADD KEY `vat_id` (`vat_id`);

--
-- Indices de la tabla `article_families`
--
ALTER TABLE `article_families`
  ADD PRIMARY KEY (`article_family_id`);

--
-- Indices de la tabla `barcodes`
--
ALTER TABLE `barcodes`
  ADD PRIMARY KEY (`barcode`),
  ADD KEY `article_id` (`article_id`);

--
-- Indices de la tabla `customers_taxables`
--
ALTER TABLE `customers_taxables`
  ADD PRIMARY KEY (`customer_tax_id`);

--
-- Indices de la tabla `payment_methods`
--
ALTER TABLE `payment_methods`
  ADD PRIMARY KEY (`payment_method_id`);

--
-- Indices de la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`ticket_id`),
  ADD KEY `customer_tax_id` (`customer_tax_id`),
  ADD KEY `payment_method_id` (`payment_method_id`),
  ADD KEY `ticket_status_id` (`ticket_status_id`);

--
-- Indices de la tabla `ticket_lines`
--
ALTER TABLE `ticket_lines`
  ADD PRIMARY KEY (`ticket_line_id`),
  ADD KEY `ticket_id` (`ticket_id`),
  ADD KEY `article_id` (`article_id`),
  ADD KEY `vat_id` (`vat_id`),
  ADD KEY `article_family_id` (`article_family_id`);

--
-- Indices de la tabla `ticket_status`
--
ALTER TABLE `ticket_status`
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
  ADD CONSTRAINT `articles_ibfk_1` FOREIGN KEY (`article_family_id`) REFERENCES `article_families` (`article_family_id`),
  ADD CONSTRAINT `articles_ibfk_2` FOREIGN KEY (`vat_id`) REFERENCES `vats` (`vat_id`);

--
-- Filtros para la tabla `barcodes`
--
ALTER TABLE `barcodes`
  ADD CONSTRAINT `barcodes_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `articles` (`article_id`);

--
-- Filtros para la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`customer_tax_id`) REFERENCES `customers_taxables` (`customer_tax_id`),
  ADD CONSTRAINT `tickets_ibfk_2` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_methods` (`payment_method_id`),
  ADD CONSTRAINT `tickets_ibfk_3` FOREIGN KEY (`ticket_status_id`) REFERENCES `ticket_status` (`ticket_status_id`);

--
-- Filtros para la tabla `ticket_lines`
--
ALTER TABLE `ticket_lines`
  ADD CONSTRAINT `ticket_lines_ibfk_1` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`ticket_id`),
  ADD CONSTRAINT `ticket_lines_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `articles` (`article_id`),
  ADD CONSTRAINT `ticket_lines_ibfk_3` FOREIGN KEY (`vat_id`) REFERENCES `vats` (`vat_id`),
  ADD CONSTRAINT `ticket_lines_ibfk_4` FOREIGN KEY (`article_family_id`) REFERENCES `article_families` (`article_family_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

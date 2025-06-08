-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2025 at 11:47 AM
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
-- Database: `parkir_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `parking_rates`
--

CREATE TABLE `parking_rates` (
  `id` int(11) NOT NULL,
  `vehicle_type` varchar(50) NOT NULL,
  `rate_per_hour` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parking_rates`
--

INSERT INTO `parking_rates` (`id`, `vehicle_type`, `rate_per_hour`) VALUES
(1, 'Mobil', 3000),
(2, 'Motor', 2000),
(3, 'Truk', 5000);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `plate_number` varchar(20) DEFAULT NULL,
  `vehicle_type` varchar(20) DEFAULT NULL,
  `entry_time` datetime DEFAULT NULL,
  `exit_time` datetime DEFAULT NULL,
  `total_fee` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `plate_number`, `vehicle_type`, `entry_time`, `exit_time`, `total_fee`) VALUES
(2, '365T8U83', 'Mobil', '2025-05-27 22:17:45', '2025-06-07 01:43:15', 25000),
(4, '12476Y73', 'Motor', '2025-05-27 22:48:57', '2025-05-27 22:49:50', 3000),
(5, '327654T78', 'Motor', '2025-05-27 23:04:49', '2025-05-27 23:06:01', 3000),
(7, '1253T8Y8', 'Motor', '2025-05-28 19:46:50', '2025-05-28 19:47:00', 3000),
(9, '237537T685', 'Motor', '2025-05-31 22:31:01', '2025-06-06 22:06:04', 25000),
(12, '23764T7', 'Motor', '2025-06-06 20:18:03', '2025-06-06 20:18:14', 2000),
(13, '1254SVU67', 'Mobil', '2025-06-07 11:08:01', '2025-06-07 11:08:08', 3000),
(14, '1254TU56', 'Motor', '2025-06-07 11:08:52', '2025-06-07 11:08:57', 2000),
(15, 'WRG23RT', 'Motor', '2025-06-07 11:23:17', '2025-06-07 11:23:27', 2000),
(17, '123TR456', 'Mobil', '2025-06-07 11:24:38', '2025-06-07 11:24:44', 3000),
(18, 'WRS56RD', 'Motor', '2025-06-07 11:39:07', '2025-06-07 11:39:19', 2000),
(24, '234RT678', 'Mobil', '2025-06-07 18:56:34', '2025-06-07 18:56:41', 3000),
(25, '214YU70', 'Motor', '2025-06-07 19:07:28', '2025-06-07 19:11:34', 2000),
(26, '123ERT567', 'Motor', '2025-06-07 20:20:40', '2025-06-07 20:32:25', 2000),
(27, '123456TR78', 'Motor', '2025-06-07 20:39:40', '2025-06-07 20:39:47', 2000),
(28, '1763542763UI78', 'Mobil', '2025-06-07 22:19:01', '2025-06-07 22:19:06', 3000);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'admin', 'admin123', 'admin'),
(3, 'dhafa', 'dhafa123', 'operator'),
(5, 'farhan', 'farhan123', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `parking_rates`
--
ALTER TABLE `parking_rates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `vehicle_type` (`vehicle_type`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `parking_rates`
--
ALTER TABLE `parking_rates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

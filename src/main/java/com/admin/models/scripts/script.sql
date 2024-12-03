CREATE DATABASE IF NOT EXISTS `User_Management`;

USE `User_Management`;

CREATE TABLE `User` (
    `user_id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) UNIQUE NOT NULL,
    `full_name` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255),
    `dob` DATE,
    `gender` ENUM('male', 'female', 'other') NOT NULL,
    `email` VARCHAR(255) UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `is_admin` BOOLEAN DEFAULT FALSE,
    `status` ENUM('online', 'offline', 'locked') DEFAULT 'offline',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `Login_History` (
    `history_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT,
    `login_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Chat_Groups` (
    `group_id` INT AUTO_INCREMENT PRIMARY KEY,
    `group_name` VARCHAR(255) NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `Friends` (
    `user_id` INT NOT NULL,
    `friend_id` INT NOT NULL,
    `status` ENUM('accepted', 'blocked') DEFAULT 'accepted',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`, `friend_id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`friend_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Friends_Request` (
    `request_id` INT AUTO_INCREMENT PRIMARY KEY,
    `sender_id` INT NOT NULL,
    `receiver_id` INT NOT NULL,
    FOREIGN KEY (`sender_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`receiver_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Messages` (
    `message_id` INT AUTO_INCREMENT PRIMARY KEY,
    `sender_id` INT NOT NULL,
    `receiver_id` INT,
    `group_id` INT,
    `content` TEXT NOT NULL,
    `sent_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`sender_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`receiver_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`group_id`) REFERENCES `Chat_Groups`(`group_id`) ON DELETE CASCADE
);

CREATE TABLE `User_Messages` (
    `message_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `is_visible` BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (`message_id`, `user_id`),
    FOREIGN KEY (`message_id`) REFERENCES `Messages`(`message_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Group_Members` (
    `group_id` INT,
    `user_id` INT,
    `joined_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`group_id`, `user_id`),
    FOREIGN KEY (`group_id`) REFERENCES `Chat_Groups`(`group_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Group_Admins` (
    `group_id` INT,
    `user_id` INT,
    PRIMARY KEY (`group_id`, `user_id`),
    FOREIGN KEY (`group_id`) REFERENCES `Chat_Groups`(`group_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `Spam_Reports` (
    `report_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT,
    `report_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `reason` TEXT,
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

CREATE TABLE `User_Activity` (
    `activity_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT,
    `activity_type` ENUM('app open', 'chat', 'group join') NOT NULL,
    `description` TEXT,
    `activity_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);
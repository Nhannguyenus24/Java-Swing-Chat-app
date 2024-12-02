-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS `User_Management`;
USE `User_Management`;

-- Tạo bảng `User` (Quản lý người dùng)
CREATE TABLE `User` (
    `user_id` INT AUTO_INCREMENT PRIMARY KEY,            -- Khóa chính
    `username` VARCHAR(255) UNIQUE NOT NULL,             -- Tên đăng nhập
    `full_name` VARCHAR(255) NOT NULL,                   -- Họ tên
    `address` VARCHAR(255),                              -- Địa chỉ
    `dob` DATE,                                          -- Ngày sinh
    `gender` ENUM('Male', 'Female', 'Non-binary') NOT NULL,                 -- Giới tính
    `email` VARCHAR(255) UNIQUE,                         -- Địa chỉ email
    `status` ENUM('Active', 'Locked', 'Inactive') DEFAULT 'Inactive', -- Trạng thái tài khoản
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,     -- Thời gian tạo tài khoản
    `password` VARCHAR(255) NOT NULL,                    -- Mật khẩu
    `is_admin` BOOLEAN DEFAULT FALSE                     -- Xác định người quản trị
);

-- Tạo bảng `Login_History` (Lịch sử đăng nhập)
CREATE TABLE `Login_History` (
    `history_id` INT AUTO_INCREMENT PRIMARY KEY,         -- Khóa chính
    `user_id` INT,                                       -- Khóa ngoại tham chiếu `User`
    `login_time` DATETIME DEFAULT CURRENT_TIMESTAMP,     -- Thời gian đăng nhập
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

-- Tạo bảng `Friends` (Danh sách bạn bè)
CREATE TABLE `Friends` (
    `user_id` INT,                                       -- Khóa ngoại tham chiếu `User`
    `friend_id` INT,                                     -- Khóa ngoại tham chiếu `User`
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,     -- Thời gian kết bạn
    PRIMARY KEY (`user_id`, `friend_id`),                -- Khóa chính (2 cột)
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE,
    FOREIGN KEY (`friend_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

-- Tạo bảng `Chat_Groups` (Nhóm chat)
CREATE TABLE `Chat_Groups` (
    `group_id` INT AUTO_INCREMENT PRIMARY KEY,           -- Khóa chính
    `group_name` VARCHAR(255) NOT NULL,                  -- Tên nhóm
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP      -- Thời gian tạo nhóm
);

-- Tạo bảng `Group_Members` (Thành viên nhóm)
CREATE TABLE `Group_Members` (
    `group_id` INT,                                      -- Khóa ngoại tham chiếu `Chat_Groups`
    `user_id` INT,                                       -- Khóa ngoại tham chiếu `User`
    `joined_at` DATETIME DEFAULT CURRENT_TIMESTAMP,      -- Thời gian gia nhập nhóm
    PRIMARY KEY (`group_id`, `user_id`),                 -- Khóa chính (2 cột)
    FOREIGN KEY (`group_id`) REFERENCES `Chat_Groups`(`group_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

-- Tạo bảng `Group_Admins` (Quản trị viên nhóm)
CREATE TABLE `Group_Admins` (
    `group_id` INT,                                      -- Khóa ngoại tham chiếu `Chat_Groups`
    `user_id` INT,                                       -- Khóa ngoại tham chiếu `User`
    PRIMARY KEY (`group_id`, `user_id`),                 -- Khóa chính (2 cột)
    FOREIGN KEY (`group_id`) REFERENCES `Chat_Groups`(`group_id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

-- Tạo bảng `Spam_Reports` (Báo cáo spam)
CREATE TABLE `Spam_Reports` (
    `report_id` INT AUTO_INCREMENT PRIMARY KEY,          -- Khóa chính
    `user_id` INT,                                       -- Khóa ngoại tham chiếu `User`
    `report_time` DATETIME DEFAULT CURRENT_TIMESTAMP,    -- Thời gian báo cáo
    `reason` TEXT,                                       -- Lý do báo cáo
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

-- Tạo bảng `User_Registrations` (Đăng ký người dùng mới)
CREATE TABLE `User_Registrations` (
    `registration_id` INT AUTO_INCREMENT PRIMARY KEY,    -- Khóa chính
    `user_id` INT,                                       -- Khóa ngoại tham chiếu `User`
    `registration_time` DATETIME DEFAULT CURRENT_TIMESTAMP, -- Thời gian đăng ký
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

-- Tạo bảng `User_Activity` (Hoạt động người dùng)
CREATE TABLE `User_Activity` (
    `activity_id` INT AUTO_INCREMENT PRIMARY KEY,        -- Khóa chính
    `user_id` INT,                                       -- Khóa ngoại tham chiếu `User`
    `activity_type` ENUM('App Open', 'Chat', 'Group Join') NOT NULL,  -- Loại hoạt động
    `activity_count` INT DEFAULT 0,                      -- Số lần thực hiện hoạt động
    `activity_date` DATETIME DEFAULT CURRENT_TIMESTAMP,  -- Ngày thực hiện hoạt động
    FOREIGN KEY (`user_id`) REFERENCES `User`(`user_id`) ON DELETE CASCADE
);

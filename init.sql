CREATE DATABASE IF NOT EXISTS ecommerce_db;
USE ecommerce_db;

-- Tạo bảng Users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100),
    email VARCHAR(100)
);

-- Tạo bảng Roles
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- Bảng trung gian User_Role
CREATE TABLE users_roles (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Tạo bảng Product
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2)
);

-- Insert dữ liệu mẫu
INSERT INTO roles (name) VALUES ('ADMIN'), ('USER');
INSERT INTO users (username, password, full_name, email) VALUES
('admin', 'admin123', 'Quản trị viên', 'admin@shop.com'),
('khachhang1', '123456', 'Nguyễn Văn A', 'a@gmail.com');

INSERT INTO users_roles VALUES (1, 1), (2, 2);

INSERT INTO products (name, description, price) VALUES
('Laptop Gaming', 'Máy tính chơi game cấu hình cao', 25000000),
('Chuột không dây', 'Chuột văn phòng', 150000);
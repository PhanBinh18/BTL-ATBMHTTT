CREATE DATABASE demo_sqli;
USE demo_sqli;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(255)
);

INSERT INTO users (full_name) VALUES
('Nguyen Van A'),
('Tran Thi B'),
('Le Van C');
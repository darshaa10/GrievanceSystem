CREATE DATABASE grievance_db;
USE grievance_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    role ENUM('citizen', 'admin') DEFAULT 'citizen',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE grievances (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(200),
    description TEXT,
    department VARCHAR(100),
    status ENUM('Pending', 'In Progress', 'Resolved') DEFAULT 'Pending',
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

INSERT INTO departments (name, email) VALUES
('Water Supply', 'water@gov.in'),
('Electricity', 'electricity@gov.in'),
('Roads', 'roads@gov.in'),
('Sanitation', 'sanitation@gov.in'),
('Health', 'health@gov.in'),
('General', 'general@gov.in');

INSERT INTO users (name, email, password, role)
VALUES ('Admin', 'admin@gov.in', 'admin123', 'admin');

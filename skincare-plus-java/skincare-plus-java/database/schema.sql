-- ============================================
-- SkinCare+ Database Schema
-- MySQL 8.0+
-- ============================================

-- Create database
CREATE DATABASE IF NOT EXISTS skincare_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE skincare_db;

-- ============================================
-- Users Table
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    skin_type VARCHAR(20),
    date_of_birth DATETIME,
    role VARCHAR(20) DEFAULT 'USER',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Skin Analysis Table
-- ============================================
CREATE TABLE IF NOT EXISTS skin_analysis (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    image_url VARCHAR(500),
    analysis_result TEXT,
    skin_concerns JSON,
    confidence_score DECIMAL(5,2),
    skin_type_detected VARCHAR(50),
    acne_detected BOOLEAN DEFAULT FALSE,
    dark_spots_detected BOOLEAN DEFAULT FALSE,
    wrinkles_detected BOOLEAN DEFAULT FALSE,
    dryness_detected BOOLEAN DEFAULT FALSE,
    redness_detected BOOLEAN DEFAULT FALSE,
    recommendations TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_created_at (created_at),
    INDEX idx_skin_type (skin_type_detected)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Recommendations Table
-- ============================================
CREATE TABLE IF NOT EXISTS recommendations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    analysis_id BIGINT,
    morning_routine TEXT,
    evening_routine TEXT,
    product_recommendations JSON,
    diet_advice TEXT,
    lifestyle_advice TEXT,
    dos TEXT,
    donts TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (analysis_id) REFERENCES skin_analysis(id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_analysis_id (analysis_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Appointments Table
-- ============================================
CREATE TABLE IF NOT EXISTS appointments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    doctor_name VARCHAR(100) NOT NULL,
    doctor_specialization VARCHAR(100),
    doctor_phone VARCHAR(20),
    doctor_email VARCHAR(100),
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    location VARCHAR(255),
    address TEXT,
    latitude DOUBLE,
    longitude DOUBLE,
    status VARCHAR(20) DEFAULT 'SCHEDULED',
    notes TEXT,
    patient_concerns TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_appointment_date (appointment_date),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Sample Data (Optional - for testing)
-- ============================================

-- Insert sample user (password: Test@123, encoded with BCrypt)
INSERT INTO users (username, email, password, full_name, phone, skin_type, role, is_active)
VALUES 
('demo_user', 'demo@skincare.com', '$2a$10$xXLbZFq0gKw7vqUYhW5gKuQxLzQ5F3wqGPQzYZvnGXpQzMzS5MxAa', 'Demo User', '+91-9876543210', 'COMBINATION', 'USER', TRUE),
('admin', 'admin@skincare.com', '$2a$10$xXLbZFq0gKw7vqUYhW5gKuQxLzQ5F3wqGPQzYZvnGXpQzMzS5MxAa', 'Admin User', '+91-9876543211', 'NORMAL', 'ADMIN', TRUE);

-- Sample skin analysis
INSERT INTO skin_analysis (user_id, image_url, analysis_result, skin_concerns, confidence_score, skin_type_detected, acne_detected, dryness_detected)
VALUES 
(1, '/uploads/sample-image.jpg', 'Mild acne with some dryness detected', '["Acne", "Dryness", "Oily T-zone"]', 85.50, 'COMBINATION', TRUE, TRUE);

-- Sample recommendation
INSERT INTO recommendations (user_id, analysis_id, morning_routine, evening_routine, diet_advice)
VALUES 
(1, 1, 
 'Step 1: Gentle cleanser\nStep 2: Toner\nStep 3: Vitamin C serum\nStep 4: Moisturizer\nStep 5: Sunscreen SPF 50',
 'Step 1: Oil-based cleanser\nStep 2: Water-based cleanser\nStep 3: Exfoliant (2-3 times/week)\nStep 4: Serum\nStep 5: Night cream',
 'Drink 8 glasses of water daily. Include fruits and vegetables rich in Vitamin A, C, and E. Avoid excessive sugar and dairy products.');

-- Sample appointment
INSERT INTO appointments (user_id, doctor_name, doctor_specialization, appointment_date, appointment_time, location, status)
VALUES 
(1, 'Dr. Sarah Johnson', 'Dermatologist', '2025-11-20', '10:00:00', 'City Skin Clinic, Chennai', 'SCHEDULED');

-- ============================================
-- Views for Analytics (Optional)
-- ============================================

-- View: User statistics
CREATE OR REPLACE VIEW user_statistics AS
SELECT 
    u.id,
    u.username,
    u.email,
    u.skin_type,
    COUNT(DISTINCT sa.id) AS total_analyses,
    COUNT(DISTINCT r.id) AS total_recommendations,
    COUNT(DISTINCT a.id) AS total_appointments,
    MAX(sa.created_at) AS last_analysis_date
FROM users u
LEFT JOIN skin_analysis sa ON u.id = sa.user_id
LEFT JOIN recommendations r ON u.id = r.user_id
LEFT JOIN appointments a ON u.id = a.user_id
GROUP BY u.id, u.username, u.email, u.skin_type;

-- View: Recent analyses
CREATE OR REPLACE VIEW recent_analyses AS
SELECT 
    sa.id,
    u.username,
    u.email,
    sa.skin_type_detected,
    sa.confidence_score,
    sa.acne_detected,
    sa.dark_spots_detected,
    sa.wrinkles_detected,
    sa.created_at
FROM skin_analysis sa
JOIN users u ON sa.user_id = u.id
ORDER BY sa.created_at DESC
LIMIT 50;

-- ============================================
-- Stored Procedures (Optional but Advanced)
-- ============================================

DELIMITER //

-- Procedure: Get user dashboard data
CREATE PROCEDURE GetUserDashboard(IN userId BIGINT)
BEGIN
    -- User info
    SELECT * FROM users WHERE id = userId;
    
    -- Recent analyses
    SELECT * FROM skin_analysis WHERE user_id = userId ORDER BY created_at DESC LIMIT 5;
    
    -- Upcoming appointments
    SELECT * FROM appointments 
    WHERE user_id = userId AND appointment_date >= CURDATE() 
    ORDER BY appointment_date ASC LIMIT 5;
    
    -- Latest recommendations
    SELECT * FROM recommendations WHERE user_id = userId ORDER BY created_at DESC LIMIT 3;
END //

-- Procedure: Get analytics
CREATE PROCEDURE GetAnalytics()
BEGIN
    -- Total users
    SELECT COUNT(*) AS total_users FROM users WHERE is_active = TRUE;
    
    -- Total analyses
    SELECT COUNT(*) AS total_analyses FROM skin_analysis;
    
    -- Analyses by skin type
    SELECT skin_type_detected, COUNT(*) AS count 
    FROM skin_analysis 
    GROUP BY skin_type_detected;
    
    -- Most common concerns
    SELECT 
        SUM(acne_detected) AS acne_count,
        SUM(dark_spots_detected) AS dark_spots_count,
        SUM(wrinkles_detected) AS wrinkles_count,
        SUM(dryness_detected) AS dryness_count,
        SUM(redness_detected) AS redness_count
    FROM skin_analysis;
END //

DELIMITER ;

-- ============================================
-- Triggers (Optional but Advanced)
-- ============================================

DELIMITER //

-- Trigger: Update user's last analysis date
CREATE TRIGGER after_analysis_insert
AFTER INSERT ON skin_analysis
FOR EACH ROW
BEGIN
    UPDATE users 
    SET updated_at = NOW() 
    WHERE id = NEW.user_id;
END //

-- Trigger: Log appointment changes
CREATE TRIGGER before_appointment_update
BEFORE UPDATE ON appointments
FOR EACH ROW
BEGIN
    SET NEW.updated_at = NOW();
END //

DELIMITER ;

-- ============================================
-- Indexes for Performance Optimization
-- ============================================

-- Additional composite indexes
CREATE INDEX idx_user_analysis_date ON skin_analysis(user_id, created_at DESC);
CREATE INDEX idx_user_appointment_date ON appointments(user_id, appointment_date);
CREATE INDEX idx_analysis_concerns ON skin_analysis(acne_detected, dark_spots_detected, wrinkles_detected);

-- ============================================
-- Verification Queries
-- ============================================

-- Check all tables
SHOW TABLES;

-- Check table structures
DESCRIBE users;
DESCRIBE skin_analysis;
DESCRIBE recommendations;
DESCRIBE appointments;

-- Check sample data
SELECT * FROM users;
SELECT * FROM skin_analysis;
SELECT * FROM recommendations;
SELECT * FROM appointments;

-- Check views
SELECT * FROM user_statistics;
SELECT * FROM recent_analyses LIMIT 10;

-- ============================================
-- Cleanup (if needed to reset)
-- ============================================

-- DROP DATABASE IF EXISTS skincare_db;

-- ============================================
-- End of Schema
-- ============================================

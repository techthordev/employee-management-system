-- ==========================================
-- AUTH SCHEMA - Spring Security Standard
-- ==========================================
\connect employee_management

CREATE SCHEMA IF NOT EXISTS auth;

-- Users table
CREATE TABLE auth.users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled  BOOLEAN NOT NULL DEFAULT TRUE
);

-- Authorities table
CREATE TABLE auth.authorities (
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users 
        FOREIGN KEY (username) 
        REFERENCES auth.users(username) 
        ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_auth_username 
    ON auth.authorities (username, authority);

-- Sample HR Users
-- Password for all: "password"
-- BCrypt: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy

-- System Admin
INSERT INTO auth.users (username, password, enabled) VALUES 
    ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', TRUE);

INSERT INTO auth.authorities (username, authority) VALUES 
    ('admin', 'ROLE_ADMIN');

-- HR Admin (Chief of HR)
INSERT INTO auth.users (username, password, enabled) VALUES 
    ('hr.admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', TRUE);

INSERT INTO auth.authorities (username, authority) VALUES 
    ('hr.admin', 'ROLE_HR_ADMIN');

-- HR Manager (Senior HR)
INSERT INTO auth.users (username, password, enabled) VALUES 
    ('hr.manager', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', TRUE);

INSERT INTO auth.authorities (username, authority) VALUES 
    ('hr.manager', 'ROLE_HR_MANAGER');

-- HR Employee (Junior HR - Read Only)
INSERT INTO auth.users (username, password, enabled) VALUES 
    ('hr.employee', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', TRUE);

INSERT INTO auth.authorities (username, authority) VALUES 
    ('hr.employee', 'ROLE_HR_EMPLOYEE');

-- Grant permissions
GRANT USAGE ON SCHEMA auth TO springconnector;
GRANT SELECT ON auth.users TO springconnector;
GRANT SELECT ON auth.authorities TO springconnector;

ALTER SCHEMA auth OWNER TO postgres;
ALTER TABLE auth.users OWNER TO postgres;
ALTER TABLE auth.authorities OWNER TO postgres;
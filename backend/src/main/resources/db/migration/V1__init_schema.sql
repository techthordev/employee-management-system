-- Initial schema for employee management system
-- Flyway migration V1

CREATE TABLE employee (
                          id SERIAL PRIMARY KEY,
                          first_name VARCHAR(45) NOT NULL,
                          last_name  VARCHAR(45) NOT NULL,
                          email      VARCHAR(45) NOT NULL UNIQUE
);

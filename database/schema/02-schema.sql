\connect employee_management

GRANT ALL ON SCHEMA public TO springconnector;
ALTER SCHEMA public OWNER TO springconnector;

-- Departments table
CREATE TABLE IF NOT EXISTS departments (
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    code VARCHAR(10) NOT NULL UNIQUE
);

-- Employee table (with department_id)
CREATE TABLE IF NOT EXISTS employee (
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(45) NOT NULL,
    last_name     VARCHAR(45) NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    department_id INT REFERENCES departments(id),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_employee_email ON employee(email);
CREATE INDEX IF NOT EXISTS idx_employee_last_name ON employee(last_name);
CREATE INDEX IF NOT EXISTS idx_employee_department ON employee(department_id);

-- Set ownership
ALTER TABLE departments OWNER TO springconnector;
ALTER TABLE employee OWNER TO springconnector;
ALTER SEQUENCE departments_id_seq OWNER TO springconnector;
ALTER SEQUENCE employee_id_seq OWNER TO springconnector;

-- Grant privileges
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO springconnector;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO springconnector;

ALTER DEFAULT PRIVILEGES IN SCHEMA public 
    GRANT ALL PRIVILEGES ON TABLES TO springconnector;
ALTER DEFAULT PRIVILEGES IN SCHEMA public 
    GRANT ALL PRIVILEGES ON SEQUENCES TO springconnector;
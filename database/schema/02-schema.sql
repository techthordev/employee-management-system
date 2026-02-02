-- Connect to database
\connect employee_management

-- Grant schema privileges
GRANT ALL ON SCHEMA public TO springconnector;
ALTER SCHEMA public OWNER TO springconnector;

-- Create employee table
CREATE TABLE IF NOT EXISTS employee (
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(45) NOT NULL,
    last_name  VARCHAR(45) NOT NULL,
    email      VARCHAR(45) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_employee_email ON employee(email);
CREATE INDEX IF NOT EXISTS idx_employee_last_name ON employee(last_name);

-- Set ownership
ALTER TABLE employee OWNER TO springconnector;
ALTER SEQUENCE employee_id_seq OWNER TO springconnector;

-- Grant privileges
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO springconnector;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO springconnector;

-- Future objects
ALTER DEFAULT PRIVILEGES IN SCHEMA public 
    GRANT ALL PRIVILEGES ON TABLES TO springconnector;
ALTER DEFAULT PRIVILEGES IN SCHEMA public 
    GRANT ALL PRIVILEGES ON SEQUENCES TO springconnector;
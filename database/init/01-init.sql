-- Create user and database
DROP USER IF EXISTS springconnector;
CREATE USER springconnector WITH PASSWORD 'springconnector';

-- Grant database privileges
ALTER DATABASE employee_management OWNER TO springconnector;
GRANT ALL PRIVILEGES ON DATABASE employee_management TO springconnector;
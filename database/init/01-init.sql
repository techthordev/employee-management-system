DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'springconnector') THEN
    CREATE USER springconnector WITH PASSWORD 'springconnector';
  END IF;
END $$;

ALTER DATABASE employee_management OWNER TO springconnector;
GRANT ALL PRIVILEGES ON DATABASE employee_management TO springconnector;

\connect employee_management

-- Add user_id column to employee table
ALTER TABLE public.employee 
ADD COLUMN user_id VARCHAR(50);

-- Add foreign key constraint
ALTER TABLE public.employee 
ADD CONSTRAINT fk_employee_user 
    FOREIGN KEY (user_id) 
    REFERENCES auth.users(username) 
    ON DELETE SET NULL;

-- Create index
CREATE INDEX idx_employee_user_id ON public.employee(user_id);

GRANT SELECT, INSERT, UPDATE, DELETE ON public.employee TO springconnector;
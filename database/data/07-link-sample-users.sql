\connect employee_management

-- Link HR system users to employee records
-- This allows HR staff to also be employees in the system

UPDATE public.employee 
SET user_id = 'hr.admin' 
WHERE email = 'lucas@techthordev.com.br';

UPDATE public.employee 
SET user_id = 'hr.manager' 
WHERE email = 'bruno@techthordev.com.br';

UPDATE public.employee 
SET user_id = 'hr.employee' 
WHERE email = 'juan@techthordev.com.br';
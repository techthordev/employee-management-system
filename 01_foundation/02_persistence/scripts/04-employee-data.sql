-- Departments
INSERT INTO public.department (name) VALUES 
('IT'), ('HR'), ('Finance'), ('Marketing'), ('Sales'), ('Operations')
ON CONFLICT (name) DO NOTHING;

-- Projects
INSERT INTO public.project (name, description) VALUES 
('EMS Upgrade', 'Employee Management System modernization'),
('Cloud Migration', 'Moving infrastructure to cloud'),
('Mobile App', 'Employee self-service mobile application')
ON CONFLICT (name) DO NOTHING;

-- Employees
INSERT INTO public.employee (first_name, last_name, email, department_id) VALUES
('Lucas','Martinez','lucas@techthordev.com.br', (SELECT id FROM public.department WHERE name='IT')),
('Sofia','Ramirez','sofia@techthordev.com.br', (SELECT id FROM public.department WHERE name='HR')),
('Mateo','Gonzalez','mateo@techthordev.com.br', (SELECT id FROM public.department WHERE name='IT')),
('Valentina','Herrera','valentina@techthordev.com.br', (SELECT id FROM public.department WHERE name='Finance')),
('Diego','Morales','diego@techthordev.com.br', (SELECT id FROM public.department WHERE name='Operations'))
ON CONFLICT (email) DO NOTHING;

-- Join-Table Assignments
INSERT INTO public.employee_projects (employee_id, project_id)
SELECT e.id, p.id FROM public.employee e, public.project p
WHERE (e.email = 'lucas@techthordev.com.br' AND p.name = 'EMS Upgrade')
ON CONFLICT DO NOTHING;

-- Profiles
INSERT INTO public.employee_profile (employee_id, bio, phone, address)
SELECT e.id, 'Senior developer', '+55-84-99999-0001', 'Natal, RN'
FROM public.employee e WHERE e.email = 'lucas@techthordev.com.br'
ON CONFLICT (employee_id) DO NOTHING;
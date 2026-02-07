\connect employee_management

-- Insert standard business departments
INSERT INTO departments (name, code) VALUES
    ('Human Resources', 'HR'),
    ('Information Technology', 'IT'),
    ('Finance', 'FIN'),
    ('Sales', 'SAL'),
    ('Marketing', 'MKT'),
    ('Operations', 'OPS'),
    ('Customer Support', 'SUP'),
    ('Research & Development', 'RND');

SELECT setval('departments_id_seq', (SELECT MAX(id) FROM departments));
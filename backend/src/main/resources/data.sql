INSERT INTO public.roles (id, name)
VALUES (1, 'OWNER'),
       (2, 'CARETAKER'),
       (3, 'ADMIN')
    ON CONFLICT (name) DO NOTHING;
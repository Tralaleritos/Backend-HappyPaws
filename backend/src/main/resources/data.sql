INSERT INTO public.roles (id, name)
VALUES (1, 'OWNER'),
       (2, 'CAREGIVER'),
       (3, 'ADMIN')
    ON CONFLICT (name) DO NOTHING;
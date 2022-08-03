CREATE EXTENSION pgcrypto;

INSERT INTO access_request (id, name, company, requested_at, granted_until) VALUES (gen_random_uuid(), 'Jerson Villano', 'PGD', '2022-08-02T19:45:40', '2022-08-03T19:45:40');
INSERT INTO access_request (id, name, company, requested_at, granted_until) VALUES (gen_random_uuid(), 'Pedro Perez', 'Google', '2022-08-02T19:45:40', '2022-08-03T19:45:40');
INSERT INTO access_request (id, name, company, requested_at, granted_until) VALUES (gen_random_uuid(), 'Paquito Valencia', 'Facebook', '2022-08-02T19:45:40', '2022-08-03T19:45:40');
INSERT INTO access_request (id, name, company, requested_at, granted_until) VALUES (gen_random_uuid(), 'Jonas Wing', 'Netflix', '2022-08-02T19:45:40', '2022-08-03T19:45:40');
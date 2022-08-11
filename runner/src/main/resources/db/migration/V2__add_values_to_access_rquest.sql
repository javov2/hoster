CREATE EXTENSION pgcrypto;

INSERT INTO access_request (id, name, company, email, requested_at, access_time)
VALUES (gen_random_uuid(), 'Jerson Villano', 'PGD', 'jv@pgd.com', '2022-08-02T19:45:40', 10);

INSERT INTO access_request (id, name, company, email, requested_at, access_time)
VALUES (gen_random_uuid(), 'Pedro Perez', 'Google', 'pp@google.com', '2022-08-02T19:45:40', 10);

INSERT INTO access_request (id, name, company, email, requested_at, access_time)
VALUES (gen_random_uuid(), 'Paquito Valencia', 'Facebook', 'pv@facebook.com', '2022-08-02T19:45:40', 10);

INSERT INTO access_request (id, name, company, email, requested_at, access_time)
VALUES (gen_random_uuid(), 'Jonas Wing', 'Netflix', 'jonas@netflix.com', '2022-08-02T19:45:40', 10);
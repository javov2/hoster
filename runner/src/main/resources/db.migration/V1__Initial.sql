CREATE TABLE access_request (
   id uuid NOT NULL,
   name VARCHAR NOT NULL,
   company VARCHAR NOT NULL,
   requested_at TIMESTAMP  NOT NULL,
   granted_until TIMESTAMP  NOT NULL,
   PRIMARY KEY(id)
);
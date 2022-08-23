CREATE TABLE access_request (
   id uuid NOT NULL,
   name VARCHAR NOT NULL,
   company VARCHAR NOT NULL,
   email VARCHAR NOT NULL,
   requested_at TIMESTAMP  NOT NULL,
   access_time bigint  NOT NULL,
   is_reviewed boolean DEFAULT FALSE,
   reviewed_at TIMESTAMP,
   PRIMARY KEY(id)
);

CREATE TABLE access (
   id uuid NOT NULL,
   is_granted boolean NOT NULL,
   reviewed_at TIMESTAMP  NOT NULL,
   PRIMARY KEY(id)
);
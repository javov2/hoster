ALTER TABLE access_request
    ADD is_reviewed BOOLEAN DEFAULT false,
    ADD reviewed_at TIMESTAMP DEFAULT requested_at;
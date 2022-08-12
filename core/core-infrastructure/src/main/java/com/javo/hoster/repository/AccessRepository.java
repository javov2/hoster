package com.javo.hoster.repository;

import com.javo.hoster.model.Access;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccessRepository {
    Mono<Access> save(Access accessRequest);
    Mono<Access> findById(UUID id);
}

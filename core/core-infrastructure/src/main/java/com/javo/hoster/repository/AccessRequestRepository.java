package com.javo.hoster.repository;

import com.javo.hoster.model.AccessRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccessRequestRepository {

    Mono<AccessRequest> save(AccessRequest accessRequest);
    Mono<AccessRequest> findById(UUID id);
    Flux<AccessRequest> findAll();
    Flux<AccessRequest> findAllNotReviewed();

}

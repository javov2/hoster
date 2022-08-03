package com.javo.hoster.repository;

import com.javo.hoster.model.AccessRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccessRequestRepository {

    Mono<AccessRequest> save(AccessRequest accessRequest);
    Flux<AccessRequest> findAll();

}

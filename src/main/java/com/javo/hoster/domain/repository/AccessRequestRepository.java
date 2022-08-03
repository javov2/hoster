package com.javo.hoster.domain.repository;

import com.javo.hoster.domain.model.AccessRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccessRequestRepository {

    Mono<AccessRequest> save(AccessRequest accessRequest);
    Flux<AccessRequest> findAll();

}

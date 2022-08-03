package com.javo.hoster.usecase.crud;

import com.javo.hoster.model.AccessRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindAccessRequestByIdUseCase {
    Mono<AccessRequest> process();
}

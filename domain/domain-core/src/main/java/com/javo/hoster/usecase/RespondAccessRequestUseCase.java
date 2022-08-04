package com.javo.hoster.usecase;

import com.javo.hoster.model.Access;
import com.javo.hoster.model.AccessRequest;
import reactor.core.publisher.Mono;

public interface RespondAccessRequestUseCase {

    Mono<Access> process(AccessRequest accessRequest);

}

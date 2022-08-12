package com.javo.hoster.usecase;

import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.model.AccessRequestConfirmation;
import reactor.core.publisher.Mono;

public interface ClaimForAccessUseCase {

    Mono<AccessRequestConfirmation> process(AccessRequest accessRequest);

}

package com.javo.hoster.usecase;

import com.javo.hoster.model.AccessRequest;
import reactor.core.publisher.Flux;

public interface FindAllAccessRequestToReviewUseCase {
    Flux<AccessRequest> process();
}

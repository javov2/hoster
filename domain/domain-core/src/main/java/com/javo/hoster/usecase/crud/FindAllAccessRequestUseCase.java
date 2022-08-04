package com.javo.hoster.usecase.crud;

import com.javo.hoster.model.AccessRequest;
import reactor.core.publisher.Flux;

public interface FindAllAccessRequestUseCase {
    Flux<AccessRequest> process();
}

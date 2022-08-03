package com.javo.hoster.usecase.crud;

import com.javo.hoster.model.AccessRequest;
import reactor.core.publisher.Flux;

public class FindAllAccessRequestUseCaseImpl implements FindAllAccessRequestUseCase{

    @Override
    public Flux<AccessRequest> process() {
        return null;
    }
}

package com.javo.hoster.usecase.crud;

import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.repository.AccessRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class FindAllAccessRequestUseCaseImpl implements FindAllAccessRequestUseCase{

    @Autowired
    private AccessRequestRepository repository;

    @Override
    public Flux<AccessRequest> process() {
        return repository.findAll();
    }
}

package com.javo.hoster.usecase.crud;

import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.repository.AccessRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class FindAccessRequestByIdUseCaseImpl implements FindAccessRequestByIdUseCase{

    @Autowired
    private AccessRequestRepository repository;

    @Override
    public Mono<AccessRequest> process(UUID id) {
        return repository.findById(id);
    }
}

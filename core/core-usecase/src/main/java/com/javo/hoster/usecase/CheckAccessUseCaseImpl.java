package com.javo.hoster.usecase;

import com.javo.hoster.model.Access;
import com.javo.hoster.repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class CheckAccessUseCaseImpl implements CheckAccessUseCase {

    @Autowired
    private AccessRepository accessRepository;

    @Override
    public Mono<Access> process(UUID accessRequestId) {
        return accessRepository.findById(accessRequestId);
    }

}

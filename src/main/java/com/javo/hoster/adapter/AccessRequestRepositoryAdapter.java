package com.javo.hoster.adapter;

import com.javo.hoster.entity.AccessRequestEntity;
import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.repository.AccessRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccessRequestRepositoryAdapter {

    @Autowired
    private AccessRequestRepository repository;

    public Mono<AccessRequest> save(AccessRequest accessRequest){
        return Mono.just(repository.save(toEntity(accessRequest)))
                .map(this::toModel);
    }

    public Flux<AccessRequest> findAll(){
        return Flux.fromIterable(repository.findAll())
                .map(this::toModel);
    }

    private AccessRequest toModel(AccessRequestEntity entity){
        return AccessRequest.builder()
                .id(entity.getId())
                .build();
    }

    private AccessRequestEntity toEntity(AccessRequest entity){
        return new AccessRequestEntity();
    }

}

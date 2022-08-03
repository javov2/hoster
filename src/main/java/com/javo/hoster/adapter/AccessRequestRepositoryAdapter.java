package com.javo.hoster.adapter;

import com.javo.hoster.entity.AccessRequestEntity;
import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.repository.AccessRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

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
                .requestedAt(entity.getRequestedAt().truncatedTo(ChronoUnit.SECONDS))
                .accessGrantedUntil(entity.getAccessGrantedUntil().truncatedTo(ChronoUnit.SECONDS))
                .company(entity.getCompany())
                .name(entity.getName())
                .build();
    }

    private AccessRequestEntity toEntity(AccessRequest model){
        var entity = new AccessRequestEntity();
        entity.setId(UUID.randomUUID());
        entity.setRequestedAt(model.getRequestedAt().truncatedTo(ChronoUnit.SECONDS));
        entity.setCompany(model.getCompany());
        entity.setName(model.getName());
        entity.setAccessGrantedUntil(model.getAccessGrantedUntil().truncatedTo(ChronoUnit.SECONDS));
        return entity;
    }

}

package com.javo.hoster.repository.adapter;


import com.javo.hoster.repository.AccessRequestJPARepository;
import com.javo.hoster.repository.AccessRequestRepository;
import com.javo.hoster.repository.entity.AccessRequestEntity;
import com.javo.hoster.model.AccessRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccessRequestRepositoryAdapter implements AccessRequestRepository {

    @Autowired
    private AccessRequestJPARepository repository;

    public Mono<AccessRequest> save(AccessRequest accessRequest){
        return Mono.just(repository.save(toEntity(accessRequest)))
                .map(this::toModel);
    }

    @Override
    public Mono<AccessRequest> findById(UUID id) {
        return Mono.just(repository.findById(id))
                .map(this::toModel);
    }

    public Flux<AccessRequest> findAll(){
        return Flux.fromIterable(repository.findAll())
                .map(this::toModel);
    }

    private AccessRequest toModel(Optional<AccessRequestEntity> entityOptional){
        var entity = entityOptional.orElseThrow();
        return toModel(entity);
    }

    private AccessRequest toModel(AccessRequestEntity entity){
        return AccessRequest.builder()
                .id(entity.getId())
                .requestedAt(entity.getRequestedAt().truncatedTo(ChronoUnit.SECONDS))
                .accessTime(entity.getAccessTime())
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
        entity.setAccessTime(model.getAccessTime());
        return entity;
    }

}

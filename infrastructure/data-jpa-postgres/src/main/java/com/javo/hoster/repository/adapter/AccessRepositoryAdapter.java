package com.javo.hoster.repository.adapter;


import com.javo.hoster.model.Access;
import com.javo.hoster.repository.AccessJPARepository;
import com.javo.hoster.repository.AccessRepository;
import com.javo.hoster.repository.entity.AccessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AccessRepositoryAdapter implements AccessRepository {

    @Autowired
    AccessJPARepository repository;

    @Override
    public Mono<Access> save(Access accessRequest) {
        return Mono.just(repository.save(toEntity(accessRequest)))
                .map(this::toModel);
    }

    @Override
    public Mono<Access> findById(UUID id) {
        return Mono.just(repository.findById(id))
                .map(this::toModel);
    }

    private Access toModel(Optional<AccessEntity> entityOptional){
        var entity = entityOptional.orElseThrow();
        return toModel(entity);
    }

    private Access toModel(AccessEntity entity){
        return Access.builder()
                .id(entity.getId())
                .reviewedAt(entity.getReviewedAt())
                .isGranted(entity.getIsGranted())
                .build();
    }

    private AccessEntity toEntity(Access model){
        var entity = new AccessEntity();
        entity.setId(model.getId());
        entity.setIsGranted(model.isGranted());
        entity.setReviewedAt(model.getReviewedAt());
        return entity;
    }

}

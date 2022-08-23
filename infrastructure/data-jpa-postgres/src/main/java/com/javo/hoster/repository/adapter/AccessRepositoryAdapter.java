package com.javo.hoster.repository.adapter;


import com.javo.hoster.exception.AccessException;
import com.javo.hoster.model.Access;
import com.javo.hoster.repository.AccessJPARepository;
import com.javo.hoster.repository.AccessRepository;
import com.javo.hoster.repository.entity.AccessEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Slf4j
public class AccessRepositoryAdapter implements AccessRepository {

    public static final String ACCESS_WITH_ID_NOT_FOUND = "Access with id %s not found";

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
                .map(accessEntity -> accessEntity.orElseThrow(() -> new AccessException(String.format(ACCESS_WITH_ID_NOT_FOUND, id))))
                .map(this::toModel);
    }

    private Access toModel(AccessEntity entity) {
        return Access.builder()
                .id(entity.getId())
                .reviewedAt(entity.getReviewedAt())
                .isGranted(entity.getIsGranted())
                .build();
    }

    private AccessEntity toEntity(Access model) {
        var entity = new AccessEntity();
        entity.setId(model.getId());
        entity.setIsGranted(model.isGranted());
        entity.setReviewedAt(model.getReviewedAt());
        return entity;
    }

}

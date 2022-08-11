package com.javo.hoster.repository.adapter;


import com.javo.hoster.model.Access;
import com.javo.hoster.repository.AccessRepository;
import com.javo.hoster.repository.AccessRequestJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class AccessRepositoryAdapter implements AccessRepository {

    @Autowired
    AccessRequestJPARepository repository;

    @Override
    public Mono<Access> save(Access accessRequest) {
        return null;
    }

    @Override
    public Mono<Access> findById(UUID id) {
        return null;
    }
}

package com.javo.hoster.repository;

import com.javo.hoster.entity.AccessRequestEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRequestRepository extends ReactiveCrudRepository<AccessRequestEntity,Integer> {

}

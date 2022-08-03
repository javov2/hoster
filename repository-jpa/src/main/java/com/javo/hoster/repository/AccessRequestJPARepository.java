package com.javo.hoster.repository;

import com.javo.hoster.domain.entity.AccessRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRequestJPARepository extends JpaRepository<AccessRequestEntity,Integer> {

}

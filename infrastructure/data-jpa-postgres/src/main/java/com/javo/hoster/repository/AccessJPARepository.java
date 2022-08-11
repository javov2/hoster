package com.javo.hoster.repository;

import com.javo.hoster.repository.entity.AccessEntity;
import com.javo.hoster.repository.entity.AccessRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccessJPARepository extends JpaRepository<AccessEntity, UUID> {

}

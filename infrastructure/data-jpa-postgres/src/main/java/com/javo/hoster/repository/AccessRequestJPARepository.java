package com.javo.hoster.repository;

import com.javo.hoster.repository.entity.AccessRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccessRequestJPARepository extends JpaRepository<AccessRequestEntity, UUID> {

    @Query(value = "SELECT * FROM access_request WHERE NOT is_reviewed ORDER BY requested_at DESC",
            nativeQuery = true)
    List<AccessRequestEntity> findAllNotReviewed();

}

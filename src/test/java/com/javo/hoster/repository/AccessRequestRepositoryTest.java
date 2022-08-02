package com.javo.hoster.repository;

import com.javo.hoster.entity.AccessRequestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccessRequestRepositoryTest {

    @Autowired
    private AccessRequestRepository accessRequestRepository;

    @Test
    void saveANewAccessRequest(){
        var toSave = new AccessRequestEntity();
        accessRequestRepository.save(toSave)
                .as(StepVerifier::create)
                .expectNextCount(1);
    }


}
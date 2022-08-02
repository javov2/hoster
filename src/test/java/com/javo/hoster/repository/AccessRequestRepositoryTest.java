package com.javo.hoster.repository;

import com.javo.hoster.entity.AccessRequestEntity;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;


/*@Testcontainers
@ActiveProfiles("test-containers")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(classes = HosterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)*/

@Testcontainers
@ActiveProfiles("test-containers")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccessRequestRepositoryTest {

    @Autowired
    private AccessRequestRepository accessRequestRepository;

    @Test
    @Order(1)
    void saveANewAccessRequest(){

        var toSave = new AccessRequestEntity();
        Mono.justOrEmpty(accessRequestRepository.save(toSave))
                .as(StepVerifier::create)
                .expectNextCount(1);
    }

    @Test
    @Order(2)
    void findAllAccessRequest(){
        var toSave = new AccessRequestEntity();
        Flux.just(accessRequestRepository.findAll())
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }


}
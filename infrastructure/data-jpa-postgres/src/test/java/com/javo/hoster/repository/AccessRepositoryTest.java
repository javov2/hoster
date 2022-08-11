package com.javo.hoster.repository;


import com.javo.hoster.RepositoryJPARunner;
import com.javo.hoster.repository.entity.AccessEntity;
import com.javo.hoster.repository.entity.AccessRequestEntity;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Testcontainers
@ActiveProfiles("test-containers")
@SpringBootTest(classes = RepositoryJPARunner.class)
class AccessRepositoryTest {

    public static final String UUID_EXAMPLE = "e58b6372-9cdf-4bbd-9412-8053a49146bd";
    private final String REQUESTED_AT = "2022-08-02T19:45:40.026626500";

    @Autowired
    private AccessJPARepository accessJPARepository;

    @Test
    @Order(1)
    void saveANewAccessRequest(){

        UUID id = UUID.fromString(UUID_EXAMPLE);

        var entityToSave = new AccessEntity();
        entityToSave.setId(id);
        entityToSave.setIsGranted(Boolean.TRUE);
        entityToSave.setReviewedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));

        Mono.justOrEmpty(accessJPARepository.save(entityToSave))
                .as(StepVerifier::create)
                .expectNext(entityToSave)
                .verifyComplete();
    }

    @Test
    @Order(2)
    void findAllAccessRequest(){

        UUID id = UUID.fromString(UUID_EXAMPLE);

        var entityToSave = new AccessEntity();
        entityToSave.setId(id);
        entityToSave.setIsGranted(Boolean.TRUE);
        entityToSave.setReviewedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));

        Flux.just(accessJPARepository.findAll())
                .as(StepVerifier::create)
                .expectNext(List.of(entityToSave))
                .verifyComplete();
    }

    @Test
    @Order(2)
    void findAccessRequestById(){

        UUID id = UUID.fromString(UUID_EXAMPLE);

        var entityToSave = new AccessEntity();
        entityToSave.setId(id);
        entityToSave.setIsGranted(Boolean.TRUE);
        entityToSave.setReviewedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));

        Mono.just(accessJPARepository.findById(UUID.fromString(UUID_EXAMPLE)))
                .as(StepVerifier::create)
                .expectNext(Optional.of(entityToSave))
                .verifyComplete();
    }


}
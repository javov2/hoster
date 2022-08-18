package com.javo.hoster.repository;


import com.javo.hoster.RepositoryJPARunner;
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
class AccessRequestRepositoryTest {

    @Autowired
    private AccessRequestJPARepository accessRequestJPARepository;

    private final String REQUESTED_AT = "2022-08-02T19:45:40.026626500";
    private final Long ACCESS_TIME = 10L;
    private final String EMAIL = "test@test.com";

    @Test
    @Order(1)
    void saveANewAccessRequest(){

        var toSave = new AccessRequestEntity();

        String uuid = "e58b6372-9cdf-4bbd-9412-8053a49146bd";

        toSave.setId(UUID.fromString(uuid));
        toSave.setName("");
        toSave.setCompany("");
        toSave.setEmail(EMAIL);
        toSave.setRequestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));
        toSave.setAccessTime(ACCESS_TIME);

        Mono.justOrEmpty(accessRequestJPARepository.save(toSave))
                .as(StepVerifier::create)
                .expectNext(toSave)
                .verifyComplete();
    }

    @Test
    @Order(2)
    void findAllAccessRequest(){

        var toSave = new AccessRequestEntity();
        toSave.setId(UUID.fromString("e58b6372-9cdf-4bbd-9412-8053a49146bd"));
        toSave.setName("");
        toSave.setCompany("");
        toSave.setEmail(EMAIL);
        toSave.setRequestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));
        toSave.setAccessTime(ACCESS_TIME);

        Flux.just(accessRequestJPARepository.findAll())
                .as(StepVerifier::create)
                .expectNext(List.of(toSave))
                .verifyComplete();
    }

    @Test
    @Order(2)
    void findAllNotReviewedAccessRequest(){

        var toSave = new AccessRequestEntity();
        toSave.setId(UUID.fromString("e58b6372-9cdf-4bbd-9412-8053a49146bd"));
        toSave.setName("");
        toSave.setCompany("");
        toSave.setEmail(EMAIL);
        toSave.setRequestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));
        toSave.setAccessTime(ACCESS_TIME);

        Flux.just(accessRequestJPARepository.findAllNotReviewed())
                .as(StepVerifier::create)
                .expectNext(List.of(toSave))
                .verifyComplete();
    }

    @Test
    @Order(2)
    void findAccessRequestById(){

        var toSave = new AccessRequestEntity();
        toSave.setId(UUID.fromString("e58b6372-9cdf-4bbd-9412-8053a49146bd"));
        toSave.setName("");
        toSave.setCompany("");
        toSave.setEmail(EMAIL);
        toSave.setRequestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));
        toSave.setAccessTime(ACCESS_TIME);

        Mono.just(accessRequestJPARepository.findById(UUID.fromString("e58b6372-9cdf-4bbd-9412-8053a49146bd")))
                .as(StepVerifier::create)
                .expectNext(Optional.of(toSave))
                .verifyComplete();
    }


}
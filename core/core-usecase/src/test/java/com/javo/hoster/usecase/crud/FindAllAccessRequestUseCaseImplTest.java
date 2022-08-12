package com.javo.hoster.usecase.crud;

import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.repository.AccessRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllAccessRequestUseCaseImplTest {

    @Mock
    private AccessRequestRepository repository;

    @InjectMocks
    private FindAllAccessRequestUseCaseImpl underTest;

    @Test
    void processExpectTwoAccessRequest() {

        when(repository.findAll())
                .thenReturn(Flux.just(
                        AccessRequest.builder().build(),
                        AccessRequest.builder().build())
                );

        underTest.process()
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();

    }

    @Test
    void processWhenAccessRequestRepositoryReturnsAnEmptyFluxExpectZeroItems() {

        when(repository.findAll())
                .thenReturn(Flux.empty());

        underTest.process()
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();

    }
}
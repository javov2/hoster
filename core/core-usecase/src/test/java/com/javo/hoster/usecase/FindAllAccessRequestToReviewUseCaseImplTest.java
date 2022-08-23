package com.javo.hoster.usecase;

import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.repository.AccessRequestRepository;
import com.javo.hoster.usecase.crud.FindAllAccessRequestUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllAccessRequestToReviewUseCaseImplTest {

    @Mock
    private AccessRequestRepository repository;

    @InjectMocks
    private FindAllAccessRequestToReviewUseCaseImpl underTest;

    @Test
    void processExpectTwoAccessRequest() {

        when(repository.findAllNotReviewed())
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

        when(repository.findAllNotReviewed())
                .thenReturn(Flux.empty());

        underTest.process()
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();

    }
}
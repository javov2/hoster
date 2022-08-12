package com.javo.hoster.usecase.crud;

import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.repository.AccessRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAccessRequestByIdUseCaseImplTest {

    @Mock
    private AccessRequestRepository repository;

    @InjectMocks
    private FindAccessRequestByIdUseCaseImpl underTest;

    public static final String UUID_EXAMPLE = "123e4567-e89b-12d3-a456-556642440000";

    @Test
    void processWhenAnAccessRequestIsFoundExpectAccessRequest() {

        UUID id = UUID.fromString(UUID_EXAMPLE);
        AccessRequest accessRequest = AccessRequest.builder()
                .build();

        when(repository.findById(id)).thenReturn(Mono.just(accessRequest));

        underTest.process(id)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        verify(repository, times(1)).findById(id);

    }

    @Test
    void processWhenAnAccessRequestIsNotFoundExpectMonoEmpty() {

        UUID id = UUID.fromString(UUID_EXAMPLE);

        when(repository.findById(id)).thenReturn(Mono.empty());

        underTest.process(id)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();

        verify(repository, times(1)).findById(id);

    }

}
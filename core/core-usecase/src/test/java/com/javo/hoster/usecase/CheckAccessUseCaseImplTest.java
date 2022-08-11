package com.javo.hoster.usecase;

import com.javo.hoster.model.Access;
import com.javo.hoster.repository.AccessRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckAccessUseCaseImplTest {

    @Mock
    private AccessRepository repository;

    @InjectMocks
    private CheckAccessUseCaseImpl underTest;

    public static final String UUID_EXAMPLE = "123e4567-e89b-12d3-a456-556642440000";
    public static final String REVIEWED_AT = "2022-08-02T19:45:40";

    @Test
    void processWhenAccessExistAndIsGrantedExpectAccess() {

        var id = UUID.fromString(UUID_EXAMPLE);
        LocalDateTime reviewedAt = LocalDateTime.parse(REVIEWED_AT).truncatedTo(ChronoUnit.SECONDS);

        var expectedAccess = Access.builder()
                .reviewedAt(reviewedAt)
                .id(id)
                .isGranted(Boolean.TRUE)
                .build();

        when(repository.findById(id)).thenReturn(Mono.just(expectedAccess));

        underTest.process(id)
                .as(StepVerifier::create)
                .expectNext(expectedAccess)
                .verifyComplete();

        verify(repository, times(1)).findById(id);
    }

    @Test
    void processWhenAccessDoesNotExistExpectMonoEmpty() {

        var id = UUID.fromString(UUID_EXAMPLE);

        when(repository.findById(id)).thenReturn(Mono.empty());

        underTest.process(id)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();

        verify(repository, times(1)).findById(id);
    }

    @Test
    void processWhenRepositoryThrowsARuntimeExceptionExpectARuntimeException() {

        var id = UUID.fromString(UUID_EXAMPLE);
        var exceptionExpected = new RuntimeException(UUID_EXAMPLE);

        when(repository.findById(id)).thenReturn(Mono.error(exceptionExpected));

        underTest.process(id)
                .as(StepVerifier::create)
                .expectErrorSatisfies(throwable -> {
                    assertEquals(RuntimeException.class, throwable.getClass());
                    assertEquals(UUID_EXAMPLE, throwable.getMessage());
                })
                .verify();

        verify(repository, times(1)).findById(id);
    }

}
package com.javo.hoster.usecase;

import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.model.AccessRequestConfirmation;
import com.javo.hoster.repository.AccessRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.lang.Boolean.logicalAnd;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClaimForAccessUseCaseImplTest {

    public static final String UUID_EXAMPLE_STRING = "123e4567-e89b-12d3-a456-556642440000";
    public static final UUID UUID_EXAMPLE = UUID.fromString(UUID_EXAMPLE_STRING);
    public static final String REQUESTED_AT_STRING = "2022-08-02T19:45:40";
    public static final LocalDateTime REQUESTED_AT = LocalDateTime.parse(REQUESTED_AT_STRING);
    public static final String EMAIL_EXAMPLE = "test@test.com";
    public static final long ACCESS_TIME = 10L;

    @Mock
    private AccessRequestRepository repository;

    @InjectMocks
    private ClaimForAccessUseCaseImpl underTest;

    @Test
    void processWhenAccessRequestRepositorySavesCorrectlyAccessRequestExpectAnAccessRequestConfirmation() {

        AccessRequest accessRequest = AccessRequest.builder()
                .id(UUID_EXAMPLE)
                .email(EMAIL_EXAMPLE)
                .company("")
                .name("")
                .accessTime(ACCESS_TIME)
                .requestedAt(REQUESTED_AT)
                .build();

        AccessRequestConfirmation accessRequestConfirmation = AccessRequestConfirmation.builder()
                .id(UUID_EXAMPLE)
                .receivedAt(REQUESTED_AT)
                .build();

        when(repository.save(argThat(accessRequestMatcher(accessRequest)))).thenReturn(Mono.just(accessRequest));

        underTest.process(accessRequest)
                .as(StepVerifier::create)
                .expectNext(accessRequestConfirmation)
                .verifyComplete();

        verify(repository, times(1)).save(argThat(accessRequestMatcher(accessRequest)));

    }

    @Test
    void processWhenAccessRequestRepositoryThrowsAnExceptionExceptionIsExpected() {

        AccessRequest accessRequest = AccessRequest.builder()
                .id(UUID_EXAMPLE)
                .email(EMAIL_EXAMPLE)
                .company("")
                .name("")
                .accessTime(ACCESS_TIME)
                .requestedAt(REQUESTED_AT)
                .build();

        AccessRequestConfirmation accessRequestConfirmation = AccessRequestConfirmation.builder()
                .id(UUID_EXAMPLE)
                .receivedAt(REQUESTED_AT)
                .build();

        when(repository.save(argThat(accessRequestMatcher(accessRequest))))
                .thenReturn(Mono.error(new RuntimeException("exception")));

        underTest.process(accessRequest)
                .as(StepVerifier::create)
                .expectErrorSatisfies(throwable -> {
                    assertEquals(RuntimeException.class, throwable.getClass());
                    assertEquals("exception", throwable.getMessage());
                })
                .verify();

        verify(repository, times(1))
                .save(argThat(accessRequestMatcher(accessRequest)));

    }

    private ArgumentMatcher<AccessRequest> accessRequestMatcher(AccessRequest toMatch) {
        return accessRequest -> {
            boolean conditionOne = logicalAnd(
                    toMatch.getAccessTime().equals(accessRequest.getAccessTime()),
                    toMatch.getCompany().equals(accessRequest.getCompany())
            );
            boolean conditionTwo = logicalAnd(
                    toMatch.getName().equals(accessRequest.getName()),
                    toMatch.getEmail().equals(accessRequest.getEmail())
            );
             return logicalAnd(conditionOne, conditionTwo);
        };
    }

}
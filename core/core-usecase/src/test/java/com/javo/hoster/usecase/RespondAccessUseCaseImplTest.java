package com.javo.hoster.usecase;

import com.javo.hoster.model.Access;
import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.repository.AccessRepository;
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
import java.util.NoSuchElementException;
import java.util.UUID;

import static java.lang.Boolean.logicalAnd;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RespondAccessUseCaseImplTest {

    public static final String UUID_EXAMPLE_STRING = "123e4567-e89b-12d3-a456-556642440000";
    public static final UUID UUID_EXAMPLE = UUID.fromString(UUID_EXAMPLE_STRING);
    public static final String REVIEWED_AT_STRING = "2022-08-02T19:45:40";
    public static final LocalDateTime REVIEWED_AT = LocalDateTime.parse(REVIEWED_AT_STRING);
    public static final String EMAIL_EXAMPLE = "test@test.com";
    public static final long ACCESS_TIME = 10L;

    @Mock
    private AccessRequestRepository accessRequestRepository;

    @Mock
    private AccessRepository accessRepository;

    @InjectMocks
    private RespondAccessUseCaseImpl underTest;

    @Test
    void processWhenAccessRequestExistAndAccessIsSavedSuccessfullyExpectAccess() {

        AccessRequest accessRequestFound = AccessRequest.builder()
                .id(UUID_EXAMPLE)
                .email(EMAIL_EXAMPLE)
                .company("")
                .name("")
                .accessTime(ACCESS_TIME)
                .requestedAt(REVIEWED_AT)
                .isReviewed(Boolean.FALSE)
                .reviewedAt(null)
                .build();

        AccessRequest accessRequestReviewed = accessRequestFound.toBuilder()
                .isReviewed(Boolean.TRUE)
                .reviewedAt(null)
                .build();

        Access access = Access.builder()
                .id(UUID_EXAMPLE)
                .reviewedAt(REVIEWED_AT)
                .isGranted(Boolean.TRUE)
                .build();

        when(accessRequestRepository.findById(UUID_EXAMPLE))
                .thenReturn(Mono.just(accessRequestFound));

        when(accessRequestRepository.save(argThat(accessRequestMatcher(accessRequestReviewed))))
                .thenReturn(Mono.just(accessRequestReviewed));

        when(accessRepository.save(argThat(accessMatcher(access))))
                .thenReturn(Mono.just(access));

        underTest.process(UUID_EXAMPLE, Boolean.TRUE)
                .as(StepVerifier::create)
                .expectNext(access)
                .verifyComplete();

        verify(accessRequestRepository, times(1)).findById(UUID_EXAMPLE);
        verify(accessRequestRepository, times(1)).save(argThat(accessRequestMatcher(accessRequestReviewed)));
        verify(accessRepository, times(1)).save(argThat(accessMatcher(access)));

    }

    @Test
    void processWhenAccessRequestRepositoryThrowsNotSuchElementExceptionExpectsNotSuchElementException() {

        when(accessRequestRepository.findById(UUID_EXAMPLE))
                .thenReturn(Mono.error(new NoSuchElementException("exception")));

        underTest.process(UUID_EXAMPLE, Boolean.TRUE)
                .as(StepVerifier::create)
                .expectErrorSatisfies(throwable -> {
                    assertEquals(NoSuchElementException.class, throwable.getClass());
                    assertEquals("exception", throwable.getMessage());
                })
                .verify();

        verify(accessRequestRepository, times(1)).findById(UUID_EXAMPLE);
        verify(accessRepository, times(0)).save(any());

    }

    private ArgumentMatcher<AccessRequest> accessRequestMatcher(AccessRequest toMatch) {
        return access -> logicalAnd(
                toMatch.getId().equals(access.getId()),
                toMatch.isReviewed() == access.isReviewed()
        );
    }

    private ArgumentMatcher<Access> accessMatcher(Access toMatch) {
        return access -> logicalAnd(
                toMatch.getId().equals(access.getId()),
                toMatch.isGranted() == access.isGranted()
        );
    }
}
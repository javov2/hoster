package com.javo.hoster.repository.adapter;


import com.javo.hoster.repository.AccessRequestJPARepository;
import com.javo.hoster.repository.entity.AccessRequestEntity;
import com.javo.hoster.model.AccessRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessRequestRepositoryAdapterTest {

    public static final String UUID_EXAMPLE_STRING = "123e4567-e89b-12d3-a456-556642440000";
    public static final UUID UUID_EXAMPLE = UUID.fromString(UUID_EXAMPLE_STRING);
    private final String REQUESTED_AT = "2022-08-02T19:45:40.026626500";
    private final Long ACCESS_TIME = 10L;
    private final String EMAIL = "test@test.com";

    @Mock
    AccessRequestJPARepository accessRequestJPARepository;

    @InjectMocks
    AccessRequestRepositoryAdapter accessRequestRepositoryAdapter;


    @Test
    void saveWhenRepositoryWorksWell() {

        var model = AccessRequest.builder()
                .id(UUID_EXAMPLE)
                .name("")
                .company("")
                .email(EMAIL)
                .accessTime(ACCESS_TIME)
                .requestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS))
                .build();

        var entityToSave = new AccessRequestEntity();
        entityToSave.setId(UUID_EXAMPLE);
        entityToSave.setName("");
        entityToSave.setCompany("");
        entityToSave.setEmail(EMAIL);
        entityToSave.setAccessTime(ACCESS_TIME);
        entityToSave.setRequestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));

        when(accessRequestJPARepository.save(entityToSave)).thenReturn(entityToSave);

        var result = accessRequestRepositoryAdapter.save(model)
                .as(StepVerifier::create)
                .expectNext(model)
                .verifyComplete();

        verify(accessRequestJPARepository, times(1)).save(entityToSave);
    }

    @Test
    void findByIdWhenElementExistExpectAccessRequest() {

        var model = AccessRequest.builder()
                .id(UUID_EXAMPLE)
                .name("")
                .company("")
                .email(EMAIL)
                .accessTime(ACCESS_TIME)
                .requestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS))
                .build();

        var entity = new AccessRequestEntity();
        entity.setId(UUID_EXAMPLE);
        entity.setName("");
        entity.setCompany("");
        entity.setEmail(EMAIL);
        entity.setAccessTime(ACCESS_TIME);
        entity.setRequestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));

        when(accessRequestJPARepository.findById(UUID_EXAMPLE)).thenReturn(Optional.of(entity));

        var result = accessRequestRepositoryAdapter.findById(UUID_EXAMPLE)
                .as(StepVerifier::create)
                .expectNext(model)
                .verifyComplete();

        verify(accessRequestJPARepository, times(1)).findById(UUID_EXAMPLE);
    }

    @Test
    void findByIdWhenElementDoesNotExistExpectNoSuchElementException() {

        when(accessRequestJPARepository.findById(UUID_EXAMPLE)).thenReturn(Optional.empty());

        var result = accessRequestRepositoryAdapter.findById(UUID_EXAMPLE)
                .as(StepVerifier::create)
                .expectErrorSatisfies(throwable -> {
                    assertEquals(NoSuchElementException.class, throwable.getClass());
                })
                .verify();

        verify(accessRequestJPARepository, times(1)).findById(UUID_EXAMPLE);
    }

    @Test
    void findAllWhenRepositoryReturnsAList() {

        var model = AccessRequest.builder()
                .name("")
                .company("")
                .email(EMAIL)
                .accessTime(ACCESS_TIME)
                .requestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS))
                .build();

        var entityFound = new AccessRequestEntity();
        entityFound.setName("");
        entityFound.setCompany("");
        entityFound.setEmail(EMAIL);
        entityFound.setAccessTime(ACCESS_TIME);
        entityFound.setRequestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));

        var requestEntityList = List.of(entityFound);
        when(accessRequestJPARepository.findAll()).thenReturn(requestEntityList);

        var result = accessRequestRepositoryAdapter.findAll()
                .as(StepVerifier::create)
                .expectNext(model)
                .verifyComplete();

        verify(accessRequestJPARepository, times(1)).findAll();

    }

    @Test
    void findAllNotReviewedWhenRepositoryReturnsAList() {

        var model = AccessRequest.builder()
                .name("")
                .company("")
                .email(EMAIL)
                .accessTime(ACCESS_TIME)
                .requestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS))
                .build();

        var entityFound = new AccessRequestEntity();
        entityFound.setName("");
        entityFound.setCompany("");
        entityFound.setEmail(EMAIL);
        entityFound.setAccessTime(ACCESS_TIME);
        entityFound.setRequestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));

        var requestEntityList = List.of(entityFound);
        when(accessRequestJPARepository.findAllNotReviewed()).thenReturn(requestEntityList);

        var result = accessRequestRepositoryAdapter.findAllNotReviewed()
                .as(StepVerifier::create)
                .expectNext(model)
                .verifyComplete();

        verify(accessRequestJPARepository, times(1)).findAllNotReviewed();

    }

}
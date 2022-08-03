package com.javo.hoster.adapter;

import com.javo.hoster.entity.AccessRequestEntity;
import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.repository.AccessRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessRequestRepositoryAdapterTest {

    @Mock
    AccessRequestRepository accessRequestRepository;

    @InjectMocks
    AccessRequestRepositoryAdapter accessRequestRepositoryAdapter;

    private final String REQUESTED_AT = "2022-08-02T19:45:40.026626500";
    private final String GRANTED_UNTIL = "2022-08-03T19:45:40.026626500";

    @Test
    void saveWhenRepositoryWorksWell() {

        var model = AccessRequest.builder()
                .name("")
                .company("")
                .accessGrantedUntil(LocalDateTime.parse(GRANTED_UNTIL).truncatedTo(ChronoUnit.SECONDS))
                .requestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS))
                .build();

        var entityToSave = new AccessRequestEntity();
        entityToSave.setName("");
        entityToSave.setCompany("");
        entityToSave.setAccessGrantedUntil(LocalDateTime.parse(GRANTED_UNTIL).truncatedTo(ChronoUnit.SECONDS));
        entityToSave.setRequestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));

        when(accessRequestRepository.save(entityToSave)).thenReturn(entityToSave);

        var result = accessRequestRepositoryAdapter.save(model)
                .as(StepVerifier::create)
                .expectNext(model)
                .verifyComplete();

        verify(accessRequestRepository, times(1)).save(entityToSave);
    }

    @Test
    void findAllWhenRepositoryReturnsAList() {

        var model = AccessRequest.builder()
                .name("")
                .company("")
                .accessGrantedUntil(LocalDateTime.parse(GRANTED_UNTIL).truncatedTo(ChronoUnit.SECONDS))
                .requestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS))
                .build();

        var entityFound = new AccessRequestEntity();
        entityFound.setName("");
        entityFound.setCompany("");
        entityFound.setAccessGrantedUntil(LocalDateTime.parse(GRANTED_UNTIL).truncatedTo(ChronoUnit.SECONDS));
        entityFound.setRequestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));

        var requestEntityList = List.of(entityFound);
        when(accessRequestRepository.findAll()).thenReturn(requestEntityList);

        var result = accessRequestRepositoryAdapter.findAll()
                .as(StepVerifier::create)
                .expectNext(model)
                .verifyComplete();

        verify(accessRequestRepository, times(1)).findAll();

    }
}
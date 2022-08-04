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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessRequestRepositoryAdapterTest {

    @Mock
    AccessRequestJPARepository accessRequestJPARepository;

    @InjectMocks
    AccessRequestRepositoryAdapter accessRequestRepositoryAdapter;

    private final String REQUESTED_AT = "2022-08-02T19:45:40.026626500";
    private final Long ACCESS_TIME = 10L;

    @Test
    void saveWhenRepositoryWorksWell() {

        var model = AccessRequest.builder()
                .name("")
                .company("")
                .accessTime(ACCESS_TIME)
                .requestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS))
                .build();

        var entityToSave = new AccessRequestEntity();
        entityToSave.setName("");
        entityToSave.setCompany("");
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
    void findAllWhenRepositoryReturnsAList() {

        var model = AccessRequest.builder()
                .name("")
                .company("")
                .accessTime(ACCESS_TIME)
                .requestedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS))
                .build();

        var entityFound = new AccessRequestEntity();
        entityFound.setName("");
        entityFound.setCompany("");
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
}
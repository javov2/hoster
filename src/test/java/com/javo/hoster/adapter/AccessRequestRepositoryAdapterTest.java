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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessRequestRepositoryAdapterTest {

    @Mock
    AccessRequestRepository accessRequestRepository;

    @InjectMocks
    AccessRequestRepositoryAdapter accessRequestRepositoryAdapter;

    @Test
    void save() {

        var toSave = AccessRequest.builder().build();
        var entityToSave = new AccessRequestEntity();
        when(accessRequestRepository.save(entityToSave)).thenReturn(entityToSave);

        var result = accessRequestRepositoryAdapter.save(toSave)
                .as(StepVerifier::create)
                .expectNext(toSave)
                .verifyComplete();

        verify(accessRequestRepository, times(1)).save(entityToSave);
    }

    @Test
    void findAll() {

        var toSave = AccessRequest.builder().build();
        var entityFound = List.of(new AccessRequestEntity());
        when(accessRequestRepository.findAll()).thenReturn(entityFound);

        var result = accessRequestRepositoryAdapter.findAll()
                .as(StepVerifier::create)
                .expectNext(toSave)
                .verifyComplete();

        verify(accessRequestRepository, times(1)).findAll();

    }
}
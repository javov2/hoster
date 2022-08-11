package com.javo.hoster.repository.adapter;


import com.javo.hoster.model.Access;
import com.javo.hoster.repository.AccessJPARepository;
import com.javo.hoster.repository.entity.AccessEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessRepositoryAdapterTest {

    public static final String UUID_EXAMPLE = "123e4567-e89b-12d3-a456-556642440000";
    private final String REQUESTED_AT = "2022-08-02T19:45:40.026626500";

    @Mock
    AccessJPARepository accessJPARepository;

    @InjectMocks
    AccessRepositoryAdapter accessRepositoryAdapter;


    @Test
    void saveWhenRepositoryWorksWell() {

        var id = UUID.fromString(UUID_EXAMPLE);

        var model = Access.builder()
                .id(id)
                .isGranted(Boolean.TRUE)
                .reviewedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS))
                .build();

        var entityToSave = new AccessEntity();
        entityToSave.setId(id);
        entityToSave.setIsGranted(Boolean.TRUE);
        entityToSave.setReviewedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));

        when(accessJPARepository.save(entityToSave)).thenReturn(entityToSave);

        var result = accessRepositoryAdapter.save(model)
                .as(StepVerifier::create)
                .expectNext(model)
                .verifyComplete();

        verify(accessJPARepository, times(1)).save(entityToSave);
    }

    @Test
    void findByIdWhenElementExistExpectAccessRequest() {

        var id = UUID.fromString(UUID_EXAMPLE);

        var model = Access.builder()
                .id(id)
                .isGranted(Boolean.TRUE)
                .reviewedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS))
                .build();

        var entity = new AccessEntity();
        entity.setId(id);
        entity.setIsGranted(Boolean.TRUE);
        entity.setReviewedAt(LocalDateTime.parse(REQUESTED_AT).truncatedTo(ChronoUnit.SECONDS));

        when(accessJPARepository.findById(id)).thenReturn(Optional.of(entity));

        var result = accessRepositoryAdapter.findById(id)
                .as(StepVerifier::create)
                .expectNext(model)
                .verifyComplete();

        verify(accessJPARepository, times(1)).findById(id);
    }

    @Test
    void findByIdWhenElementDoesNotExistExpectNoSuchElementException() {

        var id = UUID.fromString(UUID_EXAMPLE);

        when(accessJPARepository.findById(id)).thenReturn(Optional.empty());

        var result = accessRepositoryAdapter.findById(id)
                .as(StepVerifier::create)
                .expectErrorSatisfies(throwable -> {
                    assertEquals(NoSuchElementException.class, throwable.getClass());
                })
                .verify();

        verify(accessJPARepository, times(1)).findById(id);
    }

}
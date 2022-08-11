package com.javo.hoster.usecase;

import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.model.AccessRequestConfirmation;
import com.javo.hoster.repository.AccessRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class ClaimForAccessUseCaseImpl implements ClaimForAccessUseCase {

    @Autowired
    private AccessRequestRepository accessRequestRepository;

    @Override
    public Mono<AccessRequestConfirmation> process(AccessRequest accessRequest) {
        return accessRequestRepository.save(
                        accessRequest.toBuilder()
                                .requestedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                                .id(UUID.randomUUID())
                                .build())
                .map(accessRequest1 -> AccessRequestConfirmation.builder()
                        .id(accessRequest1.getId())
                        .receivedAt(accessRequest1.getRequestedAt())
                        .build());
    }
}

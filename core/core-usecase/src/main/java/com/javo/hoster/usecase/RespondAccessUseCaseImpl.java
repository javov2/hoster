package com.javo.hoster.usecase;

import com.javo.hoster.model.Access;
import com.javo.hoster.repository.AccessRepository;
import com.javo.hoster.repository.AccessRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class RespondAccessUseCaseImpl implements RespondAccessUseCase {

    @Autowired
    private AccessRequestRepository accessRequestRepository;
    @Autowired
    private AccessRepository accessRepository;

    @Override
    public Mono<Access> process(UUID accessRequestUuid, boolean isGranted) {
        return accessRequestRepository.findById(accessRequestUuid)
                .flatMap(accessRequest -> accessRepository.save(Access.builder()
                        .id(accessRequest.getId())
                        .reviewedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                        .isGranted(isGranted)
                        .build()));
    }
}

package com.javo.hoster.usecase;

import com.javo.hoster.model.Access;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RespondAccessRequestUseCase {

    Mono<Access> process(UUID accessRequestUuid, boolean isGranted);

}

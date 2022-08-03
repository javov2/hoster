package com.javo.hoster.usecase;

import com.javo.hoster.model.Access;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CheckAccessRequestUseCase {

    Mono<Access> process(UUID accessRequestId);

}

package com.javo.hoster.usecase;

import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.repository.AccessRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class FindAllAccessRequestToReviewUseCaseImpl implements FindAllAccessRequestToReviewUseCase {

    @Autowired
    private AccessRequestRepository repository;

    @Override
    public Flux<AccessRequest> process() {
        return repository.findAllNotReviewed();
    }
}

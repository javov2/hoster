package com.javo.hoster.controller.rest;

import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.usecase.crud.FindAccessRequestByIdUseCase;
import com.javo.hoster.usecase.crud.FindAllAccessRequestUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class AccessRequestController {

    @Autowired
    private FindAllAccessRequestUseCase findAllAccessRequestUseCase;
    @Autowired
    private FindAccessRequestByIdUseCase findAccessRequestByIdUseCase;

    @GetMapping("/access-request")
    public Flux<AccessRequest> getAllAccessRequest(){
        return findAllAccessRequestUseCase.process();
    }

    @GetMapping(value = "/access-request", params = {"id"})
    public Mono<AccessRequest> getAccessRequestById(@RequestParam(name = "id") String id){
        return findAccessRequestByIdUseCase.process(UUID.fromString(id));
    }

}

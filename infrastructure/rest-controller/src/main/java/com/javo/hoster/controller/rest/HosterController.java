package com.javo.hoster.controller.rest;

import com.javo.hoster.model.Access;
import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.model.AccessRequestConfirmation;
import com.javo.hoster.usecase.CheckAccessRequestUseCase;
import com.javo.hoster.usecase.ClaimForAccessRequestUseCase;
import com.javo.hoster.usecase.RespondAccessRequestUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
public class HosterController {

    @Autowired
    private CheckAccessRequestUseCase checkAccessRequestUseCase;
    @Autowired
    private ClaimForAccessRequestUseCase claimForAccessRequestUseCase;
    @Autowired
    private RespondAccessRequestUseCase respondAccessRequestUseCase;

    @ResponseBody
    @GetMapping(value = "/check-access", params = {"id"})
    public Mono<Access> checkAccess(@RequestParam(name = "id") String id){
        return checkAccessRequestUseCase.process(UUID.fromString(id));
    }

    @ResponseBody
    @PostMapping(value = "/claim-access")
    public Mono<AccessRequestConfirmation> checkAccess(AccessRequest accessRequest){
        return claimForAccessRequestUseCase.process(accessRequest);
    }

    @ResponseBody
    @PostMapping(value = "/respond-access")
    public Mono<Access> respondAccess(AccessRequest accessRequest){
        return respondAccessRequestUseCase.process(accessRequest);
    }

}

package com.javo.hoster.controller.rest;

import com.javo.hoster.controller.dto.AccessDTO;
import com.javo.hoster.controller.dto.AccessRequestConfirmationDTO;
import com.javo.hoster.controller.dto.AccessRequestDTO;
import com.javo.hoster.controller.rest.adapter.HosterControllerDTOFactory;
import com.javo.hoster.usecase.CheckAccessUseCase;
import com.javo.hoster.usecase.ClaimForAccessUseCase;
import com.javo.hoster.usecase.RespondAccessUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class HosterController {

    @Autowired
    private CheckAccessUseCase checkAccessUseCase;
    @Autowired
    private ClaimForAccessUseCase claimForAccessUseCase;
    @Autowired
    private RespondAccessUseCase respondAccessUseCase;

    @ResponseBody
    @PostMapping(value = "/claim-access", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<AccessRequestConfirmationDTO> claimAccess(@Valid @RequestBody AccessRequestDTO accessRequestDTO){
        var accessRequest = HosterControllerDTOFactory.accessRequestDtoToModel(accessRequestDTO);
        return claimForAccessUseCase.process(accessRequest)
                .map(HosterControllerDTOFactory::accessRequestConfirmationToDto);
    }

    @ResponseBody
    @PostMapping(value = "/respond-access")
    public Mono<AccessDTO> respondAccess(@RequestParam(name = "id") UUID id, @RequestParam(name = "isGranted") boolean isGranted){
        return respondAccessUseCase.process(id, isGranted)
                .map(HosterControllerDTOFactory::accessToDto);
    }

    @ResponseBody
    @GetMapping(value = "/check-access", params = {"id"})
    public Mono<AccessDTO> checkAccess(@RequestParam(name = "id") UUID id){
        return checkAccessUseCase.process(id)
                .map(HosterControllerDTOFactory::accessToDto);
    }

}

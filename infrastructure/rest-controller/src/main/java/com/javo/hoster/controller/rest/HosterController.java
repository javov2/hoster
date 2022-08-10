package com.javo.hoster.controller.rest;

import com.javo.hoster.controller.dto.AccessDTO;
import com.javo.hoster.controller.dto.AccessRequestConfirmationDTO;
import com.javo.hoster.controller.dto.AccessRequestDTO;
import com.javo.hoster.controller.rest.adapter.HosterControllerDTOFactory;
import com.javo.hoster.model.Access;
import com.javo.hoster.usecase.CheckAccessRequestUseCase;
import com.javo.hoster.usecase.ClaimForAccessRequestUseCase;
import com.javo.hoster.usecase.RespondAccessRequestUseCase;
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
    private CheckAccessRequestUseCase checkAccessRequestUseCase;
    @Autowired
    private ClaimForAccessRequestUseCase claimForAccessRequestUseCase;
    @Autowired
    private RespondAccessRequestUseCase respondAccessRequestUseCase;

    @ResponseBody
    @PostMapping(value = "/claim-access", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<AccessRequestConfirmationDTO> claimAccess(@Valid @RequestBody AccessRequestDTO accessRequestDTO){
        var accessRequest = HosterControllerDTOFactory.accessRequestDtoToModel(accessRequestDTO);
        return claimForAccessRequestUseCase.process(accessRequest)
                .map(HosterControllerDTOFactory::accessRequestConfirmationToDto);
    }

    @ResponseBody
    @PostMapping(value = "/respond-access")
    public Mono<AccessDTO> respondAccess(@RequestParam(name = "id") String id, @RequestParam(name = "isGranted") boolean isGranted){
        return respondAccessRequestUseCase.process(UUID.fromString(id), isGranted)
                .map(HosterControllerDTOFactory::accessToDto);
    }

    @ResponseBody
    @GetMapping(value = "/check-access", params = {"id"})
    public Mono<AccessDTO> checkAccess(@RequestParam(name = "id") String id){
        return checkAccessRequestUseCase.process(UUID.fromString(id))
                .map(HosterControllerDTOFactory::accessToDto);
    }

}

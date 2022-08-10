package com.javo.hoster.controller.rest.adapter;

import com.javo.hoster.controller.dto.AccessDTO;
import com.javo.hoster.controller.dto.AccessRequestConfirmationDTO;
import com.javo.hoster.controller.dto.AccessRequestDTO;
import com.javo.hoster.model.Access;
import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.model.AccessRequestConfirmation;

public class HosterControllerDTOFactory {

    private HosterControllerDTOFactory(){}

    public static AccessRequestConfirmationDTO accessRequestConfirmationToDto(AccessRequestConfirmation accessRequestConfirmation){
        var accessRequestConfirmationDTO = new AccessRequestConfirmationDTO();
        accessRequestConfirmationDTO.setId(accessRequestConfirmation.getId());
        accessRequestConfirmationDTO.setReceivedAt(accessRequestConfirmation.getReceivedAt());
        return accessRequestConfirmationDTO;
    }

    public static AccessRequest accessRequestDtoToModel(AccessRequestDTO accessRequestDTO){
        return AccessRequest.builder()
                .email(accessRequestDTO.getEmail())
                .accessTime(accessRequestDTO.getAccessTime())
                .company(accessRequestDTO.getCompany())
                .name(accessRequestDTO.getName())
                .build();
    }

    public static AccessDTO accessToDto(Access access) {
        var accessDto = new AccessDTO();
        accessDto.setGranted(access.isGranted());
        accessDto.setReviewedAt(access.getReviewedAt());
        accessDto.setId(access.getId());
        return accessDto;
    }
}

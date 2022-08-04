package com.javo.hoster.controller.rest.adapter;

import com.javo.hoster.controller.dto.AccessRequestConfirmationDTO;
import com.javo.hoster.controller.dto.AccessRequestDTO;
import com.javo.hoster.model.AccessRequest;
import com.javo.hoster.model.AccessRequestConfirmation;

public class AccessRequestConfirmationRestAdapter {

    private AccessRequestConfirmationRestAdapter(){}

    public static AccessRequestConfirmationDTO toDTO(AccessRequestConfirmation accessRequestConfirmation){
        var accessRequestConfirmationDTO = new AccessRequestConfirmationDTO();
        accessRequestConfirmationDTO.setId(accessRequestConfirmation.getId());
        accessRequestConfirmationDTO.setReceivedAt(accessRequestConfirmation.getReceivedAt());
        return accessRequestConfirmationDTO;
    }

}

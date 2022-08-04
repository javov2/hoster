package com.javo.hoster.controller.rest.adapter;

import com.javo.hoster.controller.dto.AccessRequestDTO;
import com.javo.hoster.model.AccessRequest;

public class AccessRequestRestAdapter {

    private AccessRequestRestAdapter(){}

    public static AccessRequest toModel(AccessRequestDTO accessRequestDTO){
        return AccessRequest.builder()
                .accessTime(accessRequestDTO.getAccessTime())
                .company(accessRequestDTO.getCompany())
                .name(accessRequestDTO.getName())
                .build();
    }

}

package com.javo.hoster.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AccessRequest {

    private UUID id;

}

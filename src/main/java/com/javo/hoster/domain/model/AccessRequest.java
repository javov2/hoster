package com.javo.hoster.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AccessRequest {

    private UUID id;
    String name;
    String company;
    LocalDateTime requestedAt;
    LocalDateTime accessGrantedUntil;

}

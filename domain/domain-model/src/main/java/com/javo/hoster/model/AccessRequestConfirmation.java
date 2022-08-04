package com.javo.hoster.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AccessRequestConfirmation {

    private UUID id;
    private LocalDateTime receivedAt;

}

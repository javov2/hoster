package com.javo.hoster.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccessRequestConfirmationDTO {

    private UUID id;
    private LocalDateTime receivedAt;

}

package com.javo.hoster.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class AccessRequest {

    private UUID id;
    private String name;
    private String company;
    @Email
    private String email;
    private LocalDateTime requestedAt;
    private Long accessTime;
    private boolean isReviewed;
    private LocalDateTime reviewedAt;
}

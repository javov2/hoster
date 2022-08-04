package com.javo.hoster.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AccessRequest {

    private UUID id;
    private String name;
    private String company;
    private LocalDateTime requestedAt;
    private Long accessTime;

}

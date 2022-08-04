package com.javo.hoster.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Access {
    private UUID id;
    private boolean isGranted;
}

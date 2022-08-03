package com.javo.hoster.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Data
@EqualsAndHashCode
@Table(name = "access_request")
public class AccessRequestEntity {

    @Id
    @EqualsAndHashCode.Exclude
    UUID id;

    @NotNull
    String name;
    @NotNull
    String company;
    @NotNull
    LocalDateTime requestedAt;
    @NotNull
    @Column(name = "granted_until")
    LocalDateTime accessGrantedUntil;
}


package com.javo.hoster.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Table(name = "access_request")
public class AccessRequestEntity {

    @Id
    @GeneratedValue
    UUID id;

}


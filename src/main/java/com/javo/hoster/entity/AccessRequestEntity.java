package com.javo.hoster.entity;

import lombok.Builder;
import lombok.Data;
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
@Data
public class AccessRequestEntity {

    @Id
    @GeneratedValue
    UUID id;

}


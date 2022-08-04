package com.javo.hoster.repository.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
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
    @Column(name = "access_time")
    Long accessTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessRequestEntity that = (AccessRequestEntity) o;
        return name.equals(that.name) && company.equals(that.company) && requestedAt.equals(that.requestedAt) && accessTime.equals(that.accessTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, company, requestedAt, accessTime);
    }
}


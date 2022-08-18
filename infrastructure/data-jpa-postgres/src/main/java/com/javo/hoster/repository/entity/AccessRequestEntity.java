package com.javo.hoster.repository.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "access_request")
public class AccessRequestEntity {

    @Id
    UUID id;
    @NotNull
    String name;
    @NotNull
    String company;
    @Email
    @NotNull
    String email;
    @NotNull
    LocalDateTime requestedAt;
    @NotNull
    @Column(name = "access_time")
    Long accessTime;
    boolean isReviewed;
    LocalDateTime reviewedAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessRequestEntity that = (AccessRequestEntity) o;
        return isReviewed == that.isReviewed
                && id.equals(that.id)
                && name.equals(that.name)
                && company.equals(that.company)
                && email.equals(that.email)
                && requestedAt.equals(that.requestedAt)
                && accessTime.equals(that.accessTime)
                && Objects.equals(reviewedAt, that.reviewedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, company, email, requestedAt, accessTime, isReviewed, reviewedAt);
    }
}



package com.javo.hoster.repository.entity;

import lombok.*;

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
@Table(name = "access")
public class AccessEntity {

    @Id
    @EqualsAndHashCode.Exclude
    UUID id;

    @NotNull
    Boolean isGranted;
    @NotNull
    LocalDateTime reviewedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessEntity that = (AccessEntity) o;
        return isGranted.equals(that.isGranted) && reviewedAt.equals(that.reviewedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isGranted, reviewedAt);
    }
}


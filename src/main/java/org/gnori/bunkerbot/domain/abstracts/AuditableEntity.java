package org.gnori.bunkerbot.domain.abstracts;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@MappedSuperclass
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, of = {})
public abstract class AuditableEntity extends AbstractEntity {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        final LocalDateTime currentTime = LocalDateTime.now();

        this.createdAt = currentTime;

        if (this.setUpdatedAtPrePersist()) {
            this.updatedAt = currentTime;
        }

        this.customPrePersist();
    }

    @PreUpdate
    private void preUpdate() {

        this.updatedAt = LocalDateTime.now();

        this.customPreUpdate();
    }

    @Transient
    public Optional<LocalDateTime> getSelfCreatedAt() {
        return Optional.ofNullable(this.getCreatedAt());
    }

    @Transient
    public Optional<LocalDateTime> getSelfUpdatedAt() {
        return Optional.ofNullable(this.getUpdatedAt());
    }

    @Transient
    protected boolean setUpdatedAtPrePersist() {
        return false;
    }

    @Transient
    protected void customPrePersist() {}

    @Transient
    protected void customPreUpdate() {}
}


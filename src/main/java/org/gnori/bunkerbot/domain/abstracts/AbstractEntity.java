package org.gnori.bunkerbot.domain.abstracts;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Getter
@Setter
@ToString
@MappedSuperclass
@EqualsAndHashCode(of = {"id"})
public abstract class AbstractEntity implements Persistable<Long>, Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_generator"
    )
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;

    @Transient
    @Override
    public boolean isNew() {
        return null == this.getId();
    }
}

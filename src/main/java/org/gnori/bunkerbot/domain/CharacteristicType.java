package org.gnori.bunkerbot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.abstracts.AuditableEntity;
import org.gnori.bunkerbot.domain.constants.DefaultGenericGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import static org.gnori.bunkerbot.domain.CharacteristicType.CHARACTERISTIC_TYPE_TABLE_NAME;
import static org.gnori.bunkerbot.domain.constants.DefaultGenericGenerator.*;

@GenericGenerator(
        name = NAME,
        strategy = STRATEGY,
        parameters = {
                @Parameter(name = DefaultGenericGenerator.Params.SEQUENCE_NAME, value = CHARACTERISTIC_TYPE_TABLE_NAME + POSTGRES_ENTITY_SEQUENCE_SUFFIX),
                @Parameter(name = Params.INITIAL_VALUE, value = Params.DEFAULT_VALUE),
                @Parameter(name = Params.INCREMENT_SIZE, value = Params.DEFAULT_VALUE)
        }
)
@Getter
@Setter
@Builder
@Entity
@Table(name = CHARACTERISTIC_TYPE_TABLE_NAME)
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class CharacteristicType extends AuditableEntity {

    static final String CHARACTERISTIC_TYPE_TABLE_NAME = "characteristic_types";

    @Column(name = "name", nullable = false, unique = true)
    String name;
}

package org.gnori.bunkerbot.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.abstracts.AuditableEntity;
import org.gnori.bunkerbot.domain.constants.DefaultGenericGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.List;

import static org.gnori.bunkerbot.domain.Characteristic.CHARACTERISTIC_TABLE;
import static org.gnori.bunkerbot.domain.constants.DefaultGenericGenerator.*;


@GenericGenerator(
        name = NAME,
        strategy = STRATEGY,
        parameters = {
                @Parameter(name = DefaultGenericGenerator.Params.SEQUENCE_NAME, value = CHARACTERISTIC_TABLE + POSTGRES_ENTITY_SEQUENCE_SUFFIX),
                @Parameter(name = Params.INITIAL_VALUE, value = Params.DEFAULT_VALUE),
                @Parameter(name = Params.INCREMENT_SIZE, value = Params.DEFAULT_VALUE)
        }
)
@Getter
@Setter
@Builder
@Entity
@Table(name = CHARACTERISTIC_TABLE)
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Characteristic extends AuditableEntity {

    static final String CHARACTERISTIC_TABLE = "characteristics";

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    CharacteristicType characteristicType;

    @Column(name = "description", unique = true)
    String description;

    @Column(name = "image_url")
    String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "characteristic_opposites",
            joinColumns = {
                    @JoinColumn(name = "characteristic_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "opposite_characteristic_id", referencedColumnName = "id")
            },
            indexes = {@Index(columnList = "characteristic_id, opposite_characteristic_id", unique = true)}
    )
    List<Characteristic> characteristicOpposites;
}

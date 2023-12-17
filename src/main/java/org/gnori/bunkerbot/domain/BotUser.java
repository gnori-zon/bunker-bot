package org.gnori.bunkerbot.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.abstracts.AuditableEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import static org.gnori.bunkerbot.domain.BotUser.BOT_USER_TABLE;
import static org.gnori.bunkerbot.domain.constants.DefaultGenericGenerator.*;

@GenericGenerator(
        name = NAME,
        strategy = STRATEGY,
        parameters = {
                @Parameter(name = Params.SEQUENCE_NAME, value = BOT_USER_TABLE + POSTGRES_ENTITY_SEQUENCE_SUFFIX),
                @Parameter(name = Params.INITIAL_VALUE, value = Params.DEFAULT_VALUE),
                @Parameter(name = Params.INCREMENT_SIZE, value = Params.DEFAULT_VALUE)
        }
)
@Getter
@Setter
@Builder
@Entity
@Table(name = BOT_USER_TABLE)
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BotUser extends AuditableEntity {

    static final String BOT_USER_TABLE = "bot_users";

    @Column(name = "chat_id", nullable = false, unique = true)
    Long chatId;

    @Column(name = "username")
    String username;

    @Builder.Default
    @Column(name = "is_admin", nullable = false)
    boolean isAdmin = false;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    boolean isActive = false;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    BotUserState state = BotUserState.DEFAULT;
}

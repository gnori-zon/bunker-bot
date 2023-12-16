package org.gnori.bunkerbot.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.abstracts.AuditableEntity;

import static org.gnori.bunkerbot.domain.BotUser.BOT_USER_TABLE;

@Getter
@Setter
@Entity
@Table(name = BOT_USER_TABLE)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BotUser extends AuditableEntity {

    static final String BOT_USER_TABLE = "bot_user";

    @Column(name = "chat_id", nullable = false, unique = true)
    Long chatId;

    @Column(name = "username")
    String username;

    @Column(name = "is_admin", nullable = false)
    Boolean isAdmin = false;

    @Column(name = "is_blocked", nullable = false)
    Boolean isBlocked = false;
}

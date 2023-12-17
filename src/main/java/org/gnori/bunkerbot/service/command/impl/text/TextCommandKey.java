package org.gnori.bunkerbot.service.command.impl.text;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gnori.bunkerbot.service.command.InstanceKey;

import java.util.Optional;

@RequiredArgsConstructor
public enum TextCommandKey implements InstanceKey {

    START("/start"),
    SHOW_ALL("show all"),
    SHOW_ACTIVE("show active"),
    ACTIVATE_USER("activate user"),
    DEACTIVATE_USER("deactivate user"),
    CANCEL("cancel"),
    START_GAME("start game"),
    UNDEFINED(null);

    final String prefix;

    public String getKey() {
        return prefix;
    }

    public static Optional<TextCommandKey> of(@NonNull String text) {

        for (TextCommandKey key : TextCommandKey.values()) {

            if (key != UNDEFINED && text.startsWith(key.getKey())) {
                return Optional.of(key);
            }
        }

        return Optional.empty();
    }
}

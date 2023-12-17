package org.gnori.bunkerbot.service.command.impl.text.commands.state;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StateCommandUtils {

    public static Optional<Long> extractId(String userIdRawForActivate) {

        try {
            return Optional.of(Long.valueOf(userIdRawForActivate));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}

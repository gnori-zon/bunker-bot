package org.gnori.bunkerbot.service.command.impl.callback;

import org.gnori.bunkerbot.service.command.InstanceKey;

import java.util.List;
import java.util.Optional;

public enum CallbackCommandKey implements InstanceKey {

    SET_ACTIVE_USER("set-active"),
    NEXT_STEP_GAME("next-step-game"),
    STOP_GAME("stop-game"),
    SKIP_DELETE_USER("skip-delete-user"),
    UNDEFINED(null);

    public static final String DELIMITER = "&&";
    private String prefix;

    CallbackCommandKey(String prefix) {
        this.prefix = prefix;
    }

    public String createCallbackData(String... params) {

        if (params == null) {
            return "";
        }

        final String callbackParams = String.join(DELIMITER, params);

        return String.join(DELIMITER, List.of(prefix, callbackParams));
    }

    public static Optional<CallbackCommandKey> of(String data) {

        final String[] params = data.split(DELIMITER);

        if (params.length > 0 && params[0] != null) {

            for (CallbackCommandKey key : CallbackCommandKey.values()) {

                if (key != UNDEFINED && params[0].equals(key.prefix)) {
                    return Optional.of(key);
                }
            }
        }

        return Optional.empty();
    }
}

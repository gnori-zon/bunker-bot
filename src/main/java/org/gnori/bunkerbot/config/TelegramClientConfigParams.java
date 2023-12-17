package org.gnori.bunkerbot.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class TelegramClientConfigParams {

    @Value("${tg.name}")
    String name;

    @Value("${tg.token}")
    String token;
}

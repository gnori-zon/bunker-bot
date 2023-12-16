package org.gnori.bunkerbot.service.impl.domain;

public interface BotUserUsernameChanger {

    boolean changeUsername(Long chatId, String newUsername);
}

package org.gnori.bunkerbot.service.impl.domain;

import org.gnori.bunkerbot.domain.BotUserState;

public interface BotUserStateChanger {

    boolean changeState(Long chatId, BotUserState newState);
}

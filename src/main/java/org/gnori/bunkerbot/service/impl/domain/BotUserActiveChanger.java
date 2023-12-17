package org.gnori.bunkerbot.service.impl.domain;

public interface BotUserActiveChanger {

    boolean changeActive(Long userId, boolean isActive);
}

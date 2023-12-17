package org.gnori.bunkerbot.domain;

import org.gnori.bunkerbot.service.command.InstanceKey;

public enum BotUserState implements InstanceKey {

    DEFAULT,
    WAITING_ACTIVATE_ID,
    WAITING_DEACTIVATE_ID,
    WAITING_DELETE_ID,
    UNDEFINED
}

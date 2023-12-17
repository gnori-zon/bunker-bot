package org.gnori.bunkerbot.service.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command<K extends InstanceKey> {

    void execute(Update update);
    K getSupportedKey();
}

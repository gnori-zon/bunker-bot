package org.gnori.bunkerbot.controller;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageTypeDistributor {
    void distribute(Update update);
}

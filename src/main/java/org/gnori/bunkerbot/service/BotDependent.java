package org.gnori.bunkerbot.service;

import org.telegram.telegrambots.bots.DefaultAbsSender;

public interface BotDependent {
    <B extends DefaultAbsSender> void registerBot(B bot);

}

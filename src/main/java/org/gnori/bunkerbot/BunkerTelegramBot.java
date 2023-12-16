package org.gnori.bunkerbot;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.config.TelegramClientConfigParams;
import org.gnori.bunkerbot.controller.MessageTypeDistributor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BunkerTelegramBot extends TelegramLongPollingBot {

    TelegramClientConfigParams telegramClientConfigParams;
    MessageTypeDistributor messageTypeDistributor;

    @Override
    public void onUpdateReceived(Update update) {
        messageTypeDistributor.distribute(update);
    }

    @Override
    public String getBotUsername() {
        return telegramClientConfigParams.getName();
    }

    @Override
    public String getBotToken() {
        return telegramClientConfigParams.getToken();
    }
}

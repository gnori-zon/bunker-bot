package org.gnori.bunkerbot;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.gnori.bunkerbot.config.TelegramClientConfigParams;
import org.gnori.bunkerbot.controller.MessageTypeDistributor;
import org.gnori.bunkerbot.service.BotDependent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BunkerTelegramBot extends TelegramLongPollingBot {

    TelegramClientConfigParams telegramClientConfigParams;
    MessageTypeDistributor messageTypeDistributor;
    List<BotDependent> botDependents;

    public BunkerTelegramBot(
            TelegramClientConfigParams telegramClientConfigParams,
            MessageTypeDistributor messageTypeDistributor,
            List<BotDependent> botDependents
    ) {
        this.telegramClientConfigParams = telegramClientConfigParams;
        this.messageTypeDistributor = messageTypeDistributor;
        this.botDependents = botDependents;
    }

    @PostConstruct
    private void init() {
        botDependents.forEach(dependent -> dependent.registerBot(this));

        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(this);
            log.info("tg bot init: {}, {}", this.getBotToken(), this.getBotUsername());
        } catch (TelegramApiException e) {
            log.error("bad try register bot: {}", e.getLocalizedMessage());
        }

    }

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

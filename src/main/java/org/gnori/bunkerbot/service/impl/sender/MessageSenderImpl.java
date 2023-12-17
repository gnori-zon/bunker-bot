package org.gnori.bunkerbot.service.impl.sender;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.gnori.bunkerbot.service.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageSenderImpl implements MessageSender {

    Optional<? extends DefaultAbsSender> botOptional = Optional.empty();

    @Override
    public <T extends DefaultAbsSender> void registerBot(@NonNull T bot) {
        this.botOptional = Optional.of(bot);
    }

    @Override
    public Optional<Integer> sendMessage(@NonNull SendTextParams params) {

        return botOptional.flatMap(bot -> {
            try {

                final SendMessage sendMessage = sendMessageFrom(params);
                final Integer messageId = bot.execute(sendMessage).getMessageId();

                return Optional.of(messageId);

            } catch (TelegramApiException e) {
                log.warn("bad try send message, params: {}, ex: {}", params, e.getLocalizedMessage());
                return Optional.empty();
            }
        });
    }

    private SendMessage sendMessageFrom(@NonNull SendTextParams params) {

        final SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(params.chatId());
        sendMessage.setText(params.text());
        sendMessage.enableHtml(true);

        if (params.replyKeyboard() != null) {
            sendMessage.setReplyMarkup(params.replyKeyboard());
        }

        return sendMessage;
    }
}

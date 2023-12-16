package org.gnori.bunkerbot.service.impl.editor;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.gnori.bunkerbot.service.MessageEditor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageEditorImpl implements MessageEditor {

    Optional<? extends DefaultAbsSender> botOptional = Optional.empty();

    @Override
    public <B extends DefaultAbsSender> void registerBot(@NonNull B bot) {
        this.botOptional = Optional.of(bot);
    }

    @Override
    public Optional<Integer> editReplyMarkup(EditKeyboardMarkupParams params) {

        return botOptional.flatMap(bot -> {
            try {

                final EditMessageReplyMarkup editReplyMarkup = editReplyMarkupFrom(params);
                bot.execute(editReplyMarkup);

                return Optional.of(editReplyMarkup.getMessageId());

            } catch (TelegramApiException e) {
                log.warn("bad try send message, params: {}, ex: {}", params, e.getLocalizedMessage());
                return Optional.empty();
            }
        });


    }

    private EditMessageReplyMarkup editReplyMarkupFrom(EditKeyboardMarkupParams params) {

        return EditMessageReplyMarkup.builder()
                .chatId(params.chatId())
                .messageId(params.messageId())
                .replyMarkup(params.inlineKeyboardMarkup())
                .build();
    }
}

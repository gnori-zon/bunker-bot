package org.gnori.bunkerbot.controller.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.controller.MessageTypeDistributor;
import org.gnori.bunkerbot.service.command.Command;
import org.gnori.bunkerbot.service.command.CommandContainer;
import org.gnori.bunkerbot.service.command.impl.MessageType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageTypeDistributorImpl implements MessageTypeDistributor {

    CommandContainer<MessageType, Command<MessageType>> commandContainer;

    @Override
    public void distribute(Update update) {

        final MessageType messageType = detectType(update);
        commandContainer.retrieveCommand(messageType).execute(update);
    }

    private MessageType detectType(Update update) {

        if (update.hasCallbackQuery()) {
            return MessageType.CALLBACK;
        }

        if (update.hasMessage()) {
            return MessageType.TEXT;
        }

        return MessageType.UNSUPPORTED;
    }
}

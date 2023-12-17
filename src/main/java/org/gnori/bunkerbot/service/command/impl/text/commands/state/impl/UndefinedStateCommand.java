package org.gnori.bunkerbot.service.command.impl.text.commands.state.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.service.MessageSender;
import org.gnori.bunkerbot.service.command.impl.text.commands.state.StateCommand;
import org.gnori.bunkerbot.service.impl.domain.BotUserStateChanger;
import org.gnori.bunkerbot.service.impl.sender.SendTextParams;
import org.gnori.bunkerbot.service.keyboard.generator.KeyboardGeneratorContainer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UndefinedStateCommand implements StateCommand {

    MessageSender messageSender;
    KeyboardGeneratorContainer keyboardGeneratorContainer;
    BotUserStateChanger botUserStateChanger;

    @Override
    public void execute(Update update) {

        final long chatId = update.getMessage().getChatId();

        final SendTextParams sendTextParams = SendTextParams.builder()
                .chatId(chatId)
                .text("Извините, такой команды не знаю")
                .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.DEFAULT))
                .build();

        botUserStateChanger.changeState(chatId, BotUserState.DEFAULT);
        messageSender.sendMessage(sendTextParams);
    }

    @Override
    public BotUserState getSupportedKey() {
        return BotUserState.UNDEFINED;
    }
}

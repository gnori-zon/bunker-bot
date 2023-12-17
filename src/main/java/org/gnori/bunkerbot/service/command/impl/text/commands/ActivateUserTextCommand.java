package org.gnori.bunkerbot.service.command.impl.text.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.service.MessageSender;
import org.gnori.bunkerbot.service.command.constants.ResponseConst;
import org.gnori.bunkerbot.service.command.impl.text.TextCommand;
import org.gnori.bunkerbot.service.command.impl.text.TextCommandKey;
import org.gnori.bunkerbot.service.impl.domain.BotUserStateChanger;
import org.gnori.bunkerbot.service.impl.sender.SendTextParams;
import org.gnori.bunkerbot.service.keyboard.generator.KeyboardGeneratorContainer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActivateUserTextCommand implements TextCommand {

    MessageSender messageSender;
    BotUserStateChanger botUserStateChanger;
    KeyboardGeneratorContainer keyboardGeneratorContainer;

    @Override
    public void execute(Update update) {

        final long chatId = update.getMessage().getChatId();

        final SendTextParams sendTextParams = SendTextParams.builder()
                .chatId(chatId)
                .text(ResponseConst.WAIT_INPUT_ID)
                .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.WAITING_ACTIVATE_ID))
                .build();

        messageSender.sendMessage(sendTextParams);
        botUserStateChanger.changeState(chatId, BotUserState.WAITING_ACTIVATE_ID);
    }

    @Override
    public TextCommandKey getSupportedKey() {
        return TextCommandKey.ACTIVATE_USER;
    }
}

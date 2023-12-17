package org.gnori.bunkerbot.service.keyboard.generator.impl.generators;

import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommandKey;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class KeyboardGeneratorForWaitingDeleteId extends BaseKeyboardGeneratorForState {

    @Override
    public ReplyKeyboard generate() {

        final InlineKeyboardButton skipDeleteButton = new InlineKeyboardButton("skip delete");
        skipDeleteButton.setCallbackData(CallbackCommandKey.SKIP_DELETE_USER.createCallbackData());

        return new InlineKeyboardMarkup(List.of(List.of(skipDeleteButton)));
    }

    @Override
    public BotUserState getSupportedState() {
        return BotUserState.WAITING_DELETE_ID;
    }
}

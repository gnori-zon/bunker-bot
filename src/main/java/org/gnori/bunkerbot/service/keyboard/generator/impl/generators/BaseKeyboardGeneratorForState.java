package org.gnori.bunkerbot.service.keyboard.generator.impl.generators;

import org.gnori.bunkerbot.service.command.impl.text.TextCommandKey;
import org.gnori.bunkerbot.service.keyboard.generator.KeyboardGeneratorForState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public abstract class BaseKeyboardGeneratorForState implements KeyboardGeneratorForState {

    protected KeyboardButton defaultButtonOf(TextCommandKey instanceKey) {
        return new KeyboardButton(instanceKey.getKey());
    }

    protected ReplyKeyboardMarkup withCancel() {

        final KeyboardButton cancelButton = defaultButtonOf(TextCommandKey.CANCEL);
        final KeyboardRow top = new KeyboardRow(List.of(cancelButton));

        final ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup(List.of(top));
        replyKeyboard.setResizeKeyboard(true);

        return replyKeyboard;
    }
}

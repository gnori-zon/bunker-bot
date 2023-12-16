package org.gnori.bunkerbot.service.keyboard.generator.impl.generators;

import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.service.command.impl.text.TextCommandKey;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class KeyboardGeneratorForDefault extends BaseKeyboardGeneratorForState {

    @Override
    public ReplyKeyboard generate() {

        final KeyboardButton showAllButton = defaultButtonOf(TextCommandKey.SHOW_ALL);
        final KeyboardButton showActiveButton = defaultButtonOf(TextCommandKey.SHOW_ACTIVE);
        final KeyboardRow top = new KeyboardRow(List.of(showAllButton, showActiveButton));


        final KeyboardButton activateUserButton = defaultButtonOf(TextCommandKey.ACTIVATE_USER);
        final KeyboardButton deactivateUserButton = defaultButtonOf(TextCommandKey.DEACTIVATE_USER);
        final KeyboardRow mid = new KeyboardRow(List.of(activateUserButton, deactivateUserButton));

        final KeyboardButton startGameButton = defaultButtonOf(TextCommandKey.START_GAME);
        final KeyboardRow bottom = new KeyboardRow(List.of(startGameButton));

        final ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup(List.of(top, mid, bottom));
        replyKeyboard.setResizeKeyboard(true);

        return replyKeyboard;
    }

    @Override
    public BotUserState getSupportedState() {
        return BotUserState.DEFAULT;
    }
}

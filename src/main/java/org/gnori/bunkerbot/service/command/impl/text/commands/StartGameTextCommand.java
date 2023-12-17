package org.gnori.bunkerbot.service.command.impl.text.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.service.BunkerGame;
import org.gnori.bunkerbot.service.MessageSender;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommandKey;
import org.gnori.bunkerbot.service.command.impl.text.TextCommand;
import org.gnori.bunkerbot.service.command.impl.text.TextCommandKey;
import org.gnori.bunkerbot.service.impl.sender.SendTextParams;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StartGameTextCommand implements TextCommand {

    MessageSender messageSender;
    BunkerGame bunkerGame;

    @Override
    public void execute(Update update) {

        final long chatId = update.getMessage().getChatId();
        final String text = "- <b>START GAME</b> -";

        final SendTextParams sendTextParams = SendTextParams.builder()
                .chatId(chatId)
                .text(text)
                .replyKeyboard(generateStepInlineButtons())
                .build();

        messageSender.sendMessage(sendTextParams);
        bunkerGame.startGame();
    }

    @Override
    public TextCommandKey getSupportedKey() {
        return TextCommandKey.START_GAME;
    }

    private ReplyKeyboard generateStepInlineButtons() {

        final InlineKeyboardButton nextStepButton = new InlineKeyboardButton("next step");
        nextStepButton.setCallbackData(CallbackCommandKey.NEXT_STEP_GAME.createCallbackData());

        final InlineKeyboardButton stopGameButton = new InlineKeyboardButton("stop game");
        stopGameButton.setCallbackData(CallbackCommandKey.STOP_GAME.createCallbackData());

        return new InlineKeyboardMarkup(List.of(List.of(nextStepButton, stopGameButton)));
    }
}

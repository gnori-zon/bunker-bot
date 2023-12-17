package org.gnori.bunkerbot.service.command.impl.text.commands.state.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.service.BunkerGame;
import org.gnori.bunkerbot.service.MessageEditor;
import org.gnori.bunkerbot.service.MessageSender;
import org.gnori.bunkerbot.service.command.constants.ResponseConst;
import org.gnori.bunkerbot.service.command.impl.text.commands.state.StateCommand;
import org.gnori.bunkerbot.service.command.impl.text.commands.state.StateCommandUtils;
import org.gnori.bunkerbot.service.impl.domain.BotUserActiveChanger;
import org.gnori.bunkerbot.service.impl.domain.BotUserStateChanger;
import org.gnori.bunkerbot.service.impl.editor.EditKeyboardMarkupParams;
import org.gnori.bunkerbot.service.impl.sender.SendTextParams;
import org.gnori.bunkerbot.service.keyboard.generator.KeyboardGeneratorContainer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NextStepGameStateCommand implements StateCommand {

    BunkerGame bunkerGame;
    MessageSender messageSender;
    BotUserStateChanger botUserStateChanger;
    BotUserActiveChanger botUserActiveChanger;
    KeyboardGeneratorContainer keyboardGeneratorContainer;

    @Override
    public void execute(Update update) {

        final long chatId = update.getMessage().getChatId();
        final String userIdRaw = update.getMessage().getText();

        StateCommandUtils.extractId(userIdRaw).ifPresentOrElse(
                userId -> {
                    deactivateUserAndChangeState(userId, chatId);
                    bunkerGame.deleteUser(userId);
                    bunkerGame.nextStep();
                },
                () -> sendInvalidId(chatId)
        );
    }

    @Override
    public BotUserState getSupportedKey() {
        return BotUserState.WAITING_DELETE_ID;
    }

    private void sendInvalidId(long chatId) {

        final SendTextParams sendTextParams = SendTextParams.builder()
                .chatId(chatId)
                .text(ResponseConst.INVALID_ID_INPUT)
                .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.WAITING_DELETE_ID))
                .build();

        messageSender.sendMessage(sendTextParams);
    }

    private void deactivateUserAndChangeState(Long userId, long chatId) {

        botUserStateChanger.changeState(chatId, BotUserState.DEFAULT);
        boolean isChanged = botUserActiveChanger.changeActive(userId, false);

        final String text = isChanged
                ? ResponseConst.DEACTIVATE_USER_SUCCESS
                : String.format(ResponseConst.FIND_USER_FAILURE_PATTERN, userId);

        final SendTextParams sendParams = SendTextParams.builder()
                .chatId(chatId)
                .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.DEFAULT))
                .text(text)
                .build();

        messageSender.sendMessage(sendParams);
    }
}

package org.gnori.bunkerbot.service.command.impl.text.commands.state.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.service.MessageSender;
import org.gnori.bunkerbot.service.command.constants.ResponseConst;
import org.gnori.bunkerbot.service.command.impl.text.commands.state.StateCommand;
import org.gnori.bunkerbot.service.command.impl.text.commands.state.StateCommandUtils;
import org.gnori.bunkerbot.service.impl.domain.BotUserActiveChanger;
import org.gnori.bunkerbot.service.impl.domain.BotUserStateChanger;
import org.gnori.bunkerbot.service.impl.sender.SendTextParams;
import org.gnori.bunkerbot.service.keyboard.generator.KeyboardGeneratorContainer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActivateUserStateCommand implements StateCommand {

    MessageSender messageSender;
    BotUserStateChanger botUserStateChanger;
    BotUserActiveChanger botUserActiveChanger;
    KeyboardGeneratorContainer keyboardGeneratorContainer;

    @Override
    public void execute(Update update) {

        final long chatId = update.getMessage().getChatId();
        final String userIdRawForActivate = update.getMessage().getText().trim();

        StateCommandUtils.extractId(userIdRawForActivate).ifPresentOrElse(
                userId -> activateUserAndChangeState(userId, chatId),
                () -> sendInvalidId(chatId)
        );
    }

    private void sendInvalidId(long chatId) {

        final SendTextParams sendTextParams = SendTextParams.builder()
                .chatId(chatId)
                .text(ResponseConst.INVALID_ID_INPUT)
                .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.WAITING_ACTIVATE_ID))
                .build();

        messageSender.sendMessage(sendTextParams);
    }

    private void activateUserAndChangeState(Long userId, long chatId) {

        botUserStateChanger.changeState(chatId, BotUserState.DEFAULT);
        boolean isChanged = botUserActiveChanger.changeActive(userId, true);

        final String text = isChanged
                ? ResponseConst.ACTIVATE_USER_SUCCESS
                : String.format(ResponseConst.FIND_USER_FAILURE_PATTERN, userId);

        final SendTextParams sendParams = SendTextParams.builder()
                .chatId(chatId)
                .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.DEFAULT))
                .text(text)
                .build();

        messageSender.sendMessage(sendParams);
    }

    @Override
    public BotUserState getSupportedKey() {
        return BotUserState.WAITING_ACTIVATE_ID;
    }
}

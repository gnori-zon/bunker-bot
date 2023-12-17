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
public class DeactivateUserStateCommand implements StateCommand {

    MessageSender messageSender;
    BotUserStateChanger botUserStateChanger;
    BotUserActiveChanger botUserActiveChanger;
    KeyboardGeneratorContainer keyboardGeneratorContainer;

    @Override
    public void execute(Update update) {

        final long chatId = update.getMessage().getChatId();
        final String userIdRawForActivate = update.getMessage().getText().trim();

        StateCommandUtils.extractId(userIdRawForActivate).ifPresentOrElse(
                userId -> deactivateUserAndChangeState(userId, chatId),
                () -> sendInvalidId(chatId)
        );
    }

    @Override
    public BotUserState getSupportedKey() {
        return BotUserState.WAITING_DEACTIVATE_ID;
    }

    private void sendInvalidId(long chatId) {

        final SendTextParams sendTextParams = SendTextParams.builder()
                .chatId(chatId)
                .text(ResponseConst.INVALID_ID_INPUT)
                .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.WAITING_DEACTIVATE_ID))
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
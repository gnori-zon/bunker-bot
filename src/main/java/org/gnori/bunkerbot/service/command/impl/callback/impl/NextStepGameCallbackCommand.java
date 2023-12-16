package org.gnori.bunkerbot.service.command.impl.callback.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.service.MessageEditor;
import org.gnori.bunkerbot.service.MessageSender;
import org.gnori.bunkerbot.service.command.constants.ResponseConst;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommandKey;
import org.gnori.bunkerbot.service.impl.sender.SendTextParams;
import org.gnori.bunkerbot.service.keyboard.generator.KeyboardGeneratorContainer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NextStepGameCallbackCommand extends BaseCallbackCommand {

    MessageSender messageSender;
    KeyboardGeneratorContainer keyboardGeneratorContainer;

    public NextStepGameCallbackCommand(
            MessageEditor messageEditor,
            MessageSender messageSender,
            KeyboardGeneratorContainer keyboardGeneratorContainer
    ) {
        super(messageEditor);
        this.messageSender = messageSender;
        this.keyboardGeneratorContainer = keyboardGeneratorContainer;
    }

    @Override
    public void execute(Update update) {

        final long chatId = update.getCallbackQuery().getMessage().getChatId();
        clearInlineKeyboard(update);

        final SendTextParams sendTextParams = SendTextParams.builder()
                .chatId(chatId)
                .text(ResponseConst.WAIT_INPUT_ID_FOR_DELETE)
                .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.WAITING_DELETE_ID))
                .build();

        messageSender.sendMessage(sendTextParams);
    }

    @Override
    public CallbackCommandKey getSupportedKey() {
        return CallbackCommandKey.NEXT_STEP_GAME;
    }
}

package org.gnori.bunkerbot.service.keyboard.generator.impl.generators;

import org.gnori.bunkerbot.domain.BotUserState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class KeyboardGeneratorForWaitingDeactivateId extends BaseKeyboardGeneratorForState {

    @Override
    public ReplyKeyboard generate() {
        return withCancel();
    }

    @Override
    public BotUserState getSupportedState() {
        return BotUserState.WAITING_DEACTIVATE_ID;
    }
}

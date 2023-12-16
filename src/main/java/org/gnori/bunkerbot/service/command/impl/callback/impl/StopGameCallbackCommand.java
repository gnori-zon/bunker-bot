package org.gnori.bunkerbot.service.command.impl.callback.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.service.MessageEditor;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommandKey;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StopGameCallbackCommand extends BaseCallbackCommand {

    public StopGameCallbackCommand(MessageEditor messageEditor) {
        super(messageEditor);
    }

    @Override
    public void execute(Update update) {
        clearInlineKeyboard(update);
        // todo: stop game
    }

    @Override
    public CallbackCommandKey getSupportedKey() {
        return CallbackCommandKey.STOP_GAME;
    }
}

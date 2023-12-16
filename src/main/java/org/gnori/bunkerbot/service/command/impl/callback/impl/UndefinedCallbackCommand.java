package org.gnori.bunkerbot.service.command.impl.callback.impl;

import lombok.extern.slf4j.Slf4j;
import org.gnori.bunkerbot.service.MessageEditor;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommandKey;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class UndefinedCallbackCommand extends BaseCallbackCommand {

    public UndefinedCallbackCommand(MessageEditor messageEditor) {
        super(messageEditor);
    }

    @Override
    public void execute(Update update) {

        clearInlineKeyboard(update);
        log.warn("process undefined command");
    }

    @Override
    public CallbackCommandKey getSupportedKey() {
        return CallbackCommandKey.UNDEFINED;
    }
}

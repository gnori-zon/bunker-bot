package org.gnori.bunkerbot.service.command.impl.callback.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.service.BunkerGame;
import org.gnori.bunkerbot.service.MessageEditor;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommandKey;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StopGameCallbackCommand extends BaseCallbackCommand {

    BunkerGame bunkerGame;

    public StopGameCallbackCommand(
            MessageEditor messageEditor,
            BunkerGame bunkerGame
    ) {
        super(messageEditor);
        this.bunkerGame = bunkerGame;
    }

    @Override
    public void execute(Update update) {
        clearInlineKeyboard(update);
        bunkerGame.stopGame();
    }

    @Override
    public CallbackCommandKey getSupportedKey() {
        return CallbackCommandKey.STOP_GAME;
    }
}

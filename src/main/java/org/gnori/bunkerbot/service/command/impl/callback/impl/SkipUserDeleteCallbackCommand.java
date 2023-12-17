package org.gnori.bunkerbot.service.command.impl.callback.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.repository.BotUserRepository;
import org.gnori.bunkerbot.service.BunkerGame;
import org.gnori.bunkerbot.service.MessageEditor;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommandKey;
import org.gnori.bunkerbot.service.command.impl.text.commands.state.impl.NextStepGameStateCommand;
import org.gnori.bunkerbot.service.impl.domain.BotUserStateChanger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SkipUserDeleteCallbackCommand extends BaseCallbackCommand {

    BotUserStateChanger botUserStateChanger;
    BunkerGame bunkerGame;

    public SkipUserDeleteCallbackCommand(
            BunkerGame bunkerGame,
            MessageEditor messageEditor,
            BotUserStateChanger botUserStateChanger
    ) {
        super(messageEditor);
        this.bunkerGame = bunkerGame;
        this.botUserStateChanger = botUserStateChanger;
    }

    @Override
    public void execute(Update update) {

        final long chatId = update.getCallbackQuery().getMessage().getChatId();
        clearInlineKeyboard(update);

        botUserStateChanger.changeState(chatId, BotUserState.DEFAULT);
        bunkerGame.nextStep();
    }

    @Override
    public CallbackCommandKey getSupportedKey() {
        return CallbackCommandKey.SKIP_DELETE_USER;
    }
}

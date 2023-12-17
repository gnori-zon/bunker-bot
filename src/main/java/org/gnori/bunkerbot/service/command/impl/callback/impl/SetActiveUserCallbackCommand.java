package org.gnori.bunkerbot.service.command.impl.callback.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.service.MessageEditor;
import org.gnori.bunkerbot.service.MessageSender;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommand;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommandKey;
import org.gnori.bunkerbot.service.impl.domain.BotUserActiveChanger;
import org.gnori.bunkerbot.service.impl.sender.SendTextParams;
import org.gnori.bunkerbot.service.keyboard.generator.KeyboardGeneratorContainer;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SetActiveUserCallbackCommand extends BaseCallbackCommand {

    MessageSender messageSender;
    BotUserActiveChanger botUserActiveChanger;
    KeyboardGeneratorContainer keyboardGeneratorContainer;

    public SetActiveUserCallbackCommand(
            MessageEditor messageEditor,
            MessageSender messageSender,
            BotUserActiveChanger botUserActiveChanger,
            KeyboardGeneratorContainer keyboardGeneratorContainer
    ) {
        super(messageEditor);
        this.messageSender = messageSender;
        this.botUserActiveChanger = botUserActiveChanger;
        this.keyboardGeneratorContainer = keyboardGeneratorContainer;
    }

    @Override
    public void execute(Update update) {

        final long chatId = update.getCallbackQuery().getMessage().getChatId();
        clearInlineKeyboard(update);
        extractCallbackData(update).ifPresent(userIdAndActive -> {

            boolean isChanged = botUserActiveChanger.changeActive(userIdAndActive.getFirst(), userIdAndActive.getSecond());

            final String text = isChanged
                    ? "Успешно выполнено"
                    : "Упс, неизвесьная ошибка";

            final SendTextParams sendTextParams = SendTextParams.builder()
                    .chatId(chatId)
                    .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.DEFAULT))
                    .text(text)
                    .build();

            messageSender.sendMessage(sendTextParams);
        });
    }

    @Override
    public CallbackCommandKey getSupportedKey() {
        return CallbackCommandKey.SET_ACTIVE_USER;
    }

    private Optional<Pair<Long, Boolean>> extractCallbackData(Update update) {

        return Optional.ofNullable(update.getCallbackQuery())
                .map(CallbackQuery::getData)
                .flatMap(data -> {

                    final String[] dataArgs = data.split(CallbackCommandKey.DELIMITER);

                    if (dataArgs.length > 2) {

                        final String userIdRaw = dataArgs[1].trim();
                        final String activeRaw = dataArgs[2].trim();
                        try {
                            if (!("true".equals(activeRaw) || "false".equals(activeRaw))) {
                                throw new IllegalArgumentException();
                            }

                            final Long userId = Long.valueOf(userIdRaw);
                            final Boolean isActive = Boolean.valueOf(activeRaw);

                            return Optional.of(Pair.of(userId, isActive));
                        } catch (NumberFormatException e) {
                            log.warn("bad try set active user: {}", e.getLocalizedMessage());
                        }
                    }
                    return Optional.empty();
                });
    }
}

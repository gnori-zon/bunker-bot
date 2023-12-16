package org.gnori.bunkerbot.service.command.impl.text.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.BotUser;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.repository.BotUserRepository;
import org.gnori.bunkerbot.service.MessageSender;
import org.gnori.bunkerbot.service.command.constants.ResponseConst;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommandKey;
import org.gnori.bunkerbot.service.command.impl.text.TextCommand;
import org.gnori.bunkerbot.service.command.impl.text.TextCommandKey;
import org.gnori.bunkerbot.service.impl.domain.BotUserUsernameChanger;
import org.gnori.bunkerbot.service.impl.sender.SendTextParams;
import org.gnori.bunkerbot.service.keyboard.generator.KeyboardGeneratorContainer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StartTextCommand implements TextCommand {

    MessageSender messageSender;
    BotUserUsernameChanger botUserUsernameChanger;
    BotUserRepository botUserRepository;
    KeyboardGeneratorContainer keyboardGeneratorContainer;

    @Override
    public void execute(Update update) {

        final long chatId = update.getMessage().getChatId();
        final String newUsername = update.getMessage().getFrom().getUserName();

        boolean isChanged = botUserUsernameChanger.changeUsername(chatId, newUsername);

        if (isChanged) {
            sendAdminKeyboard(chatId);
        } else {
            BotUser newBotUser = BotUser.builder()
                    .chatId(chatId)
                    .username(newUsername)
                    .build();

            newBotUser = botUserRepository.saveAndFlush(newBotUser);

            sendOnVerification(newBotUser);
        }
    }

    @Override
    public TextCommandKey getSupportedKey() {
        return TextCommandKey.START;
    }

    private void sendOnVerification(BotUser newBotUser) {

        final SendTextParams sendTextParams = SendTextParams.builder()
                .text(String.format(
                        ResponseConst.RESOLVE_ACTIVITY_USER_PATTERN,
                        newBotUser.getId(), newBotUser.getUsername()
                ))
                .replyKeyboard(createReplyKeyboard(newBotUser))
                .build();


        botUserRepository.findAllAdmin().forEach(adminUser -> {

            final SendTextParams params = sendTextParams.with(adminUser.getChatId());
            messageSender.sendMessage(params);
        });

    }

    private void sendAdminKeyboard(long chatId) {

        final SendTextParams params = SendTextParams.builder()
                .chatId(chatId)
                .text(ResponseConst.START_ADMIN)
                .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.DEFAULT))
                .build();

        messageSender.sendMessage(params);
    }

    private ReplyKeyboard createReplyKeyboard(BotUser newBotUser) {

        final String userId = String.valueOf(newBotUser.getId());

        final InlineKeyboardButton addButton = new InlineKeyboardButton("Да");
        addButton.setCallbackData(CallbackCommandKey.SET_ACTIVE_USER.createCallbackData(userId, "true"));

        final InlineKeyboardButton cancelButton = new InlineKeyboardButton("Нет");
        cancelButton.setCallbackData(CallbackCommandKey.SET_ACTIVE_USER.createCallbackData(userId, "false"));

        return new InlineKeyboardMarkup(List.of(List.of(addButton, cancelButton)));
    }
}

package org.gnori.bunkerbot.service.command.impl.text.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.BotUser;
import org.gnori.bunkerbot.repository.BotUserRepository;
import org.gnori.bunkerbot.service.MessageSender;
import org.gnori.bunkerbot.service.command.constants.ResponseConst;
import org.gnori.bunkerbot.service.command.impl.text.TextCommand;
import org.gnori.bunkerbot.service.command.impl.text.TextCommandKey;
import org.gnori.bunkerbot.service.impl.sender.SendTextParams;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShowActiveTextCommand implements TextCommand {

    MessageSender messageSender;
    BotUserRepository botUserRepository;

    @Override
    public void execute(Update update) {

        final long chatId = update.getMessage().getChatId();
        final List<BotUser> botUsers = botUserRepository.findAllByIsActive(true);

        if (botUsers.isEmpty()) {
            sendNotifyEmpty(chatId);
            return;
        }

        botUsers.forEach(botUser -> sendUserInfo(chatId, botUser));
    }

    @Override
    public TextCommandKey getSupportedKey() {
        return TextCommandKey.SHOW_ACTIVE;
    }


    private void sendNotifyEmpty(long chatId) {

        final SendTextParams sendTextParams = SendTextParams.builder()
                .chatId(chatId)
                .text(ResponseConst.EMPTY_LIST)
                .build();

        messageSender.sendMessage(sendTextParams);
    }

    private void sendUserInfo(long chatId, BotUser botUser) {

        final String userInfo = String.format(
                ResponseConst.SHORT_USER_INFO_PATTERN,
                botUser.getId(), botUser.getUsername()
        );

        final SendTextParams sendTextParams = SendTextParams.builder()
                .chatId(chatId)
                .text(userInfo)
                .build();

        messageSender.sendMessage(sendTextParams);
    }
}

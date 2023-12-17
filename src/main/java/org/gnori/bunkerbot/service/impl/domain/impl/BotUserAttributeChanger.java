package org.gnori.bunkerbot.service.impl.domain.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.repository.BotUserRepository;
import org.gnori.bunkerbot.service.impl.domain.BotUserActiveChanger;
import org.gnori.bunkerbot.service.impl.domain.BotUserStateChanger;
import org.gnori.bunkerbot.service.impl.domain.BotUserUsernameChanger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BotUserAttributeChanger
        implements BotUserActiveChanger, BotUserStateChanger, BotUserUsernameChanger {

    BotUserRepository botUserRepository;

    @Override
    public boolean changeActive(Long userId, boolean isActive) {

        return botUserRepository.findById(userId)
                .map(botUser -> {

                    botUser.setActive(isActive);
                    botUserRepository.saveAndFlush(botUser);

                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean changeState(Long chatId, BotUserState newState) {

        return botUserRepository.findByChatId(chatId)
                .map(botUser -> {

                    botUser.setState(newState);
                    botUserRepository.saveAndFlush(botUser);

                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean changeUsername(Long chatId, String newUsername) {

        return botUserRepository.findByChatId(chatId)
                .map(botUser -> {

                    botUser.setUsername(newUsername);
                    botUserRepository.saveAndFlush(botUser);

                    return true;
                })
                .orElse(false);
    }
}

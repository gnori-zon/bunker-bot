package org.gnori.bunkerbot.service.impl.permission;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.gnori.bunkerbot.domain.BotUser;
import org.gnori.bunkerbot.repository.BotUserRepository;
import org.gnori.bunkerbot.service.PermissionChecker;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionCheckerImpl implements PermissionChecker {

    BotUserRepository botUserRepository;

    @Override
    public boolean hasPermission(Update update) {

        return extractChatId(update)
                .flatMap(botUserRepository::findByChatId)
                .map(BotUser::isAdmin)
                .orElseGet(() -> "/start".equals(extractText(update)));
    }

    private String extractText(Update update) {

        try {
            return update.getMessage().getText();
        } catch (Exception e) {
            log.info("bad try extract text");
            return null;
        }
    }

    private Optional<Long> extractChatId(Update update) {
        try {

            if (update.hasMessage()) {
                return Optional.of(update.getMessage().getChatId());
            }

            if (update.hasCallbackQuery()) {
                return Optional.of(update.getCallbackQuery().getMessage().getChatId());
            }

        } catch (Exception e) {
            log.info("unexpected state in permissionChecker: {}", e.getLocalizedMessage());
        }

        return Optional.empty();
    }
}

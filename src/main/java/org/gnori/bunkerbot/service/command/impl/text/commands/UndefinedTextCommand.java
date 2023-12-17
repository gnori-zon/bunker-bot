package org.gnori.bunkerbot.service.command.impl.text.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.BotUser;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.repository.BotUserRepository;
import org.gnori.bunkerbot.service.MessageSender;
import org.gnori.bunkerbot.service.command.impl.text.TextCommand;
import org.gnori.bunkerbot.service.command.impl.text.TextCommandKey;
import org.gnori.bunkerbot.service.command.impl.text.commands.state.StateCommand;
import org.gnori.bunkerbot.service.impl.sender.SendTextParams;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UndefinedTextCommand implements TextCommand {

    BotUserRepository botUserRepository;
    Map<BotUserState, StateCommand> commands;

    public UndefinedTextCommand(
            BotUserRepository botUserRepository,
            List<StateCommand> commands
    ) {
        this.botUserRepository = botUserRepository;
        this.commands = commands.stream()
                .collect(Collectors.toMap(StateCommand::getSupportedKey, Function.identity()));
    }

    @Override
    public void execute(Update update) {

        final long chatId = update.getMessage().getChatId();

        botUserRepository.findByChatId(chatId).ifPresent(botUser -> {

            Optional.ofNullable(commands.get(botUser.getState()))
                    .orElse(commands.get(BotUserState.UNDEFINED))
                    .execute(update);
        });
    }

    @Override
    public TextCommandKey getSupportedKey() {
        return TextCommandKey.UNDEFINED;
    }
}

package org.gnori.bunkerbot.service.command.impl.text;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.service.command.impl.MessageType;
import org.gnori.bunkerbot.service.command.impl.MessageTypeCommandContainer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TextCommandContainer implements MessageTypeCommandContainer {

    Map<TextCommandKey, TextCommand> commands;

    public TextCommandContainer(List<TextCommand> commands) {

        this.commands = commands.stream()
                .collect(Collectors.toMap(TextCommand::getSupportedKey, Function.identity()));
    }

    @Override
    public void execute(Update update) {

        final TextCommandKey commandKey = detectKey(update);
        commands.get(commandKey).execute(update);
    }

    @Override
    public MessageType getSupportedKey() {
        return MessageType.TEXT;
    }

    private TextCommandKey detectKey(Update update) {

        return Optional.ofNullable(update.getMessage())
                .map(Message::getText)
                .flatMap(TextCommandKey::of)
                .orElse(TextCommandKey.UNDEFINED);
    }
}

package org.gnori.bunkerbot.service.command.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.service.command.Command;
import org.gnori.bunkerbot.service.command.CommandContainer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommandContainerImpl implements CommandContainer<MessageType, Command<MessageType>> {

    Map<MessageType, Command<MessageType>> commands;

    public CommandContainerImpl(List<Command<MessageType>> commands) {

        this.commands = commands.stream()
                .collect(Collectors.toMap(Command::getSupportedKey, Function.identity()));
    }

    @Override
    public @NonNull Command<MessageType> retrieveCommand(MessageType key) {
        return commands.get(key);
    }
}

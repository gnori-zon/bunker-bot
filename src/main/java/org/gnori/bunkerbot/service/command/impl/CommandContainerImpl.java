package org.gnori.bunkerbot.service.command.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.service.command.Command;
import org.gnori.bunkerbot.service.command.CommandContainer;
import org.gnori.bunkerbot.service.command.impl.text.TextCommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommandContainerImpl implements CommandContainer<MessageType, Command<MessageType>> {

    Map<MessageType, MessageTypeCommandContainer> containers;

    public CommandContainerImpl(List<MessageTypeCommandContainer> containers) {

        this.containers = containers.stream()
                .collect(Collectors.toMap(MessageTypeCommandContainer::getSupportedKey, Function.identity()));
    }

    @Override
    public @NonNull Command<MessageType> retrieveCommand(MessageType key) {

        return containers.get(key);
    }
}

package org.gnori.bunkerbot.service.command.impl.callback;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.service.command.impl.MessageType;
import org.gnori.bunkerbot.service.command.impl.MessageTypeCommandContainer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CallbackCommandContainer implements MessageTypeCommandContainer {

    Map<CallbackCommandKey, CallbackCommand> commands;

    public CallbackCommandContainer(List<CallbackCommand> commands) {
        this.commands = commands.stream()
                .collect(Collectors.toMap(CallbackCommand::getSupportedKey, Function.identity()));
    }

    @Override
    public void execute(Update update) {

        final CallbackCommandKey commandKey = detectKey(update);
        commands.get(commandKey).execute(update);
    }

    @Override
    public MessageType getSupportedKey() {
        return MessageType.CALLBACK;
    }

    private CallbackCommandKey detectKey(Update update) {

        return Optional.ofNullable(update.getCallbackQuery())
                .map(CallbackQuery::getData)
                .flatMap(CallbackCommandKey::of)
                .orElse(CallbackCommandKey.UNDEFINED);
    }
}

package org.gnori.bunkerbot.service.keyboard.generator.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.service.keyboard.generator.KeyboardGeneratorContainer;
import org.gnori.bunkerbot.service.keyboard.generator.KeyboardGeneratorForState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KeyboardGeneratorContainerImpl implements KeyboardGeneratorContainer {

    Map<BotUserState, KeyboardGeneratorForState> generators;

    public KeyboardGeneratorContainerImpl(List<KeyboardGeneratorForState> generators) {
        this.generators = generators.stream()
                .collect(Collectors.toMap(KeyboardGeneratorForState::getSupportedState, Function.identity()));
    }

    @Override
    public ReplyKeyboard generateForState(BotUserState state) {

        return generators.get(state).generate();
    }
}

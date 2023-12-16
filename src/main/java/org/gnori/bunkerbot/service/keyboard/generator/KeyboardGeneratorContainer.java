package org.gnori.bunkerbot.service.keyboard.generator;

import org.gnori.bunkerbot.domain.BotUserState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface KeyboardGeneratorContainer {

    ReplyKeyboard generateForState(BotUserState state);
}

package org.gnori.bunkerbot.service;

import org.gnori.bunkerbot.service.impl.editor.EditKeyboardMarkupParams;

import java.util.Optional;

public interface MessageEditor extends BotDependent {
    <T extends EditKeyboardMarkupParams> Optional<Integer> editReplyMarkup(T editParams);
}

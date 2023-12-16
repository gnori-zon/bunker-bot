package org.gnori.bunkerbot.service.impl.editor;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Builder
public record EditKeyboardMarkupParams(
        Long chatId,
        Integer messageId,
        InlineKeyboardMarkup inlineKeyboardMarkup
) {
}

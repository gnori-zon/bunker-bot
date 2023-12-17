package org.gnori.bunkerbot.service.command.impl.callback.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.gnori.bunkerbot.service.MessageEditor;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommand;
import org.gnori.bunkerbot.service.impl.editor.EditKeyboardMarkupParams;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class BaseCallbackCommand implements CallbackCommand {

    MessageEditor messageEditor;

    protected void clearInlineKeyboard(Update update) {

        final Long chatId = update.getCallbackQuery().getMessage().getChatId();
        final Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        final EditKeyboardMarkupParams editParams = EditKeyboardMarkupParams.builder()
                .chatId(chatId)
                .messageId(messageId)
                .inlineKeyboardMarkup(new InlineKeyboardMarkup(List.of(List.of())))
                .build();

        messageEditor.editReplyMarkup(editParams);
    }
}

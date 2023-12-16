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
        final String text = update.getCallbackQuery().getMessage().getText();


        final EditKeyboardMarkupParams editParams = EditKeyboardMarkupParams.builder()
                .chatId(chatId)
                .messageId(messageId)
                .inlineKeyboardMarkup(new InlineKeyboardMarkup(List.of(List.of())))
                .build();

        messageEditor.editReplyMarkup(editParams);

//        if (text != null && !text.isEmpty()) {
//            final EditTextParams editTextParams = prepareEditTextWithoutKeyboardMarkup(update);
//            editMessageService.editMessage(editTextParams);
//        } else {
//            final EditKeyboardMarkupParams editKeyboardMarkupParams = prepareEditKeyboardWithoutKeyboardMarkup(update);
//
//            editMessageService.editReplyMarkup(editKeyboardMarkupParams);
//        }
    }
//
//    private EditTextParams prepareEditTextWithoutKeyboardMarkup(Update update) {
//        final Message messageFromCallback = update.getCallbackQuery().getMessage();
//        final Long chatId = messageFromCallback.getChatId();
//        final Integer oldMessageId = messageFromCallback.getMessageId();
//        final String text = messageFromCallback.getText();
//
//        return EditTextParams.builder()
//                .chatId(chatId)
//                .logActivityData(new LogActivityData(update))
//                .messageId(oldMessageId)
//                .text(text)
//                .inlineKeyboardMarkup(new InlineKeyboardMarkup(Collections.emptyList()))
//                .build();
//    }
//
//    private EditKeyboardMarkupParams prepareEditKeyboardWithoutKeyboardMarkup(Update update) {
//        final Message messageFromCallback = update.getCallbackQuery().getMessage();
//        final Long chatId = messageFromCallback.getChatId();
//        final Integer oldMessageId = messageFromCallback.getMessageId();
//
//        return EditKeyboardMarkupParams.builder()
//                .chatId(chatId)
//                .messageId(oldMessageId)
//                .inlineKeyboardMarkup(new InlineKeyboardMarkup(Collections.emptyList()))
//                .build();
//    }
}

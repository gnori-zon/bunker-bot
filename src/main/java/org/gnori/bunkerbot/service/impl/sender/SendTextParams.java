package org.gnori.bunkerbot.service.impl.sender;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Builder
public record SendTextParams(
        Long chatId,
        String text,
        ReplyKeyboard replyKeyboard
) {

    public SendTextParams with(Long chatId) {

        return new SendTextParams(
                chatId,
                this.text,
                this.replyKeyboard
        );
    }
}

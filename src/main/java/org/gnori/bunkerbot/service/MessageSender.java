package org.gnori.bunkerbot.service;

import org.gnori.bunkerbot.service.impl.sender.SendTextParams;

import java.util.Optional;

public interface MessageSender extends BotDependent {
    <T extends SendTextParams>Optional<Integer> sendMessage(T sendMessageParams);
}

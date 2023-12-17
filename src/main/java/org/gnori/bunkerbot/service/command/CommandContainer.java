package org.gnori.bunkerbot.service.command;

public interface CommandContainer<K extends InstanceKey, C extends Command<K>> {

    C retrieveCommand(K key);
}

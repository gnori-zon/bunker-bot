package org.gnori.bunkerbot.service.command.impl;

import org.gnori.bunkerbot.service.command.InstanceKey;

public enum MessageType implements InstanceKey {

    TEXT,
    CALLBACK,
    UNSUPPORTED
}

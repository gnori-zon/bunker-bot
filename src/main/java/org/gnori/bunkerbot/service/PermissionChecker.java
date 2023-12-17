package org.gnori.bunkerbot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface PermissionChecker {

    boolean hasPermission(Update update);
}

package org.gnori.bunkerbot.service;

public interface BunkerGame {

    void startGame();
    void nextStep();
    void deleteUser(Long userId);
    void stopGame();
}

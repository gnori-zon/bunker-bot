package org.gnori.bunkerbot.service.impl.game;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.gnori.bunkerbot.domain.BotUser;
import org.gnori.bunkerbot.domain.Characteristic;
import org.gnori.bunkerbot.repository.BotUserRepository;
import org.gnori.bunkerbot.repository.CharacteristicRepository;
import org.gnori.bunkerbot.repository.CharacteristicTypeRepository;
import org.gnori.bunkerbot.service.BunkerGame;
import org.gnori.bunkerbot.service.MessageSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BunkerGameImpl implements BunkerGame {

    @Value("${game.countSteps}")
    Integer countSteps;

    final BotUserRepository botUserRepository;
    final CharacteristicRepository characteristicRepository;
    final CharacteristicTypeRepository characteristicTypeRepository;
    final MessageSender messageSender;

    Map<BotUser, List<Characteristic>> characteristicsPerUsers = new HashMap<>();

    @Override
    public void startGame() {

        botUserRepository.findAllByIsActive(true)
                .forEach(user -> characteristicsPerUsers.put(user, new ArrayList<>()));

        characteristicTypeRepository.findByNameEqualsIgnoreCase("Пол").ifPresent(gender -> {

            final LinkedList<Characteristic> genders = new LinkedList<>(characteristicRepository.findAllByCharacteristicType(gender));

            if (genders.isEmpty()) {
                log.warn("bad count genres: is empty");
                return;
            }

            characteristicsPerUsers.keySet().forEach(key -> {

                        final Characteristic characteristic = genders.removeFirst();
                        characteristicsPerUsers.get(key).add(characteristic);

                        genders.addLast(characteristic);

                    }
            );

        });

        characteristicTypeRepository.findByNameEqualsIgnoreCase("Образование").ifPresent(gender -> {

            final LinkedList<Characteristic> educations = new LinkedList<>(characteristicRepository.findAllByCharacteristicType(gender));

            if (educations.isEmpty()) {
                log.warn("bad count educations: is empty");
                return;
            }

            characteristicsPerUsers.keySet().forEach(key -> {

                        final Characteristic characteristic = educations.removeFirst();
                        characteristicsPerUsers.get(key).add(characteristic);

                        educations.addLast(characteristic);
                    }
            );

        });

    }

    @Override
    public void nextStep() {

    }

    @Override
    public void deleteUser(Long userId) {
        characteristicsPerUsers.remove(new BotUser(userId, null, false, false, null));
    }

    @Override
    public void stopGame() {
        characteristicsPerUsers = new HashMap<>();
    }
}

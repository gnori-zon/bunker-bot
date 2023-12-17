package org.gnori.bunkerbot.service.impl.game;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.gnori.bunkerbot.domain.BotUser;
import org.gnori.bunkerbot.domain.BotUserState;
import org.gnori.bunkerbot.domain.Characteristic;
import org.gnori.bunkerbot.domain.CharacteristicType;
import org.gnori.bunkerbot.repository.BotUserRepository;
import org.gnori.bunkerbot.repository.CharacteristicRepository;
import org.gnori.bunkerbot.repository.CharacteristicTypeRepository;
import org.gnori.bunkerbot.service.BunkerGame;
import org.gnori.bunkerbot.service.MessageSender;
import org.gnori.bunkerbot.service.command.impl.callback.CallbackCommandKey;
import org.gnori.bunkerbot.service.impl.sender.SendTextParams;
import org.gnori.bunkerbot.service.keyboard.generator.KeyboardGeneratorContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BunkerGameImpl implements BunkerGame {

    private static final int START_STEP_VALUE = 1;
    private static final int ITERATION_STEP_VALUE = 1;

    @Value("${game.countSteps}")
    Integer gameCountSteps;
    Integer currentStep = START_STEP_VALUE;

    final MessageSender messageSender;
    final BotUserRepository botUserRepository;
    final CharacteristicRepository characteristicRepository;
    final KeyboardGeneratorContainer keyboardGeneratorContainer;
    final CharacteristicTypeRepository characteristicTypeRepository;

    Map<BotUser, List<Characteristic>> characteristicsPerUsers = new HashMap<>();

    @Override
    public void startGame() {

        botUserRepository.findAllByIsActive(true)
                .forEach(user -> characteristicsPerUsers.put(user, new ArrayList<>()));

        simpleAppendRandomCharacteristicByName("Пол");
        simpleAppendRandomCharacteristicByName("Образование");
        sendInfoPerBotUser();
    }

    @Override
    public void nextStep() {

        currentStep += ITERATION_STEP_VALUE;
        if (currentStep.equals(gameCountSteps) || characteristicsPerUsers.isEmpty()) {
            sendInfoForAdmins();
            stopGame();
            return;
        }

        switch (currentStep) {
            case 2 -> {
                simpleAppendRandomCharacteristicByName("Опыт");
                simpleAppendRandomCharacteristicByName("Национальность");
                sendInfoPerBotUser();
            }
            default -> {
                oppositeAppendRandomCharacteristicByName("Особенность");
                oppositeAppendRandomCharacteristicByName("Особенность");
                sendInfoPerBotUser();
            }
        }

        sendInfoStepForAdmin();
    }

    @Override
    public void deleteUser(Long userId) {

        final BotUser botUserKey = new BotUser();
        botUserKey.setId(userId);

        characteristicsPerUsers.remove(botUserKey);
    }

    @Override
    public void stopGame() {
        currentStep = START_STEP_VALUE;
        characteristicsPerUsers = new HashMap<>();
    }

    private void sendInfoPerBotUser() {

        characteristicsPerUsers.forEach((botUser, botUserCharacteristics) -> {

            final SendTextParams sendTextParams = SendTextParams.builder()
                    .chatId(botUser.getChatId())
                    .text(responseTextOf(botUser, botUserCharacteristics))
                    .build();

            messageSender.sendMessage(sendTextParams);
        });
    }

    private String responseTextOf(BotUser botUser, List<Characteristic> botUserCharacteristics) {

        final String username = botUser.getUsername();
        final String characters  =botUserCharacteristics.stream()
                .map(characteristic -> String.format(
                            "%s: %s", characteristic.getCharacteristicType().getName(), characteristic.getDescription()
                ))
                .collect(Collectors.joining("\n\t"));

        return String.format("%s\n\t%s", username, characters);
    }

    private void sendInfoForAdmins() {

        botUserRepository.findAllAdmin().forEach(admin -> {

            final SendTextParams sendTextParams = SendTextParams.builder()
                    .chatId(admin.getChatId())
                    .text("- <b>END GAME</b> -")
                    .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.DEFAULT))
                    .build();

            messageSender.sendMessage(sendTextParams);
        });

        characteristicsPerUsers.forEach((botUser, botUserCharacteristics) -> {

            final String textMessage = responseTextOf(botUser, botUserCharacteristics);

            botUserRepository.findAllAdmin().forEach(admin -> {

                final SendTextParams sendTextParams = SendTextParams.builder()
                        .chatId(admin.getChatId())
                        .text(textMessage)
                        .replyKeyboard(keyboardGeneratorContainer.generateForState(BotUserState.DEFAULT))
                        .build();

                messageSender.sendMessage(sendTextParams);
            });
        });
    }

    private void sendInfoStepForAdmin() {

        botUserRepository.findAllAdmin().forEach(admin -> {

            final SendTextParams sendTextParams = SendTextParams.builder()
                    .chatId(admin.getChatId())
                    .text(String.format("step: %s", currentStep))
                    .replyKeyboard(generateStepInlineButtons())
                    .build();

            messageSender.sendMessage(sendTextParams);
        });
    }

    private void simpleAppendRandomCharacteristicByName(String name) {

        characteristicTypeRepository.findByNameEqualsIgnoreCase(name).ifPresent(characteristicType -> {

            final LinkedList<Characteristic> characteristics = new LinkedList<>(characteristicRepository.findAllByCharacteristicType(characteristicType));
            Collections.shuffle(characteristics);

            if (characteristics.isEmpty()) {
                log.warn("bad count educations: is empty");
                return;
            }

            characteristicsPerUsers.keySet().forEach(key -> {

                        final Characteristic characteristic = characteristics.removeFirst();
                        characteristicsPerUsers.get(key).add(characteristic);

                        characteristics.addLast(characteristic);
                    }
            );
        });
    }

    private void oppositeAppendRandomCharacteristicByName(String name) {

        characteristicTypeRepository.findByNameEqualsIgnoreCase(name).ifPresent(characteristicType -> {

            List<Characteristic> oppositeCharacteristics = findAllOppositeCharacteristics(characteristicType);
            LinkedList<BotUser> botUsersForAddSimpleRandomize = new LinkedList<>(characteristicsPerUsers.keySet().stream().toList());

            int countChecks = 0;
            while (!oppositeCharacteristics.isEmpty() && countChecks < characteristicsPerUsers.keySet().size()) {

                countChecks++;
                final BotUser botUser = botUsersForAddSimpleRandomize.removeFirst();
                final Set<Characteristic> bouUserCharacteristics = characteristicsPerUsers.get(botUser).stream()
                        .collect(Collectors.toUnmodifiableSet());

                boolean isFound = false;
                for (int i = 0; i < oppositeCharacteristics.size(); i++) {

                    if (!bouUserCharacteristics.contains(oppositeCharacteristics.get(i))) {
                        characteristicsPerUsers.get(botUser).add(oppositeCharacteristics.remove(i));
                        isFound = true;
                        break;
                    }
                }

                if (!isFound) {
                    botUsersForAddSimpleRandomize.addLast(botUser);
                }
            }

            botUsersForAddSimpleRandomize.forEach(botUser -> {

                final Set<Long> bouUserCharacteristicIds = characteristicsPerUsers.get(botUser).stream()
                        .map(Characteristic::getId)
                        .collect(Collectors.toUnmodifiableSet());

                final List<Characteristic> excludeBotUserCharacteristics = characteristicRepository.findAllNotIn(characteristicType.getId(), bouUserCharacteristicIds);
                Collections.shuffle(excludeBotUserCharacteristics);

                excludeBotUserCharacteristics.stream().findFirst()
                        .ifPresent(characteristic -> characteristicsPerUsers.get(botUser).add(characteristic));

            });
        });
    }

    private List<Characteristic> findAllOppositeCharacteristics(CharacteristicType characteristicType) {

        final Set<Long> uniqueCharacteristicIds = characteristicsPerUsers.values().stream()
                .flatMap(Collection::stream)
                .map(Characteristic::getId)
                .collect(Collectors.toUnmodifiableSet());

        return characteristicRepository.findOppositesFor(characteristicType.getId(), uniqueCharacteristicIds);
    }

    private ReplyKeyboard generateStepInlineButtons() {

        final InlineKeyboardButton nextStepButton = new InlineKeyboardButton("next step");
        nextStepButton.setCallbackData(CallbackCommandKey.NEXT_STEP_GAME.createCallbackData());

        final InlineKeyboardButton stopGameButton = new InlineKeyboardButton("stop game");
        stopGameButton.setCallbackData(CallbackCommandKey.STOP_GAME.createCallbackData());

        return new InlineKeyboardMarkup(List.of(List.of(nextStepButton, stopGameButton)));
    }
}

package org.gnori.bunkerbot.service.command.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseConst {

    public static final String INVALID_ID_INPUT = "Вы ввели некорректный id, используйте только цифры";
    public static final String ACTIVATE_USER_SUCCESS = "Пользователь успено активирован";
    public static final String FIND_USER_FAILURE_PATTERN = "Упс, пользователь с id %s не найден";
    public static final String DEACTIVATE_USER_SUCCESS = "Пользователь успено деактивирован";
    public static final String START_ADMIN = "Привет хозяйка, выбери чего желаешь";
    public static final String RESOLVE_ACTIVITY_USER_PATTERN = """
            Добавить в игру пользователя?
               <b>id</b>: %s
               <b>username<b>: %s
            """;
    public static final String WAIT_INPUT_ID = "Введите id пользователя";
    public static final String WAIT_INPUT_ID_FOR_DELETE = WAIT_INPUT_ID + ", чтобы исключить";
    public static final String USER_INFO_PATTERN = """
                user [<b>%s</b>],
                    <b>username</b>: %s
                    <b>is_active</b>: %s
                """;
    public static final String WAIT_NEXT_ACTION = "Выберите следующие дейтсвие";
    public static final String SHORT_USER_INFO_PATTERN = """
                user [<b>%s</b>],
                    <b>username</b>: %s
                """;
    public static final String EMPTY_LIST = "Список пуст";
}

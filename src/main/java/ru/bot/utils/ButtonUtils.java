package ru.bot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

public class ButtonUtils {

    public static InlineKeyboardButton button(String text, String callbackData) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }

    public static List<InlineKeyboardButton> row(InlineKeyboardButton... buttons) {
        return List.of(buttons);
    }

    @SafeVarargs
    public static InlineKeyboardMarkup inlineKeyboard(List<InlineKeyboardButton>... buttons) {
        return InlineKeyboardMarkup.builder()
                .keyboard(Arrays.asList(buttons))
                .build();
    }

}

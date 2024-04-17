package ru.bot.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bot.service.bot.TelegramBot;
import ru.bot.utils.MessageUtils;

import static ru.bot.utils.ButtonUtils.*;


@Component
@RequiredArgsConstructor
public class CommandProcessor {
    private final MessageUtils messageUtils;
    private final TelegramBot bot;

    public void process(Message message) {
        var command = message.getText();
        var chatId = message.getChatId();

        switch (command) {
            case "/start" -> startCommand(chatId);
            case "/menu" -> menuCommand(chatId);
            case "/stop_practice" -> stopPracticeCommand(chatId);
            default -> helpCommand(chatId);
        }
    }

    private void startCommand(Long chatId) {
        var message = messageUtils.getMessage("command.greeting");
        var buttonStartText = messageUtils.getMessage("menu.button.start");
        var markup = inlineKeyboard(
                row(button(buttonStartText, "menu"))
        );

        bot.answer(message, chatId, markup);
    }

    private void menuCommand(Long chatId) {
        var message = messageUtils.getMessage("command.menu");
        var theoryText = messageUtils.getMessage("menu.theory");
        var practiceText = messageUtils.getMessage("menu.practice");
        var markup = inlineKeyboard(
                row(button(theoryText, "tense Present"), button(practiceText, "practice"))
        );

        bot.answer(message, chatId, markup);
    }

    private void helpCommand(Long chatId) {
        var message = messageUtils.getMessage("command.help");
        bot.answer(message, chatId);
    }

    private void stopPracticeCommand(Long chatId) {
        var message = messageUtils.getMessage("command.stop_practice.error");
        bot.answer(message, chatId);
    }

}

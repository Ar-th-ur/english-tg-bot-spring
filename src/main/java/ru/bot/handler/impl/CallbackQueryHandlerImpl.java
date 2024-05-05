package ru.bot.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.bot.entity.State;
import ru.bot.handler.CallbackQueryHandler;
import ru.bot.processor.PracticeProcessor;
import ru.bot.service.bot.TelegramBot;
import ru.bot.service.db.TimeService;
import ru.bot.service.db.UserService;
import ru.bot.utils.MessageUtils;

import static ru.bot.utils.ButtonUtils.*;

@Service
@RequiredArgsConstructor
public class CallbackQueryHandlerImpl implements CallbackQueryHandler {
    private final TimeService timeService;
    private final UserService userService;

    private final PracticeProcessor practiceProcessor;
    private final MessageUtils messageUtils;
    private final TelegramBot bot;

    @Override
    public void handle(CallbackQuery callbackQuery) {
        var callbackData = callbackQuery.getData();
        var chatId = callbackQuery.getMessage().getChatId();
        var messageId = callbackQuery.getMessage().getMessageId();
        var telegramId = callbackQuery.getFrom().getId();

        switch (callbackData) {
            case "menu" -> menuCallback(chatId, messageId);
            case "practice" -> practiceCallback(telegramId, chatId, messageId);
            case "answer" -> answerCallback(telegramId, chatId, messageId);
        }

        if (callbackData.startsWith("tense")) {
            var tense = callbackData.split(" ")[1];
            tenseCallback(tense, chatId, messageId);
        } else if (callbackData.startsWith("time")) {
            var time = callbackData.split(" ", 2)[1];
            timeCallback(time, chatId, messageId);
        }
    }

    private void menuCallback(Long chatId, Integer messageId) {
        var message = messageUtils.getMessage("command.menu");
        var theoryText = messageUtils.getMessage("menu.theory");
        var practiceText = messageUtils.getMessage("menu.practice");
        var markup = inlineKeyboard(
                row(button(theoryText, "tense Present"), button(practiceText, "practice"))
        );
        bot.editMessage(message, chatId, messageId, markup);
    }

    private void practiceCallback(Long telegramId, Long chatId, Integer messageId) {
        var user = userService.findByTelegramId(telegramId);
        user.setState(State.PRACTICE);
        userService.save(user);

        var message = messageUtils.getMessage("practice.start");
        bot.editMessage(message, chatId, messageId);

        practiceProcessor.sendExercise(telegramId, chatId);
    }

    private void tenseCallback(String tense, Long chatId, Integer messageId) {
        var rowNext = row(button("▶", "tense Past"));
        if (tense.equals("Past")) {
            rowNext = row(button("◀", "tense Present"), button("▶", "tense Future"));
        } else if (tense.equals("Future")) {
            rowNext = row(button("◀", "tense Past"));
        }

        var buttonBackMessage = messageUtils.getMessage("menu.button.back");
        var backButton = row(button(buttonBackMessage, "menu"));
        var markup = inlineKeyboard(
                row(button("%s Simple".formatted(tense), "time %s Simple".formatted(tense))),
                row(button("%s Continuous".formatted(tense), "time %s Continuous".formatted(tense))),
                row(button("%s Perfect".formatted(tense), "time %s Perfect".formatted(tense))),
                row(button("%s Perfect Continuous".formatted(tense), "time %s Perfect Continuous".formatted(tense))),
                rowNext,
                backButton
        );

        var message = messageUtils.getMessage("menu.choose_time");
        bot.editMessage(message, chatId, messageId, markup);
    }

    private void timeCallback(String time, Long chatId, Integer messageId) {
        var message = timeService.findByName(time).getTheory();
        var buttonBackMessage = messageUtils.getMessage("menu.button.back");
        var markup = inlineKeyboard(
                row(button(buttonBackMessage, "tense Present"))
        );
        bot.editMessage(message, chatId, messageId, markup);
    }


    private void answerCallback(Long telegramId, Long chatId, Integer messageId) {
        var exercise = userService.findByTelegramId(telegramId).getExercise();
        var sentence = exercise.getSentence();
        bot.answer(sentence, chatId);

        var correctAnswer = messageUtils.getMessage("practice.answer", exercise.getAnswer());
        bot.answer(correctAnswer, chatId);

        bot.deleteMessage(chatId, messageId);
        practiceProcessor.sendExercise(telegramId, chatId);
    }
}

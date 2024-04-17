package ru.bot.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bot.entity.Exercise;
import ru.bot.entity.State;
import ru.bot.service.bot.TelegramBot;
import ru.bot.service.db.ExerciseService;
import ru.bot.service.db.UserService;
import ru.bot.utils.MessageUtils;

import static ru.bot.utils.ButtonUtils.*;

@Component
@RequiredArgsConstructor
public class PracticeProcessor {
    private final ExerciseService exerciseService;
    private final UserService userService;

    private final MessageUtils messageUtils;
    private final TelegramBot bot;

    public void process(Message message) {
        var chatId = message.getChatId();
        if (message.getText().equals("/stop_practice")) {
            var msg = messageUtils.getMessage("command.stop_practice");
            var username = message.getFrom().getUserName();

            var user = userService.findByUsername(username);
            user.setState(State.COMMAND);
            userService.save(user);

            bot.answer(msg, chatId);
            return;
        }

        assessAnswer(message);
    }

    private void assessAnswer(Message message) {
        var username = message.getFrom().getUserName();
        var userAnswer = message.getText();
        var chatId = message.getChatId();

        var exercise = userService.findByUsername(username).getExercise();
        if (userAnswer.equalsIgnoreCase(exercise.getAnswer())) {
            var msg = messageUtils.getMessage("practice.correct_answer");
            bot.answer(msg, chatId);
            sendExercise(username, chatId);
            return;
        }

        var msg = messageUtils.getMessage("practice.wrong_answer");
        bot.answer(msg, chatId);

        var messageId = message.getMessageId();
        sendExerciseWithAnswerButton(exercise, chatId);
        bot.deleteMessage(chatId, messageId - 1);
    }

    public void sendExerciseWithAnswerButton(Exercise exercise, Long chatId) {
        var message = exercise.getSentence();
        var buttonText = messageUtils.getMessage("practice.answer_button");
        var answerButton = inlineKeyboard(
                row(button(buttonText, "answer"))
        );
        bot.answer(message, chatId, answerButton);
    }

    public void sendExercise(String username, Long chatId) {
        var exercise = exerciseService.findAny();

        var user = userService.findByUsername(username);
        user.setExercise(exercise);
        userService.save(user);

        var message = messageUtils.getMessage("practice.exercise", exercise.getSentence());
        bot.answer(message, chatId);
    }
}

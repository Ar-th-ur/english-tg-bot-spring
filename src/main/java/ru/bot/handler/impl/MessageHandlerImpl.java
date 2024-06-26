package ru.bot.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bot.entity.State;
import ru.bot.entity.User;
import ru.bot.handler.MessageHandler;
import ru.bot.processor.CommandProcessor;
import ru.bot.processor.PracticeProcessor;
import ru.bot.service.db.UserService;

@Service
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {
    private final UserService userService;
    private final CommandProcessor commandProcessor;
    private final PracticeProcessor practiceProcessor;

    @Override
    public void handle(Message message) {
        var telegramId = message.getFrom().getId();

        if (userService.existByTelegramId(telegramId)) {
            var user = userService.findByTelegramId(telegramId);
            switch (user.getState()) {
                case COMMAND -> commandProcessor.process(message);
                case PRACTICE -> practiceProcessor.process(message);
            }
        } else {
            var chatId = message.getChatId();
            var user = new User(null, telegramId, chatId, State.COMMAND, null);
            userService.save(user);
            commandProcessor.process(message);
        }
    }
}

package ru.bot.service.bot.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bot.handler.CallbackQueryHandler;
import ru.bot.handler.MessageHandler;
import ru.bot.service.bot.UpdateListener;

@Service
@RequiredArgsConstructor
public class UpdateListenerImpl implements UpdateListener {
    private final MessageHandler messageHandler;
    private final CallbackQueryHandler callbackQueryHandler;

    public void listenUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            messageHandler.handle(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            callbackQueryHandler.handle(update.getCallbackQuery());
        }
    }
}

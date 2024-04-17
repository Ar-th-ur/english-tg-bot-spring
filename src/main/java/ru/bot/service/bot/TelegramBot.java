package ru.bot.service.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final UpdateListener updateListener;
    @Value("${telegram.bot.token}")
    private String token;
    @Value("${telegram.bot.username}")
    private String botUsername;

    @Autowired
    public TelegramBot(@Lazy UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateListener.listenUpdate(update);
    }

    public void perform(BotApiMethod<? extends Serializable> method) {
        try {
            super.execute(method);
        } catch (TelegramApiException e) {
            log.error("Failed to execute method", e);
        }
    }

    public void answer(String message, Long chatId) {
        var sendMessage = SendMessage.builder()
                .text(message)
                .chatId(chatId)
                .build();
        this.perform(sendMessage);
    }

    public void answer(String message, Long chatId, InlineKeyboardMarkup markup) {
        var sendMessage = SendMessage.builder()
                .text(message)
                .chatId(chatId)
                .replyMarkup(markup)
                .build();
        this.perform(sendMessage);
    }

    public void editMessage(String message, Long chatId, Integer messageId, InlineKeyboardMarkup markup) {
        var editMessage = EditMessageText.builder()
                .text(message)
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(markup)
                .build();
        this.perform(editMessage);
    }

    public void editMessage(String message, Long chatId, Integer messageId) {
        var editMessage = EditMessageText.builder()
                .text(message)
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(null)
                .build();
        this.perform(editMessage);
    }

    public void deleteMessage(Long chatId, Integer messageId) {
        var deleteMessage = DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
        this.perform(deleteMessage);
    }


    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}

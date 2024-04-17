package ru.bot.service.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateListener {
    void listenUpdate(Update update);
}

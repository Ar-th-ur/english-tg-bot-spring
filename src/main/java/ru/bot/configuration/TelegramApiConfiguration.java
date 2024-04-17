package ru.bot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.bot.service.bot.TelegramBot;

@Configuration
@RequiredArgsConstructor
public class TelegramApiConfiguration {
    private final TelegramBot bot;

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        var botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
        return botsApi;
    }
}

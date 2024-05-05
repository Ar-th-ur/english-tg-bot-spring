package ru.bot.service.db;


import ru.bot.entity.User;

public interface UserService {

    boolean existByTelegramId(Long telegramId);

    void save(User user);

    User findByTelegramId(Long telegramId);
}

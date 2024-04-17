package ru.bot.service.db;


import ru.bot.entity.User;

public interface UserService {

    boolean existByUsername(String username);

    void save(User user);

    User findByUsername(String username);
}

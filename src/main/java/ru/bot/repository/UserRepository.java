package ru.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bot.entity.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByTelegramId(Long telegramId);

    Optional<User> findByTelegramId(Long telegramId);
}

package ru.bot.service.db.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bot.entity.User;
import ru.bot.repository.UserRepository;
import ru.bot.service.db.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public boolean existByTelegramId(Long telegramId) {
        return userRepository.existsByTelegramId(telegramId);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId)
                .orElseThrow();
    }
}

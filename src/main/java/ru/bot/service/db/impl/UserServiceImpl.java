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
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow();
    }
}

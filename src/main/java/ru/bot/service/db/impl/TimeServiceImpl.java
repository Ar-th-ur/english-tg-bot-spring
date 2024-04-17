package ru.bot.service.db.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bot.entity.Time;
import ru.bot.repository.TimeRepository;
import ru.bot.service.db.TimeService;

@Service
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {
    private final TimeRepository timeRepository;

    @Override
    public Time findByName(String name) {
        return timeRepository.findByNameIgnoreCase(name);
    }
}

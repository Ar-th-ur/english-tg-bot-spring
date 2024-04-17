package ru.bot.service.db;

import ru.bot.entity.Time;

public interface TimeService {
    Time findByName(String name);
}

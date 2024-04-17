package ru.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bot.entity.Time;

public interface TimeRepository extends JpaRepository<Time, Integer> {
    Time findByNameIgnoreCase(String name);
}

package ru.bot.service.db.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bot.entity.Exercise;
import ru.bot.repository.ExerciseRepository;
import ru.bot.service.db.ExerciseService;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;

    @Override
    public Exercise findAny() {
        return exerciseRepository.findAny();
    }
}

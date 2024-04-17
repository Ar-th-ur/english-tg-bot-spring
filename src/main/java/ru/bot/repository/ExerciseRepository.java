package ru.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.bot.entity.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    @Query(value = "SELECT * from exercises offset floor(random() * (select count(*) from exercises)) limit 1", nativeQuery = true)
    Exercise findAny();
}

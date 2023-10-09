package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Slf4j
@Service
public class MpaService {
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Rating getGenre(long id) {
        try {
            mpaStorage.getRating(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Некорректный рейтинг Id");
        }
        log.info("Получен рейтинг - {}", mpaStorage.getRating(id).getName());
        return mpaStorage.getRating(id);
    }

    public List<Rating> getGenres() {
        return mpaStorage.getRatings();
    }
}

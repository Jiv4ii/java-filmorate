package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;

@SpringBootApplication
public class FilmorateApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmorateApplication.class, args);
		Film film = new Film().setName("film").setDescription("descr").setDuration(120).setReleaseDate(LocalDate.of(2000, 1, 1)).setMpa(new Rating().setId(2L).setName("PG"));
	}

}

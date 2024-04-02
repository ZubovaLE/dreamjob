package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.model.dto.FileDto;

import java.util.Collection;
import java.util.Optional;

public interface VacancyService {

    Vacancy save(Vacancy vacancy, FileDto image);

    boolean deleteById(int id);

    boolean update(Vacancy vacancy, FileDto image);

    Optional<Vacancy> findById(int id);

    Collection<Vacancy> findAll();

}
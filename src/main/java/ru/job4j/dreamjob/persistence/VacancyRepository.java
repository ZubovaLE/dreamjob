package ru.job4j.dreamjob.persistence;

import ru.job4j.dreamjob.model.Vacancy;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class VacancyRepository implements RecruitmentRepository<Vacancy> {

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private VacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Description 1", LocalDateTime.of(2023, 1, 1, 0, 0), false, 1));
        save(new Vacancy(0, "Junior Java Developer", "Description 2", LocalDateTime.of(2023, 2, 1, 0, 0), false, 1));
        save(new Vacancy(0, "Junior+ Java Developer", "Description 3", LocalDateTime.of(2023, 3, 1, 0, 0), false, 2));
        save(new Vacancy(0, "Middle Java Developer", "Description 4", LocalDateTime.of(2023, 4, 1, 0, 0), false, 2));
        save(new Vacancy(0, "Middle+ Java Developer", "Description 5", LocalDateTime.of(2023, 5, 1, 0, 0), false, 3));
        save(new Vacancy(0, "Senior Java Developer", "Description 6", LocalDateTime.of(2023, 6, 1, 0, 0), false, 3));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> new Vacancy(oldVacancy.getId(),
                vacancy.getTitle(), vacancy.getDescription(), vacancy.getCreationDate(), vacancy.getVisible(), vacancy.getCityId())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
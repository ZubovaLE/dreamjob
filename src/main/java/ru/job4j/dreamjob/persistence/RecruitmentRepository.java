package ru.job4j.dreamjob.persistence;

import java.util.Collection;
import java.util.Optional;

public interface RecruitmentRepository<T> {
    T save(T t);

    boolean deleteById(int id);

    boolean update(T t);

    Optional<T> findById(int id);

    Collection<T> findAll();
}
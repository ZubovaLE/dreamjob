package ru.job4j.dreamjob.service;

import java.util.Collection;
import java.util.Optional;

public interface RecruitmentService<T> {
    T save(T t);

    boolean deleteById(int id);

    boolean update(T t);

    Optional<T> findById(int id);

    Collection<T> findAll();
}
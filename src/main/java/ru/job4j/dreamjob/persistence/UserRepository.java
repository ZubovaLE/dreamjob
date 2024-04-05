package ru.job4j.dreamjob.persistence;

import ru.job4j.dreamjob.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

}
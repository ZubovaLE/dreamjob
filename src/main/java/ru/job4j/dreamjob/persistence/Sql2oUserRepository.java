package ru.job4j.dreamjob.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.model.User;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class Sql2oUserRepository implements UserRepository {
    private final Sql2o sql2o;

    @Override
    public Optional<User> save(User user) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO users(email, name, password)
                    VALUES (:email, :name, :password)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("email", user.getEmail())
                    .addParameter("name", user.getName())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE email = :email AND password = :password");
            query.addParameter("email", email);
            query.addParameter("password", password);
            var user = query.executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }
}

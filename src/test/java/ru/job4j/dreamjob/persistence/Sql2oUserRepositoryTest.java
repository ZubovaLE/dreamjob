package ru.job4j.dreamjob.persistence;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

class Sql2oUserRepositoryTest {
    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepository() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oVacancyRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        for (User user : sql2oUserRepository.findAll()) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    void whenSaveThenReturnSame() {

        // Given
        User user = new User("email", "name", "password");

        // When
        sql2oUserRepository.save(user);
        User savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).get();

        // Then
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);

    }

    @Test
    void whenSaveSeveralThenReturnAll() {

        // Given
        User userOne = new User("email one", "name one", "password one");
        User userTwo = new User("email two", "name two", "password two");

        // When
        sql2oUserRepository.save(userOne);
        sql2oUserRepository.save(userTwo);
        List<User> result = sql2oUserRepository.findAll();

        // Then
        assertThat(result).usingRecursiveComparison().isEqualTo(List.of(userOne, userTwo));

    }

    @Test
    void whenSaveSameEmailThenNotSave() {

        // Given
        User userOne = sql2oUserRepository.save(new User("email", "name one", "password one")).get();

        // When
        var result = sql2oUserRepository.save(new User(userOne.getEmail(), "name two", "password two"));

        // Then
        assertThat(result).isEqualTo(empty());

    }

    @Test
    void whenDontSaveThenNothingFound() {

        // Given

        // When

        //Then
        AssertionsForClassTypes.assertThat(sql2oUserRepository.findAll()).isEqualTo(emptyList());
    }

    @Test
    void deleteByIdThenGetEmpty() {

        // Given
        User user = new User("email", "name", "password");

        // When
        sql2oUserRepository.save(user);
        var isDeleted = sql2oUserRepository.deleteById(user.getId());
        Optional<User> savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        List<User> savedUsers = sql2oUserRepository.findAll();

        //Then
        AssertionsForClassTypes.assertThat(isDeleted).isTrue();
        AssertionsForClassTypes.assertThat(savedUser).isEqualTo(empty());
        AssertionsForClassTypes.assertThat(savedUsers).isEqualTo(emptyList());
    }

    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        // Given
        // When
        // Then
        AssertionsForClassTypes.assertThat(sql2oUserRepository.deleteById(0)).isFalse();
    }

    @Test
    public void whenFindByInvalidPassword() {
        // Given
        User user = new User("email", "name", "password");
        sql2oUserRepository.save(user);

        // When
       var savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), "incorrest");

        // Then
        AssertionsForClassTypes.assertThat(savedUser).isEqualTo(empty());
    }
}
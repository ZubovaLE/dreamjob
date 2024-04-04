package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.File;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;

import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oCandidateRepositoryTest {
    private static Sql2oCandidateRepository sql2oCandidateRepository;

    private static Sql2oFileRepository sql2oFileRepository;

    private static File file;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oVacancyRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oCandidateRepository = new Sql2oCandidateRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);

        file = new File("test", "test");
        sql2oFileRepository.save(file);
    }

    @AfterAll
    public static void deleteFile() {
        sql2oFileRepository.deleteById(file.getId());
    }

    @AfterEach
    public void clearCandidates() {
        for (Candidate candidate : sql2oCandidateRepository.findAll()) {
            sql2oCandidateRepository.deleteById(candidate.getId());
        }
    }

    @Test
    void whenSaveThenGetSame() {

        // Given
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        Candidate candidate = new Candidate(0, "name", "description", creationDate, 1, file.getId());

        // When
        sql2oCandidateRepository.save(candidate);
        Candidate savedCandidate = sql2oCandidateRepository.findById(candidate.getId()).get();

        //Then
        assertThat(savedCandidate).usingRecursiveComparison().isEqualTo(candidate);
    }

    @Test
    void whenSaveSeveralThenGetAll() {

        // Given
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        Candidate candidateOne = new Candidate(0, "name one", "description one", creationDate, 1, file.getId());
        Candidate candidateTwo = new Candidate(0, "name two", "description two", creationDate, 1, file.getId());
        Candidate candidateThree = new Candidate(0, "name three", "description three", creationDate, 1, file.getId());

        // When
        sql2oCandidateRepository.save(candidateOne);
        sql2oCandidateRepository.save(candidateTwo);
        sql2oCandidateRepository.save(candidateThree);
        var result = sql2oCandidateRepository.findAll();

        //Then
        assertThat(result).isEqualTo(List.of(candidateOne, candidateTwo, candidateThree));
    }

    @Test
    void whenDontSaveThenNothingFound() {

        // Given

        // When

        //Then
        assertThat(sql2oCandidateRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oCandidateRepository.findById(0)).isEqualTo(empty());
    }


    @Test
    void deleteByIdThenGetEmptyOptional() {
        // Given
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        var candidate = sql2oCandidateRepository.save(new Candidate(0, "name", "description",
                creationDate, 1, file.getId()));

        // When
        var isDeleted = sql2oCandidateRepository.deleteById(candidate.getId());
        var savedCandidate = sql2oCandidateRepository.findById(candidate.getId());

        //Then
        assertThat(isDeleted).isTrue();
        assertThat(savedCandidate).isEqualTo(empty());
    }

    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oCandidateRepository.deleteById(0)).isFalse();
    }

    @Test
    void whenUpdateThenGetUpdated() {
        // Given
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        var candidate = sql2oCandidateRepository.save(new Candidate(0, "title", "description",
                creationDate, 1, file.getId()));
        var updatedCandidate = new Candidate(
                candidate.getId(), "new title", "new description", creationDate.plusDays(1), 1, file.getId()
        );

        // When
        var isUpdated = sql2oCandidateRepository.update(updatedCandidate);
        var savedCandidate = sql2oCandidateRepository.findById(updatedCandidate.getId()).get();

        //Then
        assertThat(isUpdated).isTrue();
        assertThat(savedCandidate).usingRecursiveComparison().isEqualTo(updatedCandidate);
    }

    @Test
    public void whenUpdateUnExistingCandidateThenGetFalse() {
        // Given
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        var candidate = new Candidate(0, "name", "description", creationDate, 1, file.getId());

        // When
        var isUpdated = sql2oCandidateRepository.update(candidate);

        //Then
        assertThat(isUpdated).isFalse();
    }
}
package ru.job4j.dreamjob.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.model.City;

import java.util.Collection;

@RequiredArgsConstructor
@Repository
public class Sql2oCityRepository implements CityRepository {

    private final Sql2o sql2o;

    @Override
    public Collection<City> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM cities");
            return query.executeAndFetch(City.class);
        }
    }

}
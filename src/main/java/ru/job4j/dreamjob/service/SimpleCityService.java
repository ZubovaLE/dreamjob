package ru.job4j.dreamjob.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.persistence.CityRepository;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class SimpleCityService implements CityService {

    private final CityRepository cityRepository;

    @Override
    public Collection<City> findAll() {
        return cityRepository.findAll();
    }
}
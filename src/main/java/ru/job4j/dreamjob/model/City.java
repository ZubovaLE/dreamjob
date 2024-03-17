package ru.job4j.dreamjob.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class City {
    private final int id;

    private final String name;
}

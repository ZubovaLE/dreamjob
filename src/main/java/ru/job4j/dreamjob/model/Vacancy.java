package ru.job4j.dreamjob.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vacancy {
    @EqualsAndHashCode.Include
    private int id;

    private String title;
    private String description;
    private LocalDateTime creationDate;
}
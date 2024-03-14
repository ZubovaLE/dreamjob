package ru.job4j.dreamjob.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vacancy {
    @EqualsAndHashCode.Include
    private int id;

    private String title;
    private String description;
    private LocalDateTime creationDate = LocalDateTime.now();
}
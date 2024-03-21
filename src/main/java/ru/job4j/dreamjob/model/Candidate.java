package ru.job4j.dreamjob.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Candidate {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String description;
    private LocalDateTime creationDate = LocalDateTime.now();
    private int cityId;
    private int fileId;
}
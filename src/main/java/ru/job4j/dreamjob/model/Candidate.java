package ru.job4j.dreamjob.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Candidate {
    private int id;
    private String name;
    private String description;
    private LocalDateTime creationDate;
}
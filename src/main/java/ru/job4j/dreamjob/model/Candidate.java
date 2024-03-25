package ru.job4j.dreamjob.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Candidate {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "description", "description",
            "creation_date", "creationDate",
            "city_id", "cityId",
            "file_id", "fileId"
    );

    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String description;
    private LocalDateTime creationDate = LocalDateTime.now();
    private int cityId;
    private int fileId;
}
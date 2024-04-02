package ru.job4j.dreamjob.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FileDto {
    private String name;

    private byte[] content;
}

package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.File;
import ru.job4j.dreamjob.model.dto.FileDto;

import java.util.Optional;

public interface FileService {

    File save(FileDto fileDto);

    Optional<FileDto> getFileById(int id);

    void deleteById(int id);
}
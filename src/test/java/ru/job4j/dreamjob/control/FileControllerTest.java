package ru.job4j.dreamjob.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.job4j.dreamjob.model.dto.FileDto;
import ru.job4j.dreamjob.service.FileService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {
    private FileService fileService;
    private FileController fileController;

    @BeforeEach
    void setUp() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    @Test
    void whenGetByIdThenGetOk() {
        FileDto fileDto = new FileDto("test", new byte[]{1, 2, 3});
        when(fileService.getFileById(1)).thenReturn(Optional.of(fileDto));
        ResponseEntity expectedResponseEntity = ResponseEntity.ok(fileDto.getContent());

        ResponseEntity actualResponse = fileController.getById(1);

        assertEquals(expectedResponseEntity, actualResponse);
    }

    @Test
    void whenGetByInvalidIdThenGetNotFoundResponse() {
        ResponseEntity<FileDto> expectedResponseEntity = ResponseEntity.notFound().build();
        when(fileService.getFileById(1)).thenReturn(Optional.empty());

        ResponseEntity responseEntity = fileController.getById(1);

        assertEquals(expectedResponseEntity, responseEntity);
    }

}
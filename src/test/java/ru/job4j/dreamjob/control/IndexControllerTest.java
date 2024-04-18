package ru.job4j.dreamjob.control;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class IndexControllerTest {

    private final IndexController indexController = new IndexController();

    @Test
    void whenGetIndexThenReturnIndexPage() {
        String expectedView = "index";

        var view = indexController.getIndex();

        assertThat(view).isEqualTo(expectedView);
    }

}
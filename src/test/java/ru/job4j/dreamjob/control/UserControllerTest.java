package ru.job4j.dreamjob.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void getRegistrationPage() {
        String expectedView = "users/registration";

        String view = userController.getRegistrationPage();

        assertThat(view).isEqualTo(expectedView);
    }

    @Test
    void whenRegisterSuccessfullyThenRedirectIndexPage() {
        User user = new User(1, "email", "name", "password");
        when(userService.save(any())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(model, user);

        assertThat(view).isEqualTo("redirect : /index");
    }

    @Test
    void getLoginPage() {
    }

    @Test
    void loginUser() {
    }

    @Test
    void logout() {
    }

}
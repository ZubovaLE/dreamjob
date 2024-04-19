package ru.job4j.dreamjob.control;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
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
    void registerWhenEmailAlreadyExistsThenGetErrorPageWithMessage() {
        var expectedMessage = "Пользователь с таким email уже существует";
        when(userService.save(any())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.register(model, new User());
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void getLoginPage() {
        String expectedView = "users/login";

        String view = userController.getLoginPage();

        assertThat(view).isEqualTo(expectedView);
    }

    @Test
    void whenLoginUserSuccessfullyThenRedirectVacanciesPage() {
        User user = new User(1, "email", "name", "password");
        when(userService.findByEmailAndPassword(any(), any())).thenReturn(Optional.of(user));
        HttpServletRequest request = new MockHttpServletRequest();

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);
        var actualUser = request.getSession().getAttribute("user");

        assertThat(view).isEqualTo("redirect:/vacancies");
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void loginUserWithIncorrectDataThenGeLoginPageWithErrorMessage() {
        when(userService.save(any())).thenReturn(Optional.empty());
        HttpServletRequest request = new MockHttpServletRequest();
        String expectedMessage = "Почта или пароль введены неверно";

        var model = new ConcurrentModel();
        var view = userController.loginUser(new User(), model, request);
        var actualMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void logout() {
    }

}
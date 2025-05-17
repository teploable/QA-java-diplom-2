package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.service.AuthResponse;
import site.nomoreparties.stellarburgers.service.Specification;

public class LoginUserTest {
    UserSteps userSteps = new UserSteps();
    AuthResponse loginResponse;
    Response response;

    @Before
    @Step("Создание пользователя")
    public void setUp() {
        Specification.setSpecification();
        userSteps.createUser(UserData.user);
    }

    @Test
    @DisplayName("Проверка авторизации")
    public void checkLoginUserTest() {
        loginResponse = userSteps.loginUserAuthResponse(UserData.getLoginData(UserData.user));
        userSteps.checkResponseBody(loginResponse);
        userSteps.deleteUser(loginResponse.getAccessToken(), UserData.user);
    }

    @Test
    @DisplayName("Авторизация пользователя с неправильными данными и проверка ответа")
    public void loginIncorrectUserTest() {
        response = userSteps.loginIncorrectUser();
        userSteps.checkIncorrectLoginDataStatusCode(response);
        userSteps.checkIncorrectLoginDataResponse(response);
    }
}

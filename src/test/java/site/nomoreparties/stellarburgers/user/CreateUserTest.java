package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.service.AuthResponse;
import site.nomoreparties.stellarburgers.service.Specification;

public class CreateUserTest {
    AuthResponse regResponse;
    UserSteps userSteps = new UserSteps();

    @Before
    public void setUp() {
        Specification.setSpecification();
    }

    @Test
    @DisplayName("Создание нового пользователя и проверка ответа")
    public void createNewUserTest() {
        regResponse = userSteps.createUserAuthResponse(UserData.user);
        userSteps.checkResponseBody(regResponse);
        userSteps.deleteUser(regResponse.getAccessToken(), UserData.user);
    }

    @Test
    @DisplayName("Создание существующего пользователя и проверка ответов")
    public void createExistingUserTest() {
        regResponse = userSteps.createUserAuthResponse(UserData.user);
        userSteps.checkResponseBody(regResponse);
        Response newResponse = userSteps.createUser(UserData.user);
        userSteps.checkForbiddenStatusCode(newResponse);
        userSteps.checkExistingUserCreationResponse(newResponse);
        userSteps.deleteUser(regResponse.getAccessToken(), UserData.user);
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void createUserWithoutNameTest() {
        Response response = userSteps.createUserWithoutName();
        userSteps.checkForbiddenStatusCode(response);
        userSteps.checkUserCreationWithoutCompulsoryParametersResponse(response);
    }

    @Test
    @DisplayName("Создание пользователя без почты")
    public void createUserWithoutEmailTest() {
        Response response = userSteps.createUserWithoutEmail();
        userSteps.checkForbiddenStatusCode(response);
        userSteps.checkUserCreationWithoutCompulsoryParametersResponse(response);
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    public void createUserWithoutPasswordTest() {
        Response response = userSteps.createUserWithoutPassword();
        userSteps.checkForbiddenStatusCode(response);
        userSteps.checkUserCreationWithoutCompulsoryParametersResponse(response);
    }
}

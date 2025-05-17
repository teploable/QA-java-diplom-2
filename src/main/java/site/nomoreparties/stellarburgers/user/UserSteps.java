package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.service.AuthResponse;
import site.nomoreparties.stellarburgers.service.Specification;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static site.nomoreparties.stellarburgers.service.ApiHandlers.*;

public class UserSteps {
    @Step("Создание пользователя")
    public Response createUser(User user) {
        Specification.setSpecification();
        return given()
                .body(user)
                .when()
                .post(USER_REGISTER_PATH);
    }

    @Step("Создание пользователя и возвращение ответа")
    public AuthResponse createUserAuthResponse(User user) {
        Specification.setSpecification();
        return given()
                .body(user)
                .when()
                .post(USER_REGISTER_PATH)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .response()
                .as(AuthResponse.class);
    }

    @Step("Создание тела запроса для создание пользователя без почты")
    public Response createUserWithoutEmail() {
        return createUser(UserData.userWithoutEmail);
    }

    @Step("Создание тела запроса для создания пользователя без пароля")
    public Response createUserWithoutPassword() {
        return createUser(UserData.userWithoutPassword);
    }

    @Step("Создание тела запроса для создание пользователя без имени")
    public Response createUserWithoutName() {
        return createUser(UserData.userWithoutName);
    }

    @Step("Проверка ответа при отсуствии обязательных параметров в теле запроса на создание пользователя")
    public void checkUserCreationWithoutCompulsoryParametersResponse(Response response) {
        response.then().body("success", is(false))
                .and().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Проверка ответа при попытке создать уже существующего пользователя")
    public void checkExistingUserCreationResponse(Response response) {
        response.then().body("success", is(false)).
                assertThat().body("message", equalTo("User already exists"));
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken, User user) {
        given()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .delete(USER_PATH)
                .then()
                .statusCode(HTTP_ACCEPTED);
    }

    @Step("Авторизация пользователя")
    public Response loginUser(UserData user) {
        Specification.setSpecification();
        return given()
                .body(user)
                .when()
                .post(USER_LOGIN_PATH);
    }

    @Step("Авторизация пользователя и возвращение ответа")
    public AuthResponse loginUserAuthResponse(UserData user) {
        return given()
                .body(user)
                .when()
                .post(USER_LOGIN_PATH)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .response()
                .as(AuthResponse.class);
    }

    @Step("Авторизация пользователя с неверными данными")
    public Response loginIncorrectUser() {
        return loginUser(UserData.getLoginData(UserData.newUser));
    }

    @Step("Изменение данных пользователя")
    public Response editUserData(String accessToken, User user) {
        Specification.setSpecification();
        return given()
                .header("authorization", accessToken)
                .body(user)
                .when()
                .patch(USER_PATH);
    }

    @Step("Изменение данных пользователя без авторизации")
    public Response editUserDataWithoutAuthToken(User user){
        Specification.setSpecification();
        return given()
                .body(user)
                .when()
                .patch(USER_PATH);
    }

    @Step("Проверка ответа")
    public void checkResponseBody(AuthResponse response) {
        assertTrue(response.isSuccess());
        assertEquals(UserData.userEmail, response.getUser().getEmail());
        assertEquals(UserData.userName, response.getUser().getName());
        assertFalse(response.getAccessToken().isBlank());
        assertFalse(response.getRefreshToken().isBlank());
    }

    @Step("Проверка статуса ответа 403 Forbidden")
    public void checkForbiddenStatusCode(Response response) {
        assertEquals(HTTP_FORBIDDEN, response.getStatusCode());
    }

    @Step("Проверка тела ответа (Ошибка авторизации)")
    public void checkIncorrectLoginDataResponse(Response response){
        response.then()
                .body("success", is(false))
                .and()
                .assertThat()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Проверка статуса ответа 401 Unauthorized)")
    public void checkIncorrectLoginDataStatusCode(Response response) {
        response.then().statusCode(HTTP_UNAUTHORIZED);
    }
}

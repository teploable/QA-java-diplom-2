package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.service.IngredientsResponse;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.*;
import static site.nomoreparties.stellarburgers.service.ApiHandlers.*;

public class OrderSteps {
    @Step("Создание заказа с авторизацией")
    public Response createOrderWithAuth(String accessToken, Order order) {
        return given()
                .header("authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Проверка ответа после создания заказа")
    public void checkOrderCreationResponse(Response response) {
        response.then()
                .body("success",is(true))
                .and()
                .body("name", notNullValue())
                .body("order",notNullValue())
                .statusCode(HTTP_OK);
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutAuth(Order order) {
        return given()
                .body(order)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Получение списка заказов пользователя")
    public Response getUserOrderList(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .when()
                .get(ORDER_PATH);
    }

    @Step("Проверка ответа получения списка заказов пользователя")
    public void checkUserOrderListResponse(Response response) {
        response.then()
                .body("success",is(true))
                .and()
                .body("orders",notNullValue())
                .body("total",notNullValue())
                .body("totalToday",notNullValue())
                .statusCode(HTTP_OK);
    }

    @Step("Получение заказов без авторизации")
    public Response getOrdersWithoutAuth() {
        return given()
                .when()
                .get(ORDER_PATH);
    }

    @Step("Проверка ответа на создание заказа без авторизации")
    public void checkOrderCreationWithoutAuthResponse(Response response) {
        response.then()
                .body("success",is(true))
                .and()
                .body("name", notNullValue())
                .body("order",notNullValue())
                .statusCode(HTTP_OK);
    }

    @Step("Проверка ответа получения списка заказов без авторизации")
    public void checkOrderListWithoutAuthResponse(Response response) {
        response.then()
                .body("success",is(false))
                .and()
                .body("message",equalTo("You should be authorised"))
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Step("Получение списка ингредиентов")
    public IngredientsResponse getIngredients() {
        return given()
                .when()
                .get("ingredients")
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .response()
                .as(IngredientsResponse.class);
    }

    @Step("Проверка ответа при заказе без ингредиентов")
    public void checkOrderCreationWithoutIngredientsResponse(Response response) {
        response.then()
                .body("success",is(false))
                .and()
                .body("message",equalTo("Ingredient ids must be provided"))
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Step("Проверка ответа при заказе с неверным хэшем ингредиентов")
    public void checkOrderCreationWithIncorrectIngredientsResponse(Response response) {
        response.then()
                .statusCode(HTTP_SERVER_ERROR);
    }
}

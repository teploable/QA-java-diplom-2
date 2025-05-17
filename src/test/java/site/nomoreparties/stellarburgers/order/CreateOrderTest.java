package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.service.IngredientsResponse;
import site.nomoreparties.stellarburgers.service.Specification;
import site.nomoreparties.stellarburgers.user.UserData;
import site.nomoreparties.stellarburgers.user.UserSteps;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderTest {
    UserSteps userSteps = new UserSteps();
    OrderSteps orderSteps = new OrderSteps();
    String authToken;
    IngredientsResponse ingredients;
    Order order;

    @Before
    @Step("Создание пользователя")
    public void setUp() {
        Specification.setSpecification();
        Response loginResponse = userSteps.createUser(UserData.user);
        authToken = loginResponse.then().extract().path("accessToken");
    }

    @After
    @Step("Удаление пользователя")
    public void tearDown() {
        userSteps.deleteUser(authToken, UserData.user);
    }

    @Test
    @DisplayName("Авторизация, создание заказа")
    public void createOrderWithAuthTest() {
        ingredients = orderSteps.getIngredients();
        ArrayList<String> tempOrder = new OrderRandom().createRandomOrder(ingredients);
        order = new Order(tempOrder);
        Response response = orderSteps.createOrderWithAuth(authToken, order);
        orderSteps.checkOrderCreationResponse(response);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthTest() {
        ingredients = orderSteps.getIngredients();
        ArrayList<String> tempOrder = new OrderRandom().createRandomOrder(ingredients);
        order = new Order(tempOrder);
        Response response = orderSteps.createOrderWithoutAuth(order);
        orderSteps.checkOrderCreationWithoutAuthResponse(response);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        ArrayList<String> tempOrder = new ArrayList<>(List.of(new String[]{}));
        order = new Order(tempOrder);
        Response response = orderSteps.createOrderWithAuth(authToken, order);
        orderSteps.checkOrderCreationWithoutIngredientsResponse(response);
    }

    @Test
    @DisplayName("Создание заказа с неверным хэшем ингредиентов")
    public void createOrderWithIncorrectIngredientsTest() {
        ArrayList<String> tempOrder = new ArrayList<>(List.of(new String[]{"1", "2"}));
        order = new Order(tempOrder);
        Response response = orderSteps.createOrderWithAuth(authToken, order);
        orderSteps.checkOrderCreationWithIncorrectIngredientsResponse(response);
    }
}

package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.service.Specification;
import site.nomoreparties.stellarburgers.user.UserData;
import site.nomoreparties.stellarburgers.user.UserSteps;

public class OrderListTest {
    UserSteps userSteps = new UserSteps();
    OrderSteps orderSteps = new OrderSteps();
    String authToken;

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
    @DisplayName("Получение списка заказов авторизованного пользователя")
    public void getUsersOrdersTest() {
        Response response = orderSteps.getUserOrderList(authToken);
        orderSteps.checkUserOrderListResponse(response);
    }

    @Test
    @DisplayName("Получение списка заказов без авторизации")
    public void getOrdersWithoutAuthTest() {
        Response response = orderSteps.getOrdersWithoutAuth();
        orderSteps.checkOrderListWithoutAuthResponse(response);
    }
}

package site.nomoreparties.stellarburgers.service;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {
    public static void setSpecification() {
        RestAssured.requestSpecification = getRequestSpecification();
        RestAssured.responseSpecification = getResponseSpecification();
    }

    private static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("https://stellarburgers.nomoreparties.site/api")
                .setContentType(ContentType.JSON)
                .build();
    }

    private static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .build();
    }
}

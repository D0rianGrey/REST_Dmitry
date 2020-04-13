package com.herokuapp.restfulbooker;

import com.herokuapp.restfulbooking.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HealthCheckBDDGet extends BaseTest {

    @Test
    public void healthCheckTest() {

        given().
                spec(spec).
                when().
                get("/ping").
                then().assertThat().statusCode(201);
    }
}

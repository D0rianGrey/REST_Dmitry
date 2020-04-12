package com.herokuapp.restfulbooking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class BaseTest {
    protected Response createBooking() {
        // Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Eugene");
        body.put("lastname", "Vakerin");
        body.put("totalprice", 150);
        body.put("depositpaid", false);


        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2020-01-01");
        bookingDates.put("checkout", "2021-01-01");

        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "Baby crib");


        //Get response
        Response response = RestAssured.given().
                contentType(ContentType.JSON).
                body(body.toString()).
                post("https://restful-booker.herokuapp.com/booking");
        return response;
    }
}

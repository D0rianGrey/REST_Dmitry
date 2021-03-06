package com.herokuapp.restfulbooker;

import com.herokuapp.restfulbooking.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTestsGet extends BaseTest {

    @Test
    public void getBookingIdWithoutFiltersTest() {
        // Get response with booking ids

        Response response = RestAssured.given(spec).get("/booking");
        response.prettyPrint();

        //Verify response 200

        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 but it isn't");

        //Verify at least 1 booking id in response

        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of booking Ids is empty but it shouldn't be");

    }

    @Test
    public void getBookingIdsWithFilterTest() {
        //Add query parameter to spec
        spec.queryParam("firstname", "Eugene");
        spec.queryParam("lastname", "Vakerin");


        // Get response with booking ids
        Response response = RestAssured.given(spec).get("/booking");
        response.prettyPrint();

        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 but it isn't");

        //Verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of booking Ids is empty but it shouldn't be");
    }


}

package com.herokuapp.restfulbooker;

import com.herokuapp.restfulbooking.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTestWithOutGet extends BaseTest {

    @Test
    public void getBookingTest() {
        //Create booking
        Response responseCreate = createBooking();
        responseCreate.prettyPrint();

        //Get bookingID of new booking
        int bookingId = responseCreate.jsonPath().getInt("bookingid");
        System.out.println(bookingId);

        //Set path parameter
        spec.pathParam("bookingId", bookingId);

        //Get response with booking
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.prettyPrint();

        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 but it isn't");

        //Verify all fields
        SoftAssert softAssert = new SoftAssert();

        //first name
        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Eugene", "firstname in response is not expected");

        //last name
        String actualLasttName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLasttName, "Vakerin", "lastname in response is not expected");

        //total price
        int totalPrice = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(totalPrice, 150, "totalprice in response is not expected");

        //deposit paid
        boolean depositPaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositPaid, "depositpaid should be true but it isn't");

        //booking dates check in
        String actualCheckIn = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2020-01-01", "checkin in response is not expected");

        //booking dates check out
        String actualCheckOut = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2021-01-01", "checkout in response is not expected");

        //additional needs
        String additionalNeeds = response.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(additionalNeeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }
}

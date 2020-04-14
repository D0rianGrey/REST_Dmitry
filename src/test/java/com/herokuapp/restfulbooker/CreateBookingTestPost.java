package com.herokuapp.restfulbooker;

import com.herokuapp.restfulbooking.BaseTest;
import com.herokuapp.restfulbooking.Booking;
import com.herokuapp.restfulbooking.Bookingdates;
import com.herokuapp.restfulbooking.Bookingid;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTestPost extends BaseTest {

    @Test(enabled = false)
    public void createBookingTest() {
        Response response = createBooking();

        //Verifications
        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 but it isn't");

        //Verify all fields
        SoftAssert softAssert = new SoftAssert();

        //first name
        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Eugene", "firstname in response is not expected");

        //last name
        String actualLasttName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLasttName, "Vakerin", "lastname in response is not expected");

        //total price
        int totalPrice = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(totalPrice, 150, "totalprice in response is not expected");

        //deposit paid
        boolean depositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depositPaid, "depositpaid should be false but it isn't");

        //booking dates check in
        String actualCheckIn = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2020-01-01", "checkin in response is not expected");

        //booking dates check out
        String actualCheckOut = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2021-01-01", "checkout in response is not expected");

        //additional needs
        String additionalNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(additionalNeeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();

    }

    @Test(enabled = false)
    public void createBookingPOJOTest() {
        //Serialization
        //POJO - Plain Old Java Object instead of JSONObject body = new JSONObject();

        Bookingdates bookingdates = new Bookingdates("2020-01-01", "2021-01-01");
        Booking booking = new Booking("Eugene", "Vakerin", 150, false, bookingdates, "Baby crib");


        //Get response
        Response response = RestAssured.given(spec).
                contentType(ContentType.JSON).
                body(booking).
                post("/booking");
        response.prettyPrint();

        //Verifications
        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 but it isn't");

        //Verify all fields
        SoftAssert softAssert = new SoftAssert();

        //first name
        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Eugene", "firstname in response is not expected");

        //last name
        String actualLasttName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLasttName, "Vakerin", "lastname in response is not expected");

        //total price
        int totalPrice = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(totalPrice, 150, "totalprice in response is not expected");

        //deposit paid
        boolean depositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depositPaid, "depositpaid should be false but it isn't");

        //booking dates check in
        String actualCheckIn = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2020-01-01", "checkin in response is not expected");

        //booking dates check out
        String actualCheckOut = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2021-01-01", "checkout in response is not expected");

        //additional needs
        String additionalNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(additionalNeeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();

    }

    @Test
    public void createBookingPOJOTest2() {
        //De-Serialization
        //POJO - Plain Old Java Object instead of JSONObject body = new JSONObject();

        Bookingdates bookingdates = new Bookingdates("2020-01-01", "2021-01-01");
        Booking booking = new Booking("Eugene", "Vakerin", 150, false, bookingdates, "Baby crib");


        //Get response
        Response response = RestAssured.given(spec).
                contentType(ContentType.JSON).
                body(booking).
                post("/booking");
        response.prettyPrint();

        Bookingid bookingid = response.as(Bookingid.class);

        //Verifications
        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 but it isn't");
        System.out.println("Request booking : " + booking.toString());
        System.out.println("Response booking: " + bookingid.getBooking().toString());

        //Verify all fields
        Assert.assertEquals(bookingid.getBooking().toString(), booking.toString());

    }
}

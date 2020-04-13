package com.herokuapp.restfulbooker;

import com.herokuapp.restfulbooking.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTestPut extends BaseTest {

    @Test
    public void updateBookingTest() {
        //Create booking
        Response responseCreate = createBooking();
        responseCreate.prettyPrint();

        //Get bookingID of new booking
        int bookingId = responseCreate.jsonPath().getInt("bookingid");
        System.out.println(bookingId);

        //Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Volodya");
        body.put("lastname", "Vakerin");
        body.put("totalprice", 175);
        body.put("depositpaid", false);


        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2020-01-01");
        bookingDates.put("checkout", "2021-01-01");

        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "Baby crib");


        //Update booking
        Response responseUpdate = RestAssured.given(spec).
                auth().
                preemptive().
                basic("admin", "password123").
                contentType(ContentType.JSON).
                body(body.toString()).
                put("/booking/" + bookingId);
        responseUpdate.prettyPrint();

        //Verifications
        //Verify response 200
        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status should be 200 but it isn't");

        //Verify all fields
        SoftAssert softAssert = new SoftAssert();

        //first name
        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Volodya", "firstname in response is not expected");

        //last name
        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Vakerin", "lastname in response is not expected");

        //total price
        int totalPrice = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(totalPrice, 175, "totalprice in response is not expected");

        //deposit paid
        boolean depositPaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositPaid, "depositpaid should be false but it isn't");

        //booking dates check in
        String actualCheckIn = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2020-01-01", "checkin in response is not expected");

        //booking dates check out
        String actualCheckOut = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2021-01-01", "checkout in response is not expected");

        //additional needs
        String additionalNeeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(additionalNeeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();


    }
}

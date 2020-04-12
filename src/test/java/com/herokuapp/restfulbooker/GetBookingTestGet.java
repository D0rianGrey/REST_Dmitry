package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTestGet {

    @Test
    public void getBookingTest() {
        //Get response with booking
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/1");
        response.print();

        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 but it isn't");

        //Verify all fields
        SoftAssert softAssert = new SoftAssert();

        //first name
        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Eric", "firstname in response is not expected");

        //last name
        String actualLasttName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLasttName, "Jackson", "lastname in response is not expected");

        //total price
        int totalPrice = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(totalPrice, 170, "totalprice in response is not expected");

        //deposit paid
        boolean depositPaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositPaid, "depositpaid should be true but it isn't");

        //booking dates check in
        String actualCheckIn = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2020-01-24", "checkin in response is not expected");

        //booking dates check out
        String actualCheckOut = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2020-03-04", "checkout in response is not expected");

        //additional needs
        String additionalNeeds = response.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(additionalNeeds, "Breakfast", "additionalneeds in response is not expected");

        softAssert.assertAll();





        /*String firstName = response.jsonPath().get("firstname");
        String lastName = response.jsonPath().get("lastname");
        Assert.assertFalse(firstName.isEmpty());
        Assert.assertFalse(lastName.isEmpty());
        System.out.println("First name - " + firstName + ", last name - " + lastName);*/

        /*
  {
  "firstname":"Eric",
  "lastname":"Jackson",
  "totalprice":170,
  "depositpaid":true,
  "bookingdates":
  {
  "checkin":"2020-01-24",
  "checkout":"2020-03-04"
  },
  "additionalneeds":"Breakfast"}
}
         */
    }
}

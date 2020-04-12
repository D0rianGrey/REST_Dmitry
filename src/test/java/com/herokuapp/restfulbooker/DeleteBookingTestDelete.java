package com.herokuapp.restfulbooker;

import com.herokuapp.restfulbooking.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTestDelete extends BaseTest {

    @Test
    public void deleteBookingTest() {
        //Create booking
        Response responseCreate = createBooking();
        responseCreate.prettyPrint();

        //Get bookingID of new booking
        int bookingId = responseCreate.jsonPath().getInt("bookingid");
        System.out.println(bookingId);

        //Delete booking
        Response responseDelete = RestAssured.given().
                auth().
                preemptive().
                basic("admin", "password123").
                delete("https://restful-booker.herokuapp.com/booking/" + bookingId);
        responseDelete.prettyPrint();

        //Verifications
        //Verify response 200
        Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status should be 201 but it isn't");

        Response responseGet = RestAssured.get("https://restful-booker.herokuapp.com/booking" + bookingId);
        responseGet.prettyPrint();

        Assert.assertEquals(responseGet.getBody().asString(), "Not Found", "Body should be 'Not Found', but it isn't");
    }
}

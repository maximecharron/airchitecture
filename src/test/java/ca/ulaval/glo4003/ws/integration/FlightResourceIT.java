package ca.ulaval.glo4003.ws.integration;

import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.hasItem;

public class FlightResourceIT{

    @Test
    public void givenAFlightFromAToB_whenSearchingFlightsFromAToB_thenThisFlightIsReturned() {
        get("/api/search/flights?from=YQB&to=DUB&datetime=2017-04-23T20:15").then().body("availableSeats", hasItem(100));
    }
}

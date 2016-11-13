package ca.ulaval.glo4003.air.integration;

import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;

public class FlightResourceIT {

    @Test
    public void givenAFlightFromAToB_whenSearchingFlightsFromAToB_thenThisFlightIsReturned() {
        get("/api/search/flights?from=YQB&to=DUB&datetime=2017-04-23T20:15&weight=5").then().body("flights[0].arrivalAirport", equalTo("DUB"));
    }
}

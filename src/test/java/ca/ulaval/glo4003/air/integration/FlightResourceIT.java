package ca.ulaval.glo4003.air.integration;

import org.junit.Ignore;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.hasItem;

public class FlightResourceIT {
    @Ignore
    @Test
    public void givenAFlightFromAToB_whenSearchingFlightsFromAToB_thenThisFlightIsReturned() {
        get("/api/search/flights?from=YQB&to=DUB&datetime=2017-04-23T20:15&weight=5").then().body("flights", hasItem(hasItem("AF0001")));
    }
}

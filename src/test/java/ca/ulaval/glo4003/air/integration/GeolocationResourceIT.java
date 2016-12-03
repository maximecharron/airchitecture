package ca.ulaval.glo4003.air.integration;

import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;

public class GeolocationResourceIT {

    @Test
    public void givenARequestForNearestAirport_whenGeolocalizingIPAddress_thenAnAirportIATAIsReturned() {
        get("/api/geolocation/findNearestAirport")
            .then()
            .body("nearestAirportIATA",
                anyOf(
                    equalTo("YQB"),
                    equalTo("SNN"),
                    equalTo("DUB")));
    }
}

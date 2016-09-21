package ca.ulaval.glo4003.ws.integration;

import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.hasItem;

public class ContactResourceIT extends AbstractIntegrationTest {

    @Test
    public void givenContacts_whenGetAllContacts_thenContactsReturned() {
        get("/api/telephony/contacts").then().body("name", hasItem("Steve Jobs"));
    }
}

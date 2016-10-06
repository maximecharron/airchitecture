package ca.ulaval.glo4003.ws.integration;

import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UserResourceIT {

    @Test
    public void givenValidCredentials_whenLogin_thenLoginIsSuccessful() {
        given().formParam("email", "bob@test.com")
               .formParam("password", "1234")
               .contentType("application/x-www-form-urlencoded")
               .post("http://localhost:8888/api/auth/login")
               .then()
               .statusCode(200);
    }

    @Test
    public void givenANewUser_whenSigningUp_thenSignUpIsSuccessful() {
        given().formParam("email", "ginette@test.com")
               .formParam("password", "12345")
               .contentType("application/x-www-form-urlencoded")
               .post("http://localhost:8888/api/auth/signup")
               .then()
               .statusCode(204);
    }
}

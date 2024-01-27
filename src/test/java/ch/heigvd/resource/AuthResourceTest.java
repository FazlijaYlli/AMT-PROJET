package ch.heigvd.resource;

import ch.heigvd.entities.Account;
import ch.heigvd.service.API;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class AuthResourceTest {


    @Test
    void meShouldReturnErrorWhenNotLoggedIn() {
        String expectedResponse = API.createErrorResponse("Not logged in").toString();

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/me")
                .then()
                .body(equalTo(expectedResponse));
    }

    @Test
    void registerShouldReturnErrorWhenMissingParameters() {
        String expectedResponse = API.createErrorResponse("Missing parameters").toString();

        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/register")
                .then()
                .body(equalTo(expectedResponse));
    }

    @Test
    @TestTransaction
    void registerShouldReturnSuccessWhenAccountCreated() {

        Account account = new Account();
        account.setId(1L);
        account.setUsername("user");
        account.setEmail("user@example.com");
        account.setPassword("password");


        JsonObject expectedResponse = API.createResponse("Account created", account);

        given()
                .contentType(ContentType.URLENC)
                .formParam("username", "user")
                .formParam("email", "user@example.com")
                .formParam("password", "password")
                .when()
                .post("/register")
                .then()
                .body(containsString(((JsonObject)expectedResponse.get("data")).get("username").toString()));
    }

    @Test
    @TestTransaction
    void loginShouldReturnSuccessWhenCredentialsAreValid() {

        given()
                .contentType(ContentType.URLENC)
                .formParam("username", "newUser")
                .formParam("email", "new@example.com")
                .formParam("password", "password")
                .when()
                .post("/register")
                .then()
                .statusCode(200);


        given()
                .contentType(ContentType.URLENC)
                .formParam("email", "new@example.com")
                .formParam("password", "password")
                .when()
                .post("/login")
                .then()
                .statusCode(200);
    }

}
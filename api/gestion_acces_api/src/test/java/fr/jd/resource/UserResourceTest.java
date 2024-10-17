package fr.jd.resource;

import fr.jd.service.CreateAdressDTO;
import fr.jd.service.CreateUserDTO;
import fr.jd.service.GetCompleteUserDTO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UserResourceTest {

    @Inject
    UserResource userResource;

    @Test
    public void testCreateUser() {
        String data = "{\n" +
                "\t\"username\":\"test\",\n" +
                "\t\"email\":\"test@test.com\",\n" +
                "\t\"password\":\"chuipashash\",\n" +
                "\t\"role\":\"user\",\n" +
                "\t\"country\": \"France\",\n" +
                "\t\"zip\": \"45987\",\n" +
                "\t\"city\": \"Los Santos\",\n" +
                "\t\"street\": \"liberty\",\n" +
                "\t\"number\": \"45\"\n" +
                "}";

        given()
                .contentType("application/json")
                .body(data)
                .when().post("/user")
                .then()
                .statusCode(200) // Vérifie que la création a réussi
                .body("username", is("test")) // Vérifie le body de la réponse
                .body("email", is("test@test.com"));
    }
}

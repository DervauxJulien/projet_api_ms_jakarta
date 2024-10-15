package fr.jd;

import fr.jd.entity.Users;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Random;

@QuarkusTest
class UserResourceTest {
    @Test
    void testGetAllUsers() throws JsonProcessingException {
        List<Users> usersList = Users.listAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(usersList);

        given()
                .when().get("/users")
                .then()
                .statusCode(200)
                .body("size()", is(usersList.size()));
    }

    @Test
    void testGetUserById() throws JsonProcessingException {
        Integer userId = new Random().nextInt(100);

        Users user = Users.findById(userId);

        if (user != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String expectedJson = objectMapper.writeValueAsString(user);

            given()
                    .when().get("/users/" + userId)
                    .then()
                    .statusCode(200)
                    .body(is(expectedJson));
        } else {
            given()
                    .when().get("/users/" + userId)
                    .then()
                    .statusCode(404);
        }
    }

    @Test
    void testUpdateUser() throws JsonProcessingException {
        Users user = Users.findById(1);

        if (user != null) {
            Users updatedUser = new Users();
            updatedUser.setUsername("newUsername");
            updatedUser.setEmail("newemail@example.com");

            ObjectMapper objectMapper = new ObjectMapper();
            String userJson = objectMapper.writeValueAsString(updatedUser);

            given()
                    .contentType("application/json")
                    .body(userJson)
                    .when().patch("/users/update/" + user.getId())
                    .then()
                    .statusCode(200);

            Users updatedEntity = Users.findById(user.getId());
            given()
                    .when().get("/users/" + user.getId())
                    .then()
                    .statusCode(200)
                    .body("username", is(updatedEntity.getUsername()))
                    .body("email", is(updatedEntity.getEmail()));
        } else {
            System.out.println("Utilisateur non trouvé dans la base de données");
        }
    }



}
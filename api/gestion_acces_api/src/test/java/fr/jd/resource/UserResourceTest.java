package fr.jd.resource;

import fr.jd.entity.Users;
import fr.jd.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
class UserResourceTest {

    @InjectMocks
    UserResource userResource;

    @Mock
    UserRepository userRepository;

    // Cette méthode s'assure que les mocks sont initialisés avant chaque test
    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Créer des utilisateurs fictifs
        Users user1 = new Users();
        user1.setUsername("User1");
        user1.setEmail("user1@example.com");

        Users user2 = new Users();
        user2.setUsername("User2");
        user2.setEmail("user2@example.com");

        List<Users> mockUsers = Arrays.asList(user1, user2);

        when(userRepository.listAll()).thenReturn(mockUsers);

        List<Users> result = userResource.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("User1", result.get(0).getUsername());
        assertEquals("user2@example.com", result.get(1).getEmail());
    }

    @Test
    void testGetUserById() {
        Users mockUser = new Users();
        mockUser.setId(1);
        mockUser.setUsername("User1");
        mockUser.setEmail("user1@example.com");

        when(userRepository.findById(1)).thenReturn(mockUser);

        // Tester la méthode
        Users result = userResource.getUserById(1);

        assertEquals("User1", result.getUsername());
        assertEquals("user1@example.com", result.getEmail());
    }

    @Test
    @Transactional
    void testUpdateUser() {

        Users existingUser = new Users();
        existingUser.setId(1);
        existingUser.setUsername("OldUser");
        existingUser.setEmail("olduser@example.com");

        when(userRepository.findById(1)).thenReturn(existingUser);


        Users userUpdates = new Users();
        userUpdates.setUsername("NewUser");
        userUpdates.setEmail("newuser@example.com");

        Response response = userResource.updateUser(1, userUpdates);

        // Vérifie que l'utilisateur a bien été mis à jour
        Users updatedUser = (Users) response.getEntity();
        assertEquals("NewUser", updatedUser.getUsername());
        assertEquals("newuser@example.com", updatedUser.getEmail());
    }

    @Test
    @Transactional
    void testDeleteOneUser() {

        Users mockUser = new Users();
        mockUser.setId(1);

        when(userRepository.findById(1)).thenReturn(mockUser);

        Response response = userResource.deleteOneUser(1L);

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}

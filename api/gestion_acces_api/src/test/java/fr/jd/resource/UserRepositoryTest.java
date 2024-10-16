package fr.jd.resource;

import fr.jd.entity.Users;
import fr.jd.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    @Test
    void testFindAllUsers() {
        List<Users> users = userRepository.listAll();
        assertEquals(0, users.size());
    }
}


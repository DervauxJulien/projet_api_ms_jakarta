package fr.jd.service;

import fr.jd.entity.Users;
import fr.jd.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CreateUserDTO {

    @Inject
    UserRepository userRepository;

    public static class CreateUserData {
        public String username;
        public String email;
        public String password;
        public String role;
    }

    public Boolean checkDataValidity(CreateUserData createUserData) {
        if (createUserData.username == null)
            return false;
        if (createUserData.password == null)
            return false;
        if (createUserData.role == null)
            return false;
        if (createUserData.email == null || userRepository.find("email", createUserData.email).stream().count() != 0)
            return false;
        return true;
    }

    public static Users createUser(CreateUserData createUserData) {
        Users userDTO = new Users();
        userDTO.setUsername(createUserData.username);
        userDTO.setPassword(createUserData.password);
        userDTO.setRole(createUserData.role);
        userDTO.setEmail(createUserData.email);
        return userDTO;
    }
}

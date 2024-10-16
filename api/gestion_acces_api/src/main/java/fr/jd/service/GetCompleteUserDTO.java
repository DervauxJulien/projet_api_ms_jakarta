package fr.jd.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetCompleteUserDTO {

    public static class CreateCompletUserData {
        public String username;
        public String email;
        public String password;
        public String role;
        public String country;
        public String zip;
        public String city;
        public String street;
        public String number;
    }
}

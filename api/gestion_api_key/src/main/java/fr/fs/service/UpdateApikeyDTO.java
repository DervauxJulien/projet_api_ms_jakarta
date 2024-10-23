package fr.fs.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Date;

@ApplicationScoped
public class UpdateApikeyDTO {

    public static class updateApikeyDTO {
        public String admin_email;
        public Integer quota;
        public Date expiration_date;
    }
}

package fr.fs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Apikey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_apiKey")
    private int id;
    @Column(name = "api_key")
    private String apiKey;
    @Column(name = "quota")
    private int quota;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "expiration_date")
    private Date expirationDate;
    @Column(name = "admin_email")
    private String adminEmail;
}

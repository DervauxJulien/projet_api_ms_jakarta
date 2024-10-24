package fr.fs.secure;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Setter;
import lombok.Getter;

@Entity(name = "users")
@Getter
@Setter
public class User extends PanacheEntityBase {
    private String username;
    private String email;
    private String password;
    private String role;// Use e.g Bcrypt to encrypt password, don't store it as plain text :)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
}
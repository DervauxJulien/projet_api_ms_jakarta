package fr.jd.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
public class Users extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;
    public String username;
    public String email;
    public String password;
    public String role;
    public boolean isLock;
    public Date last_connection;
    public Integer pin;
    @OneToOne
    @JoinColumn(name = "id_adress")
    public Adress adress;
}

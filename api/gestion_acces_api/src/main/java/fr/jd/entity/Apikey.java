package fr.jd.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Apikey extends PanacheEntityBase {

    @Id
    @Column(name = "apiKey")
    private Integer id;
    @Column(name = "quota")
    public int quota;
    @Column(name = "creation_date")
    public Date creation_date;
    @Column(name = "expiration_date")
    public Date expiration_date;
}

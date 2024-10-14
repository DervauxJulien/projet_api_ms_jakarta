package fr.jd.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Adress extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adress")
    private Integer id;
    @Column(name = "country")
    public String country;
    @Column(name = "zip")
    public String zip;
    @Column(name = "city")
    public String city;
    @Column(name = "street")
    public String street;
    @Column(name = "number")
    public String number;
}

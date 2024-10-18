package fr.jd.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Mail extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mail")
    private Integer id;
    @Column(name = "to")
    public String to;
    @Column(name = "object")
    public String object;
    @Column(name = "sending_date")
    public Date sending_date;
}

package fr.jd.repository;

import fr.jd.entity.Adress;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdressRepository implements PanacheRepositoryBase<Adress, Integer> {
}

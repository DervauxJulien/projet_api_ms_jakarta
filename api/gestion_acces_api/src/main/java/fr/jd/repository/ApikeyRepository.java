package fr.jd.repository;

import fr.jd.entity.Apikey;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApikeyRepository implements PanacheRepositoryBase<Apikey, Integer> {
}

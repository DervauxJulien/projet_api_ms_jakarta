package fr.fs.repository;

import fr.fs.entity.Apikey;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApikeyRepository implements PanacheRepositoryBase<Apikey, Integer> {
}

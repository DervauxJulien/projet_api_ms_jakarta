package fr.jd.repository;

import fr.jd.entity.Mail;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MailRepository implements PanacheRepositoryBase<Mail, Integer> {
}

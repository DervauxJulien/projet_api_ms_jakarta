package fr.fs.secure;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;

// Service pour la logique métier
@ApplicationScoped
class UserService {
    @Inject
    EntityManager em;

    public boolean existsByLoginOrEmail(String login, String email) {
        return User.count("username = ?1 or email = ?2", login, email) > 0;
    }

    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(
                User.find("username", login).firstResult()
        );
    }

    @Transactional
    public User save(User user) {
        user.persist();
        return user;
    }
}

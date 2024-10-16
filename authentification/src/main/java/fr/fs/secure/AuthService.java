package fr.fs.secure;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    @Inject
    EntityManager em;

    @Transactional
    public String authenticate(String username, String password) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            if (user != null && BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray()).verified) {
                Set<String> roles = new HashSet<>();
                roles.add(user.getRole());
                return TokenUtils.generateToken(username, roles);
            }
        } catch (NoResultException e) {
            // Nom d'utilisateur non trouvé
        }
        return null;
    }

    public void registerUser(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
        user.setRole(role);
        em.persist(user);
    }
}

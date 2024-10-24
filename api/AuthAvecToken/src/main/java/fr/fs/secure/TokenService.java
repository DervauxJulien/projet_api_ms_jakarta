package fr.fs.secure;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@ApplicationScoped
public class TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    private static final String ISSUER = "secure";
    private static final String AUDIENCE = "using-jwt";
    private static final Duration TOKEN_VALIDITY = Duration.ofMinutes(60);

    /**
     * Génère un token pour un utilisateur
     */
    public String generateUserToken(String email, String username) {
        try {
            validateInput(email);
            validateInput(username);
            return generateToken(email, username, Roles.USER);
        } catch (Exception e) {
            throw new TokenGenerationException("Erreur lors de la génération du token utilisateur", e);
        }
    }

    /**
     * Génère un token pour un service
     */
    public String generateServiceToken(String serviceId, String serviceName) {
        try {
            validateInput(serviceId);
            validateInput(serviceName);
            return generateToken(serviceId, serviceName, Roles.SERVICE);
        } catch (Exception e) {
            throw new TokenGenerationException("Erreur lors de la génération du token service", e);
        }
    }

    private String generateToken(String subject, String name, String... roles) {
        try {
            validateInput(subject);
            validateInput(name);
            validateRoles(roles);

            String token = Jwt.claims()
                    .issuer(ISSUER)
                    .subject(subject)
                    .claim("jti", UUID.randomUUID().toString())
                    .claim(Claims.upn.name(), subject)
                    .claim(Claims.preferred_username.name(), name)
                    .claim(Claims.groups.name(), new HashSet<>(Arrays.asList(roles)))  // Modification ici
                    .audience(AUDIENCE)
                    .issuedAt(System.currentTimeMillis() / 1000L)
                    .expiresAt(System.currentTimeMillis() / 1000L + TOKEN_VALIDITY.toSeconds())
                    .sign();

            LOGGER.debug("Token generated for subject: {}", subject);
            return token;

        } catch (Exception e) {
            throw new TokenGenerationException("Échec de la génération du token", e);
        }
    }

    private void validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new TokenGenerationException("Paramètre invalide : la valeur ne peut pas être nulle ou vide");
        }
    }

    private void validateRoles(String[] roles) {
        if (roles == null || roles.length == 0) {
            throw new TokenGenerationException("Les rôles ne peuvent pas être nuls ou vides");
        }
        for (String role : roles) {
            validateInput(role);
        }
    }
}

class TokenGenerationException extends RuntimeException {
    public TokenGenerationException(String message) {
        super(message);
    }

    public TokenGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
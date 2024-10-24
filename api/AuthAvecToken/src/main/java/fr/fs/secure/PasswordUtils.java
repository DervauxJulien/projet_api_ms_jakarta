package fr.fs.secure;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordUtils.class);

    // Hachage du mot de passe
    public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt();
        LOGGER.debug("Generated salt: " + salt); // Ajoute ce log
        String hashedPassword = BCrypt.hashpw(plainTextPassword, salt);
        LOGGER.debug("Hashed password: " + hashedPassword); // Ajoute ce log
        return hashedPassword;
    }

    // Vérification du mot de passe
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}

package fr.fs.secure;

import io.smallrye.jwt.build.Jwt;
import java.util.Set;

public class TokenUtils {

    public static String generateToken(String username, Set<String> roles) {
        return Jwt.issuer("your-issuer")
                .upn(username)
                .groups(roles)
                .expiresIn(3600)
                .sign();
    }
}

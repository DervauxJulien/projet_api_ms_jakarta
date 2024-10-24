package fr.fs.secure;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Inject
    TokenService tokenService;

    @Inject
    UserService userService;

    public static class RegisterRequest {
        @NotBlank(message = "Le login est obligatoire")
        @Size(min = 3, max = 50, message = "Le login doit faire entre 3 et 50 caractères")
        public String login;

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Format d'email invalide")
        public String email;

        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 8, message = "Le mot de passe doit faire au moins 8 caractères")
        public String password;
        public String role;
    }

    public static class LoginRequest {
        @NotBlank(message = "Le login est obligatoire")
        public String login;

        @NotBlank(message = "Le mot de passe est obligatoire")
        public String password;
    }

    public static class AuthResponse {
        public String token;
        public UserDTO user;

        public AuthResponse(String token, UserDTO user) {
            this.token = token;
            this.user = user;
        }
    }

    @POST
    @Path("/register")
    @Transactional
    public Response register(@Valid RegisterRequest request) {
        try {
            // Vérifier si l'utilisateur existe déjà
            if (userService.existsByLoginOrEmail(request.login, request.email)) {
                return Response.status(Status.CONFLICT)
                        .entity(new ErrorResponse("Un utilisateur existe déjà avec ce login ou cet email"))
                        .build();
            }

            // Créer l'utilisateur
            User user = new User();
            user.setUsername(request.login);
            user.setEmail(request.email);
            user.setPassword(PasswordUtils.hashPassword(request.password)); 
            user.setRole(request.role);// Utiliser PasswordUtils

            userService.save(user);

            // Générer le token
            String token = tokenService.generateUserToken(user.getEmail(), user.getUsername());

            return Response.status(Status.CREATED)
                    .entity(new AuthResponse(token, UserDTO.from(user)))
                    .build();

        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'inscription", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erreur lors de l'inscription"))
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {
        try {
            return userService.findByLogin(request.login)
                    .filter(user -> PasswordUtils.checkPassword(request.password, user.getPassword())) // Utiliser PasswordUtils
                    .map(user -> {
                        try {
                            String token = tokenService.generateUserToken(user.getEmail(), user.getUsername());
                            return Response.ok(new AuthResponse(token, UserDTO.from(user))).build();
                        } catch (TokenGenerationException e) {
                            LOGGER.error("Erreur lors de la génération du token", e);
                            return Response.status(Status.INTERNAL_SERVER_ERROR)
                                    .entity(new ErrorResponse("Erreur lors de la génération du token"))
                                    .build();
                        }
                    })
                    .orElse(Response.status(Status.UNAUTHORIZED)
                            .entity(new ErrorResponse("Login ou mot de passe incorrect"))
                            .build());
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la connexion", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erreur lors de la connexion"))
                    .build();
        }
    }

    // DTO pour ne pas exposer les données sensibles
    public static class UserDTO {
        public Long id_user;
        public String login;
        public String email;
        public String role;

        public static UserDTO from(User user) {
            UserDTO dto = new UserDTO();
            dto.id_user = user.getId_user();
            dto.login = user.getUsername();
            dto.email = user.getEmail();
            dto.role = user.getRole();
            return dto;
        }
    }

    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}

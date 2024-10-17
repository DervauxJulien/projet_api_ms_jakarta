package fr.jd.resources;

import fr.jd.entity.Mail;
import fr.jd.entity.Users;
import io.quarkus.mailer.Mailer;
import io.quarkus.qute.Template;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.Random;

@Path("/email")
public class EmailResource {

    @Inject
    Mailer mailer;

    @Inject
    Template hello;

    @GET
    @Path("/send-verification")
    @Transactional
    public Uni<Mail> sendVerificationEmail(@QueryParam("to") String to) {

        int pin = Integer.parseInt(generatePinCode());

        Users user = findOrCreateUser(to);

        storePinInDatabase(user, pin);

       mailer.send(
                io.quarkus.mailer.Mail.withHtml(
                        to,
                        "Votre code de vérification",
                        hello.data("name", user.getUsername(), "pin", pin).render()
                )
        );
        return null;
    }

    private String generatePinCode() {
        Random random = new Random();
        int pin = 100000 + random.nextInt(900000);  // Génère un nombre à 6 chiffres
        return String.valueOf(pin);
    }

    private Users findOrCreateUser(String email) {
        Users user = Users.find("email", email).firstResult();

        if (user == null) {
            user = new Users();
            user.setEmail(email);
            user.setUsername("Utilisateur");
            user.persist();
        }

        return user;
    }

    private void storePinInDatabase(Users user, int pin) {
        System.out.println("Sauvegarder le code PIN pour l'utilisateur " + user.getEmail() + " : " + pin);

        user.setPin(pin);
        user.persist();
    }
}

package fr.jd.resources;

import fr.jd.entity.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.qute.Template;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/email")
public class EmailResource {

    @Inject
    Mailer mailer;

    @Inject
    Template hello;

    Mail mail = new Mail();

    @GET
    @Path("/send-verification")
    @Transactional
    public Uni<Response> sendVerificationEmail(Mail mail) {

        return mailer.send(
                io.quarkus.mailer.Mail.withHtml(
                        mail.to,
                        "Votre code de vérification",
                        hello.data("name", mail.to, "pinCode", @RequestBody pin).render()
                )
        );
    }
}

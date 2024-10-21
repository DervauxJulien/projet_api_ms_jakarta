package fr.jd.resources;

import fr.jd.model.VerificationRequestPinDTO;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/email")
public class EmailResource {

    @Inject
    Mailer mailer;

    @POST
    @Path("/send-verification")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Uni<Response> sendVerificationEmail(VerificationRequestPinDTO request) {

        if (request.email == null || request.object == null ) {
            return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email et objet sont obligatoires").build());
        }

            mailer.send(
                    Mail.withText(request.email,
                            request.object,
                            "A simple email sent from a Quarkus application by JULIEN."
                    )
            );
        return Uni.createFrom().item(Response.ok().build());
    }
}


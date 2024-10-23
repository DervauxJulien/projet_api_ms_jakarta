package fr.fs.resource;

import fr.fs.entity.Apikey;
import fr.fs.repository.ApikeyRepository;
import fr.fs.service.CreateApikeyDTO;
import fr.fs.service.UpdateApikeyDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Path("/apikey")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApikeyResource {

    @Inject
    ApikeyRepository apikeyRepository;
    @Inject
    CreateApikeyDTO apikeyDTO;
    @Inject
    UpdateApikeyDTO updateApikeyDTO;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addApikey(CreateApikeyDTO.createApikeyDTO apikey) throws NoSuchAlgorithmException {
        Apikey createdApikey = apikeyDTO.createApikey(apikey);
        createdApikey.setApiKey(CreateApikeyDTO.generateAPIKey());
        apikeyRepository.persist(createdApikey);
        return Response.ok().build();
    }

    @POST
    @Transactional
    @Path("/{adminEmail}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateApikey(UpdateApikeyDTO.updateApikeyDTO apikeyDTO, @PathParam("adminEmail") String adminEmail) throws NoSuchAlgorithmException {
        Apikey apikey = apikeyRepository.find("adminEmail", adminEmail).stream().findFirst().get();
        if (apikeyDTO.admin_email != null) { apikey.setAdminEmail(apikeyDTO.admin_email); }
        if (apikeyDTO.quota != null) { apikey.setQuota(apikeyDTO.quota); }
        if (apikeyDTO.expiration_date != null) { apikey.setExpirationDate(apikeyDTO.expiration_date); }
        apikeyRepository.persist(apikey);

        return Response.ok(apikey).build();
    }

    @GET
    @Path("/{adminEmail}")
    public Apikey getApikeyByAdminEmail(@PathParam("adminEmail") String adminEmail) {
        return apikeyRepository.find("adminEmail", adminEmail).stream().findFirst().orElse(null);
    }

    @GET
    @Path("/isValid")
    public Boolean isValid(@HeaderParam("apikey") String apikey) {
        if (apikeyRepository.find("apiKey", apikey).stream().count() == 0) {
            return false;
        }
        return true;
    }

    @DELETE
    @Transactional
    @Path("/{adminEmail}")
    public Response deleteApikey(@PathParam("adminEmail") String adminEmail) {
        Apikey apikey = apikeyRepository.find("adminEmail", adminEmail).stream().findFirst().get();
        apikeyRepository.delete(apikey);
        return Response.ok().build();
    }
}

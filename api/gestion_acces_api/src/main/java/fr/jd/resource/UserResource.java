package fr.jd.resource;

import fr.jd.entity.Users;
import fr.jd.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
public class UserResource {
    @Inject
    UserRepository userRepository;

    @GET
    public List<Users> getAllUsers() {
        return Users.listAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Users getUserById(@PathParam("id") int id) {
        Users entity = Users.findById(id);
        if (entity == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return entity;
    }

    @DELETE
    @Transactional
    @Path("delete/{id}")
    public Response deleteOne(@PathParam("id") Long id) {
        Users entity = Users.findById(id);
        if (entity == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        entity.delete();
        return Response.noContent().build();
    }



}

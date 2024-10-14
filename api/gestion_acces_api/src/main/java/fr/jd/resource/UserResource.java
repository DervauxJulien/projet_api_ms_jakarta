package fr.jd.resource;

import fr.jd.entity.Adress;
import fr.jd.entity.Users;
import fr.jd.repository.AdressRepository;
import fr.jd.repository.UserRepository;
import fr.jd.service.CreateAdressDTO;
import fr.jd.service.CreateUserDTO;
import fr.jd.service.GetCompleteUserDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserRepository userRepository;
    @Inject
    CreateUserDTO userDTO;
    @Inject
    AdressResource adressResource;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(GetCompleteUserDTO.CreateCompletUserData userData) {
        CreateAdressDTO.CreateAdressData createAdressData = new CreateAdressDTO.CreateAdressData();
        CreateUserDTO.CreateUserData createUserData = new CreateUserDTO.CreateUserData();

        createAdressData.city = userData.city;
        createAdressData.country = userData.country;
        createAdressData.street = userData.street;
        createAdressData.zip = userData.zip;
        createAdressData.number = userData.number;

        Adress adress = adressResource.createAdress(createAdressData);


        createUserData.username = userData.username;
        createUserData.password = userData.password;
        createUserData.email = userData.email;
        createUserData.role = userData.role;

        if (userDTO.checkDataValidity(createUserData)) {
            Users createdUser = CreateUserDTO.createUser(createUserData);
            createdUser.setAdress(adress);
            userRepository.persist(createdUser);
            return Response.ok(createdUser).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

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

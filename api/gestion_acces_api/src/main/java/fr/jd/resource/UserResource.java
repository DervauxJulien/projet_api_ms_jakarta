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

    @Transactional
    @PATCH
    @Path("update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Integer id, Users userUpdates) {
        Users entity = Users.findById(id);
        if (entity == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        if (userUpdates.getUsername() != null) {
            entity.setUsername(userUpdates.getUsername());
        }
        if (userUpdates.getEmail() != null) {
            entity.setEmail(userUpdates.getEmail());
        }
        if (userUpdates.getPassword() != null) {
            entity.setPassword(userUpdates.getPassword());
        }
        if (userUpdates.getRole() != null) {
            entity.setRole(userUpdates.getRole());
        }
        if (userUpdates.isLock() != entity.isLock()) {
            entity.setLock(userUpdates.isLock());
        }
        if (userUpdates.getLast_connection() != null) {
            entity.setLast_connection(userUpdates.getLast_connection());
        }
        if (userUpdates.getPin() != null) {
            entity.setPin(userUpdates.getPin());
        }

        if (userUpdates.getAdress() != null) {
            if (entity.getAdress() == null) {
                entity.setAdress(userUpdates.getAdress());
            } else {
                Adress updatedAdress = userUpdates.getAdress();
                Adress currentAdress = entity.getAdress();

                if (updatedAdress.getStreet() != null) {
                    currentAdress.setStreet(updatedAdress.getStreet());
                }
                if (updatedAdress.getCity() != null) {
                    currentAdress.setCity(updatedAdress.getCity());
                }
                if (updatedAdress.getZip() != null) {
                    currentAdress.setZip(updatedAdress.getZip());
                }
            }
        }
        return Response.ok(entity).build();
    }

    @DELETE
    @Transactional
    @Path("delete/{id}")
    public Response deleteOneUser(@PathParam("id") Long id) {
        Users entity = Users.findById(id);
        if (entity == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        entity.delete();
        return Response.noContent().build();
    }



}

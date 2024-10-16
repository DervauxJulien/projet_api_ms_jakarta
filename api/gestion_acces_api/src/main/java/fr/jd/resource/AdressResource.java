package fr.jd.resource;

import fr.jd.entity.Adress;
import fr.jd.repository.AdressRepository;
import fr.jd.service.CreateAdressDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AdressResource {

    @Inject
    CreateAdressDTO createAdressDTO;
    @Inject
    AdressRepository adressRepository;

    public Adress createAdress(CreateAdressDTO.CreateAdressData createAdressData) {
        Adress createdAdress = createAdressDTO.createAdress(createAdressData);
        try {
            adressRepository.persist(createdAdress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createdAdress;
    }
}

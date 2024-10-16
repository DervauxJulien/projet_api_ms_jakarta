package fr.jd.service;

import fr.jd.entity.Adress;
import fr.jd.repository.AdressRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CreateAdressDTO {

    @Inject
    AdressRepository adressRepository;

    public static class CreateAdressData {
        public String country;
        public String zip;
        public String city;
        public String street;
        public String number;
    }

    public static Adress createAdress(CreateAdressData data) {
        Adress adressDTO = new Adress();
        adressDTO.setCountry(data.country);
        adressDTO.setZip(data.zip);
        adressDTO.setCity(data.city);
        adressDTO.setStreet(data.street);
        adressDTO.setNumber(data.number);
        return adressDTO;
    }
}

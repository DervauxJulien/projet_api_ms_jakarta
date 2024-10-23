package fr.fs.service;

import fr.fs.entity.Apikey;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.DatatypeConverter;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@ApplicationScoped
public class CreateApikeyDTO {

    public static class createApikeyDTO {
        public String admin_email;
        public int quota;
    }

    public static Apikey createApikey(createApikeyDTO apikeyData) {
        Apikey apikey = new Apikey();
        apikey.setAdminEmail(apikeyData.admin_email);
        apikey.setQuota(apikeyData.quota);
        apikey.setCreationDate(new Date());
        return apikey;
    }

    public static String generateAPIKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        byte[] publicKey = keyGen.genKeyPair().getPublic().getEncoded();
        String base64Binary = DatatypeConverter.printBase64Binary(publicKey).replaceAll("/", "");

        return base64Binary.substring(base64Binary.length() - 32);
    }
}

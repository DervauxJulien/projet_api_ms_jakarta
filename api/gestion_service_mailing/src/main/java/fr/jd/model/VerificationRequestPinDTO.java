package fr.jd.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class VerificationRequestPinDTO {
    public String email;
    public String pin;
    public String object;
    public String sending_date;
    public String content;
    public String apiKey;

}

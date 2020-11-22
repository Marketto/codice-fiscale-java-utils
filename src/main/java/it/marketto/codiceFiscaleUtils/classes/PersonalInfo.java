package it.marketto.codiceFiscaleUtils.classes;

import it.marketto.codiceFiscaleUtils.enumerators.Genders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {
	private String firstName;
    private String lastName;

    private ZonedDateTime date;
    private Integer day;
    private Integer month;
    private Integer year;

    private Genders gender;
    private String place;
    private Integer omocodeId;
}

package it.marketto.codiceFiscaleUtils.classes;

import it.marketto.codiceFiscaleUtils.gender.Genders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {
	private String firstName;
    private String lastName;

    private Calendar date;
    private Integer day;
    private Integer month;
    private Integer year;

    private Genders gender;
    private String place;
    private Integer omocodeId;
}

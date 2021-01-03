package it.marketto.codiceFiscaleUtils.validator.personalInfo;

import it.marketto.codiceFiscaleUtils.classes.CfDateUtils;
import it.marketto.codiceFiscaleUtils.classes.CfParser;
import it.marketto.codiceFiscaleUtils.classes.PersonalInfo;
import it.marketto.codiceFiscaleUtils.enumerators.Genders;
import it.marketto.codiceFiscaleUtils.exceptions.*;
import org.junit.Test;
import org.junit.Test.None;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class PersonalInfoToCf {

    @Test
    public void parseCf() throws InvalidBirthDayException, EmptyCfException, IncompleteCfException, InvalidBirthDateException, InvalidBirthYearException, InvalidBirthMonthException {

        PersonalInfo decoded = CfParser.cfDecode("VRNGNY07D68C351V");
        assertNotNull(decoded);
        assertNotNull(decoded.getDate());
        // assertEquals("V*R*N*", decoded.getLastName());
        // assertEquals("G*N*Y*", decoded.getFirstName());
        assertEquals(2007, decoded.getDate().getYear());
        assertEquals(3, decoded.getDate().getMonthValue());
        assertEquals(28, decoded.getDate().getDayOfMonth());
        assertEquals(Genders.FEMALE, decoded.getGender());
        assertEquals(0, decoded.getOmocodeId());
    }

    @Test
    public void parsePersonalInfo() throws InvalidLastNameException, InvalidFirstNameException {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setDate(CfDateUtils.toZoneDateTime(1907, 4, 28));
        personalInfo.setFirstName("Génny");
        personalInfo.setGender(Genders.FEMALE);
        personalInfo.setLastName("Verònesi");
        //personalInfo.setPlace("Catania");
        personalInfo.setPlaceCode("C351");

        String cf = CfParser.encodeCf(personalInfo);
        assertEquals("VRNGNY07D68C351V", cf);
    }

    @Test
    public void parsePersonalInfoWithSpaces() throws InvalidLastNameException, InvalidFirstNameException {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setDate(CfDateUtils.toZoneDateTime(1907, 4, 28));
        personalInfo.setFirstName(" Génny Jay ");
        personalInfo.setGender(Genders.FEMALE);
        personalInfo.setLastName(" Verònesi ");
        //personalInfo.setPlace("Catania");
        personalInfo.setPlaceCode("C351");

        String cf = CfParser.encodeCf(personalInfo);
        assertEquals("VRNGNY07D68C351V", cf);
    }

    @Test
    public void parsePersonalInfoWithDiacritics() throws InvalidLastNameException, InvalidFirstNameException {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setDate(CfDateUtils.toZoneDateTime(1902, 5, 5));
        personalInfo.setFirstName("Mìa");
        personalInfo.setGender(Genders.FEMALE);
        personalInfo.setLastName("Màrin");
        //personalInfo.setPlace("Catania");
        personalInfo.setPlaceCode("L219");

        String cf = CfParser.encodeCf(personalInfo);
        assertEquals("MRNMIA02E45L219X", cf);
    }

}

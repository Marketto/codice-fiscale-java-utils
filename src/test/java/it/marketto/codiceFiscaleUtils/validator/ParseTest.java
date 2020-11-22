package it.marketto.codiceFiscaleUtils.validator;

import it.marketto.codiceFiscaleUtils.classes.CfParser;
import it.marketto.codiceFiscaleUtils.classes.PersonalInfo;
import it.marketto.codiceFiscaleUtils.enumerators.Genders;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class ParseTest {

    @Test
    public void parseCf() {
        PersonalInfo decoded = CfParser.cfDecode("VRNGNY07D68C351V");
        assertNotNull(decoded);
        assertEquals("V*R*N*", decoded.getLastName());
        assertEquals("G*N*Y*", decoded.getFirstName());
        assertEquals(java.util.Optional.of(2007), decoded.getYear());
        assertEquals(java.util.Optional.of(3), decoded.getMonth());
        assertEquals(java.util.Optional.of(28), decoded.getDay());
        assertEquals("F", decoded.getGender());
        assertEquals(java.util.Optional.of(0), decoded.getOmocodeId());
    }

    @Test
    public void parsePersonalInfo() {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setDay(28);
        personalInfo.setFirstName("Génny");
        personalInfo.setGender(Genders.FEMALE);
        personalInfo.setLastName("Verònesi");
        personalInfo.setMonth(3);
        personalInfo.setPlace("Catania");
        personalInfo.setYear(1907);

        String cf = CfParser.encodeCf(personalInfo);
        assertEquals("VRNGNY07D68C351V", cf);
    }
}

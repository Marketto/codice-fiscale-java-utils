package it.marketto.codiceFiscaleUtils.validator.personalInfo;

import it.marketto.codiceFiscaleUtils.classes.CfDateUtils;
import it.marketto.codiceFiscaleUtils.classes.CfParser;
import it.marketto.codiceFiscaleUtils.classes.PersonalInfo;
import it.marketto.codiceFiscaleUtils.enumerators.Genders;
import it.marketto.codiceFiscaleUtils.exceptions.*;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class FirstNameToCfTest {
    @Test
    public void firstNameToCf() throws InvalidFirstNameException {
        assertEquals("DNQ", CfParser.firstNameToCf("Dominique"));
        assertEquals("LND", CfParser.firstNameToCf(" Alexander "));
        assertEquals("MRK", CfParser.firstNameToCf("Mark"));
        assertEquals("TMO", CfParser.firstNameToCf("Tom"));
        assertEquals("NAI", CfParser.firstNameToCf("Ania"));
        assertEquals("NOX", CfParser.firstNameToCf("No"));
        assertEquals("AIX", CfParser.firstNameToCf("Ai"));
        assertEquals("PRL", CfParser.firstNameToCf("Pier Ale"));
        assertEquals("LOE", CfParser.firstNameToCf("Olè"));
        assertEquals("CII", CfParser.firstNameToCf("Içi"));
    }
    @Test(expected = InvalidFirstNameException.class)
    public void firstNameToCfEmptyException() throws InvalidFirstNameException {
        CfParser.firstNameToCf("");
    }
    @Test(expected = InvalidFirstNameException.class)
    public void firstNameToCfSpaceException() throws InvalidFirstNameException {
        CfParser.firstNameToCf(" ");
    }
    @Test(expected = InvalidFirstNameException.class)
    public void firstNameToCfIncompleteException() throws InvalidFirstNameException {
        CfParser.firstNameToCf("K");
    }
    @Test(expected = InvalidFirstNameException.class)
    public void firstNameToCfInvalidException() throws InvalidFirstNameException {
        CfParser.firstNameToCf("@à");
    }
}

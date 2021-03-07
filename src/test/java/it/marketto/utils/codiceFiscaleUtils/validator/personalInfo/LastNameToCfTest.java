package it.marketto.utils.codiceFiscaleUtils.validator.personalInfo;

import it.marketto.utils.codiceFiscaleUtils.classes.CfParser;
import it.marketto.utils.codiceFiscaleUtils.exceptions.InvalidLastNameException;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class LastNameToCfTest {
    @Test
    public void lastNameToCf() throws InvalidLastNameException {
        assertEquals("RSS", CfParser.lastNameToCf("Rossi"));
        assertEquals("RNE", CfParser.lastNameToCf(" Reno "));
        assertEquals("DRN", CfParser.lastNameToCf("Di Reno"));
        assertEquals("GOI", CfParser.lastNameToCf("Goia"));
        assertEquals("AIE", CfParser.lastNameToCf("Aieie"));
        assertEquals("NOX", CfParser.lastNameToCf("No"));
        assertEquals("AIX", CfParser.lastNameToCf("Ai"));
        assertEquals("DAI", CfParser.lastNameToCf("D'Aieie"));
        assertEquals("LOE", CfParser.lastNameToCf("Olè"));
        assertEquals("CII", CfParser.lastNameToCf("Içi"));
    }

    @Test(expected = InvalidLastNameException.class)
    public void lastNameToCfEmptyException() throws InvalidLastNameException {
        CfParser.lastNameToCf("");
    }

    @Test(expected = InvalidLastNameException.class)
    public void lastNameToCfSpaceException() throws InvalidLastNameException {
        CfParser.lastNameToCf(" ");
    }

    @Test(expected = InvalidLastNameException.class)
    public void lastNameToCfIncompleteException() throws InvalidLastNameException {
        CfParser.lastNameToCf("K");
    }

    @Test(expected = InvalidLastNameException.class)
    public void lastNameToCfInvalidException() throws InvalidLastNameException {
        CfParser.lastNameToCf("@à");
    }
}

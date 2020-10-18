package it.marketto.codiceFiscaleUtils.validator;

import it.marketto.codiceFiscaleUtils.classes.CfDateUtils;
import it.marketto.codiceFiscaleUtils.classes.CfValidator;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Calendar;

import static junit.framework.TestCase.*;

public class DateUtilsTest {
    @Test
    public void validateBirthDate() {
        ZonedDateTime dt = CfDateUtils.toZoneDateTime(2000, 4, 25);
        System.out.println(dt.toString());
        assertEquals("2000-04-25T00:00+02:00[Europe/Rome]", dt.toString());
    }
}

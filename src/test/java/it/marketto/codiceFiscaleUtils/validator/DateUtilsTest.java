package it.marketto.codiceFiscaleUtils.validator;

import it.marketto.codiceFiscaleUtils.classes.CfDateUtils;
import org.junit.Test;

import java.time.ZonedDateTime;

import static junit.framework.TestCase.assertEquals;

public class DateUtilsTest {
    @Test
    public void validateBirthDate() {
        ZonedDateTime dt = CfDateUtils.toZoneDateTime(2000, 4, 25);
        assertEquals("2000-04-25T00:00+02:00[Europe/Rome]", dt.toString());
    }
}

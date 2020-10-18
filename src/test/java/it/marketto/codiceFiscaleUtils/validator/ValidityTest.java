package it.marketto.codiceFiscaleUtils.validator;

import org.junit.Test;

import it.marketto.codiceFiscaleUtils.classes.CfValidator;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Calendar;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ValidityTest {

	@Test
	public void validateCodiceFiscale() {
		assertTrue(CfValidator.codiceFiscale("VRNGNY07D68C351V").isValid());
		assertTrue(CfValidator.codiceFiscale("MRNMIA02E45L219X").isValid());
		assertTrue(CfValidator.codiceFiscale("GSTPPP31C06D620Z").isValid());
		assertFalse(CfValidator.codiceFiscale("VRNGNY07D68C351K").isValid());
		assertFalse(CfValidator.codiceFiscale("").isValid());
		assertFalse(CfValidator.codiceFiscale(null).isValid());
	}

	@Test
	public void validateLastName() {
		assertTrue(CfValidator.isLastNameValid("Test"));
		assertTrue(CfValidator.isLastNameValid("Tést N'àme"));
		assertFalse(CfValidator.isLastNameValid("@!#"));
		assertFalse(CfValidator.isLastNameValid(""));
		assertFalse(CfValidator.isLastNameValid(null));
	}

	@Test
	public void validateFirstName() {
		assertTrue(CfValidator.isFirstNameValid("Test"));
		assertTrue(CfValidator.isFirstNameValid("Tést N'àme"));
		assertFalse(CfValidator.isFirstNameValid("@!#"));
		assertFalse(CfValidator.isFirstNameValid(""));
		assertFalse(CfValidator.isFirstNameValid(""));
	}

	@Test
	public void validateBirthDate() {
		assertTrue(CfValidator.isBirthDateValid(LocalDate.parse("1999-01-01")));
		assertTrue(CfValidator.isBirthDateValid(LocalDate.parse("2000-02-28")));
		assertTrue(CfValidator.isBirthDateValid(1999, 1, 1));
		assertTrue(CfValidator.isBirthDateValid(2020, 12, 31));
		assertFalse(CfValidator.isBirthDateValid(1999, 2, 30));
		assertFalse(CfValidator.isBirthDateValid((LocalDate) null));
	}

	@Test
	public void validateGender() {
		assertTrue(CfValidator.isGenderValid('M'));
		assertTrue(CfValidator.isGenderValid('F'));
		assertTrue(CfValidator.isGenderValid('m'));
		assertTrue(CfValidator.isGenderValid('f'));
		assertFalse(CfValidator.isGenderValid('X'));
		assertTrue(CfValidator.isGenderValid("Male"));
		assertFalse(CfValidator.isGenderValid(""));
		assertFalse(CfValidator.isGenderValid((String) null));
	}
}

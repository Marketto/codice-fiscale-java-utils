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
		assertTrue(CfValidator.isCodiceFiscaleValid("VRNGNY07D68C351V"));
		assertTrue(CfValidator.isCodiceFiscaleValid("MRNMIA02E45L219X"));
		assertTrue(CfValidator.isCodiceFiscaleValid("GSTPPP31C06D620Z"));
		assertFalse(CfValidator.isCodiceFiscaleValid("VRNGNY07D68C351K"));
		assertFalse(CfValidator.isCodiceFiscaleValid(""));
		assertFalse(CfValidator.isCodiceFiscaleValid(null));
	}

	@Test
	public void validateLastName() {
		assertTrue(CfValidator.isLastNameValid("Test"));
		assertTrue(CfValidator.isLastNameValid(" Test"));
		assertTrue(CfValidator.isLastNameValid("Tést N'àme"));
		assertFalse(CfValidator.isLastNameValid("@!#"));
		assertFalse(CfValidator.isLastNameValid("A"));
		assertFalse(CfValidator.isLastNameValid(""));
		assertFalse(CfValidator.isLastNameValid(null));
	}

	@Test
	public void validateFirstName() {
		assertTrue(CfValidator.isFirstNameValid("Test"));
		assertTrue(CfValidator.isFirstNameValid(" Test"));
		assertTrue(CfValidator.isFirstNameValid("Tést Nàme"));
		assertFalse(CfValidator.isFirstNameValid("@!#"));
		assertFalse(CfValidator.isFirstNameValid("B"));
		assertFalse(CfValidator.isFirstNameValid(""));
		assertFalse(CfValidator.isFirstNameValid(null));
	}

	@Test
	public void validateBirthDate() {
		assertTrue(CfValidator.isBirthDateValid("1999-01-01"));
		assertTrue(CfValidator.isBirthDateValid("2000-02-28"));
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
		assertFalse(CfValidator.isGenderValid(null));
	}

	@Test
	public void validBirthPlace() {
		assertTrue(CfValidator.isBirthPlaceValid("H501"));
		assertFalse(CfValidator.isBirthPlaceValid(""));
		assertFalse(CfValidator.isBirthPlaceValid(null));
		assertFalse(CfValidator.isBirthPlaceValid("Moon"));
	}
}

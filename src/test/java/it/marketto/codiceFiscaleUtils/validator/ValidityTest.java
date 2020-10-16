package it.marketto.codiceFiscaleUtils.validator;

import org.junit.Test;

import it.marketto.codiceFiscaleUtils.classes.CfValidator;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ValidityTest {

	@Test
	public void validateCodiceFiscale() {
		assertTrue(CfValidator.codiceFiscale("VRNGNY07D68C351V").isValid());
		assertTrue(CfValidator.codiceFiscale("MRNMIA02E45L219X").isValid());
		assertTrue(CfValidator.codiceFiscale("GSTPPP31C06D620Z").isValid());
		assertFalse(CfValidator.codiceFiscale("VRNGNY07D68C351K").isValid());
		// assertFalse(CfValidator.codiceFiscale("GSTPPP99C06D620V").isValid());
		assertFalse(CfValidator.codiceFiscale("").isValid());
		assertTrue(CfValidator.codiceFiscale("VRNGNY07D68C351K").isInvalid());
		assertFalse(CfValidator.codiceFiscale("VRNGNY07D68C351V").isInvalid());
		assertFalse(CfValidator.codiceFiscale("").isInvalid());
	}

	@Test
	public void validateLastName() {
		assertTrue(CfValidator.isLastNameValid("Test"));
		assertTrue(CfValidator.isLastNameValid("Tést N'àme"));
		assertFalse(CfValidator.isLastNameValid("@!#"));
		assertFalse(CfValidator.isLastNameValid(""));
		assertFalse(CfValidator.isLastNameInvalid("Test"));
		assertTrue(CfValidator.isLastNameInvalid("@!#"));
		assertFalse(CfValidator.isLastNameInvalid(""));
	}
}

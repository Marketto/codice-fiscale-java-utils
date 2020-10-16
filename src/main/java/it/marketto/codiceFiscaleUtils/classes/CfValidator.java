package it.marketto.codiceFiscaleUtils.classes;

import org.apache.commons.lang3.StringUtils;

public class CfValidator {

	public static CfMismatchValidator codiceFiscale(String codiceFiscale) {
		return new CfMismatchValidator(codiceFiscale);
	}

	public static boolean isLastNameValid(String lastName) {
		return CfPattern.lastName().asPredicate().test(lastName);
	}
	public static boolean isLastNameInvalid(String lastName) {
		return !StringUtils.isEmpty(lastName) && !isLastNameValid(lastName);
	}
}

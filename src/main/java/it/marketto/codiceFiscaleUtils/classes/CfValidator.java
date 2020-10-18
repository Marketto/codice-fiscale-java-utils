package it.marketto.codiceFiscaleUtils.classes;

import it.marketto.codiceFiscaleUtils.constants.Settings;
import it.marketto.codiceFiscaleUtils.gender.Gender;
import it.marketto.codiceFiscaleUtils.gender.Genders;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.*;
import java.util.*;

public class CfValidator {

	// Codice Fiscale
	public static CfMismatchValidator codiceFiscale(String codiceFiscale) {
		return new CfMismatchValidator(codiceFiscale);
	}

	// Last Name
	public static boolean isLastNameValid(String lastName) {
		return !StringUtils.isEmpty(lastName) && CfPattern.lastName().asPredicate().test(lastName);
	}

	// First Name
	public static boolean isFirstNameValid(String firstName) {
		return !StringUtils.isEmpty(firstName) && CfPattern.firstName().asPredicate().test(firstName);
	}

	// BirthDate
	public static boolean isBirthDateValid(ZonedDateTime date) {
		return date != null;
	}
	public static boolean isBirthDateValid(LocalDate date) {
		return CfDateUtils.toZoneDateTime(date) != null;
	}
	public static boolean isBirthDateValid(Date date) {
		return CfDateUtils.toZoneDateTime(date) != null;
	}
	public static boolean isBirthDateValid(LocalDateTime date) {
		return CfDateUtils.toZoneDateTime(date) != null;
	}
	public static boolean isBirthDateValid(Integer year, Integer month, Integer day) {
		try {
			return CfDateUtils.toZoneDateTime(year, month, day) != null;
		} catch (DateTimeException e) {
			return false;
		}
	}

	public static boolean isGenderValid(Character gender) {
		return gender != null && Arrays.stream(Gender.toArray())
				.filter(g -> g.toString().charAt(0) == Character.toUpperCase(gender))
				.findFirst()
				.orElse(null) != null;
	}
	public static boolean isGenderValid(String gender) {
		return !StringUtils.isEmpty(gender) && Arrays.stream(Gender.toArray())
				.filter(g -> g.toString().equalsIgnoreCase(gender))
				.findFirst()
				.orElse(null) != null;
	}
}

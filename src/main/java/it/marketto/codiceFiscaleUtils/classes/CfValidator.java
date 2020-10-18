package it.marketto.codiceFiscaleUtils.classes;

import it.marketto.codiceFiscaleUtils.constants.Settings;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

	public static boolean isFirstNameValid(String firstName) {
		return CfPattern.firstName().asPredicate().test(firstName);
	}
	public static boolean isFirstNameInvalid(String firstName) {
		return !StringUtils.isEmpty(firstName) && !isFirstNameValid(firstName);
	}

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


	public static boolean isBirthDateInvalid(ZonedDateTime date) {
		return false;
	}
	public static boolean isBirthDateInvalid(Integer year, Integer month, Integer day) {
		if (year == null && month == null && day == null) {
			return false;
		}
		if (year == null || ( month == null  && day != null)) {
			return true;
		}
		return !isBirthDateValid(year, month != null ? month : 1, day != null ? day : 1);
	}
	public static boolean isBirthDateInvalid(LocalDate date) {
		return false;
	}
}

package it.marketto.codiceFiscaleUtils.gender;

import it.marketto.codiceFiscaleUtils.enumerators.Genders;

public class GenderUtils {
	private static int MAX_MONTH_DAY = 31;

	public static Integer getDay(int genderDay) {
        final int plainDay = genderDay % Genders.FEMALE.toValue();
        return (plainDay > 0 && plainDay <= MAX_MONTH_DAY) ? plainDay : null;
    }

    public static Integer genderizeDay(int day, Genders gender) {
        return day + gender.toValue();
    }
}

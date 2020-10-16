package it.marketto.codiceFiscaleUtils.gender;

import java.util.Arrays;
import java.util.Optional;

public class Gender {
	private static int MAX_MONTH_DAY = 31;

	public static Integer getDay(int genderDay) {
        final int plainDay = genderDay % Genders.FEMALE.toValue();
        return (plainDay > 0 && plainDay <= MAX_MONTH_DAY) ? plainDay : null;
    }

    public static Genders getGender(Integer genderDay) {
    	return Arrays.stream(toArray())
    		.filter(gender -> genderDay >= gender.toValue() && genderDay <= gender.toValue() + MAX_MONTH_DAY)
    		.findFirst()
    		.orElse(null);
    }

    public static Integer genderizeDay(int day, Genders gender) {
        return day + gender.toValue();
    }

    public static Genders[] toArray() {
		return new Genders[]{ Genders.MALE, Genders.FEMALE };
    }

}

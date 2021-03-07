package it.marketto.utils.codiceFiscaleUtils.enumerators;

import org.apache.commons.lang3.Range;

import java.util.Arrays;

public enum Genders {
	MALE(0),
	FEMALE(40);

	private static int MAX_MONTH_DAY = 31;
	int genderOffset;

	Genders(int genderOffset) {
		this.genderOffset = genderOffset;
	}

	public int toValue() {
		return genderOffset;
	}

	public static Genders from(String value) {
		return Arrays.stream(values())
				.filter(gender -> gender.toString().equalsIgnoreCase(value))
				.findFirst()
				.orElse(null);
	}
	public static Genders from(char value) {
		return Arrays.stream(values())
				.filter(gender -> gender.toString().substring(0, 1).equalsIgnoreCase(String.valueOf(value)))
				.findFirst()
				.orElse(null);
	}
	public static Genders from(int value) {
		return Arrays.stream(values())
				.filter(gender -> Range.between(0, MAX_MONTH_DAY).contains(value - gender.toValue()))
				.findFirst()
				.orElse(null);
	}
}

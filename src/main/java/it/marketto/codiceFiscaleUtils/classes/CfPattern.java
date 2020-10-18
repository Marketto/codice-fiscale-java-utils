package it.marketto.codiceFiscaleUtils.classes;

import it.marketto.codiceFiscaleUtils.constants.CfMatchers;
import it.marketto.codiceFiscaleUtils.constants.CfOffsets;
import it.marketto.codiceFiscaleUtils.exceptions.InvalidLastNameException;
import it.marketto.codiceFiscaleUtils.gender.Genders;
import lombok.val;
import org.apache.commons.lang3.Functions;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class CfPattern {
	public static Pattern cfLastName(String lastName) {
        String matcher = CfMatchers.CF_SURNAME_MATCHER;
        if (lastName != null) {
            if (!lastName().asPredicate().test(lastName)) {
            	// throw new InvalidLastNameException();
            }
            //matcher = CfParser.lastNameToCf(lastName) || matcher;
        }
        return isolatedInsensitiveTailor(matcher);
    }

    private static Pattern cfFirstName(String firstName) {
		return null;
	}

	private static Pattern cfDateGender(ZonedDateTime date, Genders gender) {
		return null;
	}

	private static Pattern cfPlace(ZonedDateTime date, String place) {
		return null;
	}

	/**
	 * Generates full CF validator based on given optional input or generic
	 * @param personalInfo Input Object
	 * @return CodiceFiscale matcher
	 */
	public static Pattern codiceFiscale(PersonalInfo personalInfo) {
		String matcher = CfMatchers.CODICE_FISCALE;
		if (personalInfo != null) {
            String parsedCf = CfParser.encodeCf(personalInfo);

			if (!StringUtils.isEmpty(parsedCf)) {
				matcher = deomocode(parsedCf);
			} else {
				if (
					personalInfo.getLastName() != null ||
					personalInfo.getFirstName() != null ||
					personalInfo.getYear() != null ||
					personalInfo.getMonth() != null ||
					personalInfo.getDay() != null ||
					personalInfo.getDate() != null ||
					personalInfo.getGender() != null ||
					personalInfo.getPlace() != null
				) {
					ZonedDateTime dtParams = personalInfo.getDate() != null ?
						personalInfo.getDate() :
						CfDateUtils.toZoneDateTime(
							personalInfo.getYear(),
							personalInfo.getMonth(),
							personalInfo.getDay()
						);

					Function<Void, Pattern>[] generator = new Function[] {
							(v) -> cfLastName(personalInfo.getLastName()),
							(v) -> cfFirstName(personalInfo.getFirstName()),
							(v) -> cfDateGender(dtParams, personalInfo.getGender()),
							(v) -> cfPlace(dtParams, personalInfo.getPlace())
					};

					matcher = "";
					for (final Function<Void, Pattern> validator : generator) {
						Pattern cfMatcher = validator.apply(null);
                        String cfValueMatcher = cfMatcher.toString().substring(1, cfMatcher.toString().length() - 1);

						if (StringUtils.isEmpty(cfValueMatcher)) {
							throw new Error("Unable to handle [" + cfMatcher + "]");
						}
						matcher += "(?:" + cfValueMatcher + ")";
					}
					// Final addition of CheckDigit
					matcher += CfMatchers.CHECK_DIGIT;
				}
			}
		}
		return isolatedInsensitiveTailor(matcher);
	}
	public static Pattern codiceFiscale() {
		return codiceFiscale(null);
	}

	private static final String LETTER_SET = "[\\p{L}]";

	private static String getConsonants(String text) {
		return StringUtils.isEmpty(text) ? "" : text.chars()
				.sequential()
				.filter(c -> Pattern.compile(CfMatchers.CONSONANT_LIST).asPredicate().test(String.valueOf(c)))
				.collect(StringBuilder::new, (builder, c) -> builder.append((char) c), StringBuilder::append)
				.toString();
	}

	private static String getVowels(String text) {
		return StringUtils.isEmpty(text) ? "" : text.chars()
				.sequential()
				.filter(c -> Pattern.compile(CfMatchers.VOWEL_LIST).asPredicate().test(String.valueOf(c)))
				.collect(StringBuilder::new, (builder, c) -> builder.append((char) c), StringBuilder::append)
				.toString();
	}

	public static Pattern lastName(String codiceFiscale) {
		final String SEPARATOR_SET = "[' ]";
		final String VALID_PARTIAL_CF_LAST_NAME = "^[A-Z]{1,3}";
		String matcher = "(?:" + LETTER_SET + "+" + SEPARATOR_SET + "?)+";

		if (!StringUtils.isEmpty(codiceFiscale)) {
			final String normalizedCf = DiacriticRemover.removeDiacritics(codiceFiscale);
			final Predicate<String> partialCfLastNamePattern = Pattern
					.compile(VALID_PARTIAL_CF_LAST_NAME, Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE)
					.asPredicate();

			if (partialCfLastNamePattern.test(normalizedCf)) {

				final String lastNameCfU = normalizedCf.substring(
						CfOffsets.LASTNAME_OFFSET,
						CfOffsets.LASTNAME_OFFSET + CfOffsets.LASTNAME_SIZE
				).toUpperCase();

				final String consonants = getConsonants(lastNameCfU);
				final String vowels = getVowels(lastNameCfU);

				final String diacriticsVowelMatcher = "[" + CfMatchers.VOWEL_LIST + "]";
				final String midDiacriticVowelMatcher = "(?:" + diacriticsVowelMatcher + SEPARATOR_SET + "?)*";
				final String endingDiacritcVowelMatcher = "(?:" + SEPARATOR_SET + midDiacriticVowelMatcher + diacriticsVowelMatcher + ")?";

				final String optionalSeparatorSet = SEPARATOR_SET + "?";
				if (consonants.length() >= 3) {
					// 3 Consonants
					matcher = midDiacriticVowelMatcher +
							StringUtils.join(
									optionalSeparatorSet + midDiacriticVowelMatcher,
									consonants.split("")
							)
							+ "(?:" + optionalSeparatorSet + LETTER_SET + "*" + LETTER_SET + ")?";
				} else if (consonants.length() >= 2) {
					// 2 Consonants + 1 Vowel
					String[] possibilities = {
							vowels.charAt(0) + optionalSeparatorSet + consonants.charAt(0) + midDiacriticVowelMatcher + consonants.charAt(1),
							consonants.charAt(0) + optionalSeparatorSet + StringUtils.join(optionalSeparatorSet, vowels.split("")) + optionalSeparatorSet + midDiacriticVowelMatcher + consonants.charAt(1),
							StringUtils.join(optionalSeparatorSet, consonants.split("")) + optionalSeparatorSet + vowels.charAt(0)
					};
					matcher = "(?:" + StringUtils.join("|", possibilities) + ")" + endingDiacritcVowelMatcher;
				} else if (consonants.length() >= 1) {
					// 1 Consonant + 2 Vowels
					String[] possibilities = {
							StringUtils.join(optionalSeparatorSet, vowels.substring(0, 2).split(""))
									+ midDiacriticVowelMatcher + StringUtils.join(optionalSeparatorSet, vowels.substring(0, 2).split("")),
							vowels.charAt(0) + optionalSeparatorSet + StringUtils.join(optionalSeparatorSet, consonants.split("")) + vowels.charAt(1),
							consonants.charAt(0) + optionalSeparatorSet + StringUtils.join(optionalSeparatorSet, vowels.substring(0, 2).split(""))
					};
					matcher = "(?:" + StringUtils.join("|", possibilities) + ")" + endingDiacritcVowelMatcher;
				} else {
					// 3 Vowels
					matcher = "(?:" + StringUtils.join(optionalSeparatorSet, vowels.split("")) + ")" + endingDiacritcVowelMatcher;
				}
			}
		}

		return isolatedInsensitiveTailor(" *(" + matcher + ") *");
	}
	public static Pattern lastName() {
		return lastName(null);
	}

	public static Pattern firstName(String codiceFiscale) {
		if (codiceFiscale != null) {
			final String normalizedCf = DiacriticRemover.removeDiacritics(codiceFiscale);

			final String ALL_CONSONANTS_FIRST_NAME = "^.{3}[" + CfMatchers.CONSONANT_LIST + "]{3}";
			final Predicate<String> partialCfFirstNamePattern = Pattern.compile(ALL_CONSONANTS_FIRST_NAME, Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE)
					.asPredicate();

			if (partialCfFirstNamePattern.test(normalizedCf)) {
				final String firstNameCfU = normalizedCf.substring(
						CfOffsets.FIRSTNAME_OFFSET,
						CfOffsets.FIRSTNAME_OFFSET + CfOffsets.FIRSTNAME_SIZE
				).toUpperCase();

				final String consonants = getConsonants(firstNameCfU);
				final String vowels = getVowels(firstNameCfU);

				final String diacriticsVowelMatcher = "[" + CfMatchers.VOWEL_LIST + "]";
				final String diacriticsConsonantMatcher = "[" + CfMatchers.CONSONANT_LIST + "]";
				final String matcher = diacriticsVowelMatcher + "*"
						+ consonants.charAt(0) + diacriticsVowelMatcher + "*"
						+ "(?" + diacriticsConsonantMatcher + diacriticsVowelMatcher + ")?"
						+ consonants.charAt(1)
						+ diacriticsVowelMatcher + "*"
						+ consonants.charAt(2)
						+ LETTER_SET + "*";

				return isolatedInsensitiveTailor(" *(" + matcher + ") *");
			}
		}
		return lastName(StringUtils.isEmpty(codiceFiscale)
			? null
			: codiceFiscale.substring(CfOffsets.FIRSTNAME_OFFSET, CfOffsets.FIRSTNAME_OFFSET + CfOffsets.FIRSTNAME_SIZE));
	}
	public static Pattern firstName() {
		return firstName(null);
	}

	public static Pattern gender(String codiceFiscale) {
		return null;
	}


	private static String deomocode(String parsedCf) {
		return null;
	}

	private static Pattern isolatedInsensitiveTailor(String matcher) {
		return Pattern.compile("^(?:" + matcher + ")$", Pattern.CASE_INSENSITIVE);
	}
}

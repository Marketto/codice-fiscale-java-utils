package it.marketto.utils.codiceFiscaleUtils.classes;

import it.marketto.utils.codiceFiscaleUtils.constants.*;
import it.marketto.utils.codiceFiscaleUtils.enumerators.CRC;
import it.marketto.utils.codiceFiscaleUtils.enumerators.Genders;
import it.marketto.utils.codiceFiscaleUtils.exceptions.InvalidPartialCfException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Validator;
import javax.validation.constraints.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class CfValidator {
    private static Validator validator;

    public static boolean isCodiceFiscaleValid(@NotEmpty String cf) {
        Pattern pattern = Pattern.compile(
                "^(" + CfMatchers.CODICE_FISCALE + ")$",
                Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE
        );
        if (StringUtils.isNotEmpty(cf) && pattern.matcher(cf).find()) {
            CRC crc;
            try {
                crc = CheckDigitizer.checkDigit(cf.substring(CfOffsets.CF_INIT_OFFSET, CfOffsets.CRC_OFFSET));
            } catch (InvalidPartialCfException e) {
                return false;
            }
            return cf.substring(CfOffsets.CRC_OFFSET, CfOffsets.CRC_OFFSET + CfOffsets.CRC_SIZE)
                    .equalsIgnoreCase(crc.toString());
        }
        return false;
    }

    public static boolean isLastNameValid(@NotEmpty String lastName) {
        return FieldNormalizer.cleanField(lastName, Settings.MIN_NAME_LENGTH)
                .toUpperCase()
                .matches("^(" + PersonalInfoMatchers.LAST_NAME + ")$");
    }

    public static boolean isFirstNameValid(@NotEmpty String firstName) {
        return FieldNormalizer.cleanField(firstName, Settings.MIN_NAME_LENGTH)
                .toUpperCase()
                .matches("^(" + PersonalInfoMatchers.FIRST_NAME + ")$");
    }

    public static boolean isBirthDateValid(@NotEmpty String iso8601DateString) {
        return StringUtils.isNotEmpty(iso8601DateString) &&
                iso8601DateString.matches("^(" + DateMatchers.ISO8601_DATE_TIME + ")$") &&
                isBirthDateValid(LocalDate.parse(iso8601DateString));
    }

    public static boolean isBirthDateValid(
            @Min(Settings.ITALIAN_FOUNDATION_YEAR)
            @Max(9999)
                    int year,

            @Positive
            @Max(12)
                    int month,

            @Positive
            @Max(31)
                    int day
    ) {
        LocalDate localDate;
        try {
            localDate = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            localDate = null;
        }
        return isBirthDateValid(localDate);
    }

    public static boolean isBirthDateValid(@PastOrPresent LocalDate birthDate) {
        return birthDate != null
                && birthDate.isAfter(Settings.ITALIAN_FOUNDATION_DATE)
                && birthDate.isBefore(LocalDate.now());
    }

    public static boolean isGenderValid(char gender) {
        return Genders.from(gender) != null;
    }

    public static boolean isGenderValid(@NotEmpty String gender) {
        return Genders.from(gender) != null;
    }

    public static boolean isBirthPlaceValid(@NotEmpty String belfioreCode) {
        return belfioreCode != null &&
                belfioreCode.toUpperCase().matches("^(" + CfMatchers.BELFIORE_CODE_MATCHER + ")$");
    }
}

package it.marketto.utils.codiceFiscaleUtils.classes;

import com.sun.istack.internal.NotNull;
import it.marketto.utils.codiceFiscaleUtils.constants.CfOffsets;
import it.marketto.utils.codiceFiscaleUtils.constants.CommonMatchers;
import it.marketto.utils.codiceFiscaleUtils.constants.Settings;
import it.marketto.utils.codiceFiscaleUtils.enumerators.BirthMonths;
import it.marketto.utils.codiceFiscaleUtils.enumerators.Genders;
import it.marketto.utils.codiceFiscaleUtils.enumerators.Omocodes;
import it.marketto.utils.codiceFiscaleUtils.exceptions.*;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class CfParser {
    public static byte OMOCODE_BITMAP = (byte) 0b0111011011000000;


    public static PersonalInfo cfDecode(String codiceFiscale)
            throws EmptyCfException, IncompleteCfException, InvalidBirthDayException, InvalidBirthDateException, InvalidBirthYearException, InvalidBirthMonthException {
        if (StringUtils.isEmpty(codiceFiscale)) {
            throw new EmptyCfException();
        }
        if (codiceFiscale.length() < 16) {
            throw new IncompleteCfException();
        }

        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setGender(cfToGender(codiceFiscale));
        personalInfo.setDate(cfToBirthDate(codiceFiscale));

        return personalInfo;
    }

    private static boolean checkBitmap(int offset) {
    	byte binaryOffset = (byte) Math.round(Math.pow(2, offset));
        return (binaryOffset & OMOCODE_BITMAP) != 0;
    }

    private static char charOmocode(char c, int offset) {
        if (String.valueOf(c).toUpperCase().matches("^" + CommonMatchers.ALL_LETTERS + "$") && checkBitmap(offset)) {
            return Omocodes.from(c).toChar();
        }
        return c;
    }

    private static String partialCfDeomocode(String partialCodiceFiscale, int offset) {
    	return IntStream.range(0, partialCodiceFiscale.length())
        	.mapToObj(idx -> charOmocode(partialCodiceFiscale.charAt(idx), idx + offset))
        	.collect(Collector.of(
        			StringBuilder::new,
        			StringBuilder::append,
        			StringBuilder::append,
        			StringBuilder::toString
        		));

    }
    private static String partialCfDeomocode(String partialCodiceFiscale) {
    	return partialCfDeomocode(partialCodiceFiscale, 0);
    }

    public static String cfDeomocode(String codiceFiscale) throws InvalidPartialCfException {
        if (codiceFiscale != null && codiceFiscale.length() <= CfOffsets.YEAR_OFFSET) {
            return codiceFiscale;
        }
        String deomocodedCf = partialCfDeomocode(codiceFiscale);
        if (deomocodedCf.length() < CfOffsets.CRC_OFFSET) {
            return deomocodedCf;
        }
        String partialDeomocodedCf = deomocodedCf.substring(CfOffsets.LASTNAME_OFFSET, CfOffsets.CRC_OFFSET);
        return (partialDeomocodedCf + CheckDigitizer.checkDigit(deomocodedCf).toChar()).toUpperCase();
    }
    public static String cfDeomocode() throws InvalidPartialCfException {
    	return cfDeomocode(null);
    }
    
    public static String cfOmocode(String codiceFiscale, byte omocodeId) throws InvalidPartialCfException {
        if (omocodeId == 0) {
            return cfDeomocode(codiceFiscale);
        }
        char[] omocodedCf = codiceFiscale.toCharArray();
        for (int i = codiceFiscale.length() - 1, o = 0; i >= 0; i--) {
            if ((Math.round(Math.pow(2, i)) & OMOCODE_BITMAP) != 0) {
            	boolean charToEncode = (Math.round(Math.pow(2, o)) & omocodeId) != 0;
                boolean isOmocode = Pattern.matches("^" + CommonMatchers.NUMBER_LIST + "$", String.valueOf(omocodedCf[i]));
                if (charToEncode != isOmocode) {
                    omocodedCf[i] = Omocodes.from(omocodedCf[i]).toChar();
                }
                o++;
            }
        }
        Character crc = omocodedCf.length > CfOffsets.CRC_OFFSET ? omocodedCf[CfOffsets.CRC_OFFSET] : null;
        if (crc != null) {
            String partialCf = codiceFiscale.substring(CfOffsets.CF_INIT_OFFSET, CfOffsets.CRC_OFFSET);
            char crcChar = CheckDigitizer.checkDigit(partialCf).toChar();
            return (partialCf + crcChar).toUpperCase();
        }
        return codiceFiscale;
    }
    public static String cfOmocode(String codiceFiscale, int omocodeId) throws InvalidPartialCfException {
    	return cfOmocode(codiceFiscale, (byte) omocodeId);
    }
    public static String cfOmocode(String codiceFiscale) throws InvalidPartialCfException {
    	return cfOmocode(codiceFiscale, 0);
    }

    /**
     * Parse birth year information
     */
    public static int cfToBirthYear(String codiceFiscale) throws EmptyCfException, IncompleteCfException, InvalidBirthYearException {
        if (StringUtils.isEmpty(codiceFiscale)){
            throw new EmptyCfException();
        }
        if (codiceFiscale.length() < CfOffsets.YEAR_OFFSET + CfOffsets.YEAR_SIZE) {
            throw new IncompleteCfException();
        }

        String cfBirthYearPart = codiceFiscale.substring(CfOffsets.YEAR_OFFSET, CfOffsets.YEAR_OFFSET + CfOffsets.YEAR_SIZE);
        int birthYear;
        try {
            birthYear = Integer.parseInt(partialCfDeomocode(cfBirthYearPart, CfOffsets.YEAR_OFFSET), 10);
        } catch(NumberFormatException e) {
            throw new InvalidBirthYearException();
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentCentury = (int) Math.floor((double) currentYear / 100.0) * 100;
        int centuryAdjust = (birthYear > (currentYear - currentCentury)) ? -100 : 0;
        return birthYear + currentCentury + centuryAdjust;
    }

    /**
     * Parse birth month information
     */
    public static int cfToBirthMonth(String codiceFiscale) throws EmptyCfException, IncompleteCfException, InvalidBirthMonthException {
        if (StringUtils.isEmpty(codiceFiscale)){
            throw new EmptyCfException();
        }
        if (codiceFiscale.length() < CfOffsets.MONTH_OFFSET + CfOffsets.MONTH_SIZE) {
            throw new IncompleteCfException();
        }

        String cfBirthMonthPart = codiceFiscale.substring(CfOffsets.MONTH_OFFSET, CfOffsets.MONTH_OFFSET + CfOffsets.MONTH_SIZE).toUpperCase();
        BirthMonths birthMonth = BirthMonths.from(cfBirthMonthPart.charAt(0));
        if (birthMonth == null) {
            throw new InvalidBirthMonthException();
        }
        return birthMonth.toValue();
    }

    private static Genders birthDayGenderToGender(int birthDayGender) throws InvalidBirthDayException {
        Genders gender = Genders.from(birthDayGender);
        if (gender == null) {
            throw new InvalidBirthDayException();
        }
        return gender;
    }

    /**
     * Parse birth day information
     */
    public static int cfToBirthDay(String codiceFiscale) throws EmptyCfException, IncompleteCfException, InvalidBirthDayException {
        int birthDayGender = cfToBirthDayGender(codiceFiscale);
        Genders gender = birthDayGenderToGender(birthDayGender);
        return birthDayGender - gender.toValue();
    }

    /**
     * Parse gender information
     */
    public static Genders cfToGender(String codiceFiscale) throws EmptyCfException, IncompleteCfException, InvalidBirthDayException {
        int birthDayGender = cfToBirthDayGender(codiceFiscale);
        return birthDayGenderToGender(birthDayGender);
    }

    private static int cfToBirthDayGender(String codiceFiscale) throws EmptyCfException, IncompleteCfException, InvalidBirthDayException {
        if (StringUtils.isEmpty(codiceFiscale)) {
            throw new EmptyCfException();
        }
        if (codiceFiscale.length() < CfOffsets.DAY_OFFSET + CfOffsets.DAY_SIZE) {
            throw new IncompleteCfException();
        }

        String cfBirthDayPart = codiceFiscale.substring(CfOffsets.DAY_OFFSET, CfOffsets.DAY_OFFSET + CfOffsets.DAY_SIZE);
        int birthDayGender;
        try {
            birthDayGender = Integer.valueOf(partialCfDeomocode(cfBirthDayPart, CfOffsets.DAY_OFFSET), 10);
        } catch (NumberFormatException e) {
            throw new InvalidBirthDayException();
        }
        return birthDayGender;
    }

    /**
     * Parse birth date information
     */
    public static ZonedDateTime cfToBirthDate(String codiceFiscale) throws InvalidBirthDayException, EmptyCfException, IncompleteCfException, InvalidBirthMonthException, InvalidBirthYearException, InvalidBirthDateException {
        int birthDay = cfToBirthDay(codiceFiscale);
        int birthMonth = cfToBirthMonth(codiceFiscale);
        int birthYear = cfToBirthYear(codiceFiscale);
        
        ZonedDateTime birthDate = CfDateUtils.toZoneDateTime(birthYear, birthMonth, birthDay);
        if (birthDate == null) {
            throw new InvalidBirthDateException();
        }
        return birthDate;
    }

    public static String cfToBirthPlace(String codiceFiscale) {
        return null;
    }

    private static char[] charExtractor(@NotNull String text, String CHAR_LIST) {
        try {
            Matcher matcher = Pattern.compile("[" + CHAR_LIST + "]+", Pattern.CASE_INSENSITIVE)
                    .matcher(text);
            StringBuilder result = new StringBuilder();
            while (matcher.find()) {
                result.append(matcher.group());
            }
            return result.toString().toCharArray();
        } catch (IllegalStateException e) {
            return "".toCharArray();
        }
    }


    public static String lastNameToCf(@NotNull String lastName) throws InvalidLastNameException {
        String cleanedName = FieldNormalizer.cleanField(lastName, Settings.MIN_NAME_LENGTH, null);
        if (cleanedName == null) {
            throw new InvalidLastNameException();
        }

        char[] consonants = charExtractor(cleanedName, CommonMatchers.CONSONANT_LIST);
        char[] vowels = charExtractor(cleanedName, CommonMatchers.VOWEL_LIST);

        String partialCf = (new String(consonants) + new String(vowels) + "XX")
                .substring(0, CfOffsets.LASTNAME_SIZE);

        return partialCf.toUpperCase();
    }

    public static String firstNameToCf(@NotNull String firstName) throws InvalidFirstNameException {
        String cleanedName = FieldNormalizer.cleanField(firstName, Settings.MIN_NAME_LENGTH, null);
        if (cleanedName == null) {
            throw new InvalidFirstNameException();
        }

        char[] consonants = charExtractor(cleanedName, CommonMatchers.CONSONANT_LIST);
        if (consonants.length > CfOffsets.FIRSTNAME_SIZE) {
            return (consonants[0] + (new String(consonants)).substring(2, CfOffsets.FIRSTNAME_SIZE + 1))
                .toUpperCase();
        }
        try {
            return lastNameToCf(firstName);
        } catch (InvalidLastNameException e) {
            throw new InvalidFirstNameException();
        }
    }

    public static String dateYearToCf(@NotNull ZonedDateTime date) {
        return String.valueOf(date.getYear()).substring(2);
    }

    public static char dateMonthToCf(@NotNull ZonedDateTime date) {
        return BirthMonths.from(date.getMonthValue() -1).toChar();
    }

    public static String dateDayGenderToCf(@NotNull ZonedDateTime date, Genders gender) {
        return String.format("%02d", date.getDayOfMonth() + gender.toValue());
    }

    public static String encodeCf(PersonalInfo personalInfo) throws InvalidLastNameException, InvalidFirstNameException {
        if (personalInfo == null) {
            return null;
        }
        String partialCf = lastNameToCf(personalInfo.getLastName())
            + firstNameToCf(personalInfo.getFirstName())
            + dateYearToCf(personalInfo.getDate())
            + dateMonthToCf(personalInfo.getDate())
            + dateDayGenderToCf(personalInfo.getDate(), personalInfo.getGender())
            + personalInfo.getPlaceCode();

        try {
            return partialCf + CheckDigitizer.checkDigit(partialCf);
        } catch (InvalidPartialCfException e) {
            return null;
        }
    }
}

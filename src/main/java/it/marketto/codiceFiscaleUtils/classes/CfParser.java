package it.marketto.codiceFiscaleUtils.classes;

import it.marketto.codiceFiscaleUtils.constants.CfOffsets;
import it.marketto.codiceFiscaleUtils.constants.GenericMatchers;
import it.marketto.codiceFiscaleUtils.enumerators.BirthMonths;
import it.marketto.codiceFiscaleUtils.enumerators.CRC;
import it.marketto.codiceFiscaleUtils.enumerators.Omocodes;
import it.marketto.codiceFiscaleUtils.exceptions.*;
import it.marketto.codiceFiscaleUtils.enumerators.Genders;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class CfParser {
    public static byte OMOCODE_BITMAP = (byte) 0b0111011011000000;


    public static PersonalInfo cfDecode(String codiceFiscale)
        throws EmptyCfException, IncompleteCfException, InvalidBirthDayException
    {
        if (StringUtils.isEmpty(codiceFiscale)) {
            throw new EmptyCfException();
        }
        if (codiceFiscale.length() < 16) {
            throw new IncompleteCfException();
        }

        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setGender(cfToGender(codiceFiscale));

        return personalInfo;
    }
    
    private static boolean checkBitmap(int offset) {
    	byte binaryOffset = (byte) Math.round(Math.pow(2, offset));
        return (binaryOffset & OMOCODE_BITMAP) != 0;
    }

    private static char charOmocode(char c, int offset) {
        if (String.valueOf(c).toUpperCase().matches(GenericMatchers.CHAR_LETTER) && checkBitmap(offset)) {
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
    
    private static Character applyCaseToChar(Character targetChar, char counterCaseChar) {
    	if (targetChar == null) {
    		return null;
    	}
        boolean isUpperCase = String.valueOf(counterCaseChar).equalsIgnoreCase(String.valueOf(counterCaseChar).toUpperCase());
        boolean isLowerCase = String.valueOf(counterCaseChar).equalsIgnoreCase(String.valueOf(counterCaseChar).toLowerCase());

        if (isUpperCase && !isLowerCase) {
            return Character.toUpperCase(targetChar);
        } else if (!isUpperCase && isLowerCase) {
            return Character.toLowerCase(targetChar);
        }
        return targetChar;
    }
    private static Character applyCaseToChar(CRC crc, char counterCaseChar) {
    	return applyCaseToChar(crc.toChar(), counterCaseChar);
    }

    public static String cfDeomocode(String codiceFiscale) {
        if (codiceFiscale != null && codiceFiscale.length() <= CfOffsets.YEAR_OFFSET) {
            return codiceFiscale;
        }
        String deomocodedCf = partialCfDeomocode(codiceFiscale);
        if (deomocodedCf.length() < CfOffsets.CRC_OFFSET) {
            return deomocodedCf;
        }
        String partialDeomocodedCf = deomocodedCf.substring(CfOffsets.LASTNAME_OFFSET, CfOffsets.CRC_OFFSET);
        return partialDeomocodedCf + applyCaseToChar(
            CheckDigitizer.checkDigit(deomocodedCf).toChar(),
            deomocodedCf.substring(CfOffsets.CRC_OFFSET, CfOffsets.CRC_SIZE).charAt(0)
        );
    }
    public static String cfDeomocode() {
    	return cfDeomocode(null);
    }
    
    public static String cfOmocode(String codiceFiscale, byte omocodeId) {
        if (omocodeId == 0) {
            return cfDeomocode(codiceFiscale);
        }
        char[] omocodedCf = codiceFiscale.toCharArray();
        for (int i = codiceFiscale.length() - 1, o = 0; i >= 0; i--) {
            if ((Math.round(Math.pow(2, i)) & OMOCODE_BITMAP) != 0) {
            	boolean charToEncode = (Math.round(Math.pow(2, o)) & omocodeId) != 0;
                boolean isOmocode = Pattern.matches(GenericMatchers.CHAR_NUMBER, String.valueOf(omocodedCf[i]));
                if (charToEncode != isOmocode) {
                    omocodedCf[i] = Omocodes.from(omocodedCf[i]).toChar();
                }
                o++;
            }
        }
        Character crc = omocodedCf.length > CfOffsets.CRC_OFFSET ? omocodedCf[CfOffsets.CRC_OFFSET] : null;
        if (crc != null) {
            String partialCf = codiceFiscale.substring(CfOffsets.LASTNAME_OFFSET, CfOffsets.CRC_OFFSET);
            Character crcChar = applyCaseToChar(
            	CheckDigitizer.checkDigit(partialCf),
                crc
            );
            if (crcChar != null) {
            	return partialCf + crcChar;
            }
        }
        return codiceFiscale;
    }
    public static String cfOmocode(String codiceFiscale, int omocodeId) {
    	return cfOmocode(codiceFiscale, (byte) omocodeId);
    }
    public static String cfOmocode(String codiceFiscale) {
    	return cfOmocode(codiceFiscale, 0);
    }

    /**
     * Parse birth year information
     */
    public static int cfToBirthYear(String codiceFiscale) throws EmptyCfException, IncompleteCfException, InvalidBirthYearException {
        if (StringUtils.isEmpty(codiceFiscale)){
            throw new EmptyCfException();
        }
        String cfBirthYearPart = codiceFiscale.substring(CfOffsets.YEAR_OFFSET, CfOffsets.YEAR_SIZE);
        if (cfBirthYearPart == null || cfBirthYearPart.length() < CfOffsets.YEAR_SIZE) {
            throw new IncompleteCfException();
        }
        Integer birthYear = Integer.parseInt(partialCfDeomocode(cfBirthYearPart, CfOffsets.YEAR_OFFSET), 10);

        if (birthYear == null) {
            throw new InvalidBirthYearException();
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentCentury = (int) Math.floor(currentYear / 100) * 100;
        int centuryAdjust = (birthYear > (currentYear - currentCentury)) ? -100 : 0;
        int birthFullYear = birthYear + currentCentury + centuryAdjust;

        return birthFullYear;
    }

    /**
     * Parse birth month information
     */
    public static int cfToBirthMonth(String codiceFiscale) throws EmptyCfException, IncompleteCfException, InvalidBirthMonthException {
        if (StringUtils.isEmpty(codiceFiscale)){
            throw new EmptyCfException();
        }

        String cfBirthMonthPart = codiceFiscale.substring(CfOffsets.MONTH_OFFSET, CfOffsets.MONTH_SIZE).toUpperCase();
        if (cfBirthMonthPart == null ||cfBirthMonthPart.length() < CfOffsets.MONTH_SIZE) {
            throw new IncompleteCfException();
        }
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
            if (StringUtils.isEmpty(codiceFiscale)){
            throw new EmptyCfException();
        }

        String cfBirthDayPart = codiceFiscale.substring(CfOffsets.DAY_OFFSET, CfOffsets.DAY_SIZE);
        if (cfBirthDayPart == null || cfBirthDayPart.length() < CfOffsets.DAY_SIZE) {
            throw new IncompleteCfException();
        }
        Integer birthDayGender = Integer.valueOf(partialCfDeomocode(cfBirthDayPart, CfOffsets.DAY_OFFSET), 10);
        if (birthDayGender == null) {
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

    public static String encodeCf(PersonalInfo personalInfo) {
        return null;
    }
}

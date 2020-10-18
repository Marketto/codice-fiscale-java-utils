package it.marketto.codiceFiscaleUtils.classes;

import it.marketto.codiceFiscaleUtils.constants.CfOffsets;
import it.marketto.codiceFiscaleUtils.constants.GenericMatchers;
import it.marketto.codiceFiscaleUtils.constants.Settings;
import it.marketto.codiceFiscaleUtils.enumerators.CRC;
import it.marketto.codiceFiscaleUtils.enumerators.Omocodes;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class CfParser {
    public static byte OMOCODE_BITMAP = (byte) 0b0111011011000000;
    
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
    
    private static Character appyCaseToChar(Character targetChar, char counterCaseChar) {
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
    private static Character appyCaseToChar(CRC crc, char counterCaseChar) {
    	return appyCaseToChar(crc.toChar(), counterCaseChar);
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
        return partialDeomocodedCf + appyCaseToChar(
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
            Character crcChar = appyCaseToChar(
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

    public static String cfToBirthPlace(String codiceFiscale) {
        return null;
    }

    public static String encodeCf(PersonalInfo personalInfo) {
        return null;
    }
}

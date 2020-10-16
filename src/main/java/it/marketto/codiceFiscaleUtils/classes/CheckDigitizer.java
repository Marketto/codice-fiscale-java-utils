package it.marketto.codiceFiscaleUtils.classes;

import java.util.regex.Pattern;

import it.marketto.codiceFiscaleUtils.constants.CfOffsets;
import it.marketto.codiceFiscaleUtils.constants.GenericMatchers;
import it.marketto.codiceFiscaleUtils.enumerators.CRC;

public class CheckDigitizer {
    private static int CHAR_OFFSET = 'A';
    private static int NUM_CHAR_OFFSET = '0';
    private static int CRC_MOD = 26;
/*
    public static evaluateChar(partialCF: string = ""): IGeneratorWrapper<number, 0, void> {
      return generatorWrapper(this.evaluateCharGenerator(partialCF));
    }
*/    
	public static CRC checkDigit(String partialCf) {
		if (partialCf != null && partialCf.length() >= CfOffsets.CRC_OFFSET) {
			// First 15 chars, excluding CRC if present
			final String upperCaseCf = partialCf.substring(CfOffsets.CF_INIT_OFFSET, CfOffsets.CRC_OFFSET).toUpperCase();
			final int partialCfLength = upperCaseCf.length();
			if (partialCfLength > 0) {
				int cdCodeSum = 0;
	            for (int index = 0; index < partialCfLength; index++) {
	                char targetChar = upperCaseCf.charAt(index);
	                final boolean isNumber = Pattern.matches(GenericMatchers.CHAR_NUMBER, String.valueOf(targetChar));
	                if (isNumber) {
	                    // Numbers have always (odd/even) the same values of corresponding letters (0-9 => A-J)
						targetChar = (char) (CHAR_OFFSET + targetChar - NUM_CHAR_OFFSET);
	                }
	                // Odd/Even are shifted/swapped
	                // array starts from 0, "Agenzia delle Entrate" documentation counts the string from 1
	                boolean isOdd = (index % 2) == 0; // Odd according to documentation
					final int mappedValue;
	                if (isOdd) {
	                    // Odd positions
						mappedValue = CRC.from(targetChar).toValue();
					} else {
	                    // Even positions
						mappedValue = (targetChar - CHAR_OFFSET);
	                }
	                cdCodeSum += mappedValue;
				}
	            return CRC.from((char) (cdCodeSum % CRC_MOD + CHAR_OFFSET));
			}
        }
		return null;
    }
}

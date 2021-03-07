package it.marketto.utils.codiceFiscaleUtils.classes;

import java.text.Normalizer;

public class FieldNormalizer {

	public static String cleanField(String text, int minLength, String emptyValue) {
		if (text != null) {
			String cleanedText = removeSymbolsNumbers(removeDiacritics(text));
			if (cleanedText.length() >= minLength) {
				return cleanedText;
			}
		}
		return emptyValue;
	}
	public static String cleanField(String text, int minLength) {
		return cleanField(text, minLength, "");
	}
	public static String cleanField(String text) {
		return cleanField(text, 1);
	}

	public static String removeDiacritics(String text) {
		if (text == null) {
			return "";
		}
		return Normalizer.normalize(text, Normalizer.Form.NFD)
				.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	}

	public static String removeSymbolsNumbers(String text) {
		if (text == null) {
			return "";
		}
		return text
			.replaceAll("[^A-Za-z]", "");
	}
}

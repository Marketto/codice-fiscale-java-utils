package it.marketto.codiceFiscaleUtils.classes;

import java.text.Normalizer;

public class DiacriticRemover {
	public static String removeDiacritics(String text) {
		return Normalizer.normalize(text, Normalizer.Form.NFD)
				.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	}
}

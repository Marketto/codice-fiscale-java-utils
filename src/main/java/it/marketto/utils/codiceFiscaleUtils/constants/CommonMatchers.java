package it.marketto.utils.codiceFiscaleUtils.constants;

public class CommonMatchers {
    public static final String CONSONANT_LIST = "B-DF-HJ-NP-TV-Z";
    public static final String VOWEL_LIST = "AEIOU";
    public static final String ALL_LETTERS = "[A-Z]";
    public static final String NUMBER_LIST = "\\d";
    public static final String NON_ZERO_NUMBER_LIST = "1-9";
    public static final String ZERO_LIST = "0";

    public static final String MONTH_LIST = "A-EHLMPR-T";
    public static final String MONTH_30DAYS_LIST = "DHPS";
    public static final String MONTH_31DAYS_LIST = "ACELMRT";
    public static final String CITY_CODE_LIST = "A-M";
    public static final String COUNTRY_CODE_LIST = "Z";

    public static final String ALL_LETTERS_WITH_DIACRITICS = "[\\p{L}]";
}

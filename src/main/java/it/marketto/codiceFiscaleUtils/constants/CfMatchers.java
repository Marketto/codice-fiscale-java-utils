package it.marketto.codiceFiscaleUtils.constants;

public final class CfMatchers {
	public static String CONSONANT_LIST = "B-DF-HJ-NP-TV-Z";
	public static String VOWEL_LIST = "AEIOU";
	public static String OMOCODE_NUMBER_LIST = "\\dLMNP-V";
	public static String OMOCODE_NON_ZERO_NUMBER_LIST = "1-9MNP-V";
	public static String OMOCODE_ZERO_LIST = "0L";
	public static String MONTH_LIST = "A-EHLMPR-T";
	public static String MONTH_30DAYS_LIST = "DHPS";
	public static String MONTH_31DAYS_LIST = "ACELMRT";
	public static String CITY_CODE_LIST = "A-M";
	public static String COUNTRY_CODE_LIST = "Z";

	public static String CF_NAME_MATCHER = "[A-Z][" + VOWEL_LIST + "][" + VOWEL_LIST + "X]|[" + CONSONANT_LIST + "]{2}[A-Z]";
	public static String CF_SURNAME_MATCHER = CF_NAME_MATCHER;
	public static String CF_FULL_NAME_MATCHER = "(?:" + CF_NAME_MATCHER + "){2}";

	public static String YEAR_MATCHER = "[" + OMOCODE_NUMBER_LIST + "]{2}";
	public static String LEAP_YEAR_MATCHER = "[02468LNQSU][048LQU]|[13579MPRTV][26NS]";
	public static String MONTH_MATCHER = "[" + MONTH_LIST + "]";
	public static String DAY_2X_MATCHER = "[26NS]";
	public static String DAY_3X_MATCHER = "[37PT]";
	public static String DAY_29_MATCHER = "[" + OMOCODE_ZERO_LIST + "4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[1256MNRS][" + OMOCODE_NUMBER_LIST + "]";
	public static String DAY_30_MATCHER = "[" + DAY_3X_MATCHER + "][" + OMOCODE_ZERO_LIST + "]";
	public static String DAY_31_MATCHER = "[" + DAY_3X_MATCHER + "][" + OMOCODE_ZERO_LIST + "1M]";

	public static String DAY_MATCHER = "(?:" + DAY_29_MATCHER + "|" + DAY_3X_MATCHER + "[" + OMOCODE_ZERO_LIST + "1M])";
	public static String MALE_DAY_MATCHER = "(?:[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[12MN][" + OMOCODE_NUMBER_LIST + "]|[3P][" + OMOCODE_ZERO_LIST + "1M])";
	public static String FEMALE_DAY_MATCHER = "(?:[4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[56RS][" + OMOCODE_NUMBER_LIST + "]|[7T][" + OMOCODE_ZERO_LIST + "1M])";
	public static String MONTH_DAY_MATCHER = "" + MONTH_MATCHER + "(?:" + DAY_29_MATCHER + ")|[" + MONTH_30DAYS_LIST + "]" + DAY_30_MATCHER + "|[" + MONTH_31DAYS_LIST + "]" + DAY_31_MATCHER + "";
	public static String FULL_DATE_MATCHER = "" + YEAR_MATCHER + "(?:" + MONTH_MATCHER + "(?:[" + OMOCODE_ZERO_LIST + "4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[15MR][" + OMOCODE_NUMBER_LIST + "]|" + DAY_2X_MATCHER + "[0-8LMNP-U])|[" + MONTH_30DAYS_LIST + "]" + DAY_3X_MATCHER + "[" + OMOCODE_ZERO_LIST + "]|[" + MONTH_31DAYS_LIST + "]" + DAY_3X_MATCHER + "[" + OMOCODE_ZERO_LIST + "1M]|[" + MONTH_30DAYS_LIST + "" + MONTH_31DAYS_LIST + "]" + DAY_2X_MATCHER + "[9V])|(?:" + LEAP_YEAR_MATCHER + ")B" + DAY_2X_MATCHER + "[9V]";
	public static String MALE_FULL_DATE_MATCHER = "" + YEAR_MATCHER + "(?:" + MONTH_MATCHER + "(?:[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[1M][" + OMOCODE_NUMBER_LIST + "]|[2N][0-8LMNP-U])|[" + MONTH_30DAYS_LIST + "][3P][" + OMOCODE_ZERO_LIST + "]|[" + MONTH_31DAYS_LIST + "][3P][" + OMOCODE_ZERO_LIST + "1M]|[" + MONTH_30DAYS_LIST + "" + MONTH_31DAYS_LIST + "][2N][9V])|(?:" + LEAP_YEAR_MATCHER + ")B[2N][9V]";
	public static String FEMALE_FULL_DATE_MATCHER = "" + YEAR_MATCHER + "(?:" + MONTH_MATCHER + "(?:[4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[5R][" + OMOCODE_NUMBER_LIST + "]|[6S][0-8LMNP-U])|[" + MONTH_30DAYS_LIST + "][7T][" + OMOCODE_ZERO_LIST + "]|[" + MONTH_31DAYS_LIST + "][7T][" + OMOCODE_ZERO_LIST + "1M]|[" + MONTH_30DAYS_LIST + "" + MONTH_31DAYS_LIST + "][6S][9V])|(?:" + LEAP_YEAR_MATCHER + ")B[6S][9V]";

	public static String CITY_CODE_MATCHER = "[" + CITY_CODE_LIST + "](?:[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]{2}|[" + OMOCODE_ZERO_LIST + "](?:[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]|[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]))";
	public static String COUNTRY_CODE_MATCHER = "" + COUNTRY_CODE_LIST + "[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]{2}";
	public static String BELFIORE_CODE_MATCHER = "(?:[" + CITY_CODE_LIST + "" + COUNTRY_CODE_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]{2})|(?:[" + CITY_CODE_LIST + "][" + OMOCODE_ZERO_LIST + "](?:[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]|[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]))";

	public static String CHECK_DIGIT = "[A-Z]";

	public static String CODICE_FISCALE = "" + CF_FULL_NAME_MATCHER + "(?:" + FULL_DATE_MATCHER + ")(?:" + BELFIORE_CODE_MATCHER + ")" + CHECK_DIGIT + "";

	public static String PARTIAL_CF_NAME_MATCHER = "[A-Z][" + VOWEL_LIST + "]?|[" + CONSONANT_LIST + "]{1,2}";
	public static String PARTIAL_CF_FULL_NAME = "(?:" + PARTIAL_CF_NAME_MATCHER + ")|(?:(?:" + CF_NAME_MATCHER + ")(?:" + PARTIAL_CF_NAME_MATCHER + ")?)";
	public static String PARTIAL_YEAR = "[" + OMOCODE_NUMBER_LIST + "]";
	public static String PARTIAL_MONTH_DAY = "" + MONTH_MATCHER + "[" + OMOCODE_ZERO_LIST + "12456MNQRS]?|[" + MONTH_30DAYS_LIST + "" + MONTH_31DAYS_LIST + "]" + DAY_3X_MATCHER + "";
	public static String PARTIAL_FULL_DATE = "" + PARTIAL_YEAR + "|(?:" + YEAR_MATCHER + "(?:" + PARTIAL_MONTH_DAY + ")?)";
	public static String PARTIAL_BELFIORE_CODE_MATCHER = "[" + CITY_CODE_LIST + "" + COUNTRY_CODE_LIST + "](?:[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]?)?|[" + COUNTRY_CODE_LIST + "](?:[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NUMBER_LIST + "]?)?";

	public static String PARTIAL_CF = "" + PARTIAL_CF_FULL_NAME + "|(?:" + CF_FULL_NAME_MATCHER + "(?:(?:" + PARTIAL_FULL_DATE + ")|(?:" + FULL_DATE_MATCHER + ")(?:(?:" + PARTIAL_BELFIORE_CODE_MATCHER + ")|(?:" + BELFIORE_CODE_MATCHER + ")" + CHECK_DIGIT + "?)?)?)?";

}

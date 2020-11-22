package it.marketto.codiceFiscaleUtils.constants;

public final class CfMatchers {
    public static final String CONSONANT_LIST = "B-DF-HJ-NP-TV-Z";
	public static final String VOWEL_LIST = "AEIOU";
	public static final String OMOCODE_NUMBER_LIST = "\\dLMNP-V";
	public static final String OMOCODE_NON_ZERO_NUMBER_LIST = "1-9MNP-V";
	public static final String OMOCODE_ZERO_LIST = "0L";
	public static final String MONTH_LIST = "A-EHLMPR-T";
	public static final String MONTH_30DAYS_LIST = "DHPS";
	public static final String MONTH_31DAYS_LIST = "ACELMRT";
	public static final String CITY_CODE_LIST = "A-M";
	public static final String COUNTRY_CODE_LIST = "Z";

	public static final String CF_NAME_MATCHER = "[A-Z][" + VOWEL_LIST + "][" + VOWEL_LIST + "X]|[" + CONSONANT_LIST + "]{2}[A-Z]";
	public static final String CF_SURNAME_MATCHER = CF_NAME_MATCHER;
	public static final String CF_FULL_NAME_MATCHER = "(?:" + CF_NAME_MATCHER + "){2}";

	public static final String YEAR_MATCHER = "[" + OMOCODE_NUMBER_LIST + "]{2}";
	public static final String LEAP_YEAR_MATCHER = "[02468LNQSU][048LQU]|[13579MPRTV][26NS]";
	public static final String MONTH_MATCHER = "[" + MONTH_LIST + "]";
	public static final String DAY_2X_MATCHER = "[26NS]";
	public static final String DAY_3X_MATCHER = "[37PT]";
	public static final String DAY_29_MATCHER = "[" + OMOCODE_ZERO_LIST + "4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[1256MNRS][" + OMOCODE_NUMBER_LIST + "]";
	public static final String DAY_30_MATCHER = "[" + DAY_3X_MATCHER + "][" + OMOCODE_ZERO_LIST + "]";
	public static final String DAY_31_MATCHER = "[" + DAY_3X_MATCHER + "][" + OMOCODE_ZERO_LIST + "1M]";

	public static final String DAY_MATCHER = "(?:" + DAY_29_MATCHER + "|" + DAY_3X_MATCHER + "[" + OMOCODE_ZERO_LIST + "1M])";
	public static final String MALE_DAY_MATCHER = "(?:[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[12MN][" + OMOCODE_NUMBER_LIST + "]|[3P][" + OMOCODE_ZERO_LIST + "1M])";
	public static final String FEMALE_DAY_MATCHER = "(?:[4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[56RS][" + OMOCODE_NUMBER_LIST + "]|[7T][" + OMOCODE_ZERO_LIST + "1M])";
	public static final String MONTH_DAY_MATCHER = "" + MONTH_MATCHER + "(?:" + DAY_29_MATCHER + ")|[" + MONTH_30DAYS_LIST + "]" + DAY_30_MATCHER + "|[" + MONTH_31DAYS_LIST + "]" + DAY_31_MATCHER + "";
	public static final String FULL_DATE_MATCHER = "" + YEAR_MATCHER + "(?:" + MONTH_MATCHER + "(?:[" + OMOCODE_ZERO_LIST + "4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[15MR][" + OMOCODE_NUMBER_LIST + "]|" + DAY_2X_MATCHER + "[0-8LMNP-U])|[" + MONTH_30DAYS_LIST + "]" + DAY_3X_MATCHER + "[" + OMOCODE_ZERO_LIST + "]|[" + MONTH_31DAYS_LIST + "]" + DAY_3X_MATCHER + "[" + OMOCODE_ZERO_LIST + "1M]|[" + MONTH_30DAYS_LIST + "" + MONTH_31DAYS_LIST + "]" + DAY_2X_MATCHER + "[9V])|(?:" + LEAP_YEAR_MATCHER + ")B" + DAY_2X_MATCHER + "[9V]";
	public static final String MALE_FULL_DATE_MATCHER = "" + YEAR_MATCHER + "(?:" + MONTH_MATCHER + "(?:[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[1M][" + OMOCODE_NUMBER_LIST + "]|[2N][0-8LMNP-U])|[" + MONTH_30DAYS_LIST + "][3P][" + OMOCODE_ZERO_LIST + "]|[" + MONTH_31DAYS_LIST + "][3P][" + OMOCODE_ZERO_LIST + "1M]|[" + MONTH_30DAYS_LIST + "" + MONTH_31DAYS_LIST + "][2N][9V])|(?:" + LEAP_YEAR_MATCHER + ")B[2N][9V]";
	public static final String FEMALE_FULL_DATE_MATCHER = "" + YEAR_MATCHER + "(?:" + MONTH_MATCHER + "(?:[4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[5R][" + OMOCODE_NUMBER_LIST + "]|[6S][0-8LMNP-U])|[" + MONTH_30DAYS_LIST + "][7T][" + OMOCODE_ZERO_LIST + "]|[" + MONTH_31DAYS_LIST + "][7T][" + OMOCODE_ZERO_LIST + "1M]|[" + MONTH_30DAYS_LIST + "" + MONTH_31DAYS_LIST + "][6S][9V])|(?:" + LEAP_YEAR_MATCHER + ")B[6S][9V]";

	public static final String CITY_CODE_MATCHER = "[" + CITY_CODE_LIST + "](?:[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]{2}|[" + OMOCODE_ZERO_LIST + "](?:[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]|[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]))";
	public static final String COUNTRY_CODE_MATCHER = "" + COUNTRY_CODE_LIST + "[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]{2}";
	public static final String BELFIORE_CODE_MATCHER = "(?:[" + CITY_CODE_LIST + "" + COUNTRY_CODE_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]{2})|(?:[" + CITY_CODE_LIST + "][" + OMOCODE_ZERO_LIST + "](?:[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]|[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]))";

	public static final String CHECK_DIGIT = "[A-Z]";

	public static final String CODICE_FISCALE = "" + CF_FULL_NAME_MATCHER + "(?:" + FULL_DATE_MATCHER + ")(?:" + BELFIORE_CODE_MATCHER + ")" + CHECK_DIGIT + "";
}

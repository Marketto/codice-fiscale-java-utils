package it.marketto.codiceFiscaleUtils.constants;

public final class CfMatchers {
	public static final String OMOCODE_NUMBER_LIST = CommonMatchers.NUMBER_LIST + "LMNP-V";
	public static final String OMOCODE_NON_ZERO_NUMBER_LIST = CommonMatchers.NON_ZERO_NUMBER_LIST + "MNP-V";
	public static final String OMOCODE_ZERO_LIST = CommonMatchers.ZERO_LIST + "0";

	public static final String CF_NAME_MATCHER = "" + CommonMatchers.ALL_LETTERS + "[" + CommonMatchers.VOWEL_LIST + "][" + CommonMatchers.VOWEL_LIST + "X]|[" + CommonMatchers.CONSONANT_LIST + "]{2}" + CommonMatchers.ALL_LETTERS + "";
	public static final String CF_SURNAME_MATCHER = CF_NAME_MATCHER;
	public static final String CF_FULL_NAME_MATCHER = "(?:" + CF_NAME_MATCHER + "){2}";

	public static final String YEAR_MATCHER = "[" + OMOCODE_NUMBER_LIST + "]{2}";
	public static final String LEAP_YEAR_MATCHER = "[02468LNQSU][048LQU]|[13579MPRTV][26NS]";
	public static final String MONTH_MATCHER = "[" + CommonMatchers.MONTH_LIST + "]";
	public static final String DAY_2X_MATCHER = "[26NS]";
	public static final String DAY_3X_MATCHER = "[37PT]";
	public static final String DAY_29_MATCHER = "[" + OMOCODE_ZERO_LIST + "4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[1256MNRS][" + OMOCODE_NUMBER_LIST + "]";
	public static final String DAY_30_MATCHER = "[" + DAY_3X_MATCHER + "][" + OMOCODE_ZERO_LIST + "]";
	public static final String DAY_31_MATCHER = "[" + DAY_3X_MATCHER + "][" + OMOCODE_ZERO_LIST + "1M]";

	public static final String DAY_MATCHER = "(?:" + DAY_29_MATCHER + "|" + DAY_3X_MATCHER + "[" + OMOCODE_ZERO_LIST + "1M])";
	public static final String MALE_DAY_MATCHER = "(?:[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[12MN][" + OMOCODE_NUMBER_LIST + "]|[3P][" + OMOCODE_ZERO_LIST + "1M])";
	public static final String FEMALE_DAY_MATCHER = "(?:[4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[56RS][" + OMOCODE_NUMBER_LIST + "]|[7T][" + OMOCODE_ZERO_LIST + "1M])";
	public static final String MONTH_DAY_MATCHER = "" + MONTH_MATCHER + "(?:" + DAY_29_MATCHER + ")|[" + CommonMatchers.MONTH_30DAYS_LIST + "]" + DAY_30_MATCHER + "|[" + CommonMatchers.MONTH_31DAYS_LIST + "]" + DAY_31_MATCHER + "";
	public static final String FULL_DATE_MATCHER = "" + YEAR_MATCHER + "(?:" + MONTH_MATCHER + "(?:[" + OMOCODE_ZERO_LIST + "4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[15MR][" + OMOCODE_NUMBER_LIST + "]|" + DAY_2X_MATCHER + "[0-8LMNP-U])|[" + CommonMatchers.MONTH_30DAYS_LIST + "]" + DAY_3X_MATCHER + "[" + OMOCODE_ZERO_LIST + "]|[" + CommonMatchers.MONTH_31DAYS_LIST + "]" + DAY_3X_MATCHER + "[" + OMOCODE_ZERO_LIST + "1M]|[" + CommonMatchers.MONTH_30DAYS_LIST + "" + CommonMatchers.MONTH_31DAYS_LIST + "]" + DAY_2X_MATCHER + "[9V])|(?:" + LEAP_YEAR_MATCHER + ")B" + DAY_2X_MATCHER + "[9V]";
	public static final String MALE_FULL_DATE_MATCHER = "" + YEAR_MATCHER + "(?:" + MONTH_MATCHER + "(?:[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[1M][" + OMOCODE_NUMBER_LIST + "]|[2N][0-8LMNP-U])|[" + CommonMatchers.MONTH_30DAYS_LIST + "][3P][" + OMOCODE_ZERO_LIST + "]|[" + CommonMatchers.MONTH_31DAYS_LIST + "][3P][" + OMOCODE_ZERO_LIST + "1M]|[" + CommonMatchers.MONTH_30DAYS_LIST + "" + CommonMatchers.MONTH_31DAYS_LIST + "][2N][9V])|(?:" + LEAP_YEAR_MATCHER + ")B[2N][9V]";
	public static final String FEMALE_FULL_DATE_MATCHER = "" + YEAR_MATCHER + "(?:" + MONTH_MATCHER + "(?:[4Q][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]|[5R][" + OMOCODE_NUMBER_LIST + "]|[6S][0-8LMNP-U])|[" + CommonMatchers.MONTH_30DAYS_LIST + "][7T][" + OMOCODE_ZERO_LIST + "]|[" + CommonMatchers.MONTH_31DAYS_LIST + "][7T][" + OMOCODE_ZERO_LIST + "1M]|[" + CommonMatchers.MONTH_30DAYS_LIST + "" + CommonMatchers.MONTH_31DAYS_LIST + "][6S][9V])|(?:" + LEAP_YEAR_MATCHER + ")B[6S][9V]";

	public static final String CITY_CODE_MATCHER = "[" + CommonMatchers.CITY_CODE_LIST + "](?:[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]{2}|[" + OMOCODE_ZERO_LIST + "](?:[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]|[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]))";
	public static final String COUNTRY_CODE_MATCHER = "" + CommonMatchers.COUNTRY_CODE_LIST + "[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]{2}";
	public static final String BELFIORE_CODE_MATCHER = "(?:[" + CommonMatchers.CITY_CODE_LIST + "" + CommonMatchers.COUNTRY_CODE_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]{2})|(?:[" + CommonMatchers.CITY_CODE_LIST + "][" + OMOCODE_ZERO_LIST + "](?:[" + OMOCODE_NON_ZERO_NUMBER_LIST + "][" + OMOCODE_NUMBER_LIST + "]|[" + OMOCODE_ZERO_LIST + "][" + OMOCODE_NON_ZERO_NUMBER_LIST + "]))";

	public static final String CHECK_DIGIT = CommonMatchers.ALL_LETTERS;

	public static final String CODICE_FISCALE = "" + CF_FULL_NAME_MATCHER + "(?:" + FULL_DATE_MATCHER + ")(?:" + BELFIORE_CODE_MATCHER + ")" + CHECK_DIGIT + "";
}

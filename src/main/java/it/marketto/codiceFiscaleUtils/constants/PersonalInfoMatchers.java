package it.marketto.codiceFiscaleUtils.constants;

public class PersonalInfoMatchers {
    private static final String NAME_PART = CommonMatchers.ALL_LETTERS_WITH_DIACRITICS + "+";
    private static final String SURNAME_PART = "(?:" + NAME_PART + "'\\s?)?" + NAME_PART;

    public static final String BELFIORE_CODE_MATCHER = "(?:[" + CommonMatchers.CITY_CODE_LIST + "" + CommonMatchers.COUNTRY_CODE_LIST + "][" + CommonMatchers.NON_ZERO_NUMBER_LIST + "][" + CommonMatchers.NUMBER_LIST + "]{2})|(?:[" + CommonMatchers.CITY_CODE_LIST + "][" + CommonMatchers.ZERO_LIST + "](?:[" + CommonMatchers.NON_ZERO_NUMBER_LIST + "][" + CommonMatchers.NUMBER_LIST + "]|[" + CommonMatchers.ZERO_LIST + "][" + CommonMatchers.NON_ZERO_NUMBER_LIST + "]))";
    public static final String FIRST_NAME = NAME_PART + "(?:\\s" + NAME_PART + ")*";
    public static final String LAST_NAME = SURNAME_PART + "(?:\\s" + SURNAME_PART + ")*";
}

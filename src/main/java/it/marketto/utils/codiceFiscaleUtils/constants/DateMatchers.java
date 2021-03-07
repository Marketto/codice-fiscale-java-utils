package it.marketto.utils.codiceFiscaleUtils.constants;

public class DateMatchers {
    public static final String YEAR = "[12][0-9]{3}";
    public static final String MONTH = "0[1-9]|1[0-2]";
    public static final String DAYS_30_MONTHS = "0[469]|11";
    public static final String DAYS_31_MONTHS = "0[13578]|1[02]";
    public static final String MONTH_DAY = "(?:" + MONTH + ")-(?:0[1-9]|[12]\\d)|(?:" + DAYS_30_MONTHS + ")-30|(?:" + DAYS_31_MONTHS + ")-3[01]";
    public static final String HOURS = "[01]\\d|2[0-3]";
    public static final String MINUTES = "[0-5]\\d";
    public static final String SECONDS = MINUTES;
    public static final String MILLISECONDS = "\\d{3}";
    public static final String TIMEZONE = "Z|[-+](?:" + HOURS + ")(?::?" + MINUTES + ")?";
    public static final String TIME = "(?:" + HOURS + ")(?::" + MINUTES + "(?::" + SECONDS + "(\\." + MILLISECONDS + ")?)?(?:" + TIMEZONE + ")?)?";
    public static final String ISO8601_DATE_TIME = YEAR + "-" + MONTH_DAY + "(?:T" + TIME + ")?";
}

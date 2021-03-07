package it.marketto.utils.codiceFiscaleUtils.constants;

public final class CfOffsets {
	public static int LASTNAME_OFFSET = 0;
	public static int LASTNAME_SIZE = 3;
	public static int FIRSTNAME_OFFSET = 3;
	public static int FIRSTNAME_SIZE = 3;
	public static int YEAR_OFFSET = 6;
	public static int YEAR_SIZE = 2;
	public static int MONTH_OFFSET = 8;
	public static int MONTH_SIZE = 1;
	public static int DAY_OFFSET = 9;
	public static int DAY_SIZE = 2;
	public static int DATE_OFFSET = YEAR_OFFSET;
	public static int DATE_SIZE = YEAR_SIZE + MONTH_SIZE + DAY_SIZE;
	public static int GENDER_OFFSET = DAY_OFFSET;
	public static int GENDER_SIZE = 1;
	public static int PLACE_OFFSET = 11;
	public static int PLACE_SIZE = 4;
	public static int CRC_OFFSET = 15;
	public static int CRC_SIZE = 1;
	public static int CF_INIT_OFFSET = LASTNAME_OFFSET;
	public static int CF_SIZE = CF_INIT_OFFSET + LASTNAME_SIZE + FIRSTNAME_SIZE + DATE_SIZE + PLACE_SIZE + CRC_SIZE;
}

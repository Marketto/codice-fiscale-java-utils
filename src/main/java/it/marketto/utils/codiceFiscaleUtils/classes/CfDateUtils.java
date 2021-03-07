package it.marketto.utils.codiceFiscaleUtils.classes;

import it.marketto.utils.codiceFiscaleUtils.constants.Settings;

import java.time.*;
import java.util.Date;

public class CfDateUtils {
    public static ZonedDateTime toZoneDateTime(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return ZonedDateTime.of(date, ZoneId.of(Settings.ITALIAN_TIME_ZONE));
    }
    public static ZonedDateTime toZoneDateTime(LocalDate date) {
        if (date == null) {
            return null;
        }
        return toZoneDateTime(LocalDateTime.of(date, LocalTime.of(0, 0)));
    }
    public static ZonedDateTime toZoneDateTime(Date date) {
        return toZoneDateTime(
            date.toInstant()
                .atZone(ZoneId.of(Settings.ITALIAN_TIME_ZONE))
                .toLocalDateTime()
        );
    }
    public static ZonedDateTime toZoneDateTime(Integer year, Integer month, Integer day) {
        if (
            year != null && month != null && day != null &&
            year >= 0 && year <= LocalDate.now().getYear() &&
            month >= 1 && month <= 12 &&
            day >= 1 && day <= 31
        ) {
            return toZoneDateTime(LocalDateTime.of(year, month, day, 0, 0, 0));
        }
        return null;
    }
}

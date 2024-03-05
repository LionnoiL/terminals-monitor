package ua.gaponov.monitor.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.gaponov.monitor.report.DatePeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    public static DatePeriod getCurrentDayPeriod() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59, 999999999);
        return DatePeriod.of(startOfDay, endOfDay);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return dateTime.format(formatter);
    }

    public static LocalDateTime getLocalDateFromString(String stringDate, boolean endDay) {
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (endDay) {
            return LocalDate.from(europeanDateFormatter.parse(stringDate)).atTime(23, 59, 59, 999999999);
        } else {
            return LocalDate.from(europeanDateFormatter.parse(stringDate)).atStartOfDay();
        }
    }
}

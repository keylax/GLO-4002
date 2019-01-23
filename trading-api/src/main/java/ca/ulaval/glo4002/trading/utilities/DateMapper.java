package ca.ulaval.glo4002.trading.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.util.TimeZone;

public class DateMapper {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String ACTUAL_ZONE = "UTC";

    public static LocalDateTime toDateTime(Date _date) {
        Instant dateInstant = _date.toInstant();
        ZoneId actualTimeZone = ZoneId.of(ACTUAL_ZONE);
        return LocalDateTime.ofInstant(dateInstant, actualTimeZone);
    }

    public static LocalDateTime toDateTime(String _date) {
        Date date = toDate(_date);
        Instant dateInstant = date.toInstant();
        ZoneId actualTimeZone = ZoneId.of(ACTUAL_ZONE);
        return LocalDateTime.ofInstant(dateInstant, actualTimeZone);
    }

    public static Instant toInstant(LocalDateTime _dateTime) {
        ZoneId zoneId = ZoneId.of(ACTUAL_ZONE);
        return _dateTime.atZone(zoneId).toInstant();
    }

    public static Date toDate(String _date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            formatter.setTimeZone(TimeZone.getTimeZone(ZoneId.of(ACTUAL_ZONE)));
            return formatter.parse(_date);
        } catch (ParseException e) {
            throw new RuntimeException("date parsing failed");
        }
    }
}

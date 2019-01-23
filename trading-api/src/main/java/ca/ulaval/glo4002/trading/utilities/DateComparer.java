package ca.ulaval.glo4002.trading.utilities;

import java.time.Instant;
import java.time.LocalDateTime;

public class DateComparer {
    public static boolean areEquivalent(LocalDateTime _dateTime, Instant _instant) {
        Instant dateTimeInstant = DateMapper.toInstant(_dateTime);
        String serializedDateTimeInstant = dateTimeInstant.toString();
        String serializedInstant = _instant.toString();
        return serializedDateTimeInstant.equals(serializedInstant);
    }
}

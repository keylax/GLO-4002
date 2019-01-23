package ca.ulaval.glo4002.trading.utilities;

import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

public class DateComparerTest {
    private static final String A_TIME_ZONE = "UTC";
    private static final String DATE_FORMAT_SEPARATOR = "-";

    @Test
    public void givenALocalDateAndAnInstantDateHavingTheSameDate_whenTheDatesAreCompared_thenReturnTrue() {
        int year = 2018, month = 12, day = 31, other = 0;
        ZoneId aZoneId = ZoneId.of(A_TIME_ZONE);
        LocalDateTime aLocalDateTime = LocalDateTime.of(year, month, day, other, other, other, other);
        LocalDate aLocalDate = LocalDate.parse(year + DATE_FORMAT_SEPARATOR + month + DATE_FORMAT_SEPARATOR + day);
        Instant anInstantDate = aLocalDate.atStartOfDay(aZoneId).toInstant();

        boolean datesAreEquivalent = DateComparer.areEquivalent(aLocalDateTime, anInstantDate);

        Assert.assertTrue(datesAreEquivalent);
    }
}

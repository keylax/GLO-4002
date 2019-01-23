package ca.ulaval.glo4002.trading.utilities;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DateMapperTest {
    private static final String ACTUAL_ZONE = "UTC";

    private ZoneId expectedZoneId;

    @Before
    public void setup() {
        expectedZoneId = ZoneId.of(ACTUAL_ZONE);
    }

    @Test
    public void whenMappingDateToDateTime_thenReturnExpectedZonedDateTime() {
        Date aDate = new Date();

        LocalDateTime actualDateTime = DateMapper.toDateTime(aDate);

        Assert.assertEquals(aDate.toInstant(), actualDateTime.atZone(expectedZoneId).toInstant());
    }

    @Test
    public void whenMappingDateTimeToInstant_thenReturnExpectedInstant() {
        LocalDateTime dateTime = LocalDateTime.now();

        Instant actualInstant = DateMapper.toInstant(dateTime);

        Assert.assertEquals(dateTime.atZone(expectedZoneId).toInstant(), actualInstant);
    }

    @Test
    public void whenMappingStringToDate_thenReturnExpectedDate() {
        String aDate = "2018-04-15T15:50:30.333Z";

        Date mappedDate = DateMapper.toDate(aDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
        String actualDate = dateFormat.format(mappedDate);
        String expectedDate = "Sun Apr 15 15:50:30 +0000 2018";
        Assert.assertEquals(expectedDate, actualDate);
    }
}

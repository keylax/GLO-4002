package ca.ulaval.glo4002.trading.domain.stocks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class MarketTest {
    private static final String AN_OPEN_MARKET_DATE = "2018-01-01T12:00";
    private static final String A_WEEKEND_DATE = "2018-11-11T08:00";
    private static final String A_CLOSED_HOUR_MARKET_DATE = "2018-11-13T23:00";

    private static final String AN_OPEN_TIME = "07:00";
    private static final String A_CLOSE_TIME = "10:00";
    private static final String A_TIMEZONE = "UTC-04:00";
    private static final String A_MARKET_SYMBOL = "NASDAQ";

    private Market market;

    @Before
    public void setup() {
        ZoneId aZoneId = ZoneId.of(A_TIMEZONE);

        LocalTime anOpenTime = LocalTime.parse(AN_OPEN_TIME);
        LocalTime aCloseTime = LocalTime.parse(A_CLOSE_TIME);
        OpenHours openTimePair = new OpenHours(anOpenTime, aCloseTime);
        ArrayList<OpenHours> openTimeList = new ArrayList<>();
        openTimeList.add(openTimePair);

        market = new Market(A_MARKET_SYMBOL, openTimeList, aZoneId);
    }

    @Test
    public void givenOpenedMarket_whenValidatingIfOpen_thenReturnTrue() {
        LocalDateTime date = LocalDateTime.parse(AN_OPEN_MARKET_DATE);

        boolean returnedValue = market.isOpen(date);

        Assert.assertTrue(returnedValue);
    }

    @Test
    public void givenAWeekendDate_whenValidatingIfOpen_thenReturnFalse() {
        LocalDateTime date = LocalDateTime.parse(A_WEEKEND_DATE);

        boolean returnedValue = market.isOpen(date);

        Assert.assertFalse(returnedValue);
    }
    @Test
    public void giveClosedMarketHoursDate_whenValidatingIfOpen_thenReturnFalse() {
        LocalDateTime date = LocalDateTime.parse(A_CLOSED_HOUR_MARKET_DATE);

        boolean returnedValue = market.isOpen(date);

        Assert.assertFalse(returnedValue);
    }

}

package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.assemblers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StockPriceTest {
    private static final String A_DATE_SERIALIZED = "2018-07-08 07:00";
    private static final BigDecimal A_STOCK_PRICE = BigDecimal.ONE;
    private StockPrice stockPriceWithDate;
    private DateTimeFormatter format;

    @Before
    public void setup() {
        format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime aDate = LocalDateTime.parse(A_DATE_SERIALIZED, format);
        stockPriceWithDate = new StockPrice(aDate, A_STOCK_PRICE);
    }

    @Test
    public void givenPriceOnEqualDate_whenCompareTwoPrices_thenReturnZero() {
        String aGreaterDateSerialized = "2018-07-08 07:00";
        LocalDateTime anEqualDate = LocalDateTime.parse(aGreaterDateSerialized, format);
        StockPrice stockPriceOnEqualDate = new StockPrice(anEqualDate, A_STOCK_PRICE);

        int result = StockPrice.dateComparator.compare(stockPriceOnEqualDate, stockPriceWithDate);

        Assert.assertEquals(0, result);
    }

    @Test
    public void givenPriceOnGreaterDate_whenCompareTwoPrices_thenReturnPositiveResult() {
        String aGreaterDateSerialized = "2018-07-08 08:00";
        LocalDateTime aGreaterDate = LocalDateTime.parse(aGreaterDateSerialized, format);
        StockPrice stockPriceOnGreaterDate = new StockPrice(aGreaterDate, A_STOCK_PRICE);

        int result = StockPrice.dateComparator.compare(stockPriceOnGreaterDate, stockPriceWithDate);

        Assert.assertTrue(result >= 1);
    }

    @Test
    public void givenPriceOnLesserDate_whenCompareTwoPrices_thenReturnNegativeResult() {
        String aLesserDateSerialized = "2018-07-08 06:30";
        LocalDateTime aLesserDate = LocalDateTime.parse(aLesserDateSerialized, format);
        StockPrice stockPriceOnLesserDate = new StockPrice(aLesserDate, A_STOCK_PRICE);

        int result = StockPrice.dateComparator.compare(stockPriceOnLesserDate, stockPriceWithDate);

        Assert.assertTrue(result <= -1);
    }

    @Test
    public void givenALesserDate_whenValidatingIfPriceInEffect_thenReturnFalse() {
        String aLesserDateSerialized = "2018-07-08 06:30";
        LocalDateTime aLesserDate = LocalDateTime.parse(aLesserDateSerialized, format);

        boolean isPriceInEffect = stockPriceWithDate.isPriceInEffect(aLesserDate);

        Assert.assertFalse(isPriceInEffect);
    }

    @Test
    public void givenAGreaterDate_whenValidatingIfPriceInEffect_thenReturnTrue() {
        String aGreaterDateSerialized = "2018-07-08 07:30";
        LocalDateTime aGreaterDate = LocalDateTime.parse(aGreaterDateSerialized, format);

        boolean isPriceInEffect = stockPriceWithDate.isPriceInEffect(aGreaterDate);

        Assert.assertTrue(isPriceInEffect);
    }

    @Test
    public void givenADateWithSameDayOfYear_whenValidatingIfPriceInEffectOnSameDay_thenReturnTrue() {
        String aDateWithSameDaySerialized = "2018-07-08 08:00";
        LocalDateTime aDateWithSameDay = LocalDateTime.parse(aDateWithSameDaySerialized, format);

        boolean isPriceInEffect = stockPriceWithDate.isPriceInEffectOnSameDay(aDateWithSameDay);

        Assert.assertTrue(isPriceInEffect);
    }

    @Test
    public void givenADateWithADifferentDayOfYear_whenValidatingIfPriceInEffectOnSameDay_thenReturnFalse() {
        String aDateWithSameDaySerialized = "2018-07-07 07:00";
        LocalDateTime aDateWithSameDay = LocalDateTime.parse(aDateWithSameDaySerialized, format);

        boolean isPriceInEffect = stockPriceWithDate.isPriceInEffectOnSameDay(aDateWithSameDay);

        Assert.assertFalse(isPriceInEffect);
    }
}

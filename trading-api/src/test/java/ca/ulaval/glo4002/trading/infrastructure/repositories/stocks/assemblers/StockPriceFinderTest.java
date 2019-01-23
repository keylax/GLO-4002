package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.assemblers;

import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.exceptions.StockDateNotFoundException;
import ca.ulaval.glo4002.trading.utilities.DateMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockPriceFinderTest {
    private static final BigDecimal PRICE_ON_FIRST_DATE = BigDecimal.ONE;
    private static final BigDecimal PRICE_ON_SECOND_DATE = BigDecimal.TEN;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String FIRST_DATE_SERIALIZED = "2018-07-07 04:00:00";
    private static final String SECOND_DATE_SERIALIZED = "2018-07-08 05:00:00";

    private StockPriceFinder stockPriceFinder;
    private List<StockPrice> stockPriceList;

    @Before
    public void setup() throws ParseException {
        stockPriceFinder = new StockPriceFinder();
        Date firstDate = new SimpleDateFormat(DATE_FORMAT).parse(FIRST_DATE_SERIALIZED);
        Date secondDate = new SimpleDateFormat(DATE_FORMAT).parse(SECOND_DATE_SERIALIZED);
        LocalDateTime firstDateTime = DateMapper.toDateTime(firstDate);
        LocalDateTime secondDateTime = DateMapper.toDateTime(secondDate);
        StockPrice aStockPrice = new StockPrice(firstDateTime, PRICE_ON_FIRST_DATE);
        StockPrice anotherStockPrice = new StockPrice(secondDateTime, PRICE_ON_SECOND_DATE);
        stockPriceList = new ArrayList<>();
        stockPriceList.add(aStockPrice);
        stockPriceList.add(anotherStockPrice);
    }

    @Test(expected = StockDateNotFoundException.class)
    public void givenEmptyPrices_whenFindingStockPrice_thenThrowException() throws ParseException {
        String aSerializedDate = "2018-07-08 05:01:19";
        Date sDate = new SimpleDateFormat(DATE_FORMAT).parse(aSerializedDate);
        LocalDateTime aDateTime = DateMapper.toDateTime(sDate);
        List<StockPrice> emptyStockPriceList = new ArrayList<>();

        stockPriceFinder.findPriceAtDate(emptyStockPriceList, aDateTime);
    }

    @Test
    public void givenADateBeforePriceEnterInEffect_whenFindingStockPrice_thenReturnPreviousPrice() throws ParseException {
        String dateBeforeSecondPriceIsInEffectSerialized = "2018-07-08 04:59:19";
        Date dateBeforeSecondPriceIsInEffect = new SimpleDateFormat(DATE_FORMAT)
                .parse(dateBeforeSecondPriceIsInEffectSerialized);
        LocalDateTime dateTimeBeforeSecondPriceIsInEffect = DateMapper.toDateTime(dateBeforeSecondPriceIsInEffect);

        BigDecimal actualStockPrice = stockPriceFinder.findPriceAtDate(
                stockPriceList,
                dateTimeBeforeSecondPriceIsInEffect);

        Assert.assertEquals(PRICE_ON_FIRST_DATE, actualStockPrice);
    }

    @Test
    public void givenADateAfterPriceEnterInEffect_whenFindingStockPrice_thenReturnSaidPrice() throws ParseException {
        String dateAfterSecondPriceIsInEffectSerialized = "2018-07-08 05:01:19";
        Date dateAfterSecondPriceIsInEffect = new SimpleDateFormat(DATE_FORMAT)
                .parse(dateAfterSecondPriceIsInEffectSerialized);
        LocalDateTime dateTimeAfterSecondPriceIsInEffect = DateMapper.toDateTime(dateAfterSecondPriceIsInEffect);

        BigDecimal actualStockPrice = stockPriceFinder.findPriceAtDate(
                stockPriceList,
                dateTimeAfterSecondPriceIsInEffect);

        Assert.assertEquals(PRICE_ON_SECOND_DATE, actualStockPrice);
    }

    @Test(expected = StockDateNotFoundException.class)
    public void givenADateAfterTheLastKnownPriceOfTheStock_whenFindingStockPrice_thenThrowException() throws ParseException {
        String aDatePostLastKnownPriceSerialized = "2018-07-09 05:00:00";
        Date aDatePostLastKnownPrice = new SimpleDateFormat(DATE_FORMAT).parse(aDatePostLastKnownPriceSerialized);
        LocalDateTime aDateTimePostLastKnownPrice = DateMapper.toDateTime(aDatePostLastKnownPrice);

        stockPriceFinder.findPriceAtDate(stockPriceList, aDateTimePostLastKnownPrice);
    }

    @Test(expected = StockDateNotFoundException.class)
    public void givenADateBeforeTheFirstKnownPriceOfTheStock_whenFindingStockPrice_thenThrowException() throws ParseException {
        String aDatePreLastKnownPriceSerialized = "2015-01-01 02:00:00";
        Date invalidDate = new SimpleDateFormat(DATE_FORMAT).parse(aDatePreLastKnownPriceSerialized);
        LocalDateTime invalidDateTime = DateMapper.toDateTime(invalidDate);

        stockPriceFinder.findPriceAtDate(stockPriceList, invalidDateTime);
    }
}

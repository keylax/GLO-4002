package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.assemblers;

import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.MarketDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.PriceDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.StockDTO;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.utilities.DateComparer;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ulaval.glo4002.trading.utilities.DateMapper;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StockAssemblerTest {
    private static final BigDecimal A_PRICE = BigDecimal.TEN;
    private static final int AN_ID = 1;
    private static final int A_MARKET_ID = 2;
    private static final String A_STOCK_SYMBOL = "test-stock-symbol";
    private static final String A_TYPE = "test-type";
    private static final String A_MARKET = "test-market";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String A_STOCK_PRICE_DATE_SERIALIZED = "2018-07-08 08:00:00";
    private static final String A_TRANSACTION_DATE_SERIALIZED = "2018-07-08 08:30:00";
    private static final String A_TIMEZONE = "UTC-04:00";
    private static final String SOME_OPEN_HOURS = "9:30-12:00";

    private StockAssembler stockAssembler;
    private PriceDTO priceDTO;
    private StockDTO stockDTO;
    private MarketDTO marketDTO;
    private Date aStockPriceDate;
    private LocalDateTime aTransactionDateTime;

    @Before
    public void setup() throws ParseException {
        stockAssembler = new StockAssembler();

        Date aTransactionDate = new SimpleDateFormat(DATE_FORMAT).parse(A_TRANSACTION_DATE_SERIALIZED);
        aTransactionDateTime = DateMapper.toDateTime(aTransactionDate);
        aStockPriceDate = new SimpleDateFormat(DATE_FORMAT).parse(A_STOCK_PRICE_DATE_SERIALIZED);
        setupDTOs();
    }

    @Test
    public void whenAssemblingPrice_thenReturnExpectedPrice() {
        StockPrice returnedStockPrice = stockAssembler.toStockPrice(priceDTO);

        Assert.assertEquals(returnedStockPrice.getPrice(), A_PRICE);
        Assert.assertTrue(DateComparer.areEquivalent(
                returnedStockPrice.getDateInEffect(),
                aStockPriceDate.toInstant()));
    }

    @Test
    public void whenAssemblingStock_thenReturnExpectedStock() {
        Stock returnedStock = stockAssembler.toDomainObject(stockDTO, A_MARKET, aTransactionDateTime);

        Assert.assertEquals(returnedStock.getSymbol(), A_STOCK_SYMBOL);
    }

    @Test
    public void whenAssemblingMarket_thenReturnExpectedMarket() {
        Market returnedMarket = stockAssembler.toDomainObject(marketDTO);

        Assert.assertEquals(returnedMarket.getSymbol(), A_MARKET);
    }

    private void setupDTOs() {
        priceDTO = new PriceDTO();
        priceDTO.date = aStockPriceDate;
        priceDTO.price = A_PRICE;

        List<PriceDTO> somePrices = new ArrayList<>();
        somePrices.add(priceDTO);

        stockDTO = new StockDTO();
        stockDTO.id = AN_ID;
        stockDTO.symbol = A_STOCK_SYMBOL;
        stockDTO.type = A_TYPE;
        stockDTO.market = A_MARKET;
        stockDTO.prices = somePrices;

        marketDTO = new MarketDTO();
        marketDTO.id = A_MARKET_ID;
        marketDTO.symbol = A_MARKET;
        marketDTO.openHours = new String[] {SOME_OPEN_HOURS};
        marketDTO.timezone = A_TIMEZONE;
    }
}

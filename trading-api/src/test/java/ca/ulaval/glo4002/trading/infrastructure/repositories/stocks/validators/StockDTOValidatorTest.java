package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.validators;

import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.MarketDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.PriceDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.StockDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StockDTOValidatorTest {
    private static final double A_PRICE = 103.98089034795903;
    private static final int A_STOCK_ID = 1;
    private static final int A_MARKET_ID = 1;
    private static final String A_STOCK_MARKET = "NASDAQ";
    private static final String A_STOCK_SYMBOL = "GOOG";
    private static final String A_STOCK_TYPE = "common";
    private static final String SOME_OPEN_HOURS = "9:30-12:00";

    private StockDTO validStock;
    private PriceDTO validPrice;
    private MarketDTO validMarket;

    private StockDTOValidator validator;

    @Before
    public void setup() {
        validPrice = new PriceDTO();
        validPrice.date = new Date();
        validPrice.price = new BigDecimal(A_PRICE);

        validStock = new StockDTO();
        ArrayList<PriceDTO> pricesList = new ArrayList<>();
        pricesList.add(validPrice);
        validStock.prices = pricesList;
        validStock.id = A_STOCK_ID;
        validStock.market = A_STOCK_MARKET;
        validStock.symbol = A_STOCK_SYMBOL;
        validStock.type = A_STOCK_TYPE;

        validMarket = new MarketDTO();
        validMarket.timezone = "UTC-04:00";
        validMarket.symbol = A_STOCK_MARKET;
        validMarket.openHours = new String[] {SOME_OPEN_HOURS};
        validMarket.id = A_MARKET_ID;

        validator = new StockDTOValidator();
    }

    @Test
    public void givenValidPrice_whenValidating_returnTrue() {
        boolean isPriceValid = validator.isPriceDTOValid(validPrice);

        Assert.assertTrue(isPriceValid);
    }

    @Test
    public void givenPriceWithNullPrice_whenValidating_returnFalse() {
        validPrice.price = null;

        boolean isPriceValid = validator.isPriceDTOValid(validPrice);

        Assert.assertFalse(isPriceValid);
    }

    @Test
    public void givenPriceWithNullDate_whenValidating_returnFalse() {
        validPrice.date = null;

        boolean isPriceValid = validator.isPriceDTOValid(validPrice);

        Assert.assertFalse(isPriceValid);
    }

    @Test
    public void givenValidStock_whenValidating_returnTrue() {
        boolean isStockValid = validator.isStockDTOValid(validStock);

        Assert.assertTrue(isStockValid);
    }

    @Test
    public void givenStockWithNullSymbol_whenValidating_returnFalse() {
        validStock.symbol = null;

        boolean isStockValid = validator.isStockDTOValid(validStock);

        Assert.assertFalse(isStockValid);
    }

    @Test
    public void givenStockWithNullMarket_whenValidating_returnFalse() {
        validStock.market = null;

        boolean isStockValid = validator.isStockDTOValid(validStock);

        Assert.assertFalse(isStockValid);
    }

    @Test
    public void givenStockWithNullPrice_whenValidating_returnFalse() {
        validStock.prices = null;

        boolean isStockValid = validator.isStockDTOValid(validStock);

        Assert.assertFalse(isStockValid);
    }

    @Test
    public void givenStockWithInvalidPriceInNonNullList_whenValidating_returnFalse() {
        PriceDTO invalidPrice = new PriceDTO();
        invalidPrice.date = null;
        invalidPrice.price = null;
        validStock.prices.add(invalidPrice);

        boolean isStockValid = validator.isStockDTOValid(validStock);

        Assert.assertFalse(isStockValid);
    }

    @Test
    public void givenValidMarket_whenValidating_returnTrue() {
        boolean isMarketValid = validator.isMarketDTOValid(validMarket);

        Assert.assertTrue(isMarketValid);
    }

    @Test
    public void givenMarketWithNullOpenHours_whenValidating_returnFalse() {
        validMarket.openHours = null;

        boolean isMarketValid = validator.isMarketDTOValid(validMarket);

        Assert.assertFalse(isMarketValid);
    }

    @Test
    public void givenMarketWithNullSymbol_whenValidating_returnFalse() {
        validMarket.symbol = null;

        boolean isMarketValid = validator.isMarketDTOValid(validMarket);

        Assert.assertFalse(isMarketValid);
    }

    @Test
    public void givenMarketWithNullTimezone_whenValidating_returnFalse() {
        validMarket.timezone = null;

        boolean isMarketValid = validator.isMarketDTOValid(validMarket);

        Assert.assertFalse(isMarketValid);
    }
}

package ca.ulaval.glo4002.trading.interfaces.rest.transactions.validators;

import ca.ulaval.glo4002.trading.application.services.transactions.DTOs.requests.StockRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StockRequestValidatorTest {
    private static final String A_STOCK_MARKET = "NASDAQ";
    private static final String A_STOCK_SYMBOL = "GOOG";

    private StockRequestValidator validator;
    private StockRequest stockRequest;

    @Before
    public void setup() {
        stockRequest = new StockRequest();
        stockRequest.market = A_STOCK_MARKET;
        stockRequest.symbol = A_STOCK_SYMBOL;

        validator = new StockRequestValidator();
    }

    @Test
    public void givenValidStockRequest_whenValidating_returnTrue() {
        boolean isStockValid = validator.isStockRequestValid(stockRequest);

        Assert.assertTrue(isStockValid);
    }

    @Test
    public void givenStockRequestWithNullSymbol_whenValidating_returnFalse() {
        stockRequest.symbol = null;

        boolean isStockValid = validator.isStockRequestValid(stockRequest);

        Assert.assertFalse(isStockValid);
    }

    @Test
    public void givenStockRequestWithNullMarket_whenValidating_returnFalse() {
        stockRequest.market = null;

        boolean isStockValid = validator.isStockRequestValid(stockRequest);

        Assert.assertFalse(isStockValid);
    }

    @Test
    public void givenStockRequestWithNull_whenValidating_returnFalse() {
        stockRequest = null;

        boolean isStockValid = validator.isStockRequestValid(stockRequest);

        Assert.assertFalse(isStockValid);
    }
}

package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.exceptions.StockNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class StockNotFoundExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String A_STOCK_SYMBOL = "MSFT";
    private static final String A_MARKET_SYMBOL = "NASDAQ";
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"stock '%s:%s' not found\",\"error\":\"STOCK_NOT_FOUND\"}";

    @Test
    public void whenMappingAStockNotFoundException_thenReturnExpectedResponse() {
        String expectedString = String.format(EXPECTED_ENTITY, A_MARKET_SYMBOL, A_STOCK_SYMBOL);
        StockNotFoundExceptionMapper mapper = new StockNotFoundExceptionMapper();
        StockNotFoundException exception = new StockNotFoundException(A_MARKET_SYMBOL, A_STOCK_SYMBOL);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedString, mapperResponse.getEntity());
    }
}

package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.NotEnoughStockException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class NotEnoughStockExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String A_STOCK_SYMBOL = "MSFT";
    private static final String A_MARKET_SYMBOL = "NASDAQ";
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"not enough stock '%s:%s'\",\"error\":\"NOT_ENOUGH_STOCK\"}";

    @Test
    public void whenMappingANotEnoughStockException_thenReturnExpectedResponse() {
        String expectedString = String.format(EXPECTED_ENTITY, A_MARKET_SYMBOL, A_STOCK_SYMBOL);
        NotEnoughStockExceptionMapper mapper = new NotEnoughStockExceptionMapper();
        NotEnoughStockException exception = new NotEnoughStockException(A_MARKET_SYMBOL, A_STOCK_SYMBOL);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedString, mapperResponse.getEntity());
    }
}

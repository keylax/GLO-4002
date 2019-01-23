package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.exceptions.StockDateNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class StockDateNotFoundExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"the transaction date is invalid\",\"error\":\"INVALID_DATE\"}";

    @Test
    public void whenMappingAStockDateNotFoundException_thenReturnExpectedResponse() {
        StockDateNotFoundExceptionMapper mapper = new StockDateNotFoundExceptionMapper();
        StockDateNotFoundException exception = new StockDateNotFoundException();

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(EXPECTED_ENTITY, mapperResponse.getEntity());
    }
}

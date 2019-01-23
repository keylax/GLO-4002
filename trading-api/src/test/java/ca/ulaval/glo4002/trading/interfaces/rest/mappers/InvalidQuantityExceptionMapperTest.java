package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.transactions.exceptions.InvalidQuantityException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class InvalidQuantityExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"quantity is invalid\",\"error\":\"INVALID_QUANTITY\"}";

    @Test
    public void whenMappingAInvalidQuantityException_thenReturnExpectedResponse() {
        InvalidQuantityExceptionMapper mapper = new InvalidQuantityExceptionMapper();
        InvalidQuantityException exception = new InvalidQuantityException();

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(EXPECTED_ENTITY, mapperResponse.getEntity());
    }
}

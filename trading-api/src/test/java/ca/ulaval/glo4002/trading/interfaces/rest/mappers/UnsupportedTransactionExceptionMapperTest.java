package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.interfaces.rest.transactions.exceptions.UnsupportedTransactionException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class UnsupportedTransactionExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String INVALID_TRANSACTION_TYPE = "BORROW";
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"transaction %s is not supported\",\"error\":\"UNSUPPORTED_TRANSACTION_TYPE\"}";

    @Test
    public void whenMappingAUnsupportedTransactionException_thenReturnExpectedResponse() {
        String expectedResponse = String.format(EXPECTED_ENTITY, INVALID_TRANSACTION_TYPE);
        UnsupportedTransactionExceptionMapper mapper = new UnsupportedTransactionExceptionMapper();
        UnsupportedTransactionException exception = new UnsupportedTransactionException(INVALID_TRANSACTION_TYPE);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedResponse, mapperResponse.getEntity());
    }
}

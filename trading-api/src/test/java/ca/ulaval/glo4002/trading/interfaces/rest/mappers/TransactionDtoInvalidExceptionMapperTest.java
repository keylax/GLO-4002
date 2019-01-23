package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.interfaces.rest.transactions.exceptions.TransactionDtoInvalidException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class TransactionDtoInvalidExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"all parameters must be declared\",\"error\":\"UNDEFINED_PARAMETER\"}";

    @Test
    public void whenMappingATransactionDtoInvalidException_thenReturnExpectedResponse() {
        TransactionDtoInvalidExceptionMapper mapper = new TransactionDtoInvalidExceptionMapper();
        TransactionDtoInvalidException exception = new TransactionDtoInvalidException();

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(EXPECTED_ENTITY, mapperResponse.getEntity());
    }
}

package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.exceptions.TransactionNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class TransactionNotFoundExceptionMapperTest {
    private static final int NOT_FOUND_CODE = 404;
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"transaction with number %s not found\",\"error\":\"TRANSACTION_NOT_FOUND\"}";

    @Test
    public void whenMappingATransactionNotFoundException_thenReturnExpectedResponse() {
        TransactionNumber aTransactionNumber = new TransactionNumber();
        String expectedString = String.format(EXPECTED_ENTITY, aTransactionNumber.toString());
        TransactionNotFoundExceptionMapper mapper = new TransactionNotFoundExceptionMapper();
        TransactionNotFoundException exception = new TransactionNotFoundException(aTransactionNumber);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(NOT_FOUND_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedString, mapperResponse.getEntity());
    }
}

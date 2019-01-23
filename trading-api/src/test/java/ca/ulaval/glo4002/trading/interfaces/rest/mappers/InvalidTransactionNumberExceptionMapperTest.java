package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.InvalidTransactionNumberException;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class InvalidTransactionNumberExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String EXPECTED_ENTITY =
            "{\"transactionNumber\":\"%s\",\"description\":\"the transaction number is invalid\","
            + "\"error\":\"INVALID_TRANSACTION_NUMBER\"}";

    @Test
    public void whenMappingAInvalidTransactionNumberException_thenReturnExpectedResponse() {
        TransactionNumber aTransactionNumber = new TransactionNumber();
        String expectedString = String.format(EXPECTED_ENTITY, aTransactionNumber.toString());
        InvalidTransactionNumberExceptionMapper mapper = new InvalidTransactionNumberExceptionMapper();
        InvalidTransactionNumberException exception = new InvalidTransactionNumberException(aTransactionNumber);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedString, mapperResponse.getEntity());
    }
}

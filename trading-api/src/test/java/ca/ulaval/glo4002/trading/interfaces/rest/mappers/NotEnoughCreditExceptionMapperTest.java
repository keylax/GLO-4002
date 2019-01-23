package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.NotEnoughCreditException;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class NotEnoughCreditExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String EXPECTED_ENTITY =
            "{\"transactionNumber\":\"%s\",\"description\":\"not enough credits in wallet\","
            + "\"error\":\"NOT_ENOUGH_CREDITS\"}";

    @Test
    public void whenMappingANotEnoughCreditException_thenReturnExpectedResponse() {
        TransactionNumber aTransactionNumber = new TransactionNumber();
        String expectedString = String.format(EXPECTED_ENTITY, aTransactionNumber.toString());
        NotEnoughCreditExceptionMapper mapper = new NotEnoughCreditExceptionMapper();
        NotEnoughCreditException exception = new NotEnoughCreditException(aTransactionNumber);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedString, mapperResponse.getEntity());
    }
}

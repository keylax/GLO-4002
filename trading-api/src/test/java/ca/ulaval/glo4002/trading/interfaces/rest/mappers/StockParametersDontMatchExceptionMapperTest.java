package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.StockParametersDontMatchException;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class StockParametersDontMatchExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String EXPECTED_ENTITY =
            "{\"transactionNumber\":\"%s\",\"description\":\"stock parameters don't match existing ones\","
            + "\"error\":\"STOCK_PARAMETERS_DONT_MATCH\"}";

    @Test
    public void whenMappingAStockParametersDontMatchException_thenReturnExpectedResponse() {
        TransactionNumber aTransactionNumber = new TransactionNumber();
        String expectedString = String.format(EXPECTED_ENTITY, aTransactionNumber.toString());
        StockParametersDontMatchExceptionMapper mapper = new StockParametersDontMatchExceptionMapper();
        StockParametersDontMatchException exception = new StockParametersDontMatchException(aTransactionNumber);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedString, mapperResponse.getEntity());
    }
}

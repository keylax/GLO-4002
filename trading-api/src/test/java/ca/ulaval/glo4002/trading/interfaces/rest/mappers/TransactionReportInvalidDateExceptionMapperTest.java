package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.application.services.transactions.exceptions.TransactionReportInvalidDateException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class TransactionReportInvalidDateExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String A_SERIALIZED_DATE = "2015-01-01";
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"date '%s' is invalid\",\"error\":\"INVALID_DATE\"}";

    @Test
    public void whenMappingATransactionReportInvalidDateException_thenReturnExpectedResponse() {
        String expectedEntity = String.format(EXPECTED_ENTITY, A_SERIALIZED_DATE);
        TransactionReportInvalidDateExceptionMapper mapper = new TransactionReportInvalidDateExceptionMapper();
        TransactionReportInvalidDateException exception = new TransactionReportInvalidDateException(A_SERIALIZED_DATE);

        Response actualResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, actualResponse.getStatus());
        Assert.assertEquals(expectedEntity, actualResponse.getEntity());
    }
}

package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportMissingDateException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class TransactionReportMissingDateExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"date is missing\",\"error\":\"MISSING_DATE\"}";

    @Test
    public void whenMappingATransactionReportMissingDateException_thenReturnExpectedResponse() {
        TransactionReportMissingDateExceptionMapper mapper = new TransactionReportMissingDateExceptionMapper();
        TransactionReportMissingDateException exception = new TransactionReportMissingDateException();

        Response actualResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, actualResponse.getStatus());
        Assert.assertEquals(EXPECTED_ENTITY, actualResponse.getEntity());
    }
}

package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportMissingTypeException;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class TransactionReportMissingTypeExceptionMapperTest {
    private static final String EXPECTED_ENTITY =
            "{\"description\":\"report type is missing\",\"error\":\"MISSING_REPORT_TYPE\"}";
    private static final int BAD_REQUEST_CODE = 400;

    @Test
    public void whenMappingATransactionReportMissingTypeException_thenReturnExpectedResponse() {
        TransactionReportMissingTypeExceptionMapper mapper = new TransactionReportMissingTypeExceptionMapper();
        TransactionReportMissingTypeException anException = new TransactionReportMissingTypeException();

        Response actualResponse = mapper.toResponse(anException);

        Assert.assertEquals(BAD_REQUEST_CODE, actualResponse.getStatus());
        Assert.assertEquals(EXPECTED_ENTITY, actualResponse.getEntity());
    }

}

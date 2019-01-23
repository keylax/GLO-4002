package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportTypeUnsupportedException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class TransactionReportTypeUnsupportedExceptionMapperTest {
    private static final String UNSUPPORTED_TYPE = "OTHER";
    private static final String EXPECTED_ENTITY = "{\"description\":\"report '"
            + UNSUPPORTED_TYPE + "' is not supported\",\"error\":\"REPORT_TYPE_UNSUPPORTED\"}";
    private static final int BAD_REQUEST_CODE = 400;

    @Test
    public void whenMappingATransactionReportTypeUnsupportedException_thenReturnExpectedResponse() {
        TransactionReportTypeUnsupportedExceptionMapper mapper = new TransactionReportTypeUnsupportedExceptionMapper();
        TransactionReportTypeUnsupportedException anException =
                new TransactionReportTypeUnsupportedException(UNSUPPORTED_TYPE);

        Response actualResponse = mapper.toResponse(anException);

        Assert.assertEquals(BAD_REQUEST_CODE, actualResponse.getStatus());
        Assert.assertEquals(EXPECTED_ENTITY, actualResponse.getEntity());
    }
}

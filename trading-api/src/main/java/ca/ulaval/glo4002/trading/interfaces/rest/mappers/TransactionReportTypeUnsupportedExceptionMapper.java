package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;
import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportTypeUnsupportedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TransactionReportTypeUnsupportedExceptionMapper
        implements ExceptionMapper<TransactionReportTypeUnsupportedException> {
    private static final String INVALID_REPORT_TYPE_CODE = "REPORT_TYPE_UNSUPPORTED";
    private static final String INVALID_TYPE_DESCRIPTION = "report '%s' is not supported";

    private String createJsonResponse(TransactionReportTypeUnsupportedException _exception) {
        String errorDescription = String.format(INVALID_TYPE_DESCRIPTION, _exception.getReportType());
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(INVALID_REPORT_TYPE_CODE, errorDescription);
    }

    public Response toResponse(TransactionReportTypeUnsupportedException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

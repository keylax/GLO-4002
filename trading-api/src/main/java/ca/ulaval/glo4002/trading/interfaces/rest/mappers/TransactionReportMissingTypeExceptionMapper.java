package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;
import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportMissingTypeException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TransactionReportMissingTypeExceptionMapper
        implements ExceptionMapper<TransactionReportMissingTypeException> {
    private static final String MISSING_REPORT_TYPE_CODE = "MISSING_REPORT_TYPE";
    private static final String MISSING_REPORT_TYPE_DESCRIPTION = "report type is missing";

    private String createJsonResponse() {
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(MISSING_REPORT_TYPE_CODE, MISSING_REPORT_TYPE_DESCRIPTION);
    }

    public Response toResponse(TransactionReportMissingTypeException _exception) {
        String serializedJson = createJsonResponse();
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

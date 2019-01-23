package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;
import ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions.TransactionReportMissingDateException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TransactionReportMissingDateExceptionMapper
        implements ExceptionMapper<TransactionReportMissingDateException> {
    private static final String MISSING_DATE_CODE = "MISSING_DATE";
    private static final String MISSING_DATE_DESCRIPTION = "date is missing";

    private String createJsonResponse() {
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(MISSING_DATE_CODE, MISSING_DATE_DESCRIPTION);
    }

    public Response toResponse(TransactionReportMissingDateException _exception) {
        String serializedJson = createJsonResponse();
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

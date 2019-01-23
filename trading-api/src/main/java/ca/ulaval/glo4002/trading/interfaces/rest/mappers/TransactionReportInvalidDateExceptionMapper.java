package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.application.services.transactions.exceptions.TransactionReportInvalidDateException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TransactionReportInvalidDateExceptionMapper
        implements ExceptionMapper<TransactionReportInvalidDateException> {
    private static final String INVALID_DATE_CODE = "INVALID_DATE";
    private static final String INVALID_DATE_DESCRIPTION = "date '%s' is invalid";

    private String createJsonResponse(TransactionReportInvalidDateException _exception) {
        String errorDescription = String.format(INVALID_DATE_DESCRIPTION, _exception.getSerializedDate());
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(INVALID_DATE_CODE, errorDescription);
    }

    public Response toResponse(TransactionReportInvalidDateException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

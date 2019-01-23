package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;
import ca.ulaval.glo4002.trading.interfaces.rest.transactions.exceptions.UnsupportedTransactionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnsupportedTransactionExceptionMapper implements ExceptionMapper<UnsupportedTransactionException> {
    private static final String UNSUPPORTED_TRANSACTION_ERROR = "UNSUPPORTED_TRANSACTION_TYPE";
    private static final String UNSUPPORTED_TRANSACTION_DESCRIPTION = "transaction %s is not supported";

    private String createJsonResponse(UnsupportedTransactionException _exception) {
        String errorDescription = String.format(UNSUPPORTED_TRANSACTION_DESCRIPTION, _exception.getTransactionType());
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(UNSUPPORTED_TRANSACTION_ERROR, errorDescription);
    }

    public Response toResponse(UnsupportedTransactionException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

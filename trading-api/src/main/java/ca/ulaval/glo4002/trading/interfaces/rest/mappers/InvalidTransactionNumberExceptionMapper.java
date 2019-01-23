package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.InvalidTransactionNumberException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidTransactionNumberExceptionMapper implements ExceptionMapper<InvalidTransactionNumberException> {
    private static final String INVALID_TRANSACTION_NUMBER_ERROR = "INVALID_TRANSACTION_NUMBER";
    private static final String INVALID_TRANSACTION_NUMBER_DESCRIPTION = "the transaction number is invalid";

    private String createJsonResponse(InvalidTransactionNumberException _exception) {
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildTransactionExceptionError(
                INVALID_TRANSACTION_NUMBER_ERROR,
                INVALID_TRANSACTION_NUMBER_DESCRIPTION,
                _exception.getTransactionNumber());
    }

    public Response toResponse(InvalidTransactionNumberException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

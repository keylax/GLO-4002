package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.exceptions.TransactionNotFoundException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TransactionNotFoundExceptionMapper implements ExceptionMapper<TransactionNotFoundException> {
    private static final String TRANSACTION_NOT_FOUND_ERROR = "TRANSACTION_NOT_FOUND";
    private static final String TRANSACTION_NOT_FOUND_DESCRIPTION = "transaction with number %s not found";

    private String createJsonResponse(TransactionNotFoundException _exception) {
        String transactionNotFoundDescription = String.format(
                TRANSACTION_NOT_FOUND_DESCRIPTION,
                _exception.getTransactionNumber());
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(TRANSACTION_NOT_FOUND_ERROR, transactionNotFoundDescription);
    }

    public Response toResponse(TransactionNotFoundException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.NOT_FOUND).entity(serializedJson).build();
    }
}

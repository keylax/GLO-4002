package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.NotEnoughCreditException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotEnoughCreditExceptionMapper implements ExceptionMapper<NotEnoughCreditException> {
    private static final String NOT_ENOUGH_CREDIT_WALLET_ERROR = "NOT_ENOUGH_CREDITS";
    private static final String NOT_ENOUGH_CREDIT_WALLET_DESCRIPTION = "not enough credits in wallet";

    private String createJsonResponse(NotEnoughCreditException _exception) {
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildTransactionExceptionError(
                NOT_ENOUGH_CREDIT_WALLET_ERROR,
                NOT_ENOUGH_CREDIT_WALLET_DESCRIPTION,
                _exception.getTransactionNumber());
    }

    public Response toResponse(NotEnoughCreditException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

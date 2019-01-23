package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.AccountDuplicateCurrencyException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccountDuplicateCurrencyExceptionMapper implements ExceptionMapper<AccountDuplicateCurrencyException> {
    private static final String SAME_CURRENCY_ERROR = "DUPLICATE_CURRENCY";
    private static final String SAME_CURRENCY_DESCRIPTION = "The currency %s cannot be declared twice";

    private String createJsonResponse(AccountDuplicateCurrencyException _exception) {
        JsonResponse jsonResponse = new JsonResponse();
        String errorDescription = String.format(SAME_CURRENCY_DESCRIPTION, _exception.getCurrency());

        return jsonResponse.buildExceptionError(SAME_CURRENCY_ERROR, errorDescription);
    }

    public Response toResponse(AccountDuplicateCurrencyException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

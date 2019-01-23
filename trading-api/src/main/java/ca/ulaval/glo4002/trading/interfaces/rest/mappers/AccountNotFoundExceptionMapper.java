package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.exceptions.AccountNotFoundException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccountNotFoundExceptionMapper implements ExceptionMapper<AccountNotFoundException> {
    private static final String ACCOUNT_NOT_FOUND_ERROR = "ACCOUNT_NOT_FOUND";
    private static final String ACCOUNT_NOT_FOUND_DESCRIPTION = "account with number %s not found";

    private String createJsonResponse(AccountNotFoundException _exception) {
        JsonResponse jsonResponse = new JsonResponse();
        String accountNotFoundDescription = String.format(ACCOUNT_NOT_FOUND_DESCRIPTION, _exception.getAccountNumber());
        return jsonResponse.buildExceptionError(ACCOUNT_NOT_FOUND_ERROR, accountNotFoundDescription);
    }

    public Response toResponse(AccountNotFoundException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.NOT_FOUND).entity(serializedJson).build();
    }
}

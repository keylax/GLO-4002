package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.infrastructure.repositories.accounts.exceptions.AccountAlreadyOpenException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccountAlreadyOpenExceptionMapper implements ExceptionMapper<AccountAlreadyOpenException> {
    private static final String ACCOUNT_ALREADY_OPEN_ERROR = "ACCOUNT_ALREADY_OPEN";
    private static final String ACCOUNT_ALREADY_OPEN_DESCRIPTION = "account already open for investor %d";

    private String createJsonResponse(AccountAlreadyOpenException _exception) {
        JsonResponse jsonResponse = new JsonResponse();
        String errorDescription = String.format(ACCOUNT_ALREADY_OPEN_DESCRIPTION, _exception.getInvestorId());

        return jsonResponse.buildExceptionError(ACCOUNT_ALREADY_OPEN_ERROR, errorDescription);
    }

    public Response toResponse(AccountAlreadyOpenException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

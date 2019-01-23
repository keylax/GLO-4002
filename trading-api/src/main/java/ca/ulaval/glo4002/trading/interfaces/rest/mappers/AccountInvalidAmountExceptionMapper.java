package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.AccountInvalidAmountException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccountInvalidAmountExceptionMapper implements ExceptionMapper<AccountInvalidAmountException> {
    private static final String INVALID_AMOUNT_ERROR = "INVALID_AMOUNT";
    private static final String INVALID_AMOUNT_DESCRIPTION = "credit amount cannot be lower than or equal to zero";

    private String createJsonResponse() {
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(INVALID_AMOUNT_ERROR, INVALID_AMOUNT_DESCRIPTION);
    }

    public Response toResponse(AccountInvalidAmountException _exception) {
        String serializedJson = createJsonResponse();
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

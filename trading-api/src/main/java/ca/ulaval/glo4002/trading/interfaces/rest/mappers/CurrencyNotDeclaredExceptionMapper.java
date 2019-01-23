package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.CurrencyNotDeclaredException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CurrencyNotDeclaredExceptionMapper implements ExceptionMapper<CurrencyNotDeclaredException> {
    private static final String UNDEFINED_CURRENCY_ERROR = "UNDEFINED_CURRENCY";
    private static final String UNDEFINED_CURRENCY_DESCRIPTION =
            "This account does not have credits for the currency %s";

    private String createJsonResponse(CurrencyNotDeclaredException _exception) {
        JsonResponse jsonResponse = new JsonResponse();
        String errorDescription = String.format(UNDEFINED_CURRENCY_DESCRIPTION, _exception.getCurrency());

        return jsonResponse.buildExceptionError(UNDEFINED_CURRENCY_ERROR, errorDescription);
    }

    public Response toResponse(CurrencyNotDeclaredException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

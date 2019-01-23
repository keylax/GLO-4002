package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.UnsupportedCurrencyException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UnsupportedCurrencyExceptionMapper implements ExceptionMapper<UnsupportedCurrencyException> {
    private static final String UNSUPPORTED_CURRENCY_ERROR = "UNSUPPORTED_CURRENCY";
    private static final String UNSUPPORTED_CURRENCY_DESCRIPTION = "The currency %s is unsupported, "
                                                                   + "must be USD, CHF or JPY";

    private String createJsonResponse(UnsupportedCurrencyException _exception) {
        JsonResponse jsonResponse = new JsonResponse();
        String errorDescription = String.format(UNSUPPORTED_CURRENCY_DESCRIPTION, _exception.getCurrency());

        return jsonResponse.buildExceptionError(UNSUPPORTED_CURRENCY_ERROR, errorDescription);
    }

    public Response toResponse(UnsupportedCurrencyException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

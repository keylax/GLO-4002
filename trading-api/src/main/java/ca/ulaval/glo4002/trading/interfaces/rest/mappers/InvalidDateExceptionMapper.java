package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.transactions.exceptions.InvalidDateException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidDateExceptionMapper implements ExceptionMapper<InvalidDateException> {
    private static final String INVALID_DATE_ERROR = "INVALID_DATE";
    private static final String INVALID_DATE_DESCRIPTION = "the transaction date is invalid";

    private String createJsonResponse() {
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(INVALID_DATE_ERROR, INVALID_DATE_DESCRIPTION);
    }

    public Response toResponse(InvalidDateException _exception) {
        String serializedJson = createJsonResponse();
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

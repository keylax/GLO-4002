package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.transactions.exceptions.InvalidQuantityException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidQuantityExceptionMapper implements ExceptionMapper<InvalidQuantityException> {
    private static final String INVALID_QUANTITY_ERROR = "INVALID_QUANTITY";
    private static final String INVALID_QUANTITY_DESCRIPTION = "quantity is invalid";

    private String createJsonResponse() {
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(INVALID_QUANTITY_ERROR, INVALID_QUANTITY_DESCRIPTION);
    }

    public Response toResponse(InvalidQuantityException _exception) {
        String serializedJson = createJsonResponse();
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

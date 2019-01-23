package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.exceptions.ClientAPIException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ClientAPIExceptionMapper implements ExceptionMapper<ClientAPIException> {
    private static final String ERROR_TYPE = "INTERNAL_ERROR";

    private String createJsonResponse(ClientAPIException _exception) {
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(ERROR_TYPE, _exception.getErrorDescription());
    }

    public Response toResponse(ClientAPIException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(serializedJson).build();
    }
}

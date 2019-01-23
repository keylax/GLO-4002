package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;
import ca.ulaval.glo4002.trading.interfaces.rest.transactions.exceptions.TransactionDtoInvalidException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TransactionDtoInvalidExceptionMapper implements ExceptionMapper<TransactionDtoInvalidException> {
    private static final String DTO_UNDEFINED_PARAMETER_ERROR = "UNDEFINED_PARAMETER";
    private static final String DTO_UNDEFINED_PARAMETER_DESCRIPTION = "all parameters must be declared";

    private String createJsonResponse() {
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(DTO_UNDEFINED_PARAMETER_ERROR, DTO_UNDEFINED_PARAMETER_DESCRIPTION);
    }

    public Response toResponse(TransactionDtoInvalidException _exception) {
        String serializedJson = createJsonResponse();
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

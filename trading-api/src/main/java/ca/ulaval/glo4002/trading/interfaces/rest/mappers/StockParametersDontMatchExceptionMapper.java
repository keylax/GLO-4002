package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.StockParametersDontMatchException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class StockParametersDontMatchExceptionMapper implements ExceptionMapper<StockParametersDontMatchException> {
    private static final String STOCK_PARAMETERS_DONT_MATCH_ERROR = "STOCK_PARAMETERS_DONT_MATCH";
    private static final String STOCK_PARAMETERS_DONT_MATCH_DESCRIPTION = "stock parameters don't match existing ones";

    private String createJsonResponse(StockParametersDontMatchException _exception) {
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildTransactionExceptionError(
                STOCK_PARAMETERS_DONT_MATCH_ERROR,
                STOCK_PARAMETERS_DONT_MATCH_DESCRIPTION,
                _exception.getTransactionNumber());
    }

    public Response toResponse(StockParametersDontMatchException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

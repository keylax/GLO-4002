package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.accounts.exceptions.NotEnoughStockException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotEnoughStockExceptionMapper implements ExceptionMapper<NotEnoughStockException> {
    public static final String NOT_ENOUGH_STOCK_ERROR = "NOT_ENOUGH_STOCK";
    public static final String NOT_ENOUGH_STOCK_DESCRIPTION = "not enough stock '%s:%s'";

    private String createJsonResponse(NotEnoughStockException _exception) {
        String notEnoughStockDescription = String.format(
                NOT_ENOUGH_STOCK_DESCRIPTION,
                _exception.getMarket(),
                _exception.getSymbol());
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(NOT_ENOUGH_STOCK_ERROR, notEnoughStockDescription);
    }

    public Response toResponse(NotEnoughStockException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

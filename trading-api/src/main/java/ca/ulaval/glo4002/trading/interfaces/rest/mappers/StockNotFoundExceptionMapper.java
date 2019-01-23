package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.exceptions.StockNotFoundException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class StockNotFoundExceptionMapper implements ExceptionMapper<StockNotFoundException> {
    private static final String STOCK_NOT_FOUND_ERROR = "STOCK_NOT_FOUND";
    private static final String STOCK_NOT_FOUND_DESCRIPTION = "stock '%s:%s' not found";

    private String createJsonResponse(StockNotFoundException _exception) {
        String stockNotFoundDescription = String.format(
                STOCK_NOT_FOUND_DESCRIPTION,
                _exception.getMarket(),
                _exception.getSymbol());
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildExceptionError(STOCK_NOT_FOUND_ERROR, stockNotFoundDescription);
    }

    public Response toResponse(StockNotFoundException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

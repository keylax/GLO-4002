package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.transactions.exceptions.MarketIsClosedException;
import ca.ulaval.glo4002.trading.interfaces.rest.mappers.response.JsonResponse;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MarketIsClosedExceptionMapper implements ExceptionMapper<MarketIsClosedException> {
    private static final String MARKET_CLOSED_ERROR = "MARKET_CLOSED";
    private static final String MARKET_CLOSED_DESCRIPTION = "market '%s' is closed";

    private String createJsonResponse(MarketIsClosedException _exception) {
        JsonResponse jsonResponse = new JsonResponse();
        return jsonResponse.buildTransactionExceptionError(
                MARKET_CLOSED_ERROR,
                String.format(MARKET_CLOSED_DESCRIPTION, _exception.getMarket()),
                _exception.getTransactionNumber());
    }

    public Response toResponse(MarketIsClosedException _exception) {
        String serializedJson = createJsonResponse(_exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(serializedJson).build();
    }
}

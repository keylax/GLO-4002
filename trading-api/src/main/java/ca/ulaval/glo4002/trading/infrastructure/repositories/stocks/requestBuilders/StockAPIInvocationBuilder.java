package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.requestBuilders;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class StockAPIInvocationBuilder {
    private static final String STOCKS_PATH = "stocks";
    private static final String MARKETS_PATH = "markets";

    public Builder getStockRequest(WebTarget _apiTarget, String _market, String _symbol) {
        return _apiTarget.path(STOCKS_PATH)
                         .path(_market)
                         .path(_symbol)
                         .request()
                         .accept(MediaType.APPLICATION_JSON);
    }

    public Builder getMarketRequest(WebTarget _apiTarget, String _market) {
        return _apiTarget.path(MARKETS_PATH)
                         .path(_market)
                         .request()
                         .accept(MediaType.APPLICATION_JSON);
    }
}

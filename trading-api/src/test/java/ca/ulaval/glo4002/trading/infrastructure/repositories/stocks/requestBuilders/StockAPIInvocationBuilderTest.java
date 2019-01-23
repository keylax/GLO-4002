package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.requestBuilders;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockAPIInvocationBuilderTest {
    private static final String STOCKS_PATH = "stocks";
    private static final String MARKETS_PATH = "markets";
    private static final String MEDIA_APPLICATION_JSON = "application/json";
    private static final String A_MARKET = "aMarket";
    private static final String A_SYMBOL = "aSymbol";

    @Mock
    private WebTarget apiTarget;

    @Mock
    private Builder expectedInvocation;

    @Mock
    private Builder requestBuilder;

    private StockAPIInvocationBuilder invocationBuilder;

    @Before
    public void setup() {
        invocationBuilder = new StockAPIInvocationBuilder();
        when(requestBuilder.accept(MEDIA_APPLICATION_JSON)).thenReturn(expectedInvocation);
    }

    @Test
    public void whenBuildingGetStockRequest_thenReturnExpectedInvocationRequest() {
        WebTarget symbolTarget = mock(WebTarget.class);
        WebTarget marketTarget = mock(WebTarget.class);
        WebTarget stocksTarget = mock(WebTarget.class);
        when(apiTarget.path(STOCKS_PATH)).thenReturn(stocksTarget);
        when(stocksTarget.path(A_MARKET)).thenReturn(marketTarget);
        when(marketTarget.path(A_SYMBOL)).thenReturn(symbolTarget);
        when(symbolTarget.request()).thenReturn(requestBuilder);

        Builder actualInvocation = invocationBuilder.getStockRequest(apiTarget, A_MARKET, A_SYMBOL);

        Assert.assertEquals(expectedInvocation, actualInvocation);
    }

    @Test
    public void whenBuildingGetMarketRequest_thenReturnExpectedInvocationRequest() {
        WebTarget symbolTarget = mock(WebTarget.class);
        WebTarget marketTarget = mock(WebTarget.class);
        when(apiTarget.path(MARKETS_PATH)).thenReturn(marketTarget);
        when(marketTarget.path(A_MARKET)).thenReturn(symbolTarget);
        when(symbolTarget.request()).thenReturn(requestBuilder);

        Builder actualInvocation = invocationBuilder.getMarketRequest(apiTarget, A_MARKET);

        Assert.assertEquals(expectedInvocation, actualInvocation);
    }
}

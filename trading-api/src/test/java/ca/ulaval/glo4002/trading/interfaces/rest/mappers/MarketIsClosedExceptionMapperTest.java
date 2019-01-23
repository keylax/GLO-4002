package ca.ulaval.glo4002.trading.interfaces.rest.mappers;

import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;
import ca.ulaval.glo4002.trading.domain.transactions.exceptions.MarketIsClosedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;


import javax.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class MarketIsClosedExceptionMapperTest {
    private static final int BAD_REQUEST_CODE = 400;
    private static final String MARKET_SYMBOL = "nasdaq";
    private static final String EXPECTED_ENTITY =
            "{\"transactionNumber\":\"%s\",\"description\":\"market '%s' is closed\",\"error\":\"MARKET_CLOSED\"}";

    @Mock
    private Market aMarket;

    @Before
    public void setup() {
        when(aMarket.getSymbol()).thenReturn(MARKET_SYMBOL);
    }

    @Test
    public void whenMappingAMarketIsClosedException_thenReturnExpectedResponse() {
        TransactionNumber aTransactionNumber = new TransactionNumber();
        String expectedString = String.format(EXPECTED_ENTITY, aTransactionNumber.toString(), aMarket.getSymbol());
        MarketIsClosedExceptionMapper mapper = new MarketIsClosedExceptionMapper();
        MarketIsClosedException exception = new MarketIsClosedException(aMarket, aTransactionNumber);

        Response mapperResponse = mapper.toResponse(exception);

        Assert.assertEquals(BAD_REQUEST_CODE, mapperResponse.getStatus());
        Assert.assertEquals(expectedString, mapperResponse.getEntity());
    }
}

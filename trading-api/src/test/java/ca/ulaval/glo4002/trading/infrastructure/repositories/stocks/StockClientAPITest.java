package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks;

import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.MarketDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.APIRequestExecutor;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.StockDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.assemblers.StockAssembler;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.configurations.ClientAPIInitializer;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.exceptions.ClientAPIException;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.exceptions.StockDtoInvalidException;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.exceptions.StockNotFoundException;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.requestBuilders.StockAPIInvocationBuilder;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.validators.StockDTOValidator;
import ca.ulaval.glo4002.trading.utilities.DateMapper;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockClientAPITest {
    private static final String AN_EXCEPTION_MESSAGE = "anException";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String API_URL = "http://localhost:8080";
    private static final String VALID_SYMBOL = "mySymbol";
    private static final String VALID_MARKET = "myMarket";
    private static final String A_SERIALIZED_DATE = "2018-07-08 08:00:00";

    private LocalDateTime aStockDate;
    private StockClientAPI client;
    private StockDTO aStockDTO;
    private MarketDTO aMarketDTO;

    @Mock
    private Market expectedMarket;

    @Mock
    private ClientAPIInitializer configuration;

    @Mock
    private APIRequestExecutor requestExecutor;

    @Mock
    private StockAPIInvocationBuilder invocationBuilder;

    @Mock
    private StockAssembler assembler;

    @Mock
    private WebTarget apiTarget;

    @Mock
    private Builder requestInvocation;

    @Mock
    private StockDTOValidator stockDTOValidator;

    @Mock
    private Stock expectedStock;

    @Before
    public void setup() throws ParseException {
        aMarketDTO = new MarketDTO();
        aStockDTO = new StockDTO();

        Date aDate = new SimpleDateFormat(DATE_FORMAT).parse(A_SERIALIZED_DATE);
        aStockDate = DateMapper.toDateTime(aDate);
        client = new StockClientAPI(configuration, requestExecutor, invocationBuilder, assembler, stockDTOValidator);

        when(configuration.generateClientWebTarget(API_URL)).thenReturn(apiTarget);
    }

    @Test
    public void whenFindingMarketFromAPI_thenReturnExpectedMarket() {
        when(stockDTOValidator.isMarketDTOValid(aMarketDTO)).thenReturn(true);
        when(invocationBuilder.getMarketRequest(apiTarget, VALID_MARKET)).thenReturn(requestInvocation);
        when(requestExecutor.executeGetRequest(requestInvocation, MarketDTO.class)).thenReturn(aMarketDTO);
        when(assembler.toDomainObject(aMarketDTO)).thenReturn(expectedMarket);

        Market actualMarket = client.findMarketBy(VALID_MARKET);

        Assert.assertEquals(expectedMarket, actualMarket);
    }

    @Test(expected = StockDtoInvalidException.class)
    public void whenFindingMarketFromAPIAndStockDTOIsInvalid_thenThrowBadRequestException() {
        when(stockDTOValidator.isMarketDTOValid(aMarketDTO)).thenReturn(false);

        client.findMarketBy(VALID_MARKET);
    }

    @Test
    public void whenFindingStockFromAPI_thenReturnExpectedStock() {
        when(stockDTOValidator.isStockDTOValid(aStockDTO)).thenReturn(true);
        when(invocationBuilder.getStockRequest(apiTarget, VALID_MARKET, VALID_SYMBOL)).thenReturn(requestInvocation);
        when(requestExecutor.executeGetRequest(requestInvocation, StockDTO.class)).thenReturn(aStockDTO);
        when(assembler.toDomainObject(aStockDTO, VALID_MARKET, aStockDate)).thenReturn(expectedStock);

        Stock actualStock = client.findStockBy(VALID_MARKET, VALID_SYMBOL, aStockDate);

        Assert.assertEquals(expectedStock, actualStock);
    }

    @Test(expected = StockNotFoundException.class)
    public void whenFindingStockFromAPIAndAPIRequestThrowAnException_thenThrowBadRequestException() {
        when(invocationBuilder.getStockRequest(apiTarget, VALID_MARKET, VALID_SYMBOL)).thenReturn(requestInvocation);
        when(requestExecutor.executeGetRequest(requestInvocation, StockDTO.class))
                .thenThrow(new ClientAPIException(AN_EXCEPTION_MESSAGE));

        client.findStockBy(VALID_MARKET, VALID_SYMBOL, aStockDate);
    }

    @Test(expected = StockDtoInvalidException.class)
    public void whenFindingStockFromAPIAndStockDTOIsInvalid_thenThrowBadRequestException() {
        when(stockDTOValidator.isStockDTOValid(aStockDTO)).thenReturn(false);

        client.findStockBy(VALID_MARKET, VALID_SYMBOL, aStockDate);
    }
}

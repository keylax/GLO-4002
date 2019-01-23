package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks;

import ca.ulaval.glo4002.trading.domain.stocks.Market;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.MarketDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.APIRequestExecutor;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.assemblers.StockAssembler;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.configurations.ClientAPIInitializer;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.DTOs.StockDTO;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.exceptions.ClientAPIException;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.exceptions.StockNotFoundException;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.exceptions.StockDtoInvalidException;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.requestBuilders.StockAPIInvocationBuilder;
import ca.ulaval.glo4002.trading.domain.stocks.Stock;
import ca.ulaval.glo4002.trading.domain.stocks.StockRepository;
import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.validators.StockDTOValidator;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import java.time.LocalDateTime;

public class StockClientAPI implements StockRepository {
    private static final String API_URL = "http://localhost:8080";

    private ClientAPIInitializer configuration;
    private APIRequestExecutor requestExecutor;
    private StockAPIInvocationBuilder invocationBuilder;
    private StockAssembler assembler;
    private StockDTOValidator stockDTOValidator;

    public StockClientAPI(ClientAPIInitializer _configuration,
                          APIRequestExecutor _requestExecutor,
                          StockAPIInvocationBuilder _invocationBuilder,
                          StockAssembler _assembler,
                          StockDTOValidator _stockDTOValidator) {
        configuration = _configuration;
        requestExecutor = _requestExecutor;
        invocationBuilder = _invocationBuilder;
        assembler = _assembler;
        stockDTOValidator = _stockDTOValidator;
    }

    public Stock findStockBy(String _market, String _symbol, LocalDateTime _date) {
        WebTarget apiTarget = configuration.generateClientWebTarget(API_URL);
        Builder invocation = invocationBuilder.getStockRequest(apiTarget, _market, _symbol);
        StockDTO stockDTO;

        try {
            stockDTO = requestExecutor.executeGetRequest(invocation, StockDTO.class);
        } catch (ClientAPIException _exception) {
            throw new StockNotFoundException(_market, _symbol);
        }

        if (!stockDTOValidator.isStockDTOValid(stockDTO)) {
            throw new StockDtoInvalidException();
        }
        
        return assembler.toDomainObject(stockDTO, _market, _date);
    }

    public Market findMarketBy(String _market) {
        WebTarget apiTarget = configuration.generateClientWebTarget(API_URL);
        Builder invocation = invocationBuilder.getMarketRequest(apiTarget, _market);
        MarketDTO marketDTO = requestExecutor.executeGetRequest(invocation, MarketDTO.class);

        if (!stockDTOValidator.isMarketDTOValid(marketDTO)) {
            throw new StockDtoInvalidException();
        }

        return assembler.toDomainObject(marketDTO);
    }
}

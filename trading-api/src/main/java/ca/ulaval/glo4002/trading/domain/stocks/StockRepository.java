package ca.ulaval.glo4002.trading.domain.stocks;

import java.time.LocalDateTime;

public interface StockRepository {
    Stock findStockBy(String _market, String _symbol, LocalDateTime _date);
    Market findMarketBy(String _market);
}

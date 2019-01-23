package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.exceptions;

public class StockNotFoundException extends RuntimeException {
    private String market;
    private String symbol;

    public StockNotFoundException(String _market, String _symbol) {
        market = _market;
        symbol = _symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getMarket() {
        return market;
    }
}

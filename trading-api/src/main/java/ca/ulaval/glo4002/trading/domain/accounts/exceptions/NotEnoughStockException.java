package ca.ulaval.glo4002.trading.domain.accounts.exceptions;

public class NotEnoughStockException extends RuntimeException {
    private String market;
    private String symbol;

    public NotEnoughStockException(String _market, String _symbol) {
        market = _market;
        symbol = _symbol;
    }

    public String getMarket() {
        return market;
    }

    public String getSymbol() {
        return symbol;
    }
}

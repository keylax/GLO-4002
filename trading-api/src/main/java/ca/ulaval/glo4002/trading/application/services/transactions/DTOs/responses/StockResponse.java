package ca.ulaval.glo4002.trading.application.services.transactions.DTOs.responses;

public class StockResponse {
    public final String market;
    public final String symbol;

    public StockResponse(String _market, String _symbol) {
        market = _market;
        symbol = _symbol;
    }

    public boolean equals(Object _other) {
        if (_other == null) {
            return false;
        }

        if (getClass() != _other.getClass()) {
            return false;
        }

        return market == ((StockResponse) _other).market && symbol.equals(((StockResponse) _other).symbol);
    }

    public int hashCode() {
        return super.hashCode();
    }
}

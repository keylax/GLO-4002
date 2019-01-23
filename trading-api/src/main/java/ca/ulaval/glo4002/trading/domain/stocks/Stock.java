package ca.ulaval.glo4002.trading.domain.stocks;

import java.math.BigDecimal;

public class Stock {
    private BigDecimal stockPrice;
    private String symbol;
    private String market;

    public Stock(String _symbol, String _market, BigDecimal _price) {
        symbol = _symbol;
        market = _market;
        stockPrice = _price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getMarketSymbol() {
        return market;
    }

    public BigDecimal getPrice() {
        return stockPrice;
    }
}

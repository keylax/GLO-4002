package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.assemblers;

import ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.exceptions.StockDateNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;

public class StockPriceFinder {
    public BigDecimal findPriceAtDate(List<StockPrice> _prices, LocalDateTime _date) {
        _prices.sort(StockPrice.dateComparator);

        if (isBeforeFirstKnownPrice(_prices, _date)) {
            throw new StockDateNotFoundException();
        }

        for (int i = 0; i < _prices.size(); i++) {
            StockPrice currentStockPrice = _prices.get(i);
            if (!currentStockPrice.isPriceInEffect(_date)) {
                StockPrice previousStockPrice = _prices.get(i - 1);
                return previousStockPrice.getPrice();
            }
        }

        StockPrice lastStockPrice = _prices.get(_prices.size() - 1);
        if (lastStockPrice.isPriceInEffectOnSameDay(_date)) {
            return lastStockPrice.getPrice();
        }

        throw new StockDateNotFoundException();
    }

    private boolean isBeforeFirstKnownPrice(List<StockPrice> _stockPrices, LocalDateTime _date) {
        if (_stockPrices.isEmpty()) {
            return true;
        }

        StockPrice firstKnownStockPrice = _stockPrices.get(0);
        return !firstKnownStockPrice.isPriceInEffect(_date);
    }
}

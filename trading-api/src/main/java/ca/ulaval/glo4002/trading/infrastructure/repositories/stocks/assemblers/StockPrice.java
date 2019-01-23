package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.assemblers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;

public class StockPrice {
    private LocalDateTime dateInEffect;
    private BigDecimal price;

    public StockPrice(LocalDateTime _dateInEffect, BigDecimal _price) {
        dateInEffect = _dateInEffect;
        price = _price;
    }

    public boolean isPriceInEffect(LocalDateTime _comparedDate) {
        return dateInEffect.isBefore(_comparedDate);
    }

    public boolean isPriceInEffectOnSameDay(LocalDateTime _comparedDate) {
        boolean sameDay = dateInEffect.getDayOfYear() == _comparedDate.getDayOfYear();
        boolean sameYear = dateInEffect.getYear() == _comparedDate.getYear();
        return sameDay && sameYear;
    }

    public LocalDateTime getDateInEffect() {
        return dateInEffect;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static Comparator<StockPrice> dateComparator = Comparator.comparing(_stockPrice -> _stockPrice.dateInEffect);
}

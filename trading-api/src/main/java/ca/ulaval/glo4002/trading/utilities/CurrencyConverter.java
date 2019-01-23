package ca.ulaval.glo4002.trading.utilities;

import java.math.BigDecimal;

public class CurrencyConverter {
    private static final BigDecimal USD_TO_CAD_EXCHANGE_RATE = BigDecimal.valueOf(1.31d);
    private static final BigDecimal CHF_TO_CAD_EXCHANGE_RATE = BigDecimal.valueOf(1.45d);
    private static final BigDecimal JPY_TO_CAD_EXCHANGE_RATE = BigDecimal.valueOf(0.01d);

    public static BigDecimal convertToCAD(BigDecimal _amount, String _currency) {
        if (_currency.equals("USD")) {
            return _amount.multiply(USD_TO_CAD_EXCHANGE_RATE);
        } else if (_currency.equals("CHF")) {
            return _amount.multiply(CHF_TO_CAD_EXCHANGE_RATE);
        } else if (_currency.equals("JPY")) {
            return _amount.multiply(JPY_TO_CAD_EXCHANGE_RATE);
        }

        throw new RuntimeException("Invalid currency");
    }
}

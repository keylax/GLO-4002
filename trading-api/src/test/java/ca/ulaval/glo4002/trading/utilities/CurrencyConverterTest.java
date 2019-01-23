package ca.ulaval.glo4002.trading.utilities;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CurrencyConverterTest {
    private static final BigDecimal USD_TO_CAD_EXCHANGE_RATE = BigDecimal.valueOf(1.31d);
    private static final BigDecimal CHF_TO_CAD_EXCHANGE_RATE = BigDecimal.valueOf(1.45d);
    private static final BigDecimal JPY_TO_CAD_EXCHANGE_RATE = BigDecimal.valueOf(0.01d);
    private static final String USD_CURRENCY = "USD";
    private static final String CHF_CURRENCY = "CHF";
    private static final String JPY_CURRENCY = "JPY";
    private static final BigDecimal AN_AMOUNT = BigDecimal.TEN;

    @Test
    public void givenAUSDAmount_whenConvertToCAD_thenReturnTheCorrectCADAmount() {
        BigDecimal actualConvertedAmount = CurrencyConverter.convertToCAD(AN_AMOUNT, USD_CURRENCY);

        BigDecimal expectedAmount = AN_AMOUNT.multiply(USD_TO_CAD_EXCHANGE_RATE);
        Assert.assertEquals(expectedAmount, actualConvertedAmount);
    }

    @Test
    public void givenACHFAmount_whenConvertToCAD_thenReturnTheCorrectCADAmount() {
        BigDecimal actualConvertedAmount = CurrencyConverter.convertToCAD(AN_AMOUNT, CHF_CURRENCY);

        BigDecimal expectedAmount = AN_AMOUNT.multiply(CHF_TO_CAD_EXCHANGE_RATE);
        Assert.assertEquals(expectedAmount, actualConvertedAmount);
    }

    @Test
    public void givenAJPYAmount_whenConvertToCAD_thenReturnTheCorrectCADAmount() {
        BigDecimal actualConvertedAmount = CurrencyConverter.convertToCAD(AN_AMOUNT, JPY_CURRENCY);

        BigDecimal expectedAmount = AN_AMOUNT.multiply(JPY_TO_CAD_EXCHANGE_RATE);
        Assert.assertEquals(expectedAmount, actualConvertedAmount);
    }
}

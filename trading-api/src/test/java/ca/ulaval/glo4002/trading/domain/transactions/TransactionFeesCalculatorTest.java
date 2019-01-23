package ca.ulaval.glo4002.trading.domain.transactions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TransactionFeesCalculatorTest {
    private static final BigDecimal A_STANDARD_TRANSACTION_PRICE = BigDecimal.TEN;
    private static final long A_STANDARD_QUANTITY = 4;
    private static final long A_GREAT_QUANTITY = 150;
    private static final long A_GREAT_TRANSACTION_PRICE = 5500;

    private TransactionFeesCalculator feesCalculator;

    @Before
    public void setup() {
        feesCalculator = new TransactionFeesCalculator();
    }

    @Test
    public void givenAStandardQuantityAndTransactionPrice_whenCalculatingFees_thenReturnFeesWithStandardQuantityRate() {
        BigDecimal actualFees = feesCalculator.calculateTransactionFees(
                A_STANDARD_QUANTITY,
                A_STANDARD_TRANSACTION_PRICE);

        BigDecimal feesWithStandardQuantityRate = BigDecimal.ONE;
        Assert.assertEquals(feesWithStandardQuantityRate, actualFees);
    }

    @Test
    public void givenAGreatQuantityAndAStandardTransactionPrice_whenCalculatingFees_thenReturnFeesWithGreatQuantityRate() {
        BigDecimal actualFees = feesCalculator.calculateTransactionFees(
                A_GREAT_QUANTITY,
                A_STANDARD_TRANSACTION_PRICE);

        BigDecimal feesWithStandardQuantityRate = new BigDecimal(30);
        Assert.assertEquals(feesWithStandardQuantityRate, actualFees);
    }

    @Test
    public void givenAStandardQuantityAndAGreatTransactionPrice_whenCalculatingFees_thenReturnFeesWithStandardQuantityRateAndGreatTransactionPriceRate() {
        BigDecimal actualFees = feesCalculator.calculateTransactionFees(
                A_STANDARD_QUANTITY,
                new BigDecimal(A_GREAT_TRANSACTION_PRICE));

        BigDecimal expectedTransactionPriceFees = new BigDecimal(165);
        BigDecimal feesWithStandardQuantityRate = expectedTransactionPriceFees.add(BigDecimal.ONE);
        Assert.assertEquals(feesWithStandardQuantityRate.floatValue(), actualFees.floatValue(), 0);
    }

    @Test
    public void givenAGreatQuantityAndAGreatTransactionPrice_whenCalculatingFees_thenReturnFeesWithGreatQuantityRateAndGreatTransactionPriceRate() {
        BigDecimal actualFees = feesCalculator.calculateTransactionFees(
                A_GREAT_QUANTITY,
                new BigDecimal(A_GREAT_TRANSACTION_PRICE));

        BigDecimal expectedTransactionPriceFees = new BigDecimal(165);
        BigDecimal expectedQuantityFees = new BigDecimal(30);
        BigDecimal feesWithStandardQuantityRate = expectedTransactionPriceFees.add(expectedQuantityFees);
        Assert.assertEquals(feesWithStandardQuantityRate.floatValue(), actualFees.floatValue(), 0);
    }
}

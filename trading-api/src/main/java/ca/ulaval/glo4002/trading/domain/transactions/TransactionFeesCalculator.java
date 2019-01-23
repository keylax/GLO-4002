package ca.ulaval.glo4002.trading.domain.transactions;

import java.math.BigDecimal;

public class TransactionFeesCalculator {
    private static final long MAXIMUM_QUANTITY_FOR_STANDARD_TRANSACTION = 100;
    private static final long MAXIMUM_PRICE_FOR_STANDARD_TRANSACTION = 5000;
    private static final float STANDARD_QUANTITY_TRANSACTION_FEES_RATE = 0.25f;
    private static final float GREAT_QUANTITY_TRANSACTION_FEES_RATE = 0.20f;
    private static final float EXPENSIVE_ORDER_TRANSACTION_FEES_RATE = 0.03f;
    private static final float STANDARD_PRICE_TRANSACTION_FEES_RATE = 0.00f;

    public BigDecimal calculateTransactionFees(long _stocksQuantity, BigDecimal _transactionPrice) {
        BigDecimal priceFees = calculatePriceFees(_transactionPrice);
        float calculatedQuantityFees = calculateQuantityFees(_stocksQuantity);
        BigDecimal quantityFees = new BigDecimal(calculatedQuantityFees);

        return priceFees.add(quantityFees);
    }

    private float calculateQuantityFees(long _stockQuantity) {
        float quantityFeesRate = (_stockQuantity <= MAXIMUM_QUANTITY_FOR_STANDARD_TRANSACTION)
                ? STANDARD_QUANTITY_TRANSACTION_FEES_RATE
                : GREAT_QUANTITY_TRANSACTION_FEES_RATE;

        return _stockQuantity * quantityFeesRate;
    }

    private BigDecimal calculatePriceFees(BigDecimal _transactionPrice) {
        BigDecimal maximumPriceStandardTransaction = new BigDecimal(MAXIMUM_PRICE_FOR_STANDARD_TRANSACTION);
        BigDecimal priceFeesRate = (_transactionPrice.compareTo(maximumPriceStandardTransaction) <= 0)
                ? new BigDecimal(STANDARD_PRICE_TRANSACTION_FEES_RATE)
                : new BigDecimal(EXPENSIVE_ORDER_TRANSACTION_FEES_RATE);

        return _transactionPrice.multiply(priceFeesRate);
    }
}

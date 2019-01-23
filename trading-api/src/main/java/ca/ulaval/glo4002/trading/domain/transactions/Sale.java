package ca.ulaval.glo4002.trading.domain.transactions;

import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;

import java.math.BigDecimal;

public class Sale {
    public static final TransactionType TRANSACTION_TYPE = TransactionType.SELL;

    private Transaction transaction;
    private TransactionFeesCalculator feesCalculator;

    public Sale(Transaction _transaction, TransactionFeesCalculator _feesCalculator) {
        transaction = _transaction;
        feesCalculator = _feesCalculator;
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal requestedAmount = transaction.calculateRequestedAmount();
        BigDecimal transactionFees = transaction.calculateFees(feesCalculator);
        return requestedAmount.subtract(transactionFees);
    }

    public void associateAccount(AccountNumber _accountNumber) {
        transaction.associateAccount(_accountNumber);
    }

    public Transaction getTransaction() {
        return transaction;
    }
}

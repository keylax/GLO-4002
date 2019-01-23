package ca.ulaval.glo4002.trading.domain.transactions;

import ca.ulaval.glo4002.trading.domain.accounts.AccountNumber;

import java.math.BigDecimal;

public class Purchase {
    public static final TransactionType TRANSACTION_TYPE = TransactionType.BUY;

    private Transaction transaction;
    private TransactionFeesCalculator feesCalculator;

    public Purchase(Transaction _transaction, TransactionFeesCalculator _feesCalculator) {
        transaction = _transaction;
        feesCalculator = _feesCalculator;
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal requestedAmount = transaction.calculateRequestedAmount();
        BigDecimal transactionFees = transaction.calculateFees(feesCalculator);
        return requestedAmount.add(transactionFees);
    }

    public void associateAccount(AccountNumber _accountNumber) {
        transaction.associateAccount(_accountNumber);
    }

    public Transaction getTransaction() {
        return transaction;
    }
}

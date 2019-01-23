package ca.ulaval.glo4002.trading.infrastructure.repositories.transactions.exceptions;

import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;

public class TransactionNotFoundException extends RuntimeException {
    private TransactionNumber transactionNumber;

    public TransactionNotFoundException(TransactionNumber _transactionNumber) {
        transactionNumber = _transactionNumber;
    }

    public String getTransactionNumber() {
        return transactionNumber.toString();
    }
}

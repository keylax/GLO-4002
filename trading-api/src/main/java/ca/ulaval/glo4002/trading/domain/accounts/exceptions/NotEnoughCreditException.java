package ca.ulaval.glo4002.trading.domain.accounts.exceptions;

import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;


public class NotEnoughCreditException extends RuntimeException {
    private TransactionNumber transactionNumber;

    public NotEnoughCreditException(TransactionNumber _transactionNumber) {
        transactionNumber = _transactionNumber;
    }

    public String getTransactionNumber() {
        return transactionNumber.toString();
    }
}

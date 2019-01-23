package ca.ulaval.glo4002.trading.domain.accounts.exceptions;

import ca.ulaval.glo4002.trading.domain.transactions.TransactionNumber;

public class StockParametersDontMatchException extends RuntimeException {
    private TransactionNumber transactionNumber;

    public StockParametersDontMatchException(TransactionNumber _transactionNumber) {
        transactionNumber = _transactionNumber;
    }

    public String getTransactionNumber() {
        return transactionNumber.toString();
    }
}

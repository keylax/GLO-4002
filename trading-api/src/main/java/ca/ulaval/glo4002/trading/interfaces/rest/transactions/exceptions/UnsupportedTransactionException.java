package ca.ulaval.glo4002.trading.interfaces.rest.transactions.exceptions;

public class UnsupportedTransactionException extends RuntimeException {
    private String transactionType;
    public UnsupportedTransactionException(String _transactionType) {
        transactionType = _transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }
}

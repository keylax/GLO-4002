package ca.ulaval.glo4002.trading.application.services.transactions.exceptions;

public class TransactionReportInvalidDateException extends RuntimeException {
    private String serializedDate;

    public TransactionReportInvalidDateException(String _serializedDate) {
        serializedDate = _serializedDate;
    }

    public String getSerializedDate() {
        return serializedDate;
    }
}

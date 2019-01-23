package ca.ulaval.glo4002.trading.infrastructure.repositories.stocks.common.exceptions;

public class ClientAPIException extends RuntimeException {
    private String errorDescription;

    public ClientAPIException(String _errorDescription) {
        errorDescription = _errorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}

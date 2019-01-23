package ca.ulaval.glo4002.trading.domain.accounts.exceptions;

public class UnsupportedCurrencyException extends RuntimeException {
    private String currency;

    public UnsupportedCurrencyException(String _currency) {
        currency = _currency;
    }

    public String getCurrency() {
        return currency;
    }
}
